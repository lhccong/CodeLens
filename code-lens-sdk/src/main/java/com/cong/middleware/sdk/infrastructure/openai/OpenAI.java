package com.cong.middleware.sdk.infrastructure.openai;

import com.cong.middleware.sdk.infrastructure.openai.dto.ChatCompletionRequestDTO;
import com.cong.middleware.sdk.infrastructure.openai.dto.ChatCompletionSyncResponseDTO;

/**
 * iOpen AI 接口
 *
 * @author cong
 */
public interface OpenAI {

    ChatCompletionSyncResponseDTO completions(ChatCompletionRequestDTO requestDTO) throws Exception;
}
