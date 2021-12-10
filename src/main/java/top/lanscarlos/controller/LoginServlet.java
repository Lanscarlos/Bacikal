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

/**
 * 用户相关操作全部转移至 UserServlet
 * */
@Deprecated
//@WebServlet(name = "LoginServlet", value = "/login")
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
        JsonObject json = new JsonObject();

        String method = request.getParameter("method");
        if ("user-logout".equalsIgnoreCase(method)) {
            // 用户退出登录
            json.addProperty("result", true);
            json.addProperty("message", "成功登出");

            // 移除登录信息
            request.getSession().removeAttribute("uid");
        }else if("user-login".equalsIgnoreCase(method)) {
            // 用户尝试登录
            User user = dao.getUserByName(request.getParameter("username"));

            if (user != null && user.getPassword().equals(request.getParameter("password"))) {
                json.addProperty("result", true);
                json.addProperty("message", "登录成功！");

                // 存储登录信息
                request.getSession().setAttribute("uid", user.getUid());
            }else {
                json.addProperty("result", false);
                json.addProperty("message", "用户名或密码错误！");
            }

            // 断开数据库连接
            sqlSession.close();
        }else {
            // 未知的请求
            json.addProperty("result", false);
            json.addProperty("message", "未知的请求");
        }

        response.getWriter().println(json);
    }
}
