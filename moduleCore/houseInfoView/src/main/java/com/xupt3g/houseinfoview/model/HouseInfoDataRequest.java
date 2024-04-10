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
//        return new HouseInfoBaseData(houseId, "测试标题", "陕西省西安市", 5.0f, 23, "精选好房,有停车位,宽松取消,可做饭,可聚会",
//                new String[]{"https://img.zcool.cn/community/01f23a5bc82cada801213dea4a5b3f.jpg@1280w_1l_2o_100sh.jpg",
//                        "https://ts1.cn.mm.bing.net/th/id/R-C.86f60de4927aeac6f1d21ceaac877d60?rik=r%2bKyu%2b6VNCyehw&riu=http%3a%2f%2fseopic.699pic.com%2fphoto%2f50036%2f0196.jpg_wh1200.jpg&ehk=uQVHtyvwTiUJOr%2fw6YslqS5v5B7J%2fhpXAYsAna950Pk%3d&risl=&pid=ImgRaw&r=0",
//                        "https://img.zcool.cn/community/0125015e217129a80120a895f459c9.jpg@1280w_1l_2o_100sh.jpg",
//                        "https://www.sohodd.com/wp-content/uploads/2017/06/%E5%AD%98%E5%9C%A8%E5%BB%BA%E7%AD%91-%E5%BB%BA%E7%AD%91%E6%91%84%E5%BD%B1_30.jpg"},
//                new String[]{"付费停车位", "电梯", "无线网络", "换客换床品", "拖鞋", "窗户", "空调", "饮水设备", "淋浴房", "智能门锁"}, "85平",
//                "1室1厅1卫1厨", "http://stream4.iqilu.com/ksd/video/2020/02/17/c5e02420426d58521a8783e754e9f4e6.mp4", "https://i0.hdslb.com/bfs/face/29475cf31016e1ba652cfb905925685701eceb76.jpg", "告别寒冷冬季", 114, 514);
        houseInfoLiveData = new MutableLiveData<>();

        Call<HouseInfoBaseDataResponse> call = null;
        if (Boolean.TRUE.equals(LoginStatusData.getLoginStatus().getValue())) {
            //如果已登录
            call = houseInfoService.getHouseInfoBaseData(new HouseInfoRequestBody(houseId));
        } else {
            //如果未登录
            call = houseInfoService.getHouseInfoBaseData(LoginStatusData.getUserToken().getValue(), new HouseInfoRequestBody(houseId));
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
