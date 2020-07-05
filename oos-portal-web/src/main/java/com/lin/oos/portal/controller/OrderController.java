package com.lin.oos.portal.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.lin.oos.pojo.OmsOrderItem;
import com.lin.oos.pojo.UmsMember;
import com.lin.oos.pojo.UmsMemberReceiveAddress;
import com.lin.oos.service.OrderService;
import com.lin.oos.service.UmsMemberReceiveAddressService;
import com.lin.oos.vo.OosResult;
import com.lin.oos.vo.OrderDetail;
import com.lin.oos.vo.OrderItemDetail;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Controller
@RequestMapping("/order")
public class OrderController {


    @Reference
    private UmsMemberReceiveAddressService umsMemberReceiveAddressService;

    @Reference
    private OrderService orderService;


    @PostMapping("/create.do")
    @ResponseBody
    public String save(OrderDetail order, HttpServletRequest request, HttpServletResponse response){


        try {
            UmsMember umsMember = (UmsMember) request.getAttribute("loginUser");
            Long userId = umsMember.getId();
            orderService.saveOrder(order,userId);

            //清空Cookie
            Cookie[] cookies = request.getCookies();
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("OOS_CART_COOKIE")) {
                    cookie.setValue(null);
                    cookie.setMaxAge(0);// 立即销毁cookie
                    cookie.setPath("/");
                    response.addCookie(cookie);
                    break;
                }
            }

            return "订单提交成功";


        } catch (Exception e) {
            e.printStackTrace();
            /*return OosResult.build(2, "提交失败");*/
            return "订单提交失败";
        }
    }

    @PostMapping("/getOrderInfo.do")
    public String getOrderInfo(Model model, HttpServletRequest request, OrderItemDetail orderItemDetail){


        request.setAttribute("orderItemDetail",orderItemDetail);

        UmsMember umsMember = (UmsMember) request.getAttribute("loginUser");

        List<UmsMemberReceiveAddress> umsMemberReceiveAddresses = umsMemberReceiveAddressService.selectByMemberId(umsMember.getId().intValue());



        if (umsMemberReceiveAddresses.size() != 0) {
            request.setAttribute("umsMemberReceiveAddresses",umsMemberReceiveAddresses);
        }

        return "getOrderInfo";
    }

    @PostMapping("/addAddress.do")
    @ResponseBody
    public OosResult addAddress(UmsMemberReceiveAddress umsMemberReceiveAddress,HttpServletRequest request) {

        try {

            UmsMember umsMember = (UmsMember)request.getAttribute("loginUser");

            umsMemberReceiveAddress.setMemberId(umsMember.getId());

            return  umsMemberReceiveAddressService.save(umsMemberReceiveAddress);
        } catch (Exception e) {
            e.printStackTrace();
            return OosResult.build(2, "增加失败");
        }


    }


    @PostMapping("/delAddress.do")
    @ResponseBody
    public OosResult delAddress(Integer addressId){


        try {
            OosResult oosResult = umsMemberReceiveAddressService.delete(addressId);
            return oosResult;
        } catch (Exception e) {
            e.printStackTrace();
            return OosResult.build(2, "数据删除失败");
        }


    }

    @RequestMapping("/editAddress.do")
    public String editAddress(Model m, Integer addressId){

        if(addressId != null){

            UmsMemberReceiveAddress umsMemberReceiveAddress = umsMemberReceiveAddressService.selectByPrimaryKey(addressId);
            m.addAttribute("umsMemberReceiveAddress",umsMemberReceiveAddress);

        }


        return "forward:/address.html";
    }

    @PostMapping("/updateAddress.do")
    @ResponseBody
    public OosResult updateAddress(UmsMemberReceiveAddress umsMemberReceiveAddress){




        try {
            OosResult oosResult = umsMemberReceiveAddressService.update(umsMemberReceiveAddress);
            return oosResult;
        } catch (Exception e) {
            e.printStackTrace();
            return OosResult.build(2, "数据更新失败");
        }


    }

}
