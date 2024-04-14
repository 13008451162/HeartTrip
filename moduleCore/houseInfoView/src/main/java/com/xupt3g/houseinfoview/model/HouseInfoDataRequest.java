package com.xupt3g.houseinfoview.model;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.xuexiang.xui.utils.XToastUtils;
import com.xupt3g.houseinfoview.model.retrofit.HouseInfoBaseData;
import com.xupt3g.houseinfoview.model.retrofit.HouseInfoBaseDataResponse;
import com.xupt3g.houseinfoview.model.retrofit.HouseInfoRequestBody;
import com.xupt3g.houseinfoview.model.retrofit.HouseInfoService;
import com.xupt3g.houseinfoview.model.retrofit.RecommendHouse;
import com.xupt3g.houseinfoview.model.retrofit.RecommendHouseResponse;
import com.xupt3g.mylibrary1.LoginStatusData;
import com.xupt3g.mylibrary1.PublicRetrofit;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * 项目名: HeartTrip
 * 文件名: com.xupt3g.houseinfoview.model.HouseInfoDataRequest
 *
 * @author: shallew
 * @data: 2024/3/12 20:54
 * @about: TODO 进行民俗详情信息的网络请求
 */
public class HouseInfoDataRequest implements HouseInfoGetImpl {
    private MutableLiveData<HouseInfoBaseDataResponse> houseInfoLiveData;
    private MutableLiveData<RecommendHouseResponse> recommendHouseLiveData;
    private HouseInfoService houseInfoService;

    public HouseInfoDataRequest() {
        houseInfoService = (HouseInfoService) PublicRetrofit.create(HouseInfoService.class);
    }

    @Override
    public MutableLiveData<HouseInfoBaseDataResponse> getHouseInfoBaseData(int houseId) {
        //测试数据，请求成功
        houseInfoLiveData = new MutableLiveData<>();

        Call<HouseInfoBaseDataResponse> call = null;
        if (Boolean.TRUE.equals(LoginStatusData.getLoginStatus().getValue())) {
            //如果已登录
            Log.d("houseInfoService", "getHouseInfoBaseData: 已登录");
            call = houseInfoService.getHouseInfoBaseData(LoginStatusData.getUserToken().getValue(), new HouseInfoRequestBody(houseId));
        } else {
            //如果未登录
            Log.d("houseInfoService", "getHouseInfoBaseData: 未登录");
            call = houseInfoService.getHouseInfoBaseData(new HouseInfoRequestBody(houseId));
        }
        //call应该不为空
        call.enqueue(new Callback<HouseInfoBaseDataResponse>() {
            @Override
            public void onResponse(Call<HouseInfoBaseDataResponse> call, Response<HouseInfoBaseDataResponse> response) {
                HouseInfoBaseDataResponse body = response.body();
                if (body != null && body.getCode() == 200 && "OK".equals(body.getMsg())) {
                    houseInfoLiveData.setValue(body);
                } else {
                    houseInfoLiveData.setValue(new HouseInfoBaseDataResponse(PublicRetrofit.getErrorMsg()));
                }
            }

            @Override
            public void onFailure(Call<HouseInfoBaseDataResponse> call, Throwable t) {
                houseInfoLiveData.setValue(new HouseInfoBaseDataResponse(PublicRetrofit.getErrorMsg()));
                XToastUtils.error("网络请求失败！");

            }
        });
        return houseInfoLiveData;
    }

    @Override
    public MutableLiveData<RecommendHouseResponse> getRecommendHousesList() {
        //无需登录
        recommendHouseLiveData = new MutableLiveData<>();
        houseInfoService.getRecommendHousesList().enqueue(new Callback<RecommendHouseResponse>() {
            @Override
            public void onResponse(Call<RecommendHouseResponse> call, Response<RecommendHouseResponse> response) {
                RecommendHouseResponse body = response.body();
                if (body != null && body.getCode() == 200 && "OK".equals(body.getMsg())) {
                    recommendHouseLiveData.setValue(body);
                } else {
                    recommendHouseLiveData.setValue(new RecommendHouseResponse(PublicRetrofit.getErrorMsg()));
                }
            }

            @Override
            public void onFailure(Call<RecommendHouseResponse> call, Throwable t) {
                recommendHouseLiveData.setValue(new RecommendHouseResponse(PublicRetrofit.getErrorMsg()));
                XToastUtils.error("网络请求失败！");
            }
        });
        return recommendHouseLiveData;
    }
}
