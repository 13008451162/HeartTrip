package com.xupt3g.homepageview.model.net;

import android.util.Log;

import com.xupt3g.UiTools.RootDirectory;
import com.xupt3g.homepageview.model.HomestayListReq;
import com.xupt3g.homepageview.model.RecommendHomeData;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * 项目名: HeartTrip
 * 文件名: RecommendInfoTask
 *
 * @author: lukecc0
 * @data:2024/3/21 下午3:44
 * @about: TODO 房屋网络请求的实现类
 */

public class RecommendInfoTask implements RecommendNetTask<RecommendHomeData> {


    private static RecommendInfoTask INSTANCE = null;
    private Retrofit retrofit;


    public static RecommendInfoTask getInstance() {
        if (INSTANCE == null) {
            return new RecommendInfoTask();
        }
        return INSTANCE;
    }

    private RecommendInfoTask() {
        createRetrofit();
    }

    private void createRetrofit() {
        retrofit = new Retrofit.Builder()
                .baseUrl(RootDirectory.getNetRoot())
                .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    @Override
    public Observable<RecommendHomeData> execute(HomestayListReq homestayListReq) {

        RecommendService service = retrofit.create(RecommendService.class);

        return service.getHomeData(homestayListReq)
                .subscribeOn(Schedulers.io())
                .retry(2)
                .observeOn(AndroidSchedulers.mainThread());
    }


//    /**
//     * 模拟网络请求
//     *
//     * @param homestayListReq
//     * @return {@link Observable}<{@link RecommendHomeData}>
//     */
//    @Override
//    public Observable<RecommendHomeData> execute(HomestayListReq homestayListReq) {
//        RecommendHomeData data1 = new RecommendHomeData();
//        data1.setCode(200);
//
//        data1.setMsg("OK");
//
//        ArrayList<RecommendHomeData.DataDTO.ListDTO> listDTOS = new ArrayList<>();
//
//        RecommendHomeData.DataDTO.ListDTO listDTO1 = new RecommendHomeData.DataDTO.ListDTO();
//        listDTO1.setId(1);
//        listDTO1.setTitle("情侣约会");
//        listDTO1.setCover("https://www.ahstatic.com/photos/b338_ho_00_p_2048x1536.jpg");
//        listDTO1.setIntro("Intro 1");
//        listDTO1.setLocation("陕西-兵马俑景区");
//        listDTO1.setPriceAfter(1000);
//
//        RecommendHomeData.DataDTO.ListDTO listDTO2 = new RecommendHomeData.DataDTO.ListDTO();
//        listDTO2.setId(2);
//        listDTO2.setTitle("树屋");
//        listDTO2.setCover("https://ak-d.tripcdn.com/images/02064120008rsj6yx070B_R_960_660_R5_D.jpg");
//        listDTO2.setIntro("Intro 2");
//        listDTO2.setLocation("上海-外滩-东方明珠");
//        listDTO2.setPriceAfter(2000);
//
//        RecommendHomeData.DataDTO.ListDTO listDTO3 = new RecommendHomeData.DataDTO.ListDTO();
//        listDTO3.setId(3);
//        listDTO3.setTitle("家庭出行");
//        listDTO3.setCover("https://image.kkday.com/v2/image/get/w_960%2Cc_fit%2Cq_55%2Ct_webp/s1.kkday.com/product_156161/20231115073745_zvgkB/jpg");
//        listDTO3.setIntro("Intro 3");
//        listDTO3.setLocation("上饶");
//        listDTO3.setPriceAfter(3000);
//
//        RecommendHomeData.DataDTO.ListDTO listDTO4 = new RecommendHomeData.DataDTO.ListDTO();
//        listDTO4.setId(4);
//        listDTO4.setTitle("蜜月之旅");
//        listDTO4.setCover("https://image.kkday.com/v2/image/get/w_960%2Cc_fit%2Cq_55%2Ct_webp/s1.kkday.com/product_163330/20240227081326_Squ1A/jpg");
//        listDTO4.setIntro("Intro 4");
//        listDTO4.setLocation("大理-洱海");
//        listDTO4.setPriceAfter(4000);
//
//        Random random = new Random();
//
//        for (int i = 0; i < 10; i++) {
//            int randomNum = random.nextInt(4) + 1; // 生成1到4之间的随机数
//
//            switch (randomNum) {
//                case 1:
//                    listDTOS.add(listDTO1);
//                    break;
//                case 2:
//                    listDTOS.add(listDTO2);
//                    break;
//                case 3:
//                    listDTOS.add(listDTO3);
//                    break;
//                case 4:
//                    listDTOS.add(listDTO4);
//                    break;
//            }
//        }
//
//        data1.setData(new RecommendHomeData.DataDTO());
//        data1.getData().setList(listDTOS);
//
//        return Observable.just(data1);
//    }
}
