package com.lin.oos.sso.service.impl;

import com.lin.oos.pojo.UmsMember;
import com.lin.oos.service.UmsMemberService;
import com.lin.oos.sso.mapper.UmsMemberMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@com.alibaba.dubbo.config.annotation.Service
public class UmsMemberServiceImpl implements UmsMemberService {

    @Autowired
    private UmsMemberMapper umsMemberMapper;



    @Override
    public List<UmsMember> selectAll() {
        return umsMemberMapper.selectAll();
    }
}
