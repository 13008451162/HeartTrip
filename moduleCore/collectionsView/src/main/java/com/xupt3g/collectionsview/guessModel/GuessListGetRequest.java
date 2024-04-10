package com.xupt3g.collectionsview.guessModel;

import androidx.lifecycle.MutableLiveData;

import com.xuexiang.xui.utils.XToastUtils;
import com.xuexiang.xutil.tip.ToastUtils;
import com.xupt3g.UiTools.RootDirectory;
import com.xupt3g.collectionsview.guessModel.retrofit.GuessData;
import com.xupt3g.collectionsview.guessModel.retrofit.GuessListResponse;
import com.xupt3g.collectionsview.guessModel.retrofit.GuessListService;
import com.xupt3g.mylibrary1.PublicRetrofit;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * 项目名: HeartTrip
 * 文件名: com.xupt3g.collectionsview.guessModel.GuessListGetRequest
 *
 * @author: shallew
 * @data:2024/2/20 0:08
 * @about: TODO
 */
public class GuessListGetRequest implements GuessListGetImpl {
    /**
     * 获取的接口动态代理对象
     */
    private GuessListService guessListService;

    private MutableLiveData<GuessListResponse> guessListLiveData = new MutableLiveData<>();

    public GuessListGetRequest() {
        guessListService = (GuessListService)
                PublicRetrofit.create(GuessListService.class);
    }

    @Override
    public MutableLiveData<GuessListResponse> getGuessList() {
        //无需登录
        guessListService.getGuessList().enqueue(new Callback<GuessListResponse>() {
            @Override
            public void onResponse(Call<GuessListResponse> call, Response<GuessListResponse> response) {
                GuessListResponse body = response.body();
                if (body != null && body.getCode() == 200 && "OK".equals(body.getMsg())) {
                    guessListLiveData.setValue(body);
                } else {
                    guessListLiveData.setValue(new GuessListResponse(PublicRetrofit.getErrorMsg()));
                }
            }

            @Override
            public void onFailure(Call<GuessListResponse> call, Throwable t) {
                XToastUtils.error("网络请求失败！");
                guessListLiveData.setValue(new GuessListResponse(PublicRetrofit.getErrorMsg()));
            }
        });
        return guessListLiveData;
    }
}
