package com.lin.oos.portal.service.impl;


import com.alibaba.dubbo.config.annotation.Reference;
import com.lin.oos.pojo.PmsProductItem;
import com.lin.oos.service.CartService;
import com.lin.oos.service.PmsProductItemService;
import com.lin.oos.vo.CartItem;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@com.alibaba.dubbo.config.annotation.Service(interfaceClass = CartService.class)
public class CartServiceImpl implements CartService {


    @Override
    public List<CartItem> addToCart(PmsProductItem item, List<CartItem> cartItemsOld) {
        boolean itemExist = false;
        for (CartItem cartItem : cartItemsOld) {
            if (cartItem.getId().equals(item.getId())) {
                int total = cartItem.getNum() + 1;
                cartItem.setNum(total);
                itemExist = true;
                break;
            }
        }

        if (!itemExist) {
            CartItem cartItem = new CartItem();
            cartItem.setNum(1);
            cartItem.setId(item.getId());
            cartItem.setTitle(item.getTitle());
            cartItem.setImage(item.getImage());
            cartItem.setPrice(item.getPrice());

            cartItemsOld.add(cartItem);
        }


        return cartItemsOld;
    }

    @Override
    public List<CartItem> minToCart(PmsProductItem item, List<CartItem> cartItemsOld) {

        for (CartItem cartItem : cartItemsOld) {
            if (cartItem.getId().equals(item.getId())) {
                int total = cartItem.getNum() - 1;

                if (total == 0) {

                    cartItemsOld.remove(cartItem);

                    break;
                }


                cartItem.setNum(total);
                break;
            }
        }


        return cartItemsOld;
    }

    @Override
    public List<CartItem> deleteToCart(PmsProductItem item, List<CartItem> cartItemsOld) {


        for (CartItem cartItem : cartItemsOld) {
            if (cartItem.getId().equals(item.getId())) {
                cartItemsOld.remove(cartItem);
                break;
            }
        }

        return cartItemsOld;
    }
}
