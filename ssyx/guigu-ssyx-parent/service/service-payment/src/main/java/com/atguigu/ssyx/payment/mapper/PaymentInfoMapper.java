package com.atguigu.ssyx.payment.mapper;

import com.atguigu.ssyx.model.order.PaymentInfo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
* @author Administrator
* @description 针对表【payment_info(支付信息表)】的数据库操作Mapper
* @createDate 2025-08-08 09:36:04
* @Entity com.atguigu.ssyx.payment.PaymentInfo
*/
@Mapper
public interface PaymentInfoMapper extends BaseMapper<PaymentInfo> {

}




