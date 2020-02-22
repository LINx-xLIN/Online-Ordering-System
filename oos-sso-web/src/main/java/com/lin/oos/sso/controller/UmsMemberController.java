package com.lin.oos.sso.controller;


import com.alibaba.dubbo.config.annotation.Reference;
import com.lin.oos.pojo.UmsMember;
import com.lin.oos.service.UmsMemberService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class UmsMemberController {


    @Reference
    private UmsMemberService umsMemberService;

    @RequestMapping("index")
    @ResponseBody
    public List<UmsMember> indexTest(){

        List<UmsMember> umsMembers = umsMemberService.selectAll();

        return umsMembers;
    }

}
