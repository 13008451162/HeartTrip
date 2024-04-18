package com.xupt3g.messagesview.Model;

import com.google.gson.annotations.SerializedName;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 项目名: HeartTrip
 * 文件名: Message
 *
 * @author: lukecc0
 * @data:2024/4/18 下午7:13
 * @about: TODO 返回的聊天对话
 */

@NoArgsConstructor
@Data
public class Message {

    @SerializedName("id")
    private String id;
    @SerializedName("object")
    private String object;
    @SerializedName("created")
    private Integer created;
    @SerializedName("result")
    private String result;
    @SerializedName("is_truncated")
    private Boolean isTruncated;
    @SerializedName("need_clear_history")
    private Boolean needClearHistory;
    @SerializedName("usage")
    private UsageDTO usage;

    @NoArgsConstructor
    @Data
    public static class UsageDTO {
        @SerializedName("prompt_tokens")
        private Integer promptTokens;
        @SerializedName("completion_tokens")
        private Integer completionTokens;
        @SerializedName("total_tokens")
        private Integer totalTokens;


        public Integer getPromptTokens() {
            return promptTokens;
        }

        public void setPromptTokens(Integer promptTokens) {
            this.promptTokens = promptTokens;
        }

        public Integer getCompletionTokens() {
            return completionTokens;
        }

        public void setCompletionTokens(Integer completionTokens) {
            this.completionTokens = completionTokens;
        }

        public Integer getTotalTokens() {
            return totalTokens;
        }

        public void setTotalTokens(Integer totalTokens) {
            this.totalTokens = totalTokens;
        }

        @Override
        public String toString() {
            return "UsageDTO{" +
                    "promptTokens=" + promptTokens +
                    ", completionTokens=" + completionTokens +
                    ", totalTokens=" + totalTokens +
                    '}';
        }
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getObject() {
        return object;
    }

    public void setObject(String object) {
        this.object = object;
    }

    public Integer getCreated() {
        return created;
    }

    public void setCreated(Integer created) {
        this.created = created;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public Boolean getTruncated() {
        return isTruncated;
    }

    public void setTruncated(Boolean truncated) {
        isTruncated = truncated;
    }

    public Boolean getNeedClearHistory() {
        return needClearHistory;
    }

    public void setNeedClearHistory(Boolean needClearHistory) {
        this.needClearHistory = needClearHistory;
    }

    public UsageDTO getUsage() {
        return usage;
    }

    public void setUsage(UsageDTO usage) {
        this.usage = usage;
    }

    @Override
    public String toString() {
        return "Message{" +
                "id='" + id + '\'' +
                ", object='" + object + '\'' +
                ", created=" + created +
                ", result='" + result + '\'' +
                ", isTruncated=" + isTruncated +
                ", needClearHistory=" + needClearHistory +
                ", usage=" + usage +
                '}';
    }
}
