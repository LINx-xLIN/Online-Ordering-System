package com.lin.oos.manager.service.impl;


import com.lin.oos.manager.mapper.OmsOrderItemMapper;
import com.lin.oos.manager.mapper.OmsOrderMapper;
import com.lin.oos.manager.mapper.OmsOrderShippingMapper;
import com.lin.oos.pojo.OmsOrder;
import com.lin.oos.pojo.OmsOrderItem;
import com.lin.oos.pojo.OmsOrderShipping;
import com.lin.oos.service.OrderService;
import com.lin.oos.util.IDUtils;
import com.lin.oos.vo.OosResult;
import com.lin.oos.vo.OrderDetail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
@com.alibaba.dubbo.config.annotation.Service(interfaceClass = OrderService.class)
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OmsOrderMapper omsOrderMapper;

    @Autowired
    private OmsOrderItemMapper omsOrderItemMapper;

    @Autowired
    private OmsOrderShippingMapper omsOrderShippingMapper;


    @Override
    @Transactional
    public OosResult saveOrder(OrderDetail orderDetail,Long userId) {

        Long orderId = IDUtils.genItemId();
        OmsOrderShipping orderShipping = orderDetail.getOrderShipping();
        orderShipping.setCreated(new Date());
        orderShipping.setOrderId(orderId.toString());
        orderShipping.setUpdated(new Date());
        omsOrderShippingMapper.insert(orderShipping);

        List<OmsOrderItem> orderItems = orderDetail.getOrderItems();
        for (OmsOrderItem orderItem : orderItems) {
            orderItem.setOrderId(orderId.toString());
            omsOrderItemMapper.insert(orderItem);
        }


        OmsOrder omsOrder = new OmsOrder();
        omsOrder.setOrderId(orderId.toString());
        omsOrder.setCreateTime(new Date());
        omsOrder.setPayment(orderDetail.getPayment());
        omsOrder.setPaymentType(orderDetail.getPaymentType());
        omsOrder.setStatus(1);
        omsOrder.setUserId(userId);
        omsOrder.setUpdateTime(new Date());
        omsOrderMapper.insert(omsOrder);


        return OosResult.ok();
    }





}
