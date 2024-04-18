package com.xupt3g.messagesview.Model.Net;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.xupt3g.messagesview.Model.Message;
import com.xupt3g.messagesview.Model.MessageData;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * 项目名: HeartTrip
 * 文件名: ChatInfoTask
 *
 * @author: lukecc0
 * @data:2024/4/18 下午5:22
 * @about: TODO 实现网络请求工具
 */

public class ChatInfoTask implements ChatNetTask<Message> {

    Retrofit retrofit;

    private List<MessageData> listMessageData;

    private static ChatInfoTask INSTANCE = null;

    public static ChatInfoTask getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new ChatInfoTask();
            return INSTANCE;
        }

        return INSTANCE;
    }

    public ChatInfoTask() {
        onCreateRetrofit();
        listMessageData = new ArrayList<>();
    }

    private void onCreateRetrofit() {

        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                // 添加日志打印请求 URL 的拦截器
                .addInterceptor(new UrlLoggingInterceptor())
                .build();


        retrofit = new Retrofit.Builder()
                .baseUrl(ChatNetConstant.HOST)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                .client(okHttpClient)
                .build();
    }

    @Override
    public Observable<Message> execute(MessageData massageData) {

        listMessageData.add(massageData);
        ChatService service = retrofit.create(ChatService.class);


        String jsonString = JsonConverter.convertToJson(listMessageData);

        Gson gson = new Gson();
        JsonObject jsonObject = gson.fromJson(jsonString, JsonObject.class);

        Log.e("TAG", "execute: JSON : " + jsonString);

        return service.getChatRecord(ChatNetConstant.ACCESS_TOKEN, jsonObject)
                .subscribeOn(Schedulers.io())
                .retry(2)
                .observeOn(AndroidSchedulers.mainThread())
                .doOnError(Throwable::printStackTrace);
    }

    @Override
    public void upDataListMessages(Message message) {
        INSTANCE.listMessageData.add(new MessageData(MessageData.ASSISTANT, message.getResult()));
    }

    public List<MessageData> getListMessageData() {
        return INSTANCE.listMessageData;
    }

}
