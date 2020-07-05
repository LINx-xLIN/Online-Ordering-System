package com.lin.oos.manager.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.lin.oos.pojo.UmsMemberReceiveAddress;
import com.lin.oos.service.UmsMemberReceiveAddressService;
import com.lin.oos.vo.OosResult;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/address")
@CrossOrigin
public class MemberReceiveAddressController {


    @Reference
    private UmsMemberReceiveAddressService umsMemberReceiveAddressService;



    @GetMapping("/list.do")
    @ResponseBody
    public String list(@RequestParam(defaultValue = "1") int pageNum,
                       @RequestParam(defaultValue = "10") int pageSize, String keyword) {


        return umsMemberReceiveAddressService.findByExample(pageNum,pageSize,keyword);
    }


    @RequestMapping("/edit.do")
    public String edit(Model m, Integer addressId){

        if(addressId != null){

            UmsMemberReceiveAddress umsMemberReceiveAddress = umsMemberReceiveAddressService.selectByPrimaryKey(addressId);
            m.addAttribute("umsMemberReceiveAddress",umsMemberReceiveAddress);

        }


        return "forward:/memberReceiveAddress-edit.html";
    }






    /*@PostMapping("/insert.do")
    @ResponseBody
    public OosResult insert(UmsMemberReceiveAddress umsMemberReceiveAddress){




        try {
            OosResult oosResult = umsMemberReceiveAddressService.save(umsMemberReceiveAddress);
            return oosResult;
        } catch (Exception e) {
            e.printStackTrace();
            return OosResult.build(2, "数据插入失败");
        }


    }*/

    @PostMapping("/update.do")
    @ResponseBody
    public OosResult update(UmsMemberReceiveAddress umsMemberReceiveAddress){




        try {
            OosResult oosResult = umsMemberReceiveAddressService.update(umsMemberReceiveAddress);
            return oosResult;
        } catch (Exception e) {
            e.printStackTrace();
            return OosResult.build(2, "数据更新失败");
        }


    }

    @PostMapping("/delete.do")
    @ResponseBody
    public OosResult delete(Integer addressId){




        try {
            OosResult oosResult = umsMemberReceiveAddressService.delete(addressId);
            return oosResult;
        } catch (Exception e) {
            e.printStackTrace();
            return OosResult.build(2, "数据删除失败");
        }


    }


    @PostMapping("/batchDelete.do")
    @ResponseBody
    public OosResult batchDelete(@RequestParam(value = "addressIds[]") List<Integer> addressIds){


        try {
            OosResult oosResult = umsMemberReceiveAddressService.batchDelete(addressIds);
            return oosResult;
        } catch (Exception e) {
            e.printStackTrace();
            return OosResult.build(2, "数据批量删除失败");
        }


    }


}
