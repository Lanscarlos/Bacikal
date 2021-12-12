package top.lanscarlos.controller;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import org.apache.ibatis.session.SqlSession;
import top.lanscarlos.dao.GoodDAO;
import top.lanscarlos.dao.PurchaseDAO;
import top.lanscarlos.dao.UserDAO;
import top.lanscarlos.pojo.*;
import top.lanscarlos.utils.MybatisUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

@WebServlet(name = "PurchaseServlet", value = "/purchase")
public class PurchaseServlet extends HttpServlet {
    static Gson gson = new Gson();
    static HashMap<String, String> shop = new HashMap<>(); // 存储商家信息
    static HashMap<String, String> category = new HashMap<>(); // 存储分类信息

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.getWriter().println("not allow to get request!");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        response.setContentType("application/json;charset=utf-8");
        SqlSession sqlSession = MybatisUtils.getSqlSession();

        try {

            PurchaseDAO dao = sqlSession.getMapper(PurchaseDAO.class);
            JsonObject json = new JsonObject();

            switch (request.getParameter("method")) {
                case "select-all": // 获取所有订单
                    String uid = request.getParameter("uid");
                    if (uid == null || uid.isEmpty()) {
                        uid = request.getSession().getAttribute("uid").toString();
                    }
                    List<Purchase> purchases = dao.getAllPurchase(uid);
                    List<Good> goods = new ArrayList<>();
                    if (!purchases.isEmpty()) {
                        GoodDAO goodDAO = sqlSession.getMapper(GoodDAO.class);
                        if (shop.isEmpty()) {
                            for (Shop shop : goodDAO.getAllShop()) {
                                PurchaseServlet.shop.put(shop.getSid(), shop.getName());
                            }
                        }
                        if (category.isEmpty()) {
                            for (Category category : goodDAO.getAllCategory()) {
                                PurchaseServlet.category.put(category.getCid(), category.getName());
                            }
                        }
                        for(Purchase purchase : purchases) {
                            Good good = goodDAO.selectByGid(purchase.getGid());
                            good.setShop(PurchaseServlet.shop.get(good.getSid()));
                            good.setCategory(PurchaseServlet.category.get(good.getCid()));
                            goods.add(good);
                        }
                    }
                    json.addProperty("result", true);
                    json.addProperty("message", "获取成功");
                    json.add("purchases", gson.toJsonTree(purchases));
                    json.add("goods", gson.toJsonTree(goods));
                    break;
                case "select": // 获取指定订单
                    String pid = request.getParameter("pid");
                    Purchase purchase = dao.getPurchase(pid);
                    if (purchase != null) {
                        json.addProperty("result", true);
                        json.addProperty("message", "获取成功");
                        json.add("purchase", gson.toJsonTree(purchase));
                    }else {
                        json.addProperty("result", false);
                        json.addProperty("message", "订单不存在");
                    }
                    break;
                case "add": // 添加订单
                    pid = UUID.randomUUID().toString();
                    uid = request.getParameter("uid");
                    if (uid == null || uid.isEmpty()) {
                        uid = request.getSession().getAttribute("uid").toString();
                    }
                    String gid = request.getParameter("gid");
                    String time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()); // 获取当前时间为注册时间
                    int price = Integer.parseInt(request.getParameter("price"));
                    int amount = Integer.parseInt(request.getParameter("amount"));
                    int sum = price * amount;

                    purchase = new Purchase(pid, uid, gid, time, price, amount, sum);
                    if (dao.addPurchase(purchase) > 0) {
                        json.addProperty("result", true);
                        json.addProperty("message", "添加成功");
                        json.add("purchase", gson.toJsonTree(purchase));
                        sqlSession.commit();
                    }else {
                        json.addProperty("result", false);
                        json.addProperty("message", "添加失败");
                        sqlSession.rollback();
                    }
                    break;
                case "delete":
                    pid = request.getParameter("pid");
                    if (dao.deletePurchase(pid) > 0) {
                        sqlSession.commit();
                        json.addProperty("result", true);
                        json.addProperty("message", "删除成功");
                    }else {
                        json.addProperty("result", false);
                        json.addProperty("message", "删除失败");
                    }
                    break;
                default:
                    // 未知的请求
                    json.addProperty("result", false);
                    json.addProperty("message", "未知的请求");
                    break;
            }

            sqlSession.close();
            response.getWriter().println(json);
        } catch (Exception e) {
            sqlSession.close();
        }

    }
}
