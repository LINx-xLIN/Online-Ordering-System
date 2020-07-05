package com.lin.oos.manager.service.impl;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lin.oos.manager.mapper.UmsMemberMapper;
import com.lin.oos.pojo.PmsProductItem;
import com.lin.oos.pojo.PmsProductItemExample;
import com.lin.oos.pojo.UmsMember;
import com.lin.oos.pojo.UmsMemberExample;
import com.lin.oos.service.UmsMemberService;
import com.lin.oos.vo.OosResult;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
@com.alibaba.dubbo.config.annotation.Service(interfaceClass = UmsMemberService.class)
public class UmsMemberServiceImpl implements UmsMemberService {

    @Autowired
    private UmsMemberMapper umsMemberMapper;


    @Override
    public List<UmsMember> selectAll() {
        return umsMemberMapper.selectAll();
    }


    /**
     * 开发思路：通过登录页面获得用户密码与数据库用户信息校验，如果校验成功，将数据存入到Redis以及写入Cookien ，返回登录成功信息
     *
     * @param username 用户名
     * @param password 密码
     * @return
     */
    @Override
    public List<UmsMember> login(String username, String password) {
        //第一步：校验数据库信息判断是否成功

        UmsMemberExample umsMemberExample = new UmsMemberExample();
        UmsMemberExample.Criteria criteria = umsMemberExample.createCriteria();
        criteria.andUsernameEqualTo(username);

        criteria.andPasswordEqualTo(password);


        List<UmsMember> umsMembers = umsMemberMapper.selectByExample(umsMemberExample);


        return umsMembers;
    }

    @Override
    public List<UmsMember> selectByUsername(String username) {

        UmsMemberExample umsMemberExample = new UmsMemberExample();
        UmsMemberExample.Criteria criteria = umsMemberExample.createCriteria();
        criteria.andUsernameEqualTo(username);

        return umsMemberMapper.selectByExample(umsMemberExample);
    }

    @Override
    @Transactional
    public OosResult register(UmsMember umsMember) {



            umsMember.setCreateTime(new Date());


            umsMemberMapper.insert(umsMember);

            return OosResult.build(1, "注册成功");


    }



    @Override
    public List<UmsMember> findAll(UmsMember umsMember) {
        return null;
    }



    @Override
    public String findByExample(int pageNum, int pageSize, String keyword) {

        PageHelper.startPage(pageNum, pageSize);
        UmsMemberExample umsMemberExample = new UmsMemberExample();

        if (StringUtils.isNotBlank(keyword)) {
            UmsMemberExample.Criteria criteria = umsMemberExample.createCriteria();
            criteria.andUsernameLike("%" + keyword + "%");

            UmsMemberExample.Criteria criteria1 = umsMemberExample.createCriteria();
            criteria1.andNicknameLike("%" + keyword + "%");
            umsMemberExample.or(criteria1);

        }


        List<UmsMember> umsMembers = umsMemberMapper.selectByExample(umsMemberExample);

        PageInfo<UmsMember> pageInfo = new PageInfo<UmsMember>(umsMembers);


        return JSON.toJSONString(pageInfo);
    }

    @Override
    public UmsMember selectByPrimaryKey(Long memberId) {


        return umsMemberMapper.selectByPrimaryKey(memberId);
    }

    @Override
    @Transactional
    public OosResult update(UmsMember umsMember) {


        umsMemberMapper.updateByPrimaryKeySelective(umsMember);
        return OosResult.build(1,"数据更新成功");
    }

    @Override
    @Transactional
    public OosResult delete(Long memberId) {

        umsMemberMapper.deleteByPrimaryKey(memberId);

        return OosResult.build(1,"数据删除成功");
    }

    @Override
    @Transactional
    public OosResult batchDelete(List<Long> memberIds) {
        UmsMemberExample umsMemberExample = new UmsMemberExample();
        UmsMemberExample.Criteria criteria = umsMemberExample.createCriteria();
        criteria.andIdIn(memberIds);


        umsMemberMapper.deleteByExample(umsMemberExample);

        return OosResult.build(1,"数据批量删除成功");
    }

}
