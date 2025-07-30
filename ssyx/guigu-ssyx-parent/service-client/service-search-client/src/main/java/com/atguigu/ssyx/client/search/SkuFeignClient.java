package com.atguigu.ssyx.client.search;

import com.atguigu.ssyx.model.search.SkuEs;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@FeignClient(value = "service-search")
public interface SkuFeignClient {

    @GetMapping("/api/search/sku/inner/findHotSkuList")
    List<SkuEs> findHotSkuList();

}
