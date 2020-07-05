package com.lin.oos.portal.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import com.lin.oos.pojo.PmsProductItem;
import com.lin.oos.service.CartService;
import com.lin.oos.service.PmsProductItemService;
import com.lin.oos.vo.CartItem;
import com.lin.oos.vo.OosResult;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/cart")
public class CartController {


    private static final String OOS_CART_COOKIE = "OOS_CART_COOKIE" ;


    @Reference
    private CartService cartService;

    @Reference
    private PmsProductItemService pmsProductItemService;


    @GetMapping("/add.do")
    @ResponseBody
    public OosResult add(Integer itemId, HttpServletRequest request, HttpServletResponse response) {


        PmsProductItem item = pmsProductItemService.selectByPrimaryKey(itemId);

        List<CartItem> cartItemsOld = this.getCart(request);

        List<CartItem> cartItemsNew = cartService.addToCart(item,cartItemsOld);


        String encode = null;
        try {
            encode = URLEncoder.encode(JSON.toJSONString(cartItemsNew), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        Cookie c = new Cookie(OOS_CART_COOKIE, encode);
        c.setMaxAge(1*60*60*24*2);
        c.setPath("/");

        response.addCookie(c);


        return OosResult.ok();
    }



    @GetMapping("/min.do")
    @ResponseBody
    public OosResult min(Integer itemId, HttpServletRequest request, HttpServletResponse response) {


        PmsProductItem item = pmsProductItemService.selectByPrimaryKey(itemId);

        List<CartItem> cartItemsOld = this.getCart(request);

        List<CartItem> cartItemsNew = cartService.minToCart(item,cartItemsOld);


        String encode = null;
        try {
            encode = URLEncoder.encode(JSON.toJSONString(cartItemsNew), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        Cookie c = new Cookie(OOS_CART_COOKIE, encode);
        c.setPath("/");

        response.addCookie(c);


        return OosResult.ok();
    }


    @GetMapping("/delete.do")
    @ResponseBody
    public OosResult delete(Integer itemId, HttpServletRequest request, HttpServletResponse response) {


        PmsProductItem item = pmsProductItemService.selectByPrimaryKey(itemId);

        List<CartItem> cartItemsOld = this.getCart(request);

        List<CartItem> cartItemsNew = cartService.deleteToCart(item,cartItemsOld);


        String encode = null;
        try {
            encode = URLEncoder.encode(JSON.toJSONString(cartItemsNew), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        Cookie c = new Cookie(OOS_CART_COOKIE, encode);
        c.setPath("/");

        response.addCookie(c);


        return OosResult.ok();
    }



    private List<CartItem> getCart(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        boolean flag=false;
        List<CartItem>  cartItems=null;
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals(OOS_CART_COOKIE)) {
                    String value = cookie.getValue();
                    String decode = null;
                    try {
                        decode = URLDecoder.decode(value, "UTF-8");
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                    cartItems = JSON.parseArray(decode,CartItem.class);

                    flag=true;
                    break;
                }

            }
        }

        if (!flag) {
            cartItems = new ArrayList<>();
        }

        return cartItems;
    }
}
