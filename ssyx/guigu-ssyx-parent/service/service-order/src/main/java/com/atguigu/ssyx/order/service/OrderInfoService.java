package com.atguigu.ssyx.order.service;
;
import com.atguigu.ssyx.model.order.OrderInfo;
import com.atguigu.ssyx.vo.order.OrderConfirmVo;
import com.atguigu.ssyx.vo.order.OrderSubmitVo;
import com.baomidou.mybatisplus.extension.service.IService;

/**
* @author Administrator
* @description 针对表【order_info(订单)】的数据库操作Service
* @createDate 2025-08-07 11:13:43
*/
public interface OrderInfoService extends IService<OrderInfo> {

    /**
     * 确认订单
     */
    OrderConfirmVo confirmOrder();

    /**
     * 生成订单
     */
    Long submitOrder(OrderSubmitVo orderSubmitVo, Long userId);

    /**
     * 获取订单详情
     */
    OrderInfo getOrderInfoById(Long orderId);

    /**
     * 根据orderNo查询订单信息
     */
    OrderInfo getOrderInfoByOrderNo(String orderNo);

    /**
     * 订单支付成功，更新订单状态，扣减库存
     */
    void orderPay(String orderNo);
}
