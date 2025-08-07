package com.atguigu.ssyx.product.service;

import com.atguigu.ssyx.model.product.SkuInfo;
import com.atguigu.ssyx.vo.product.SkuInfoQueryVo;
import com.atguigu.ssyx.vo.product.SkuInfoVo;
import com.atguigu.ssyx.vo.product.SkuStockLockVo;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
* @author 信信宝贝
* @description 针对表【sku_info(sku信息)】的数据库操作Service
* @createDate 2025-07-23 00:15:58
*/
public interface SkuInfoService extends IService<SkuInfo> {
    /**
     * 获取sku页列表
     * @param pageParam 分页参数
     * @param skuInfoQueryVo 查询参数
     * @return
     */
    IPage<SkuInfo> selectPageSkuInfo(Page<SkuInfo> pageParam, SkuInfoQueryVo skuInfoQueryVo);

    /**
     * 商品添加方法
     * @param skuInfoVo
     */
    void saveSkuInfo(SkuInfoVo skuInfoVo);

    /**
     * 获取sku详情
     * @param id
     * @return
     */
    SkuInfoVo getSkuInfoVo(Long id);

    /**
     * 更新商品信息
     * @param skuInfoVo
     */
    void updateSkuInfo(SkuInfoVo skuInfoVo);

    /**
     * 商品审核
     * @param skuId
     * @param status
     */
    void check(Long skuId, Integer status);

    /**
     * 商品上架
     * @param skuId
     * @param status
     */
    void publish(Long skuId, Integer status);

    /**
     * 新人专享
     * @param skuId
     * @param status
     */
    void isNewPerson(Long skuId, Integer status);

    /**
     * 根据skuId列表得到sku列表
     * @param skuIdList
     */
    List<SkuInfo> findSkuInfoList(List<Long> skuIdList);

    /**
     * 根据关键字匹配sku列表
     * @param keyword
     */
    List<SkuInfo> findSkuInfoByKeyWord(String keyword);

    /**
     * 获取新人专享列表
     * @return
     */
    List<SkuInfo> findNewPersonList();

    /**
     * 检查并锁定库存
     * @param skuStockLockVoList
     * @param orderNo
     * @return
     */
    Boolean checkAndLock(List<SkuStockLockVo> skuStockLockVoList, String orderNo);
}
