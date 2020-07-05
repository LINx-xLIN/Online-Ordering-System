package com.lin.oos.manager.service.impl;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lin.oos.manager.mapper.OmsOrderMapper;
import com.lin.oos.pojo.OmsOrder;
import com.lin.oos.pojo.OmsOrderExample;
import com.lin.oos.service.OmsOrderService;
import com.lin.oos.vo.OosResult;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
@com.alibaba.dubbo.config.annotation.Service(interfaceClass = OmsOrderService.class)
public class OmsOrderServiceImpl implements OmsOrderService {


    @Autowired
    private OmsOrderMapper omsOrderMapper;


    @Override
    public OosResult save(OmsOrder omsOrder) {
        return null;
    }

    @Override
    public String findByExample(int pageNum, int pageSize, String keyword) {

        PageHelper.startPage(pageNum, pageSize);
        OmsOrderExample omsOrderExample = new OmsOrderExample();

        if (StringUtils.isNotBlank(keyword)) {
            OmsOrderExample.Criteria criteria = omsOrderExample.createCriteria();
            criteria.andOrderIdEqualTo(keyword);
        }

        List<OmsOrder> omsOrders = omsOrderMapper.selectByExample(omsOrderExample);
        PageInfo<OmsOrder> omsOrderPageInfo = new PageInfo<>(omsOrders);

        return JSON.toJSONString(omsOrderPageInfo);


    }

    @Override
    public OmsOrder selectByPrimaryKey(Integer orderId) {
        return omsOrderMapper.selectByPrimaryKey(orderId.toString());
    }

    @Override
    @Transactional
    public OosResult update(OmsOrder omsOrder) {

        omsOrder.setUpdateTime(new Date());
        omsOrderMapper.updateByPrimaryKeySelective(omsOrder);

        return  OosResult.build(1,"数据更新成功");
    }

    @Override
    @Transactional
    public OosResult delete(String orderId) {
        omsOrderMapper.deleteByPrimaryKey(orderId);

        return OosResult.build(1,"数据删除成功");
    }

    @Override
    @Transactional
    public OosResult batchDelete(List<String> orderIds) {

        OmsOrderExample omsOrderExample = new OmsOrderExample();
        OmsOrderExample.Criteria criteria = omsOrderExample.createCriteria();
        criteria.andOrderIdIn(orderIds);

        omsOrderMapper.deleteByExample(omsOrderExample);


        return OosResult.build(1,"数据批量删除成功");
    }


}
