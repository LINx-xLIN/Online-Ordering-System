package com.lin.oos.service;

import com.lin.oos.pojo.PmsProductCategory;
import com.lin.oos.pojo.PmsProductItem;
import com.lin.oos.vo.OosResult;

import java.util.List;

public interface PmsProductItemService {
    OosResult save(PmsProductItem pmsProductItem);


    PmsProductItem findByTitle(String title);

    String findByExample(int pageNum,int pageSize,String keyword);

    PmsProductItem selectByPrimaryKey(Integer itemId);

    OosResult update(PmsProductItem pmsProductItem);

    OosResult delete(Integer itemId);


    OosResult batchDelete(List<Integer> itemIds);

    List<PmsProductItem> getAllData();
}
