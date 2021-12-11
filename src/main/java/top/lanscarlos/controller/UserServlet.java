package top.lanscarlos.controller;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonPrimitive;
import org.apache.ibatis.session.SqlSession;
import top.lanscarlos.dao.UserDAO;
import top.lanscarlos.pojo.User;
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
import java.util.UUID;

@WebServlet(name = "UserServlet", value = "/user")
public class UserServlet extends HttpServlet {
    static Gson gson = new Gson();

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

        User user; // 实例对象
        String username; // 用户名
        String password; // 密码

        switch (request.getParameter("method")) {
            case "login": // 用户登录操作
                username = request.getParameter("username"); // 获取用户名
                password = request.getParameter("password"); // 获取密码
                user = dao.getUserByName(username); // 查询实例对象

                // 判断用户是否存在 以及 密码是否正确
                if (user != null && user.getPassword().equals(password)) {
                    json.addProperty("result", true);
                    json.addProperty("message", "登录成功！");

                    // 存储登录信息
                    request.getSession().setAttribute("uid", user.getUid());
                }else {
                    json.addProperty("result", false);
                    json.addProperty("message", "用户名或密码错误！");
                }
                break;
            case "logout": // 用户退出登录
                json.addProperty("result", true);
                json.addProperty("message", "成功登出");

                // 移除登录信息
                request.getSession().removeAttribute("uid");
                break;
            case "check": // 检查用户名
                username = request.getParameter("username");
                if (username == null || username.isEmpty()) { // 未输入用户名
                    json.addProperty("result", false);
                    json.addProperty("message", "请输入用户名");
                }else {
                    user = dao.getUserByName(username); // 查询实例对象
                    if (user == null) {
                        json.addProperty("result", true);
                        json.addProperty("message", "用户名可以使用");
                    }else {
                        json.addProperty("result", false);
                        json.addProperty("message", "用户名已被占用");
                    }
                }
                break;
            case "register": // 用户注册
                username = request.getParameter("username"); // 获取用户名
                password = request.getParameter("password"); // 获取密码
                String gender = request.getParameter("gender");
                String address = request.getParameter("address");
                String reg_time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()); // 获取当前时间为注册时间

                user = new User();
                user.setUid(UUID.randomUUID().toString());
                user.setName(username);
                user.setPassword(password);
                user.setGender(gender != null ? gender : "保密");
                user.setAddress(address);
                user.setReg_time(reg_time);

                if(dao.addUser(user) > 0) {
                    json.addProperty("result", true);
                    json.addProperty("message", "注册成功！");
                }else {
                    json.addProperty("result", false);
                    json.addProperty("message", "注册失败！请稍后再试！");
                }
                sqlSession.commit();
                break;
            case "info": // 查询用户信息 [登录的前提下]
                String uid = request.getParameter("uid");
                if ("".equals(uid)) {
                    uid = request.getSession().getAttribute("uid").toString();
                }
                user = dao.getUserById(uid);
                if (user != null) {
                    json.addProperty("result", true);
                    json.addProperty("message", "查询成功！");
                    json.add("user", gson.toJsonTree(user));
                }else {
                    // 用户不存在
                    json.addProperty("result", false);
                    json.addProperty("message", "用户不存在！");
                }
                break;
            case "update":
                uid = request.getParameter("uid");
                if ("".equals(uid)) {
                    uid = request.getSession().getAttribute("uid").toString();
                }
                user = dao.getUserById(uid);
                if (user != null) {
                    // 刷新数据
                    user.setGender(request.getParameter("gender"));
                    user.setAddress(request.getParameter("address"));

                    if (dao.updateUser(user) > 0) {
                        json.addProperty("result", true);
                        json.addProperty("message", "保存成功！");
                        sqlSession.commit();
                    }else {
                        json.addProperty("result", false);
                        json.addProperty("message", "保存失败！");
                        sqlSession.rollback();
                    }
                    json.add("user", gson.toJsonTree(user));

                }else {
                    // 用户不存在
                    json.addProperty("result", false);
                    json.addProperty("message", "用户不存在！");
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
    }
}
