package top.lanscarlos.controller;

import javax.servlet.*;
import javax.servlet.annotation.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

@WebFilter(filterName = "VerifyFilter", urlPatterns = "/*")
public class VerifyFilter implements Filter {
    public void init(FilterConfig config) throws ServletException {
    }

    public void destroy() {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws ServletException, IOException {
        HttpServletResponse res = (HttpServletResponse) response;
        res.addHeader("Access-Control-Allow-Credentials", "true");
        res.addHeader("Access-Control-Allow-Origin", "*");
        res.addHeader("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT");
        res.setHeader("Access-Control-Max-Age", "3600");
        res.setHeader("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept");
        res.addHeader("Access-Control-Allow-Headers", "Content-Type,X-CAF-Authorization-Token,sessionToken,X-TOKEN");
        // CROS 复杂请求时会首先发送一个 OPTIONS 请求做嗅探，来测试服务器是否支持本次请求，请求成功后才会发送真实的请求；因此放行OPTIONS请求
        if (((HttpServletRequest) request).getMethod().equals("OPTIONS")) {
            res.getWriter().println("ok");
            return;
        }

//        String url = ((HttpServletRequest) request).getRequestURL().toString();
//        if(url.contains("/js/") || url.contains("/svg/") || url.contains("login") || url.contains("register")) {
//            chain.doFilter(request, response);
//            return;
//        }
//
//        Object obj = ((HttpServletRequest) request).getSession().getAttribute("uid");
//        if (obj == null) {
//            // 未登录
//            request.getRequestDispatcher("login.html").forward(request, response);
//        }else {
            // 已登录
            chain.doFilter(request, response);
//        }
    }
}
