package com.xupt3g.messagesview.Model.Net;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.xupt3g.messagesview.Model.MessageBody;

import java.util.List;


/**
 * 项目名: HeartTrip
 * 文件名: JsonConverter
 *
 * @author: lukecc0
 * @data:2024/4/18 下午9:18
 * @about: TODO 一个转化工厂将List转为一个String类型Json
 */

public class JsonConverter {
    private static final Gson gson = new Gson();

    public static String convertToJson(List<MessageBody> listMessageData) {
        JsonObject jsonObject = new JsonObject();
        JsonArray messagesArray = new JsonArray();

        for (MessageBody messageData : listMessageData) {
            JsonObject messageJson = new JsonObject();
            messageJson.addProperty("role", messageData.getRole());
            messageJson.addProperty("content", messageData.getContent());
            messagesArray.add(messageJson);
        }

        jsonObject.add("messages", messagesArray);
        jsonObject.addProperty("system", ChatNetConstant.SYSTEM);
        return gson.toJson(jsonObject);
    }
}