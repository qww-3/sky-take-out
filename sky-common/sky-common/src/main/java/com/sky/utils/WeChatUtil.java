package com.sky.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.sky.properties.WeChatProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
@Slf4j
public class WeChatUtil {

    @Autowired
    private WeChatProperties weChatProperties;

    /**
     * 获取微信用户的openid
     */
    public String getOpenid(String code) {
        // 请求微信接口获取openid
        Map<String, String> paramMap = new HashMap<>();
        paramMap.put("appid", weChatProperties.getAppid());
        paramMap.put("secret", weChatProperties.getSecret());
        paramMap.put("js_code", code);
        paramMap.put("grant_type", "authorization_code");
        
        String wxUrl = "https://api.weixin.qq.com/sns/jscode2session";
        String responseJson = HttpClientUtil.doGet(wxUrl, paramMap);
        log.info("微信登录响应: {}", responseJson);
        
        JSONObject jsonObject = JSON.parseObject(responseJson);
        return jsonObject.getString("openid");
    }
}
