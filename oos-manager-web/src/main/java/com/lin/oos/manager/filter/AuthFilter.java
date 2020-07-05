package com.lin.oos.manager.filter;

import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


@Component
@WebFilter(filterName = "authFilter", urlPatterns = "/*")
public class AuthFilter implements Filter {

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {


        HttpServletRequest request = (HttpServletRequest)servletRequest;
        HttpServletResponse response = (HttpServletResponse)servletResponse;
        HttpSession session = request.getSession();

        String url=request.getRequestURI();


        //放行静态资源
        if(url.endsWith(".css")||url.endsWith(".js")||url.endsWith(".jpg")
                ||url.endsWith(".gif")||url.endsWith(".png")||url.endsWith(".woff")||url.endsWith(".ttf")){
            filterChain.doFilter(servletRequest,servletResponse);
            return;
        }


        String uri = request.getRequestURI();
        //是否需要过滤
        boolean needFilter = true;
        //不需要登录就可以访问的路径(比如:注册登录等)
        List<String> list = new ArrayList<String>(Arrays.asList("/login.html","/login.do","/logout.do"));
        if(list.contains(uri)){
            needFilter = false;
        }

        if (!needFilter) {
            filterChain.doFilter(servletRequest, servletResponse);
        }else {
            if (session.getAttribute("pmsAdmin") != null) {
                filterChain.doFilter(servletRequest, servletResponse);
            } else {
                response.sendRedirect("/login.html");
            }
        }
    }
}
