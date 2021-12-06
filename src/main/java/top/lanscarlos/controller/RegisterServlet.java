package top.lanscarlos.controller;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
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
import java.util.Date;
import java.util.UUID;

@WebServlet(name = "RegisterServlet", value = "/register")
public class RegisterServlet extends HttpServlet {

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
        if ("check-user".equalsIgnoreCase(method)) {
            String name = request.getParameter("username");
            if (name == null || name.isEmpty()) {
                json.addProperty("result", false);
                json.addProperty("message", "请输入用户名");
            }else {
                User user = dao.getUserByName(name);
                if (user == null) {
                    json.addProperty("result", true);
                    json.addProperty("message", "用户名可以使用");
                }else {
                    json.addProperty("result", false);
                    json.addProperty("message", "用户名已被占用");
                }
            }
        }else if ("register-user".equalsIgnoreCase(method)) {
            String gender = request.getParameter("gender");
            String address = request.getParameter("address");
            String reg_time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());

            User user = new User();
            user.setUid(UUID.randomUUID().toString());
            user.setName(request.getParameter("username"));
            user.setPassword(request.getParameter("password"));
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
        }

        sqlSession.close();
        response.getWriter().println(json);

//        if (success) {
//            request.getRequestDispatcher("login.html").forward(request, response);
//        }
    }
}
