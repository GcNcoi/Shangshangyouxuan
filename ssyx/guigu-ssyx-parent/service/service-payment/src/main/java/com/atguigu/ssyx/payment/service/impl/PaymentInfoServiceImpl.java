package com.atguigu.ssyx.payment.service.impl;

import com.atguigu.ssyx.common.exception.SsyxException;
import com.atguigu.ssyx.common.result.ResultCodeEnum;
import com.atguigu.ssyx.constant.MqConst;
import com.atguigu.ssyx.enums.PaymentStatus;
import com.atguigu.ssyx.enums.PaymentType;
import com.atguigu.ssyx.model.order.OrderInfo;
import com.atguigu.ssyx.model.order.PaymentInfo;
import com.atguigu.ssyx.order.client.OrderFeignClient;
import com.atguigu.ssyx.service.RabbitService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.atguigu.ssyx.payment.service.PaymentInfoService;
import com.atguigu.ssyx.payment.mapper.PaymentInfoMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Map;

/**
* @author Administrator
* @description 针对表【payment_info(支付信息表)】的数据库操作Service实现
* @createDate 2025-08-08 09:36:04
*/
@Service
public class PaymentInfoServiceImpl extends ServiceImpl<PaymentInfoMapper, PaymentInfo>
    implements PaymentInfoService{

    @Autowired
    private OrderFeignClient orderFeignClient;

    @Autowired
    private RabbitService rabbitService;

    @Override
    public PaymentInfo getPaymentInfoByOrderNo(String orderNo) {
        LambdaQueryWrapper<PaymentInfo> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(PaymentInfo::getOrderNo, orderNo);
        return baseMapper.selectOne(wrapper);
    }

    @Override
    public PaymentInfo savePaymentInfo(String orderNo) {
        // 远程调用接口，根据orderNo查询订单信息
        OrderInfo orderInfo = orderFeignClient.getOrderInfoByOrderNo(orderNo);
        if (orderInfo == null) {
            throw new SsyxException(ResultCodeEnum.DATA_ERROR);
        }
        // 封装到PaymentInfo对象
        PaymentInfo paymentInfo = new PaymentInfo();
        BeanUtils.copyProperties(orderInfo, paymentInfo);
        paymentInfo.setCreateTime(new Date());
        paymentInfo.setPaymentType(PaymentType.WEIXIN);
        paymentInfo.setPaymentStatus(PaymentStatus.UNPAID);
        String subject = "userId" + orderInfo.getUserId() + "下订单";
        paymentInfo.setSubject(subject);
        // TODO 从订单中获取支付金额:测试金额
        paymentInfo.setTotalAmount(new BigDecimal("0.01"));
        // 调用方式实现添加
        baseMapper.insert(paymentInfo);
        return paymentInfo;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void paySuccess(String outTradeNo, Map<String, String> resultMap) {
        // 1. 查询当前订单支付记录表状态是否是已经支付
        PaymentInfo paymentInfo = getPaymentInfoByOrderNo(outTradeNo);
        if (paymentInfo.getPaymentStatus() != PaymentStatus.UNPAID) {
            return;
        }
        // 2. 如果支付记录表支付状态为未支付，则更新状态
        paymentInfo.setPaymentStatus(PaymentStatus.PAID);
        paymentInfo.setTradeNo(resultMap.get("ransaction_id"));
        paymentInfo.setCallbackTime(new Date());
        paymentInfo.setCallbackContent(resultMap.toString());
        baseMapper.updateById(paymentInfo);
        // 3. 整合RabbitMQ实现：修改订单记录为已支付，扣减库存
        rabbitService.sendMessage(MqConst.EXCHANGE_PAY_DIRECT, MqConst.ROUTING_PAY_SUCCESS, outTradeNo);
    }

}




