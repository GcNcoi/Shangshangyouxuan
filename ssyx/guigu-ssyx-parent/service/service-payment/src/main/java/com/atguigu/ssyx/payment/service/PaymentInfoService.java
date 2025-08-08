package com.atguigu.ssyx.payment.service;

import com.atguigu.ssyx.model.order.PaymentInfo;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
* @author Administrator
* @description 针对表【payment_info(支付信息表)】的数据库操作Service
* @createDate 2025-08-08 09:36:04
*/
public interface PaymentInfoService extends IService<PaymentInfo> {

    /**
     * 根据订单编号查询支付信息
     * @param orderNo
     * @return
     */
    PaymentInfo getPaymentInfoByOrderNo(String orderNo);

    /**
     * 保存支付信息
     * @param orderNo
     * @return
     */
    PaymentInfo savePaymentInfo(String orderNo);

    /**
     * 支付成功
     * @param outTradeNo
     * @param resultMap
     */
    void paySuccess(String outTradeNo, Map<String, String> resultMap);
}
