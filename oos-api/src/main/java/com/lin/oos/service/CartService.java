package com.lin.oos.service;

import com.lin.oos.pojo.PmsProductItem;
import com.lin.oos.vo.CartItem;

import java.util.List;

public interface CartService {

    List<CartItem> addToCart(PmsProductItem item, List<CartItem> cartItemsOld);

    List<CartItem> minToCart(PmsProductItem item, List<CartItem> cartItemsOld);

    List<CartItem> deleteToCart(PmsProductItem item, List<CartItem> cartItemsOld);
}
