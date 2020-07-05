package com.lin.oos.manager.service.impl;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lin.oos.manager.mapper.PmsProductCategoryMapper;
import com.lin.oos.manager.mapper.PmsProductItemMapper;
import com.lin.oos.manager.producter.ItemProducter;
import com.lin.oos.pojo.PmsProductCategory;
import com.lin.oos.pojo.PmsProductCategoryExample;
import com.lin.oos.pojo.PmsProductItem;
import com.lin.oos.pojo.PmsProductItemExample;
import com.lin.oos.service.PmsProductCategoryService;
import com.lin.oos.service.PmsProductItemService;
import com.lin.oos.vo.OosResult;
import org.apache.commons.lang3.StringUtils;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
@com.alibaba.dubbo.config.annotation.Service(interfaceClass = PmsProductItemService.class)
public class PmsProductItemServiceImpl implements PmsProductItemService {


    @Autowired
    private PmsProductItemMapper pmsProductItemMapper;

    @Autowired
    private PmsProductCategoryMapper pmsProductCategoryMapper;

    @Autowired
    private ItemProducter itemProducter;


    @Override
    @Transactional
    public OosResult save(PmsProductItem pmsProductItem) {

        pmsProductItem.setCreate(new Date());
        pmsProductItemMapper.insert(pmsProductItem);

        PmsProductCategory pmsProductCategory = pmsProductCategoryMapper.selectByPrimaryKey(pmsProductItem.getCid());
        pmsProductItem.setCategoryName(pmsProductCategory.getName());

        itemProducter.send(pmsProductItem);


        return OosResult.build(1,"数据插入成功");
    }



    @Override
    public PmsProductItem findByTitle(String title) {


        PmsProductItemExample pmsProductItemExample = new PmsProductItemExample();
        PmsProductItemExample.Criteria criteria = pmsProductItemExample.createCriteria();
        criteria.andTitleEqualTo(title);


        List<PmsProductItem> pmsProductItems = pmsProductItemMapper.selectByExample(pmsProductItemExample);


        if (pmsProductItems.size()>0) {
            return pmsProductItems.get(0);
        }


        return null;
    }

    @Override
    public String findByExample(int pageNum, int pageSize, String keyword) {

        PageHelper.startPage(pageNum, pageSize);
        PmsProductItemExample pmsProductItemExample = new PmsProductItemExample();

        if (StringUtils.isNotBlank(keyword)) {
            PmsProductItemExample.Criteria criteria = pmsProductItemExample.createCriteria();
            criteria.andTitleLike("%" + keyword + "%");

            PmsProductItemExample.Criteria criteria1 = pmsProductItemExample.createCriteria();
            criteria1.andIngredientsLike("%" + keyword + "%");

            /*自定义*/
            PmsProductItemExample.Criteria criteria2 = pmsProductItemExample.createCriteria();
            criteria2.andCategoryNameLike("%" + keyword + "%");


            pmsProductItemExample.or(criteria1);
            pmsProductItemExample.or(criteria2);


        }


        List<PmsProductItem> pmsProductItems = pmsProductItemMapper.selectByExample(pmsProductItemExample);

        PageInfo<PmsProductItem> pageInfo = new PageInfo<PmsProductItem>(pmsProductItems);



        return JSON.toJSONString(pageInfo);




    }

    @Override
    public PmsProductItem selectByPrimaryKey(Integer itemId) {
        return pmsProductItemMapper.selectByPrimaryKey(itemId);
    }

    @Override
    @Transactional
    public OosResult update(PmsProductItem pmsProductItem) {

        pmsProductItem.setCreate(new Date());
        pmsProductItemMapper.updateByPrimaryKeySelective(pmsProductItem);



        return OosResult.build(1,"数据更新成功");

    }

    @Override
    @Transactional
    public OosResult delete(Integer itemId) {
        pmsProductItemMapper.deleteByPrimaryKey(itemId);

        return OosResult.build(1,"数据删除成功");
    }

    @Override
    @Transactional
    public OosResult batchDelete(List<Integer> itemIds) {


        PmsProductItemExample pmsProductItemExample = new PmsProductItemExample();
        PmsProductItemExample.Criteria criteria = pmsProductItemExample.createCriteria();
        criteria.andIdIn(itemIds);


        pmsProductItemMapper.deleteByExample(pmsProductItemExample);

        return OosResult.build(1,"数据批量删除成功");
    }

    @Override
    public List<PmsProductItem> getAllData() {
        return pmsProductItemMapper.selectByExample(new PmsProductItemExample());
    }
}
