package com.lin.oos.vo;

import com.lin.oos.pojo.OmsOrderItem;
import com.lin.oos.pojo.OmsOrderShipping;

import java.io.Serializable;
import java.util.List;

public class OrderDetail implements Serializable {

    //一个订单可以有多个订单项，所以使用集合
    private List<OmsOrderItem> orderItems;
    //一个订单只有一份发送地址，所以使用引用
    private OmsOrderShipping orderShipping;

    private String payment;

    private Integer paymentType;

    public String getPayment() {
        return payment;
    }

    public void setPayment(String payment) {
        this.payment = payment;
    }

    public Integer getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(Integer paymentType) {
        this.paymentType = paymentType;
    }

    public List<OmsOrderItem> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(List<OmsOrderItem> orderItems) {
        this.orderItems = orderItems;
    }

    public OmsOrderShipping getOrderShipping() {
        return orderShipping;
    }

    public void setOrderShipping(OmsOrderShipping orderShipping) {
        this.orderShipping = orderShipping;
    }
}
