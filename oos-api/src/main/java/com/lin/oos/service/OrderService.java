package com.lin.oos.service;

import com.lin.oos.vo.OosResult;
import com.lin.oos.vo.OrderDetail;

public interface OrderService {

    OosResult saveOrder(OrderDetail orderDetail,Long userId);

}
