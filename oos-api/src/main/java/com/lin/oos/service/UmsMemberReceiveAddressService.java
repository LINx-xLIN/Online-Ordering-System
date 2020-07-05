package com.lin.oos.service;

import com.lin.oos.pojo.UmsMemberReceiveAddress;
import com.lin.oos.vo.OosResult;

import java.util.List;

public interface UmsMemberReceiveAddressService {

    OosResult save(UmsMemberReceiveAddress umsMemberReceiveAddress);

    List<UmsMemberReceiveAddress> findAll(UmsMemberReceiveAddress umsMemberReceiveAddress);

    UmsMemberReceiveAddress findByName(String name);

    String findByExample(int pageNum,int pageSize,String keyword);

    UmsMemberReceiveAddress selectByPrimaryKey(Integer id);

    OosResult update(UmsMemberReceiveAddress umsMemberReceiveAddress);

    OosResult delete(Integer addressId);


    OosResult batchDelete(List<Integer> addressIds);


    List<UmsMemberReceiveAddress> selectByMemberId(Integer id);


}
