package top.lanscarlos.controller;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import org.apache.ibatis.session.SqlSession;
import top.lanscarlos.dao.CartDAO;
import top.lanscarlos.dao.GoodDAO;
import top.lanscarlos.pojo.Cart;
import top.lanscarlos.pojo.Good;
import top.lanscarlos.utils.MybatisUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@WebServlet(name = "CartServlet",urlPatterns = "/cart")
public class CartServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.getWriter().println("not allow to get request!");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json;charset=utf-8");
        SqlSession sqlSession = MybatisUtils.getSqlSession();
        CartDAO cdao = sqlSession.getMapper(CartDAO.class);
        GoodDAO gdao = sqlSession.getMapper(GoodDAO.class);
        /**
         * 1.由uid获取所有的gid
         * 2.gid进行获取List<cart>和List <good>表的所需信息
         * 查询商品加入购物车的信息gid和uid
         * gids=[,,]
         * good=[{"":""},{"":""}]
         * cart=[{"":""},{"":""}]
         * 获取uid的所有的gid,进行遍历将对应的商品信息和由gid获得amount、add_time等信息分成两个进行发送。
         * uid--->gid--->遍历--->商品信息：name/price/description/image--->在gid的good中
         */
        String uid = request.getParameter("uid");
        if("".equals(uid)){
            uid = String.valueOf(request.getSession().getAttribute("user"));
        }
        String add_time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());

        JsonObject json = new JsonObject();
        String method = request.getParameter("method");
        if (method != "" && method !=null){
            switch (method){
                case "selectInfo":
                    /*查询信息*/
                    List<Cart> carts = cdao.selectByUid(uid);
//                    List<String> list = new ArrayList<String>();
                    List<Good> goodList = new ArrayList<Good>();
                    for (Cart cart : carts) {
                        String cartGid = cart.getGid();
                        Good goodInfo = gdao.selectByGid(cartGid);
                        goodList.add(goodInfo);
                    }
//                    }
                    /*cart的用户信息*/
                    json.addProperty("carts",new Gson().toJson(carts));
                    /*用户购买的在good表对应的信息*/
                    json.addProperty("goods",new Gson().toJson(goodList));
                    json.addProperty("result",true);
                    break;
                case "deleteByIds":
                    /*批量删除*/
//                    for(Map.Entry<String, String[]> entry : request.getParameterMap().entrySet()){
//                        for(String s : entry.getValue()){
//                            System.out.println("key-> "+entry.getKey() + " value-> "+s);
//                        }
//                    }
//                    System.out.println("xxx1 -> " + request.getParameter("gids[]"));
//                    System.out.println("xxx2 -> " + request.getParameterValues("gids[]"));
                    String[] gids = request.getParameterValues("gids[]");
                    int rows = cdao.deleteByIds(uid,gids);
                    if (rows>0){
                            //删除成功
                        json.addProperty("result",true);
                        sqlSession.commit();
                    }else {
                            //删除失败
                        json.addProperty("result",false);
                        sqlSession.rollback();
                    }
                    break;
                case "increaseInfo":
                    int number = Integer.parseInt(request.getParameter("number"));
                    String gid = request.getParameter("gid");
                    //判断插入还是添加
                    Cart isempty = cdao.isEmpty(gid);
                    Cart cart = new Cart();
                    cart.setUid(uid);
                    cart.setGid(gid);
                    cart.setAdd_time(add_time);
                    if (isempty!=null){
                        int amount = cdao.selectAmountByGid(gid).getAmount();
                        int newAmount = amount+number;
                        cart.setAmount(newAmount);
                        cdao.updateById(cart);
                        json.addProperty("result",true);
                        sqlSession.commit();
                    }else {
                        cart.setAmount(number);
                        int i = cdao.insertById(cart);
                        if (i>0){
                            json.addProperty("result",true);
                            sqlSession.commit();
                        }else {
                            json.addProperty("result",false);
                            sqlSession.rollback();
                        }
                    }
                    break;
                default:
                    json.addProperty("result",false);
                    break;
            }
        }

        /**
         * 存入+关闭mybatis
         */
        sqlSession.close();
        response.getWriter().println(json);
    }

}
