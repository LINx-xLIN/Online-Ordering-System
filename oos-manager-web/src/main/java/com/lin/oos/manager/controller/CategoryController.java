package com.lin.oos.manager.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.lin.oos.pojo.PmsProductCategory;
import com.lin.oos.service.PmsProductCategoryService;
import com.lin.oos.vo.OosResult;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/category")
public class CategoryController {


    @Reference
    private PmsProductCategoryService pmsProductCategoryService;



    @GetMapping("/list.do")
    @ResponseBody
    public String list(@RequestParam(defaultValue = "1") int pageNum,
                               @RequestParam(defaultValue = "10") int pageSize, String keyword) {


        return pmsProductCategoryService.findByExample(pageNum,pageSize,keyword);
    }


    @RequestMapping("/edit.do")
    public String edit(Model m, Integer categoryId){

        if(categoryId != null){

            PmsProductCategory pmsProductCategory = pmsProductCategoryService.selectByPrimaryKey(categoryId);
            m.addAttribute("pmsProductCategory",pmsProductCategory);

        }


        return "forward:/product-category-edit.html";
    }

    @PostMapping("/checkName.do")
    @ResponseBody
    public boolean checkName(String name){

        PmsProductCategory pmsProductCategory = pmsProductCategoryService.findByName(name);

        if (pmsProductCategory != null) {
            return false;
        }
        return true;
    }

    @PostMapping("/checkNameParent.do")
    @ResponseBody
    public boolean checkNameParent(String nameParent){

        PmsProductCategory pmsProductCategory = pmsProductCategoryService.findByName(nameParent);

        if (pmsProductCategory == null) {
            return false;
        }
        return true;
    }



    @PostMapping("/insert.do")
    @ResponseBody
    public OosResult insert(PmsProductCategory pmsProductCategory){




        try {
            OosResult oosResult = pmsProductCategoryService.save(pmsProductCategory);
            return oosResult;
        } catch (Exception e) {
            e.printStackTrace();
            return OosResult.build(2, "数据插入失败");
        }


    }

    @PostMapping("/update.do")
    @ResponseBody
    public OosResult update(PmsProductCategory pmsProductCategory){




        try {
            OosResult oosResult = pmsProductCategoryService.update(pmsProductCategory);
            return oosResult;
        } catch (Exception e) {
            e.printStackTrace();
            return OosResult.build(2, "数据更新失败");
        }


    }

    @PostMapping("/delete.do")
    @ResponseBody
    public OosResult delete(Integer categoryId){




        try {
            OosResult oosResult = pmsProductCategoryService.delete(categoryId);
            return oosResult;
        } catch (Exception e) {
            e.printStackTrace();
            return OosResult.build(2, "数据删除失败");
        }


    }


    @PostMapping("/batchDelete.do")
    @ResponseBody
    public OosResult batchDelete(@RequestParam(value = "categoryIds[]") List<Integer> categoryIds){


        try {
            OosResult oosResult = pmsProductCategoryService.batchDelete(categoryIds);
            return oosResult;
        } catch (Exception e) {
            e.printStackTrace();
            return OosResult.build(2, "数据批量删除失败");
        }


    }

    @RequestMapping("/getAllCategory.do")
    @ResponseBody
    public List<PmsProductCategory> getAllCategory() {


        return pmsProductCategoryService.getAllCategory();
    }






}
