package com.xupt3g.collectionsview.guessModel;

import com.xuexiang.xutil.tip.ToastUtils;
import com.xupt3g.UiTools.RootDirectory;
import com.xupt3g.collectionsview.guessModel.retrofit.GuessData;
import com.xupt3g.collectionsview.guessModel.retrofit.GuessListResponse;
import com.xupt3g.collectionsview.guessModel.retrofit.GuessListService;

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
     * 构建的retrofit的实例
     */
    private Retrofit retrofit;
    /**
     * 获取的接口动态代理对象
     */
    private GuessListService guessListService;

    /**
     *
     */
    private List<GuessData> guessDataList;

    public GuessListGetRequest() {
        //构建Retrofit实例
        retrofit = new Retrofit.Builder()
                //根路径
                .baseUrl(RootDirectory.getRootDirectory())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        //获取接口的动态代理对象
        guessListService = retrofit.create(GuessListService.class);
    }

    @Override
    public List<GuessData> getGuessList() {
        //测试
        guessDataList = new ArrayList<>();
        Collections.addAll(guessDataList, new GuessData(1), new GuessData(2), new GuessData(3), new GuessData(4),
                new GuessData(5), new GuessData(6));

//        //无需登录
//        guessListService.getGuessList().enqueue(new Callback<GuessListResponse>() {
//            @Override
//            public void onResponse(Call<GuessListResponse> call, Response<GuessListResponse> response) {
//                GuessListResponse body = response.body();
//                if (body != null && body.getCode() == 200 && "OK".equals(body.getMsg())) {
//                    guessDataList = body.getList();
//                }
//            }
//
//            @Override
//            public void onFailure(Call<GuessListResponse> call, Throwable t) {
//                ToastUtils.toast("网络请求失败！");
//                guessDataList = null;
//            }
//        });
        return guessDataList;
    }
}
