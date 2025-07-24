package com.atguigu.ssyx.search.repository;

import com.atguigu.ssyx.model.search.SkuEs;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * 功能描述: 商品搜索数据接口
 *
 * @author: Gxf
 * @date: 2025年07月24日 16:39
 */
public interface SkuRepository extends ElasticsearchRepository<SkuEs, Long> {

}