package com.cong.middleware.sdk;

import com.cong.middleware.sdk.domain.service.impl.OpenAiCodeReviewService;
import com.cong.middleware.sdk.infrastructure.git.GitCommand;
import com.cong.middleware.sdk.infrastructure.openai.OpenAI;
import com.cong.middleware.sdk.infrastructure.openai.impl.ChatGLM;
import com.cong.middleware.sdk.infrastructure.weixin.WeiXin;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class OpenAiCodeReview {
    private  static final Logger logger = LoggerFactory.getLogger(OpenAiCodeReview.class);

    public static void main(String[] args) throws Exception {
        GitCommand gitCommand = new GitCommand(
                getEnv("GITHUB_REVIEW_LOG_URI"),
                getEnv("GITHUB_TOKEN"),
                getEnv("COMMIT_PROJECT"),
                getEnv("COMMIT_BRANCH"),
                getEnv("COMMIT_AUTHOR"),
                getEnv("COMMIT_MESSAGE")
        );

        //
        WeiXin weiXin = new WeiXin(
                getEnv("APPID"),
                getEnv("SECRET"),
                getEnv("WEIXIN_TOUSER"),
                getEnv("WEIXIN_TEMPLATE_ID")
        );

        OpenAI openAI = new ChatGLM(getEnv("GLM_API_HOST"), getEnv("GLM_API_KEY"));
        OpenAiCodeReviewService openAiCodeReviewService = new OpenAiCodeReviewService(gitCommand, openAI, weiXin);
        openAiCodeReviewService.exec();

        logger.info("代码评审结束");

    }
    private static String getEnv(String key) {
        String value = System.getenv(key);
        if (null == value || value.isEmpty()) {
            throw new RuntimeException("value is null");
        }
        return value;
    }

}
