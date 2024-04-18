package com.xupt3g.messagesview.Model.Net;

import com.google.gson.JsonObject;
import com.xupt3g.messagesview.Model.Message;

import io.reactivex.rxjava3.core.Observable;
import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * 项目名: HeartTrip
 * 文件名: ChatService
 *
 * @author: lukecc0
 * @data:2024/4/18 下午5:10
 * @about: TODO 使用Retorfit2进行网络请求
 */

public interface ChatService {

    /**
     * 通过以前的聊天记录关系新的聊天
     *
     * @param accessToken
     * @param requestBody
     * @return {@link Observable}<{@link Message}>
     */

    @POST("rpc/2.0/ai_custom/v1/wenxinworkshop/chat/ernie_speed")
    Observable<Message> getChatRecord(@Query("access_token") String accessToken,
                                      @Body JsonObject requestBody);
}
