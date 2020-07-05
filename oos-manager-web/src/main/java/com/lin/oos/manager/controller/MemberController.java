package com.lin.oos.manager.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.lin.oos.pojo.PmsProductItem;
import com.lin.oos.pojo.UmsMember;
import com.lin.oos.service.PmsProductItemService;
import com.lin.oos.service.UmsMemberService;
import com.lin.oos.vo.OosResult;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/member")
public class MemberController {

    @Reference
    private UmsMemberService umsMemberService;


    @GetMapping("/list.do")
    @ResponseBody
    public String list(@RequestParam(defaultValue = "1") int pageNum,
                       @RequestParam(defaultValue = "10") int pageSize, String keyword) {


        return umsMemberService.findByExample(pageNum,pageSize,keyword);
    }


    @RequestMapping("/edit.do")
    public String edit(Model m, Long memberId){

        if(memberId != null){
            UmsMember umsMember = umsMemberService.selectByPrimaryKey(memberId);
            m.addAttribute("umsMember",umsMember);

        }
        return "forward:/member-edit.html";
    }

    @PostMapping("/checkUsername.do")
    @ResponseBody
    public boolean checkTitle(String username){
        List<UmsMember> umsMembers = umsMemberService.selectByUsername(username);

        if (umsMembers.size() != 0) {
            return false;
        }
        return true;
    }

    @PostMapping("/insert.do")
    @ResponseBody
    public OosResult insert(UmsMember umsMember){




        try {
            OosResult oosResult = umsMemberService.register(umsMember);
            return OosResult.build(1, "数据插入成功");
        } catch (Exception e) {
            e.printStackTrace();
            return OosResult.build(2, "数据插入失败");
        }


    }
    @PostMapping("/update.do")
    @ResponseBody
    public OosResult update(UmsMember umsMember){




        try {
            OosResult oosResult = umsMemberService.update(umsMember);
            return oosResult;
        } catch (Exception e) {
            e.printStackTrace();
            return OosResult.build(2, "数据更新失败");
        }


    }






    @PostMapping("/delete.do")
    @ResponseBody
    public OosResult delete(Long memberId){




        try {
            OosResult oosResult = umsMemberService.delete(memberId);
            return oosResult;
        } catch (Exception e) {
            e.printStackTrace();
            return OosResult.build(2, "数据删除失败");
        }


    }


    @PostMapping("/batchDelete.do")
    @ResponseBody
    public OosResult batchDelete(@RequestParam(value = "memberIds[]") List<Long> memberIds){


        try {
            OosResult oosResult = umsMemberService.batchDelete(memberIds);
            return oosResult;
        } catch (Exception e) {
            e.printStackTrace();
            return OosResult.build(2, "数据批量删除失败");
        }


    }



}
