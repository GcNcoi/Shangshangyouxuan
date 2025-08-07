package com.atguigu.ssyx.order.service.impl;

import com.atguigu.ssyx.model.order.OrderItem;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.atguigu.ssyx.order.service.OrderItemService;
import com.atguigu.ssyx.order.mapper.OrderItemMapper;
import org.springframework.stereotype.Service;

/**
* @author Administrator
* @description 针对表【order_item(订单项信息)】的数据库操作Service实现
* @createDate 2025-08-07 17:31:59
*/
@Service
public class OrderItemServiceImpl extends ServiceImpl<OrderItemMapper, OrderItem>
    implements OrderItemService{

}




