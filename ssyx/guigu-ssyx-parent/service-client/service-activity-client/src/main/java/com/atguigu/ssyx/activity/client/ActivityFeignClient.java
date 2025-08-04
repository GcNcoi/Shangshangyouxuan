package com.atguigu.ssyx.activity.client;

import com.atguigu.ssyx.model.order.CartInfo;
import com.atguigu.ssyx.vo.order.OrderConfirmVo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@FeignClient("service-activity")
public interface ActivityFeignClient {

    @PostMapping("/api/activity/inner/findActivity")
    Map<Long, List<String>> findActivity(@RequestBody List<Long> skuIdList);

    @GetMapping("/api/activity/inner/findActivityAndCoupon/{skuId}/{userId}")
    Map<String, Object> findActivityAndCoupon(@PathVariable("skuId") Long skuId, @PathVariable("userId") Long userId);

    @PostMapping("/api/activity/inner/findCartActivityAndCoupon/{userId}")
    OrderConfirmVo findCartActivityAndCoupon(@RequestBody List<CartInfo> cartInfoList, @PathVariable("userId") Long userId);

}
