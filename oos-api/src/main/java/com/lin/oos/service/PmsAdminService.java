package com.lin.oos.service;

import com.lin.oos.pojo.PmsAdmin;
import com.lin.oos.vo.OosResult;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public interface PmsAdminService {


    OosResult selectByRecord(String username, String password);
}
