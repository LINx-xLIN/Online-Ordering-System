package com.lin.oos.service;

import com.lin.oos.pojo.OmsOrder;
import com.lin.oos.vo.OosResult;

import java.util.List;

public interface OmsOrderService {

    OosResult save(OmsOrder omsOrder);

    String findByExample(int pageNum,int pageSize,String keyword);

    OmsOrder selectByPrimaryKey(Integer orderId);

    OosResult update(OmsOrder omsOrder);

    OosResult delete(String orderId);

    OosResult batchDelete(List<String> orderIds);


}
