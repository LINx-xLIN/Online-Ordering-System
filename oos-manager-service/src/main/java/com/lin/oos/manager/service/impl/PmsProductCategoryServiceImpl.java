package com.lin.oos.manager.service.impl;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lin.oos.manager.mapper.PmsProductCategoryMapper;
import com.lin.oos.pojo.PmsProductCategory;
import com.lin.oos.pojo.PmsProductCategoryExample;
import com.lin.oos.service.PmsProductCategoryService;
import com.lin.oos.vo.OosResult;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
@com.alibaba.dubbo.config.annotation.Service(interfaceClass = PmsProductCategoryService.class)//加了事物注解@Transactional，要加interfaClass
public class PmsProductCategoryServiceImpl implements PmsProductCategoryService {

    @Autowired
    private PmsProductCategoryMapper pmsProductCategoryMapper;


    @Transactional
    @Override
    public OosResult save(PmsProductCategory pmsProductCategory) {

        pmsProductCategory.setCreated(new Date());

        pmsProductCategoryMapper.insert(pmsProductCategory);

        return OosResult.build(1,"数据插入成功");
    }

    @Override
    public List<PmsProductCategory> findAll(PmsProductCategory pmsProductCategory) {

        PmsProductCategoryExample pmsProductCategoryExample = new PmsProductCategoryExample();
        PmsProductCategoryExample.Criteria criteria = pmsProductCategoryExample.createCriteria();
        criteria.andNameEqualTo(pmsProductCategory.getName());

        List<PmsProductCategory> pmsProductCategories = pmsProductCategoryMapper.selectByExample(pmsProductCategoryExample);



        return pmsProductCategories;
    }

    @Override
    public PmsProductCategory findByName(String name) {
        PmsProductCategoryExample pmsProductCategoryExample = new PmsProductCategoryExample();
        PmsProductCategoryExample.Criteria criteria = pmsProductCategoryExample.createCriteria();
        criteria.andNameEqualTo(name);

        List<PmsProductCategory> pmsProductCategories = pmsProductCategoryMapper.selectByExample(pmsProductCategoryExample);

        if (pmsProductCategories.size()>0) {
            return pmsProductCategories.get(0);
        }

        return null;




    }



    @Override
    public String findByExample(int pageNum,int pageSize,String keyword) {


        PageHelper.startPage(pageNum, pageSize);
        PmsProductCategoryExample pmsProductCategoryExample = new PmsProductCategoryExample();
        if (StringUtils.isNotBlank(keyword)) {
            PmsProductCategoryExample.Criteria criteria = pmsProductCategoryExample.createCriteria();
            criteria.andNameLike("%" + keyword + "%");

            PmsProductCategoryExample.Criteria criteria1 = pmsProductCategoryExample.createCriteria();
            criteria1.andNameParentLike("%" + keyword + "%");

            pmsProductCategoryExample.or(criteria1);
        }

        List<PmsProductCategory> pmsProductCategories = pmsProductCategoryMapper.selectByExample(pmsProductCategoryExample);

        PageInfo<PmsProductCategory> pageInfo = new PageInfo<PmsProductCategory>(pmsProductCategories);



        return JSON.toJSONString(pageInfo);
    }

    @Override
    public PmsProductCategory selectByPrimaryKey(Integer id) {
        return pmsProductCategoryMapper.selectByPrimaryKey(id);
    }

    @Override
    @Transactional
    public OosResult update(PmsProductCategory pmsProductCategory) {

        pmsProductCategory.setCreated(new Date());

        pmsProductCategoryMapper.updateByPrimaryKeySelective(pmsProductCategory);

        return OosResult.build(1,"数据更新成功");





    }

    @Override
    @Transactional
    public OosResult delete(Integer categoryId) {


        pmsProductCategoryMapper.deleteByPrimaryKey(categoryId);

        return OosResult.build(1,"数据删除成功");

    }

    @Override
    @Transactional
    public OosResult batchDelete(List<Integer> categoryIds) {

        PmsProductCategoryExample pmsProductCategoryExample = new PmsProductCategoryExample();
        PmsProductCategoryExample.Criteria criteria = pmsProductCategoryExample.createCriteria();
        criteria.andIdIn(categoryIds);


        pmsProductCategoryMapper.deleteByExample(pmsProductCategoryExample);

        return OosResult.build(1,"数据批量删除成功");
    }

    @Override
    public List<PmsProductCategory> getAllCategory() {



        return pmsProductCategoryMapper.selectByExample(new PmsProductCategoryExample());
    }


}
