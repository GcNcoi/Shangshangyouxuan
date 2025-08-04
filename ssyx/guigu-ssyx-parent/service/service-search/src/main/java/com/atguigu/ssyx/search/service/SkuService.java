package com.atguigu.ssyx.search.service;

import com.atguigu.ssyx.model.search.SkuEs;
import com.atguigu.ssyx.vo.search.SkuEsQueryVo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface SkuService {

    void upperSku(Long skuId);

    void lowerSku(Long skuId);

    List<SkuEs> findHotSkuList();

    Page<SkuEs> search(Pageable pageable, SkuEsQueryVo skuEsQueryVo);

    void incrHotScore(Long skuId);
}
