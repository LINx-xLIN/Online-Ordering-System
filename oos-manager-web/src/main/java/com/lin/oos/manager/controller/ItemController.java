package com.lin.oos.manager.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.lin.oos.pojo.PmsProductCategory;
import com.lin.oos.pojo.PmsProductItem;
import com.lin.oos.service.PmsProductItemService;
import com.lin.oos.vo.OosResult;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/item")
public class ItemController {

    @Reference
    private PmsProductItemService pmsProductItemService;


    @GetMapping("/list.do")
    @ResponseBody
    public String list(@RequestParam(defaultValue = "1") int pageNum,
                       @RequestParam(defaultValue = "10") int pageSize, String keyword) {


        return pmsProductItemService.findByExample(pageNum,pageSize,keyword);
    }


    @RequestMapping("/edit.do")
    public String edit(Model m, Integer itemId){

        if(itemId != null){
            PmsProductItem pmsProductItem = pmsProductItemService.selectByPrimaryKey(itemId);
            m.addAttribute("pmsProductItem",pmsProductItem);
            
        }
        return "forward:/product-item-edit.html";
    }


    @PostMapping("/insert.do")
    @ResponseBody
    public OosResult insert(PmsProductItem pmsProductItem){




        try {
            OosResult oosResult = pmsProductItemService.save(pmsProductItem);
            return oosResult;
        } catch (Exception e) {
            e.printStackTrace();
            return OosResult.build(2, "数据插入失败");
        }


    }

    @PostMapping("/update.do")
    @ResponseBody
    public OosResult update(PmsProductItem pmsProductItem){




        try {
            OosResult oosResult = pmsProductItemService.update(pmsProductItem);
            return oosResult;
        } catch (Exception e) {
            e.printStackTrace();
            return OosResult.build(2, "数据更新失败");
        }


    }

    @PostMapping("/checkTitle.do")
    @ResponseBody
    public boolean checkTitle(String title){
        PmsProductItem pmsProductItem = pmsProductItemService.findByTitle(title);

        if (pmsProductItem != null) {
            return false;
        }
        return true;
    }


    @PostMapping("/delete.do")
    @ResponseBody
    public OosResult delete(Integer itemId){




        try {
            OosResult oosResult = pmsProductItemService.delete(itemId);
            return oosResult;
        } catch (Exception e) {
            e.printStackTrace();
            return OosResult.build(2, "数据删除失败");
        }


    }


    @PostMapping("/batchDelete.do")
    @ResponseBody
    public OosResult batchDelete(@RequestParam(value = "itemIds[]") List<Integer> itemIds){


        try {
            OosResult oosResult = pmsProductItemService.batchDelete(itemIds);
            return oosResult;
        } catch (Exception e) {
            e.printStackTrace();
            return OosResult.build(2, "数据批量删除失败");
        }


    }

}
