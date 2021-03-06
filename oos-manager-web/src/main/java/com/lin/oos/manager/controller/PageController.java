package com.lin.oos.manager.controller;


import com.alibaba.dubbo.config.annotation.Reference;
import com.lin.oos.service.PmsAdminService;
import com.lin.oos.vo.OosResult;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;

@Controller
public class PageController {

    @Reference
    private PmsAdminService pmsAdminService;


    @RequestMapping("/{page}.html")
    public String showIndex(@PathVariable String page){

        return page;
    }


    @PostMapping("/login.do")
    public String login(Model model,String username, String password, String checkVerifyCode, String verifyCode, HttpSession httpSession){

        model.addAttribute("oosResult", OosResult.build(400, "验证码错误"));
        if (checkVerifyCode.equalsIgnoreCase(verifyCode)) {
            OosResult oosResult = pmsAdminService.selectByRecord(username, password);

            if (oosResult.getStatus()==400) {
                model.addAttribute("oosResult",oosResult);
            }else {

                httpSession.setAttribute("pmsAdmin",oosResult.getData());
                return "redirect:/index.html";
            }


        }


        return "login";
    }


    @RequestMapping("/logout.do")
    public String logout(HttpSession httpSession){

        httpSession.invalidate();

        return "redirect:/login.html";
    }










}
