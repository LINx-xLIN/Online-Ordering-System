package com.lin.oos.service;

import com.lin.oos.pojo.PmsProductCategory;
import com.lin.oos.pojo.PmsProductItem;
import com.lin.oos.pojo.UmsMember;
import com.lin.oos.vo.OosResult;


import java.util.List;

public interface UmsMemberService {

    List<UmsMember> selectAll();


    List<UmsMember> login(String username, String password);

    List<UmsMember> selectByUsername(String username);

    OosResult register(UmsMember umsMember);






    List<UmsMember> findAll(UmsMember umsMember);



    String findByExample(int pageNum,int pageSize,String keyword);

    UmsMember selectByPrimaryKey(Long memberId);

    OosResult update(UmsMember umsMember);

    OosResult delete(Long memberId);


    OosResult batchDelete(List<Long> memberIds);


}
