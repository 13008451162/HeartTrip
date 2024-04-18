package com.xupt3g.messagesview.Model.Net;

/**
 * 项目名: HeartTrip
 * 文件名: UrlLoggingInterceptor
 *
 * @author: lukecc0
 * @data:2024/4/18 下午7:49
 * @about: TODO retorfit的拦截器，用于拦截Retorfit请求的网址
 */

import android.util.Log;

import java.io.IOException;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class UrlLoggingInterceptor implements Interceptor {

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        HttpUrl url = request.url();
        Log.e("TAG", "intercept: "+url);

        // 继续处理请求
        return chain.proceed(request);
    }
}