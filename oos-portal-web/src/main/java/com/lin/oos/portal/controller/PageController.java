package com.lin.oos.portal.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.lin.oos.service.PmsProductItemService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;

@Controller
public class PageController {

    @Reference
    private PmsProductItemService pmsProductItemService;

    @RequestMapping("/{page}.html")
    public String index(@PathVariable String page){




        return page;
    }


}
