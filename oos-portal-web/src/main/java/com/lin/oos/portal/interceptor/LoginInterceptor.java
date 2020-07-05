package com.lin.oos.portal.interceptor;

import com.alibaba.fastjson.JSON;
import com.lin.oos.pojo.UmsMember;
import com.lin.oos.vo.OosResult;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class LoginInterceptor implements HandlerInterceptor {

    @Value("${SSO_BASE_URL}")
    private  String SSO_BASE_URL;

    /**
     * 思路：获得Cookie里面的TOKEN，如果登录了就直接运行执行操作，如果没有登录，就跳到单点登录系统
     * Controller的映射方法执行之前拦截
     * @param request
     * @param response
     * @param handler
     * @return
     * @throws Exception
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {





        //第一步：获得Cookie
        Cookie[] cookies = request.getCookies();


        for (Cookie cookie : cookies) {
            if (cookie.getName().equals("OOS_USER_TOKEN")){
                String token = cookie.getValue();

                RestTemplate restTemplate = new RestTemplate();

                String urlToken = SSO_BASE_URL + "/token/" + token;

                OosResult oosResult = restTemplate.getForObject(urlToken, OosResult.class);

                if (oosResult.getStatus() == 400) {
                    break;
                }


                UmsMember umsMember = JSON.parseObject(JSON.toJSONString(oosResult.getData()), UmsMember.class);


                request.setAttribute("loginUser",umsMember );


                return  true;
            }
        }

        //如果登录失败，跳转到登录
        String url = SSO_BASE_URL+"/login.html";
        response.sendRedirect(url);

        //注意：返回的使用false，表示后面的方法都不执行，包括Controller的方法了。
        return false;
    }

    /**
     * Controller的映射方法正常执行之后拦截
     * 注意：方法如果处理异常，永远不会执行了
     * @param request
     * @param response
     * @param handler
     * @param modelAndView
     * @throws Exception
     */
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    /**
     * Controller的映射方法执行完成之后拦截
     * 注意：该方法不管报不报异常最后还是会执行的
     * @param request
     * @param response
     * @param handler
     * @param ex
     * @throws Exception
     */
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
