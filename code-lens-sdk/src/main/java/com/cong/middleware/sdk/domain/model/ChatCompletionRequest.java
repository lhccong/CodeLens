package com.cong.middleware.sdk.domain.model;

import java.util.List;

public class ChatCompletionRequest {

    private String model = Model.GLM_4_FLASH.getCode();
    private List<Prompt> messages;

    public static class Prompt {
        private String role;
        private String content;

        public Prompt() {
        }

        public Prompt(String role, String content) {
            this.role = role;
            this.content = content;
        }

        public String getRole() {
            return role;
        }

        public void setRole(String role) {
            this.role = role;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public List<Prompt> getMessages() {
        return messages;
    }

    public void setMessages(List<Prompt> messages) {
        this.messages = messages;
    }
}
