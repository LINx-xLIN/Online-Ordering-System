package com.lin.oos.portal.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import com.lin.oos.pojo.UmsMember;
import com.lin.oos.service.PmsProductItemService;
import com.lin.oos.vo.OosResult;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

@Controller
public class PageController {

    @Reference
    private PmsProductItemService pmsProductItemService;

    @Value("${SSO_BASE_URL}")
    private String SSO_BASE_URL;

    @RequestMapping("/{page}.html")
    public String index(@PathVariable String page , HttpServletRequest request){


        getLoginUser(page,request);

        return page;
    }

    @RequestMapping("/")
    public String index(HttpServletRequest request){

        getLoginUser("/", request);


        return "index";
    }



    private void getLoginUser(String page,HttpServletRequest request){

        if (page.equals("index")||page.equals("/")) {
            //第一步：获得Cookie
            Cookie[] cookies = request.getCookies();

            if (cookies != null) {
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
                        break;

                    }
                }
            }

        }
    }
}
