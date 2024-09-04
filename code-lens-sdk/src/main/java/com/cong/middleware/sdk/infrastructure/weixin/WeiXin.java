package com.cong.middleware.sdk.infrastructure.weixin;


import com.alibaba.fastjson2.JSON;
import com.cong.middleware.sdk.infrastructure.weixin.dto.TemplateMessageDTO;
import com.cong.middleware.sdk.types.utils.WXAccessTokenUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.Scanner;

public class WeiXin {

    private final Logger logger = LoggerFactory.getLogger(WeiXin.class);

    private final String appid;

    private final String secret;

    private final String toUser;

    private final String templateId;

    public WeiXin(String appid, String secret, String toUser, String templateId) {
        this.appid = appid;
        this.secret = secret;
        this.toUser = toUser;
        this.templateId = templateId;
    }

    public void sendTemplateMessage(String logUrl, Map<String, Map<String, String>> data) throws Exception {
        String accessToken = WXAccessTokenUtils.getAccessToken(appid, secret);

        TemplateMessageDTO templateMessageDTO = new TemplateMessageDTO(toUser, templateId);
        templateMessageDTO.setUrl(logUrl);
        templateMessageDTO.setData(data);

        URL url = new URL(String.format("https://api.weixin.qq.com/cgi-bin/message/template/send?access_token=%s", accessToken));
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Content-Type", "application/json; utf-8");
        conn.setRequestProperty("Accept", "application/json");
        conn.setDoOutput(true);

        try (OutputStream os = conn.getOutputStream()) {
            byte[] input = JSON.toJSONString(templateMessageDTO).getBytes(StandardCharsets.UTF_8);
            os.write(input, 0, input.length);
        }

        try (Scanner scanner = new Scanner(conn.getInputStream(), StandardCharsets.UTF_8.name())) {
            String response = scanner.useDelimiter("\\A").next();
            logger.info("代码评审微信 template message! {}", response);
        }
    }

}
