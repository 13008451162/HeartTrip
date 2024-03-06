package com.xupt3g.mylibrary1;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * 项目名: HeartTrip
 * 文件名: com.xupt3g.mylibrary1.PublicRetrofit
 *
 * @author: shallew
 * @data: 2024/2/28 17:23
 * @about: TODO
 */
public class PublicRetrofit {
    private static final String BASE_URL = "http://10.0.0.2";
    private static final Retrofit RETROFIT = new Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    public static Object create(Class<?> serviceClass) {
        return RETROFIT.create(serviceClass);
    }

}
