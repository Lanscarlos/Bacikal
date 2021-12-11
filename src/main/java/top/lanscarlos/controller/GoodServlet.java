package top.lanscarlos.controller;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import org.apache.ibatis.session.SqlSession;
import top.lanscarlos.dao.GoodDAO;
import top.lanscarlos.pojo.Good;
import top.lanscarlos.utils.MybatisUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * @author 10511
 */
@WebServlet(name = "GoodServlet",urlPatterns = "/good")
public class GoodServlet extends HttpServlet {

    static Gson gson = new Gson();

//    String gid="0001";
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.getWriter().println("not allow to get request!");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json;charset=utf-8");
        SqlSession sqlSession = MybatisUtils.getSqlSession();
        GoodDAO dao = sqlSession.getMapper(GoodDAO.class);
        //查询
        String name="%"+request.getParameter("name")+"%";
        String gid = request.getParameter("gid");
        String category = request.getParameter("category");
//        List<Good> goods=dao.selectByCondition(request.getParameter("gid"),name,request.getParameter("category"));
//        List<Good> goods=dao.selectAll();
//        for (int i = 0; i <goods.size() ; i++) {
//            System.out.println(goods.get(i));
//        }
        JsonObject json=new JsonObject();
//        json.addProperty("goods",new Gson().toJson(goods));

        switch (request.getParameter("method")){
            case "selectAll":
                List<Good> goods = dao.selectAll();
                json.addProperty("result",true);
                json.add("goods", gson.toJsonTree(goods));
                break;
            case "selectByGid":
                Good byGid = dao.selectByGid(gid);
                json.addProperty("result",true);
                json.add("good", gson.toJsonTree(byGid));
                break;
            case "selectByName":
                List<Good> byName = dao.selectByName(name);
                json.addProperty("result",true);
                json.add("goods", gson.toJsonTree(byName));
                break;
            case "selectByCategory":
                List<Good> byCategory = dao.selectByCategory(category);
                json.addProperty("result",true);
                json.add("goods", gson.toJsonTree(byCategory));
                break;
            case "selectByCondition":
                List<Good> byCondition = dao.selectByCondition(gid, name, category);
                json.addProperty("result",true);
                json.add("goods", gson.toJsonTree(byCondition));
                break;
            case "update":
                Good good = dao.selectByGid(gid);
                if (good != null) {

                    int stock = Integer.parseInt(request.getParameter("stock"));
                    good.setStock(stock);
                    dao.update(good);
                    sqlSession.commit();

                    json.addProperty("result",true);
                    json.addProperty("message","更新成功");
                    json.add("good", gson.toJsonTree(good));
                }else {
                    json.addProperty("result",false);
                    json.addProperty("message","更新失败");
                }
                break;
            default:
                json.addProperty("result",false);
                json.addProperty("message","未知的请求");
                break;
        }

        /**
         * 存入+关闭mybatis
         */
        sqlSession.close();
        response.getWriter().println(json);
    }
}
