package com.lin.oos.sso.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class PageController {


    @RequestMapping("/{page}.html")
    public String login(@PathVariable String page){

        return page;
    }

}
