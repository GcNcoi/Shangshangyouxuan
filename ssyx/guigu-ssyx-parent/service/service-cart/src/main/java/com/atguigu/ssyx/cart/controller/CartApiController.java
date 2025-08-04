package com.atguigu.ssyx.cart.controller;

import com.atguigu.ssyx.activity.client.ActivityFeignClient;
import com.atguigu.ssyx.cart.service.CartInfoService;
import com.atguigu.ssyx.common.auth.AuthContextHolder;
import com.atguigu.ssyx.common.result.Result;
import com.atguigu.ssyx.model.order.CartInfo;
import com.atguigu.ssyx.vo.order.OrderConfirmVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @BelongsProject: guigu-ssyx-parent
 * @BelongsPackage: com.atguigu.ssyx.cart.controller
 * @Author: GuoXiaofeng
 * @CreateTime: 2025-08-02  21:05
 * @Description: TODO
 * @Version: 1.0
 */
@Api(tags = "购物车接口")
@RestController
@RequestMapping("/api/cart")
public class CartApiController {

    @Autowired
    private CartInfoService cartInfoService;

    @Autowired
    private ActivityFeignClient activityFeignClient;

    @ApiOperation("添加购物车")
    @GetMapping("/addToCart/{skuId}/{skuNum}")
    public Result addToCart(@PathVariable("skuId") Long skuId,
                            @PathVariable("skuNum") Integer skuNum) {
        Long userId = AuthContextHolder.getUserId();
        cartInfoService.addToCart(userId, skuId, skuNum);
        return Result.ok();
    }

    @ApiOperation("删除购物车")
    @DeleteMapping("/deleteCart/{skuId}")
    public Result deleteCart(@PathVariable("skuId") Long skuId,
                             HttpServletRequest request) {
        Long userId = AuthContextHolder.getUserId();
        cartInfoService.deleteCart(skuId, userId);
        return Result.ok();
    }

    @ApiOperation(value="清空购物车")
    @DeleteMapping("/deleteAllCart")
    public Result deleteAllCart(HttpServletRequest request){
        Long userId = AuthContextHolder.getUserId();
        cartInfoService.deleteAllCart(userId);
        return Result.ok();
    }

    @ApiOperation(value="批量删除购物车")
    @PostMapping("/batchDeleteCart")
    public Result batchDeleteCart(@RequestBody List<Long> skuIdList, HttpServletRequest request){
        Long userId = AuthContextHolder.getUserId();
        cartInfoService.batchDeleteCart(skuIdList, userId);
        return Result.ok();
    }

    @ApiOperation(value="查询购物车列表")
    @GetMapping("/cartList")
    public Result cartList(HttpServletRequest request) {
        Long userId = AuthContextHolder.getUserId();
        List<CartInfo> cartInfoList = cartInfoService.getCartList(userId);
        return Result.ok(cartInfoList);
    }

    @ApiOperation(value="查询带优惠卷的购物车")
    @GetMapping("/activityCartList")
    public Result activityCartList(HttpServletRequest request) {
        Long userId = AuthContextHolder.getUserId();
        List<CartInfo> cartInfoList = cartInfoService.getCartList(userId);
        OrderConfirmVo orderTradeVo = activityFeignClient.findCartActivityAndCoupon(cartInfoList, userId);
        return Result.ok(orderTradeVo);
    }

}
