package top.lanscarlos.controller;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import org.apache.ibatis.session.SqlSession;
import top.lanscarlos.dao.GoodDAO;
import top.lanscarlos.dao.UserDAO;
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

        String method = request.getParameter("method");
        if (method !="" && method !=null ){
            switch (method){
                case "selectAll":
                    List<Good> goods = dao.selectAll();
                    json.addProperty("goods",new  Gson().toJson(goods));
                    json.addProperty("result",true);
                    break;
                case "selectByGid":
                    Good byGid = dao.selectByGid(gid);
                    json.addProperty("good",new Gson().toJson(byGid));
                    json.addProperty("result",true);
                    break;
                case "selectByName":
                    List<Good> byName = dao.selectByName(name);
                    json.addProperty("goods",new Gson().toJson(byName));
                    json.addProperty("result",true);
                    break;
                case "selectByCategory":
                    List<Good> byCategory = dao.selectByCategory(category);
                    json.addProperty("goods",new Gson().toJson(byCategory));
                    json.addProperty("result",true);
                    break;
                case "selectByCondition":
                    List<Good> byCondition = dao.selectByCondition(gid, name, category);
                    json.addProperty("goods",new Gson().toJson(byCondition));
                    json.addProperty("result",true);
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
