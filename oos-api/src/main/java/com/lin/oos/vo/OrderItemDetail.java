package com.lin.oos.vo;

import com.lin.oos.pojo.OmsOrderItem;

import java.util.List;

public class OrderItemDetail {


    private String payment;

    //一个订单可以有多个订单项，所以使用集合
    private List<OmsOrderItem> orderItems;

    public String getPayment() {
        return payment;
    }

    public void setPayment(String payment) {
        this.payment = payment;
    }

    public List<OmsOrderItem> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(List<OmsOrderItem> orderItems) {
        this.orderItems = orderItems;
    }
}
