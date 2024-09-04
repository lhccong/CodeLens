package com.cong.middleware.test;

import com.alibaba.fastjson2.JSON;
import com.cong.middleware.sdk.infrastructure.openai.dto.ChatCompletionSyncResponseDTO;
import com.cong.middleware.sdk.infrastructure.weixin.dto.TemplateMessageDTO;
import com.cong.middleware.sdk.types.utils.BearerTokenUtils;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;


public class ApiTest {
    static String apiKey = "5ec1978b2e4dabc5f2e719b509275202.DKpwZDd3tGImaxTH";

    public static void main(String[] args) {


        String token = BearerTokenUtils.getToken(apiKey);
        System.out.println(token);
    }

    @Test
    public void testHttp() throws IOException {
        String token = BearerTokenUtils.getToken(apiKey);

        URL url = new URL("https://open.bigmodel.cn/api/paas/v4/chat/completions");
        HttpURLConnection connection = getHttpURLConnection(url, token, "1+1");

        int responseCode = connection.getResponseCode();
        System.out.println(responseCode);

        BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        String inputLine;

        StringBuilder content = new StringBuilder();
        while ((inputLine = in.readLine()) != null) {
            content.append(inputLine);
        }

        in.close();
        connection.disconnect();

        ChatCompletionSyncResponseDTO response = JSON.parseObject(content.toString(), ChatCompletionSyncResponseDTO.class);
        System.out.println(response.getChoices().get(0).getMessage().getContent());

    }

    private static HttpURLConnection getHttpURLConnection(URL url, String token, String code) throws IOException {
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();

        connection.setRequestMethod("POST");
        connection.setRequestProperty("Authorization", "Bearer " + token);
        connection.setRequestProperty("Content-Type", "application/json");
        connection.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");
        connection.setDoOutput(true);


        String jsonInpuString = "{"
                + "\"model\":\"glm-4-flash\","
                + "\"messages\": ["
                + "    {"
                + "        \"role\": \"user\","
                + "        \"content\": \"你是一个高级编程架构师，精通各类场景方案、架构设计和编程语言请，请您根据git diff记录，对代码做出评审。代码为: " + code + "\""
                + "    }"
                + "]"
                + "}";


        try (OutputStream os = connection.getOutputStream()) {
            byte[] input = jsonInpuString.getBytes(StandardCharsets.UTF_8);
            os.write(input);
        }
        return connection;
    }

    @Test
    public void test_wx() {
//        String accessToken = WXAccessTokenUtils.getAccessToken();
//        System.out.println(accessToken);

        TemplateMessageDTO templateMessageDTO = new TemplateMessageDTO("","");
        templateMessageDTO.put("project","代码评审");
        templateMessageDTO.put("review","feat: 新加功能");

        String url = String.format("https://api.weixin.qq.com/cgi-bin/message/template/send?access_token=%s", "84_zJKM3QauYpPp1WuMQtA0KaCWfqyXGtnrcIBbhEsbHhZehmagCPy1wBkYhFO7ie-a4DNzurUNwl4cZcUbl55EvGeDLGTVFm38suzSkFJtnHYwm348Vt10f1uH6rQIWQaAAAVJD");
        sendPostRequest(url, JSON.toJSONString(templateMessageDTO));
    }

    private static void sendPostRequest(String urlString, String jsonBody) {
        try {
            URL url = new URL(urlString);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json; utf-8");
            conn.setRequestProperty("Accept", "application/json");
            conn.setDoOutput(true);

            try (OutputStream os = conn.getOutputStream()) {
                byte[] input = jsonBody.getBytes(StandardCharsets.UTF_8);
                os.write(input, 0, input.length);
            }

            try (Scanner scanner = new Scanner(conn.getInputStream(), StandardCharsets.UTF_8.name())) {
                String response = scanner.useDelimiter("\\A").next();
                System.out.println(response);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
