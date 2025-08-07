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

    /**
     * 上架商品
     * @param skuId
     */
    void upperSku(Long skuId);

    /**
     * 下架商品
     * @param skuId
     */
    void lowerSku(Long skuId);

    /**
     * 获取爆品商品
     * @return
     */
    List<SkuEs> findHotSkuList();

    /**
     * 查询分类商品
     * @param pageable
     * @param skuEsQueryVo
     * @return
     */
    Page<SkuEs> search(Pageable pageable, SkuEsQueryVo skuEsQueryVo);

    /**
     * 增加商品的热度
     * @param skuId
     */
    void incrHotScore(Long skuId);
}
