package com.atguigu.ssyx.payment.service;

import java.util.Map;

public interface WeixinService {

    /**
     * 根据订单号下单，生成支付链接
     * @param orderNo
     * @return
     */
    Map<String, String> createJsapi(String orderNo);

    /**
     * 查询订单支付状态
     * @param orderNo
     * @return
     */
    Map<String, String> queryPayStatus(String orderNo);
}
