package com.xupt3g.messagesview.Model;

import com.google.gson.annotations.SerializedName;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 项目名: HeartTrip
 * 文件名: MessageData
 *
 * @author: lukecc0
 * @data:2024/4/18 下午4:58
 * @about: TODO 聊天对话数据请求体
 */

@NoArgsConstructor
@Data
public class MessageData {

    public static final String  ASSISTANT = "assistant";
    public static final String  USER = "user";

    @SerializedName("role")
    private String role;
    @SerializedName("content")
    private String content;

    public MessageData(String role, String content) {
        this.role = role;
        this.content = content;
    }

    @Override
    public String toString() {
        return "MessageData{" +
                "role='" + role + '\'' +
                ", content='" + content + '\'' +
                '}';
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
