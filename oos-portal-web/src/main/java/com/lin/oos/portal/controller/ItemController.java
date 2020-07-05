package com.lin.oos.portal.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.lin.oos.service.PmsProductItemService;
import com.lin.oos.service.SearchService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/item")
public class ItemController {

    @Reference
    private PmsProductItemService pmsProductItemService;

    @Reference
    private SearchService searchService;

    @GetMapping("/list.do")
    @ResponseBody
    public String list(@RequestParam(defaultValue = "1") int pageNum,
                       @RequestParam(defaultValue = "9") int pageSize, String keyword) {



        return searchService.findByExample(pageNum, pageSize, keyword);
    }
}
