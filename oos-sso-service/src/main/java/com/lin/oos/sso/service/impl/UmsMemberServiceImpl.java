package com.lin.oos.sso.service.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.lin.oos.service.UmsMemberService;
import org.springframework.stereotype.Service;

@Service
@com.alibaba.dubbo.config.annotation.Service(interfaceClass = UmsMemberService.class)
public class UmsMemberServiceImpl  {



    @Reference
    private UmsMemberService umsMemberService;







}
