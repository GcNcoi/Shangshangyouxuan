package com.atguigu.ssyx.cart.service.impl;

import com.atguigu.ssyx.cart.service.CartInfoService;
import com.atguigu.ssyx.client.product.ProductFeignClient;
import com.atguigu.ssyx.common.constant.RedisConst;
import com.atguigu.ssyx.common.exception.SsyxException;
import com.atguigu.ssyx.common.result.ResultCodeEnum;
import com.atguigu.ssyx.enums.SkuType;
import com.atguigu.ssyx.model.order.CartInfo;
import com.atguigu.ssyx.model.product.SkuInfo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.BoundHashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * @BelongsProject: guigu-ssyx-parent
 * @BelongsPackage: com.atguigu.ssyx.cart.service.impl
 * @Author: GuoXiaofeng
 * @CreateTime: 2025-08-02  21:05
 * @Description: TODO
 * @Version: 1.0
 */
@Service
public class CartInfoServiceImpl implements CartInfoService {

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private ProductFeignClient productFeignClient;

    @Override
    public void addToCart(Long userId, Long skuId, Integer skuNum) {
        // 1. 从redis里面根据key获取数据
        String cartKey = getCartKey(userId);
        BoundHashOperations<String, String, CartInfo> hashOperations = redisTemplate.boundHashOps(cartKey);
        // 2. 根据第一步查询出来的结果，判断是否第一次添加这个商品到购物车
        CartInfo cartInfo = null;
        if (hashOperations.hasKey(skuId.toString())) {
            // 3. 如果结果里面包含skuId，表示不是第一次添加
            // 3.1 根据skuId，获取对应数量，更新数量
            cartInfo = hashOperations.get(skuId.toString());
            int currentSkuNum = cartInfo.getSkuNum() + skuNum;
            if(currentSkuNum < 1) {
                return;
            }
            // 添加购物车数量
            cartInfo.setSkuNum(currentSkuNum);
            // 获取用户当前已经购买的sku个数，sku限量，每天不能超买
            // 当天购买数量
            cartInfo.setCurrentBuyNum(currentSkuNum);
            // 大于限购个数，不能更新个数
            if(currentSkuNum >= cartInfo.getPerLimit()) {
                throw new SsyxException(ResultCodeEnum.SKU_LIMIT_ERROR);
            }
            cartInfo.setIsChecked(1);
            cartInfo.setUpdateTime(new Date());
        } else {
            // 4. 如果结果里面没有skuId，表示为第一次添加
            // 4.1 直接添加
            //第一次添加只能添加一个
            skuNum = 1;
            // 当购物车中没用该商品的时候，则直接添加到购物车！insert
            cartInfo = new CartInfo();
            // 购物车数据是从商品详情得到 {skuInfo}
            SkuInfo skuInfo = productFeignClient.getSkuInfo(skuId);
            if(null == skuInfo) {
                throw new SsyxException(ResultCodeEnum.DATA_ERROR);
            }
            BeanUtils.copyProperties(skuInfo, cartInfo);
            cartInfo.setSkuId(skuId);
            cartInfo.setUserId(userId);
            cartInfo.setSkuNum(skuNum);
            cartInfo.setCurrentBuyNum(skuNum);
            cartInfo.setSkuType(SkuType.COMMON.getCode());
            cartInfo.setIsChecked(1);
            cartInfo.setStatus(1);
            cartInfo.setCreateTime(new Date());
            cartInfo.setUpdateTime(new Date());
        }

        // 5. 更新redis缓存
        hashOperations.put(skuId.toString(), cartInfo);
        // 6.设置有效时间
        this.setCartKeyExpire(cartKey);
    }

    @Override
    public void deleteCart(Long skuId, Long userId) {
        BoundHashOperations<String, String, CartInfo> hashOperations = redisTemplate.boundHashOps(this.getCartKey(userId));
        if (hashOperations.hasKey(skuId.toString())) {
            hashOperations.delete(skuId.toString());
        }
    }

    @Override
    public void deleteAllCart(Long userId) {
        BoundHashOperations<String, String, CartInfo> hashOperations = redisTemplate.boundHashOps(this.getCartKey(userId));
        hashOperations.values().forEach(cartInfo -> {
            hashOperations.delete(cartInfo.getSkuId().toString());
        });
    }

    @Override
    public void batchDeleteCart(List<Long> skuIdList, Long userId) {
        BoundHashOperations<String, String, CartInfo> hashOperations = redisTemplate.boundHashOps(this.getCartKey(userId));
        skuIdList.forEach(skuId -> {
            hashOperations.delete(skuId.toString());
        });
    }

    @Override
    public List<CartInfo> getCartList(Long userId) {
        // 判断userId
        List<CartInfo> cartInfoList = new ArrayList<>();
        if (StringUtils.isEmpty(userId)) {
            return cartInfoList;
        }
        // 从redis获取购物车数据
        BoundHashOperations<String, String, CartInfo> hashOperations = redisTemplate.boundHashOps(this.getCartKey(userId));
        cartInfoList = hashOperations.values();
        if (!CollectionUtils.isEmpty(cartInfoList)) {
            // 根据商品添加时间进行降序排列
            cartInfoList.sort((o1, o2) -> o2.getCreateTime().compareTo(o1.getCreateTime()));
        }
        return cartInfoList;
    }

    // 购物车在redis的key
    private String getCartKey(Long userId) {
        //定义key user:userId:cart
        return RedisConst.USER_KEY_PREFIX + userId + RedisConst.USER_CART_KEY_SUFFIX;
    }

    // 设置key的过期时间！
    private void setCartKeyExpire(String cartKey) {
        redisTemplate.expire(cartKey, RedisConst.USER_CART_EXPIRE, TimeUnit.SECONDS);
    }
}
