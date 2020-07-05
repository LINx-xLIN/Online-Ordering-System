package com.lin.oos.service;

import com.lin.oos.pojo.PmsAdmin;
import com.lin.oos.pojo.PmsAdmin;
import com.lin.oos.vo.OosResult;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;

public interface PmsAdminService {


    OosResult selectByRecord(String username, String password);

    OosResult save(PmsAdmin pmsAdmin);


    PmsAdmin findByName(String name);

    String findByExample(int pageNum,int pageSize,String keyword);

    PmsAdmin selectByPrimaryKey(Integer adminId);

    OosResult update(PmsAdmin pmsAdmin);

    OosResult delete(Integer adminId);


    OosResult batchDelete(List<Integer> adminIds);
    
    
}
