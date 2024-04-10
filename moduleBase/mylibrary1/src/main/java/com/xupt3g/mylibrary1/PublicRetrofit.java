package com.xupt3g.mylibrary1;

import com.xupt3g.UiTools.RootDirectory;

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
    private static final String BASE_URL = RootDirectory.getNetRoot();
    private static final Retrofit RETROFIT = new Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build();
    /**
     * 网络请求信息出错时的msg信息（自定）
     */
    private static final String ERROR_MSG = "ERROR";
    public static String getErrorMsg() {
        return ERROR_MSG;
    }

    public static Object create(Class<?> serviceClass) {
        return RETROFIT.create(serviceClass);
    }
}
