package com.lin.oos.manager.service.impl;

import com.lin.oos.manager.mapper.PmsAdminMapper;
import com.lin.oos.pojo.PmsAdmin;
import com.lin.oos.pojo.PmsAdminExample;
import com.lin.oos.service.PmsAdminService;
import com.lin.oos.vo.OosResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@com.alibaba.dubbo.config.annotation.Service
public class PmsAdminServiceImpl implements PmsAdminService {


    @Autowired
    private PmsAdminMapper pmsAdminMapper;

    @Override
    public OosResult selectByRecord(String username, String password) {


        PmsAdminExample pmsAdminExample = new PmsAdminExample();
        PmsAdminExample.Criteria criteria = pmsAdminExample.createCriteria();
        criteria.andUsernameEqualTo(username);
        criteria.andPasswordEqualTo(password);
        List<PmsAdmin> pmsAdmins = pmsAdminMapper.selectByExample(pmsAdminExample);
        if(pmsAdmins.size()>0){


            return OosResult.ok(pmsAdmins.get(0));
        }

        return OosResult.build(400, "用户名或密码错误");
    }
}
