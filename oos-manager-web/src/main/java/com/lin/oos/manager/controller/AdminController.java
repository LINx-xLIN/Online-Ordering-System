package com.lin.oos.manager.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.lin.oos.pojo.PmsProductCategory;
import com.lin.oos.pojo.PmsAdmin;
import com.lin.oos.service.PmsAdminService;
import com.lin.oos.service.PmsProductItemService;
import com.lin.oos.vo.OosResult;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Reference
    private PmsAdminService pmsAdminService;


    @GetMapping("/list.do")
    @ResponseBody
    public String list(@RequestParam(defaultValue = "1") int pageNum,
                       @RequestParam(defaultValue = "10") int pageSize, String keyword) {


        return pmsAdminService.findByExample(pageNum,pageSize,keyword);
    }


    @RequestMapping("/edit.do")
    public String edit(Model m, Integer adminId){

        if(adminId != null){
            PmsAdmin pmsAdmin = pmsAdminService.selectByPrimaryKey(adminId);
            m.addAttribute("pmsAdmin",pmsAdmin);

        }
        return "forward:/admin-edit.html";
    }


    @PostMapping("/insert.do")
    @ResponseBody
    public OosResult insert(PmsAdmin pmsAdmin){




        try {
            OosResult oosResult = pmsAdminService.save(pmsAdmin);
            return oosResult;
        } catch (Exception e) {
            e.printStackTrace();
            return OosResult.build(2, "数据插入失败");
        }


    }

    @PostMapping("/update.do")
    @ResponseBody
    public OosResult update(PmsAdmin pmsAdmin){




        try {
            OosResult oosResult = pmsAdminService.update(pmsAdmin);
            return oosResult;
        } catch (Exception e) {
            e.printStackTrace();
            return OosResult.build(2, "数据更新失败");
        }


    }

    @PostMapping("/checkName.do")
    @ResponseBody
    public boolean checkName(String name){
        PmsAdmin pmsAdmin = pmsAdminService.findByName(name);

        if (pmsAdmin != null) {
            return false;
        }
        return true;
    }


    @PostMapping("/delete.do")
    @ResponseBody
    public OosResult delete(Integer adminId){




        try {
            OosResult oosResult = pmsAdminService.delete(adminId);
            return oosResult;
        } catch (Exception e) {
            e.printStackTrace();
            return OosResult.build(2, "数据删除失败");
        }


    }


    @PostMapping("/batchDelete.do")
    @ResponseBody
    public OosResult batchDelete(@RequestParam(value = "adminIds[]") List<Integer> adminIds){


        try {
            OosResult oosResult = pmsAdminService.batchDelete(adminIds);
            return oosResult;
        } catch (Exception e) {
            e.printStackTrace();
            return OosResult.build(2, "数据批量删除失败");
        }


    }

}
