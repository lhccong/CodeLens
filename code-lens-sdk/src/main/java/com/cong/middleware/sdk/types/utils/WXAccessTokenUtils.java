package com.cong.middleware.sdk.types.utils;

import com.alibaba.fastjson2.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * wxaccess 令牌实用程序
 *
 * @author cong
 * @date 2024/09/04
 */
public class WXAccessTokenUtils {
    private static final Logger logger = LoggerFactory.getLogger(WXAccessTokenUtils.class);
    private static final String APPID = System.getenv("APPID");
    private static final String SECRET = System.getenv("SECRET");
    private static final String GRANT_TYPE = "client_credential";
    private static final String URL_TEMPLATE = "https://api.weixin.qq.com/cgi-bin/token?grant_type=%s&appid=%s&secret=%s";

    public static String getAccessToken() {
        return getAccessToken(APPID, SECRET);
    }

    public static String getAccessToken(String APPID, String SECRET) {
        try {
            String urlString = String.format(URL_TEMPLATE, GRANT_TYPE, APPID, SECRET);
            URL url = new URL(urlString);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            int responseCode = connection.getResponseCode();

            if (responseCode == HttpURLConnection.HTTP_OK) {
                BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String inputLine;
                StringBuilder response = new StringBuilder();

                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();

                // Print the response
                logger.info("Response: {}", response);

                Token token = JSON.parseObject(response.toString(), Token.class);

                return token.getAccess_token();
            } else {
                logger.error("Failed to get access token");
                return null;
            }
        } catch (Exception e) {
            logger.error("Failed to get access token", e);
            return null;
        }
    }

    public static class Token {
        private String access_token;
        private Integer expires_in;

        public String getAccess_token() {
            return access_token;
        }

        public void setAccess_token(String access_token) {
            this.access_token = access_token;
        }

        public Integer getExpires_in() {
            return expires_in;
        }

        public void setExpires_in(Integer expires_in) {
            this.expires_in = expires_in;
        }
    }


}
