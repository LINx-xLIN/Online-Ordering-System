package com.lin.oos.manager.service.impl;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lin.oos.manager.mapper.UmsMemberReceiveAddressMapper;
import com.lin.oos.pojo.UmsMemberReceiveAddress;
import com.lin.oos.pojo.UmsMemberReceiveAddressExample;
import com.lin.oos.service.UmsMemberReceiveAddressService;
import com.lin.oos.vo.OosResult;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
@com.alibaba.dubbo.config.annotation.Service(interfaceClass = UmsMemberReceiveAddressService.class)
public class UmsMemberReceiveAddressServiceImpl implements UmsMemberReceiveAddressService {


    @Autowired
    private UmsMemberReceiveAddressMapper umsMemberReceiveAddressMapper;


    @Transactional
    @Override
    public OosResult save(UmsMemberReceiveAddress umsMemberReceiveAddress) {

       

        umsMemberReceiveAddressMapper.insert(umsMemberReceiveAddress);

        return OosResult.build(1,"数据插入成功");
    }

    @Override
    public List<UmsMemberReceiveAddress> findAll(UmsMemberReceiveAddress umsMemberReceiveAddress) {

        UmsMemberReceiveAddressExample umsMemberReceiveAddressExample = new UmsMemberReceiveAddressExample();
        UmsMemberReceiveAddressExample.Criteria criteria = umsMemberReceiveAddressExample.createCriteria();
        criteria.andNameEqualTo(umsMemberReceiveAddress.getName());

        List<UmsMemberReceiveAddress> pmsProductCategories = umsMemberReceiveAddressMapper.selectByExample(umsMemberReceiveAddressExample);



        return pmsProductCategories;
    }

    @Override
    public UmsMemberReceiveAddress findByName(String name) {
        UmsMemberReceiveAddressExample umsMemberReceiveAddressExample = new UmsMemberReceiveAddressExample();
        UmsMemberReceiveAddressExample.Criteria criteria = umsMemberReceiveAddressExample.createCriteria();
        criteria.andNameEqualTo(name);

        List<UmsMemberReceiveAddress> pmsProductCategories = umsMemberReceiveAddressMapper.selectByExample(umsMemberReceiveAddressExample);

        if (pmsProductCategories.size()>0) {
            return pmsProductCategories.get(0);
        }

        return null;




    }



    @Override
    public String findByExample(int pageNum,int pageSize,String keyword) {


        PageHelper.startPage(pageNum, pageSize);
        UmsMemberReceiveAddressExample umsMemberReceiveAddressExample = new UmsMemberReceiveAddressExample();
        if (StringUtils.isNotBlank(keyword)) {
            UmsMemberReceiveAddressExample.Criteria criteria = umsMemberReceiveAddressExample.createCriteria();
            criteria.andNameLike("%" + keyword + "%");

            UmsMemberReceiveAddressExample.Criteria criteria1 = umsMemberReceiveAddressExample.createCriteria();
            criteria1.andCityLike("%" + keyword + "%");

            UmsMemberReceiveAddressExample.Criteria criteria2 = umsMemberReceiveAddressExample.createCriteria();
            criteria2.andRegionLike("%" + keyword + "%");

            UmsMemberReceiveAddressExample.Criteria criteria3 = umsMemberReceiveAddressExample.createCriteria();
            criteria3.andDetailAddressLike("%" + keyword + "%");

            umsMemberReceiveAddressExample.or(criteria1);
            umsMemberReceiveAddressExample.or(criteria2);
            umsMemberReceiveAddressExample.or(criteria3);



        }

        List<UmsMemberReceiveAddress> pmsProductCategories = umsMemberReceiveAddressMapper.selectByExample(umsMemberReceiveAddressExample);

        PageInfo<UmsMemberReceiveAddress> pageInfo = new PageInfo<UmsMemberReceiveAddress>(pmsProductCategories);



        return JSON.toJSONString(pageInfo);
    }

    @Override
    public UmsMemberReceiveAddress selectByPrimaryKey(Integer id) {
        return umsMemberReceiveAddressMapper.selectByPrimaryKey(id);
    }

    @Override
    @Transactional
    public OosResult update(UmsMemberReceiveAddress umsMemberReceiveAddress) {



        umsMemberReceiveAddressMapper.updateByPrimaryKeySelective(umsMemberReceiveAddress);

        return OosResult.build(1,"数据更新成功");





    }

    @Override
    @Transactional
    public OosResult delete(Integer categoryId) {


        umsMemberReceiveAddressMapper.deleteByPrimaryKey(categoryId);

        return OosResult.build(1,"数据删除成功");

    }

    @Override
    @Transactional
    public OosResult batchDelete(List<Integer> categoryIds) {

        UmsMemberReceiveAddressExample umsMemberReceiveAddressExample = new UmsMemberReceiveAddressExample();
        UmsMemberReceiveAddressExample.Criteria criteria = umsMemberReceiveAddressExample.createCriteria();
        criteria.andIdIn(categoryIds);


        umsMemberReceiveAddressMapper.deleteByExample(umsMemberReceiveAddressExample);

        return OosResult.build(1,"数据批量删除成功");
    }

    @Override
    public List<UmsMemberReceiveAddress> selectByMemberId(Integer id) {

        UmsMemberReceiveAddressExample umsMemberReceiveAddressExample = new UmsMemberReceiveAddressExample();
        UmsMemberReceiveAddressExample.Criteria criteria = umsMemberReceiveAddressExample.createCriteria();
        criteria.andMemberIdEqualTo(id.longValue());
        List<UmsMemberReceiveAddress> umsMemberReceiveAddresses = umsMemberReceiveAddressMapper.selectByExample(umsMemberReceiveAddressExample);


        return umsMemberReceiveAddresses;
    }
}
