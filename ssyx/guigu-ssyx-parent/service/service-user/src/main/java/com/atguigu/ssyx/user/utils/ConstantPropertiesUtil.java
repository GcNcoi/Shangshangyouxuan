package com.atguigu.ssyx.user.utils;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @BelongsProject: guigu-ssyx-parent
 * @BelongsPackage: com.atguigu.ssyx.user.utils
 * @Author: GuoXiaofeng
 * @CreateTime: 2025-07-29  00:16
 * @Description: TODO
 * @Version: 1.0
 */
@Component
public class ConstantPropertiesUtil implements InitializingBean {

    @Value("${wx.open.app_id}")
    private String appId;

    @Value("${wx.open.app_secret}")
    private String appSecret;

    public static String WX_OPEN_APP_ID;
    public static String WX_OPEN_APP_SECRET;

    @Override
    public void afterPropertiesSet() throws Exception {
        WX_OPEN_APP_ID = appId;
        WX_OPEN_APP_SECRET = appSecret;
    }
}