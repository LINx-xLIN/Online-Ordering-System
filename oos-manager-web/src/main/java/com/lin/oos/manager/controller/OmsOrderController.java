package com.lin.oos.manager.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.lin.oos.service.OmsOrderService;
import com.lin.oos.vo.OosResult;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/order")
public class OmsOrderController {
    @Reference
    private OmsOrderService omsOrderService;

    @GetMapping("/list.do")
    @ResponseBody
    public String list(@RequestParam(defaultValue = "1") int pageNum,
                       @RequestParam(defaultValue = "10") int pageSize, String keyword) {


        return omsOrderService.findByExample(pageNum,pageSize,keyword);
    }


    @PostMapping("/delete.do")
    @ResponseBody
    public OosResult delete(String orderId){




        try {
            OosResult oosResult = omsOrderService.delete(orderId);
            return oosResult;
        } catch (Exception e) {
            e.printStackTrace();
            return OosResult.build(2, "数据删除失败");
        }


    }


    @PostMapping("/batchDelete.do")
    @ResponseBody
    public OosResult batchDelete(@RequestParam(value = "orderIds[]") List<String> orderIds){


        try {
            OosResult oosResult = omsOrderService.batchDelete(orderIds);
            return oosResult;
        } catch (Exception e) {
            e.printStackTrace();
            return OosResult.build(2, "数据批量删除失败");
        }


    }
}
