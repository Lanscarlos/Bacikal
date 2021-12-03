package top.lanscarlos.controller;

import com.google.gson.JsonObject;
import org.apache.ibatis.session.SqlSession;
import top.lanscarlos.dao.UserDAO;
import top.lanscarlos.pojo.User;
import top.lanscarlos.utils.MybatisUtils;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet(name = "LoginServlet", value = "/login")
public class LoginServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.getWriter().println("not allow to get request!");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        response.setContentType("application/json;charset=utf-8");
        SqlSession sqlSession = MybatisUtils.getSqlSession();
        UserDAO dao = sqlSession.getMapper(UserDAO.class);

        User user = dao.getUserByName(request.getParameter("username"));
        JsonObject json = new JsonObject();
        if (user != null && user.getPassword().equals(request.getParameter("password"))) {
            json.addProperty("result", true);
            json.addProperty("message", "登录成功");
            request.getSession().setAttribute("login", true);
        }else {
            json.addProperty("result", false);
            json.addProperty("message", "用户名或密码错误");
        }

        sqlSession.close();
        response.getWriter().println(json);
    }
}
