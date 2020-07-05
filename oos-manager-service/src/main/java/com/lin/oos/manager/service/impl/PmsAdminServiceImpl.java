package com.lin.oos.manager.service.impl;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lin.oos.manager.mapper.PmsAdminMapper;
import com.lin.oos.pojo.PmsAdmin;
import com.lin.oos.pojo.PmsAdminExample;
import com.lin.oos.service.PmsAdminService;
import com.lin.oos.vo.OosResult;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@com.alibaba.dubbo.config.annotation.Service(interfaceClass = PmsAdminService.class)
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

    @Override
    @Transactional
    public OosResult save(PmsAdmin pmsAdmin) {

        pmsAdminMapper.insert(pmsAdmin);

        return OosResult.build(1,"数据插入成功");
    }

    @Override
    public PmsAdmin findByName(String name) {

        PmsAdminExample pmsAdminExample = new PmsAdminExample();
        PmsAdminExample.Criteria criteria = pmsAdminExample.createCriteria();
        criteria.andUsernameEqualTo(name);

        List<PmsAdmin> pmsAdmins = pmsAdminMapper.selectByExample(pmsAdminExample);



        if (pmsAdmins.size()>0) {
            return pmsAdmins.get(0);
        }


        return null;
    }


    @Override
    public String findByExample(int pageNum, int pageSize, String keyword) {

        PageHelper.startPage(pageNum, pageSize);
        PmsAdminExample pmsAdminExample = new PmsAdminExample();
        if (StringUtils.isNotBlank(keyword)) {
            PmsAdminExample.Criteria criteria = pmsAdminExample.createCriteria();
            criteria.andUsernameLike("%"+keyword+"%");
        }

        List<PmsAdmin> pmsAdmins = pmsAdminMapper.selectByExample(pmsAdminExample);
        PageInfo<PmsAdmin> pmsAdminPageInfo = new PageInfo<>(pmsAdmins);

        return JSON.toJSONString(pmsAdminPageInfo);
    }

    @Override
    public PmsAdmin selectByPrimaryKey(Integer adminId) {
        return pmsAdminMapper.selectByPrimaryKey(adminId);
    }

    @Override
    @Transactional
    public OosResult update(PmsAdmin pmsAdmin) {
        pmsAdminMapper.updateByPrimaryKeySelective(pmsAdmin);

        return OosResult.build(1,"数据更新成功");
    }

    @Override
    @Transactional
    public OosResult delete(Integer adminId) {
        pmsAdminMapper.deleteByPrimaryKey(adminId);
        return OosResult.build(1,"数据删除成功");
    }

    @Override
    @Transactional
    public OosResult batchDelete(List<Integer> adminIds) {

        PmsAdminExample pmsAdminExample = new PmsAdminExample();
        PmsAdminExample.Criteria criteria = pmsAdminExample.createCriteria();
        criteria.andIdIn(adminIds);
        pmsAdminMapper.deleteByExample(pmsAdminExample);

        return OosResult.build(1,"数据批量删除成功");
    }
}
