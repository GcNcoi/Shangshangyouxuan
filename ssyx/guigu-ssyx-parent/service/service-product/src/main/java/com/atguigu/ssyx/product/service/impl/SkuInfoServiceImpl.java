package com.atguigu.ssyx.product.service.impl;

import com.atguigu.ssyx.model.product.SkuAttrValue;
import com.atguigu.ssyx.model.product.SkuImage;
import com.atguigu.ssyx.model.product.SkuInfo;
import com.atguigu.ssyx.model.product.SkuPoster;
import com.atguigu.ssyx.product.service.SkuAttrValueService;
import com.atguigu.ssyx.product.service.SkuImageService;
import com.atguigu.ssyx.product.service.SkuPosterService;
import com.atguigu.ssyx.vo.product.SkuInfoQueryVo;
import com.atguigu.ssyx.vo.product.SkuInfoVo;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.atguigu.ssyx.product.service.SkuInfoService;
import com.atguigu.ssyx.product.mapper.SkuInfoMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * @author 信信宝贝
 * @description 针对表【sku_info(sku信息)】的数据库操作Service实现
 * @createDate 2025-07-23 00:15:58
 */
@Service
public class SkuInfoServiceImpl extends ServiceImpl<SkuInfoMapper, SkuInfo>
        implements SkuInfoService {

    @Autowired
    private SkuAttrValueService skuAttrValueService;

    @Autowired
    private SkuImageService skuImageService;

    @Autowired
    private SkuPosterService skuPosterService;

    @Override
    public IPage<SkuInfo> selectPageSkuInfo(Page<SkuInfo> pageParam, SkuInfoQueryVo skuInfoQueryVo) {
        // 获取条件值
        String keyword = skuInfoQueryVo.getKeyword();
        String skuType = skuInfoQueryVo.getSkuType();
        Long categoryId = skuInfoQueryVo.getCategoryId();
        // 封装条件
        LambdaQueryWrapper<SkuInfo> wrapper = new LambdaQueryWrapper<>();
        if (!StringUtils.isEmpty(keyword)) {
            wrapper.like(SkuInfo::getSkuName, keyword);
        }
        if (skuType != null && skuType != "") {
            wrapper.eq(SkuInfo::getSkuType, skuType);
        }
        if (!StringUtils.isEmpty(categoryId)) {
            wrapper.eq(SkuInfo::getCategoryId, categoryId);
        }
        //调用方法查询
        return baseMapper.selectPage(pageParam, wrapper);
    }

    @Override
    public void saveSkuInfo(SkuInfoVo skuInfoVo) {
        // 1.添加sku基本信息
        SkuInfo skuInfo = new SkuInfo();
        BeanUtils.copyProperties(skuInfoVo, skuInfo);
        baseMapper.insert(skuInfo);
        // 2.保存sku海报
        List<SkuPoster> skuPosterList = skuInfoVo.getSkuPosterList();
        if (!CollectionUtils.isEmpty(skuPosterList)) {
            // 遍历:为每个海报添加商品id
            for (SkuPoster skuPoster : skuPosterList) {
                skuPoster.setSkuId(skuInfo.getId());
            }
            skuPosterService.saveBatch(skuPosterList);
        }
        // 3.保存sku图片
        List<SkuImage> skuImagesList = skuInfoVo.getSkuImagesList();
        if (!CollectionUtils.isEmpty(skuImagesList)) {
            // 遍历:为每个图片添加商品id
            for (SkuImage skuImage : skuImagesList) {
                skuImage.setSkuId(skuInfo.getId());
            }
            skuImageService.saveBatch(skuImagesList);
        }
        // 4.保存sku平台属性
        List<SkuAttrValue> skuAttrValueList = skuInfoVo.getSkuAttrValueList();
        if (!CollectionUtils.isEmpty(skuAttrValueList)) {
            // 遍历:为每个属性添加商品id
            for (SkuAttrValue skuAttrValue : skuAttrValueList) {
                skuAttrValue.setSkuId(skuInfo.getId());
            }
            skuAttrValueService.saveBatch(skuAttrValueList);
        }
    }

    @Override
    public SkuInfoVo getSkuInfo(Long id) {
        SkuInfoVo skuInfoVo = new SkuInfoVo();
        // 根据id查询sku基本信息
        SkuInfo skuInfo = baseMapper.selectById(id);
        // 根据id查询商品海报列表
        List<SkuPoster> skuPosterList = skuPosterService.getPosterListBySkuId(id);
        // 根据id查询商品图片列表
        List<SkuImage> skuImageList = skuImageService.getImageListBySkuId(id);
        // 根据id查询商品属性信息列表
        List<SkuAttrValue> skuAttrValueList = skuAttrValueService.getAttrValueListBySkuId(id);
        // 封装数据返回
        BeanUtils.copyProperties(skuInfo, skuInfoVo);
        skuInfoVo.setSkuImagesList(skuImageList);
        skuInfoVo.setSkuPosterList(skuPosterList);
        skuInfoVo.setSkuAttrValueList(skuAttrValueList);
        return skuInfoVo;
    }

    @Override
    public void updateSkuInfo(SkuInfoVo skuInfoVo) {
        Long skuId = skuInfoVo.getId();
        // 修改sku基本信息
        SkuInfo skuInfo = new SkuInfo();
        BeanUtils.copyProperties(skuInfoVo, skuInfo);
        baseMapper.updateById(skuInfo);
        // 修改sku商品海报列表
        skuImageService.remove(new LambdaQueryWrapper<SkuImage>().eq(SkuImage::getSkuId, skuId));
        List<SkuPoster> skuPosterList = skuInfoVo.getSkuPosterList();
        if (!CollectionUtils.isEmpty(skuPosterList)) {
            // 遍历:为每个海报添加商品id
            for (SkuPoster skuPoster : skuPosterList) {
                skuPoster.setSkuId(skuId);
            }
            skuPosterService.saveBatch(skuPosterList);
        }
        // 修改sku商品图片列表
        LambdaQueryWrapper<SkuImage> imageWrapper = new LambdaQueryWrapper<>();
        imageWrapper.eq(SkuImage::getSkuId, skuId);
        skuImageService.remove(imageWrapper);
        List<SkuImage> skuImagesList = skuInfoVo.getSkuImagesList();
        if (!CollectionUtils.isEmpty(skuImagesList)) {
            // 遍历:为每个图片添加商品id
            for (SkuImage skuImage : skuImagesList) {
                skuImage.setSkuId(skuId);
            }
            skuImageService.saveBatch(skuImagesList);
        }
        // 修改sku商品属性信息列表
        skuAttrValueService.remove(new LambdaQueryWrapper<SkuAttrValue>().eq(SkuAttrValue::getSkuId, skuId));
        List<SkuAttrValue> skuAttrValueList = skuInfoVo.getSkuAttrValueList();
        if (!CollectionUtils.isEmpty(skuAttrValueList)) {
            // 遍历:为每个属性添加商品id
            for (SkuAttrValue skuAttrValue : skuAttrValueList) {
                skuAttrValue.setSkuId(skuId);
            }
            skuAttrValueService.saveBatch(skuAttrValueList);
        }
    }

    @Override
    public void check(Long skuId, Integer status) {
        SkuInfo skuInfo = baseMapper.selectById(skuId);
        skuInfo.setCheckStatus(status);
        baseMapper.updateById(skuInfo);
    }

    @Override
    public void publish(Long skuId, Integer status) {
        if (status == 1) {
            // 上架
            SkuInfo skuInfo = baseMapper.selectById(skuId);
            skuInfo.setPublishStatus(status);
            baseMapper.updateById(skuInfo);
            // TODO 整合mq把数据同步到es里面
        } else {
            // 下架
            SkuInfo skuInfo = baseMapper.selectById(skuId);
            skuInfo.setPublishStatus(status);
            baseMapper.updateById(skuInfo);
            // TODO 整合mq把数据同步到es里面
        }
    }

    @Override
    public void isNewPerson(Long skuId, Integer status) {
        SkuInfo skuInfoUp = new SkuInfo();
        skuInfoUp.setId(skuId);
        skuInfoUp.setIsNewPerson(status);
        baseMapper.updateById(skuInfoUp);
    }
}




