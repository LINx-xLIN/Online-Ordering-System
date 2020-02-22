package com.lin.oos.service;

import com.lin.oos.pojo.PmsProductCategory;
import com.lin.oos.vo.OosResult;

import java.util.List;

public interface PmsProductCategoryService {

    OosResult save(PmsProductCategory pmsProductCategory);

    List<PmsProductCategory> findAll(PmsProductCategory pmsProductCategory);

    PmsProductCategory findByName(String name);

    String findByExample(int pageNum,int pageSize,String keyword);

    PmsProductCategory selectByPrimaryKey(Integer id);

    OosResult update(PmsProductCategory pmsProductCategory);

    OosResult delete(Integer categoryId);


    OosResult batchDelete(List<Integer> categoryIds);

    List<PmsProductCategory> getAllCategory();
}
