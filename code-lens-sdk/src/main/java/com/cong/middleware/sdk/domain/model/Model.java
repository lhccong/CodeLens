package com.cong.middleware.sdk.domain.model;

public enum Model {

    @Deprecated
    CHATGLM_6B_SSE("chatGLM_6b_SSE", "ChatGLM-6B 测试模型"),
    @Deprecated
    CHATGLM_LITE("chatglm_lite", "轻量版模型，适用对推理速度和成本敏感的场景"),
    @Deprecated
    CHATGLM_LITE_32K("chatglm_lite_32k", "标准版模型，适用兼顾效果和成本的场景"),
    @Deprecated
    CHATGLM_STD("chatglm_std", "适用于对知识量、推理能力、创造力要求较高的场景"),
    @Deprecated
    CHATGLM_PRO("chatglm_pro", "适用于对知识量、推理能力、创造力要求较高的场景"),
    /** 智谱AI 23年06月发布 */
    CHATGLM_TURBO("chatglm_turbo", "适用于对知识量、推理能力、创造力要求较高的场景"),
    /** 智谱AI 24年01月发布 */
    GLM_3_5_TURBO("glm-3-turbo","适用于对知识量、推理能力、创造力要求较高的场景"),
    GLM_4("glm-4","适用于复杂的对话交互和深度内容创作设计的场景"),
    GLM_4V("glm-4v","根据输入的自然语言指令和图像信息完成任务，推荐使用 SSE 或同步调用方式请求接口"),
    GLM_4_FLASH("glm-4-flash","适用简单任务，速度最快，价格最实惠的版本，具有128k上下文"),
    COGVIEW_3("cogview-3","根据用户的文字描述生成图像,使用同步调用方式请求接口"),
    ;
    private final String code;
    private final String info;

    Model(String code, String info) {
        this.code = code;
        this.info = info;
    }

    public String getCode() {
        return code;
    }

    public String getInfo() {
        return info;
    }

}
