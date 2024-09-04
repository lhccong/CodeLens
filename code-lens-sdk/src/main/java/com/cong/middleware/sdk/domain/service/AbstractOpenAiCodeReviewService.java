package com.cong.middleware.sdk.domain.service;

import com.cong.middleware.sdk.infrastructure.git.GitCommand;
import com.cong.middleware.sdk.infrastructure.openai.OpenAI;
import com.cong.middleware.sdk.infrastructure.weixin.WeiXin;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public abstract class AbstractOpenAiCodeReviewService implements OpenAiCodeReviewService {

    private final Logger logger = LoggerFactory.getLogger(AbstractOpenAiCodeReviewService.class);

    protected final GitCommand gitCommand;
    protected final OpenAI openAI;
    protected final WeiXin weiXin;

    protected AbstractOpenAiCodeReviewService(GitCommand gitCommand, OpenAI openAI, WeiXin weiXin) {
        this.gitCommand = gitCommand;
        this.openAI = openAI;
        this.weiXin = weiXin;
    }

    @Override
    public void exec() {
        try {
            logger.info("开始代码评审");
            // 1. 获取提交代码
            String diffCode = getDiffCode();
            logger.info("获取提交代码成功");
            // 2. 开始评审代码
            String recommend = codeReview(diffCode);
            logger.info("评审代码完成：{}", recommend);
            // 3. 记录评审结果；返回日志地址
            String logUrl = recordCodeReview(recommend);
            logger.info("记录评审结果成功地址：{}", logUrl);
            // 4. 发送消息通知；日志地址、通知的内容
            pushMessage(logUrl);
            logger.info("发送消息通知成功");
        } catch (Exception e) {
            logger.error("代码评审异常", e);
        }

    }

    protected abstract String getDiffCode() throws IOException, InterruptedException;

    protected abstract String codeReview(String diffCode) throws Exception;

    protected abstract String recordCodeReview(String recommend) throws Exception;

    protected abstract void pushMessage(String logUrl) throws Exception;

}
