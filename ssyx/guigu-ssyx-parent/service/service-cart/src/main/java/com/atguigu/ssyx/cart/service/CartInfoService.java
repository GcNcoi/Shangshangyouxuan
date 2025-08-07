package com.atguigu.ssyx.cart.service;

import com.atguigu.ssyx.model.order.CartInfo;

import java.util.List;

public interface CartInfoService {

    /**
     * 添加商品到购物车
     * @param userId
     * @param skuId
     * @param skuNum
     */
    void addToCart(Long userId, Long skuId, Integer skuNum);

    /**
     * 删除购物车商品
     * @param skuId
     * @param userId
     */
    void deleteCart(Long skuId, Long userId);

    /**
     * 清空购物车
     * @param userId
     */
    void deleteAllCart(Long userId);

    /**
     * 批量删除购物车商品
     * @param skuIdList
     * @param userId
     */
    void batchDeleteCart(List<Long> skuIdList, Long userId);

    /**
     * 获取购物车列表
     * @param userId
     * @return
     */
    List<CartInfo> getCartList(Long userId);

    /**
     * 购物车商品选中
     * @param userId
     * @param isChecked
     * @param skuId
     */
    void checkCart(Long userId, Integer isChecked, Long skuId);

    /**
     * 购物车全选
     * @param userId
     * @param isChecked
     */
    void checkAllCart(Long userId, Integer isChecked);

    /**
     * 批量购物车商品选中
     * @param skuIdList
     * @param userId
     * @param isChecked
     */
    void batchCheckCart(List<Long> skuIdList, Long userId, Integer isChecked);

    /**
     * 获取购物车选中商品列表
     * @param userId
     * @return
     */
    List<CartInfo> getCartCheckedList(Long userId);
}
