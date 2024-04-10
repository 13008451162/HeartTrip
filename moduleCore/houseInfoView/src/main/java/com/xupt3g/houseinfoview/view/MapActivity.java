package com.xupt3g.houseinfoview.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;
import com.baidu.location.BDLocation;
import com.baidu.location.LocationClient;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.InfoWindow;
import com.baidu.mapapi.map.MapPoi;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.core.PoiInfo;
import com.baidu.mapapi.search.poi.OnGetPoiSearchResultListener;
import com.baidu.mapapi.search.poi.PoiDetailResult;
import com.baidu.mapapi.search.poi.PoiDetailSearchOption;
import com.baidu.mapapi.search.poi.PoiDetailSearchResult;
import com.baidu.mapapi.search.poi.PoiIndoorResult;
import com.baidu.mapapi.search.poi.PoiNearbySearchOption;
import com.baidu.mapapi.search.poi.PoiResult;
import com.baidu.mapapi.search.poi.PoiSearch;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.xuexiang.xui.utils.XToastUtils;
import com.xupt3g.houseinfoview.R;
import com.xupt3g.houseinfoview.view.adapter.AttractionsAdapter;
import java.util.List;
import java.util.Objects;

public class MapActivity extends AppCompatActivity {

    private MapView mapView;
    public LocationClient lc;
    private BaiduMap baiduMap;
    private boolean isFirstLocate = true;

    private RecyclerView attractionsRecycler;

    private List<PoiInfo> poiInfoList;
    private AttractionsAdapter attractionsAdapter;

    private BottomSheetBehavior bottomSheetBehavior;
    private LinearLayout bottomSheetLayout;
    /**
     * 附近友景点的话 显示“附近景点” 没有的话 显示“暂无景点” 最好民宿不要选在附近无景点的地方
     */
    private TextView bottomSheetTitle;
    /**
     * 附近友景点的话显示，没有的话GONE
     */
    private TextView bottomSheetHint;

    private LottieAnimationView noAttractionAnim;

    private double houseLatitude;
    private double houseLongitude;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //加载地图
        //1.先调用setAgreePrivacy()方法，启用地图
        SDKInitializer.setAgreePrivacy(getApplicationContext(), true);
        SDKInitializer.initialize(getApplicationContext());
        //初始化操作一定要在setContentView()之前
        setContentView(R.layout.houseinfo_activity_map);
        mapView = (MapView) findViewById(R.id.baidu_map);
        attractionsRecycler = (RecyclerView) findViewById(R.id.houseinfo_map_recycler);
        bottomSheetLayout = (LinearLayout) findViewById(R.id.houseinfo_bottom_sheet_ll);
        bottomSheetTitle = (TextView) findViewById(R.id.houseinfo_bottom_sheet_title);
        bottomSheetHint = (TextView) findViewById(R.id.houseinfo_bottom_sheet_hint);
        noAttractionAnim = (LottieAnimationView) findViewById(R.id.homeseinfo_nodata_anim) ;

        //获取到BaiduMap的实例，地图的总控制器
        baiduMap = mapView.getMap();

//        GPSLocation();
        try {
            lc = new LocationClient(getApplicationContext());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        //如果经纬度错误，会默认定位到北京天安门
        houseLatitude = 39.91515;
        houseLongitude = 116.404;

        //获取上个活动传来的经纬度 有数据时可恢复
//        String temLat = getIntent().getStringExtra("HouseLatitude");
//        String temLon = getIntent().getStringExtra("HouseLongitude");
//        if (temLat != null && !"".equals(temLat) && temLon != null && !"".equals(temLon)) {
//            //经纬度不为空
//            houseLatitude = Double.parseDouble(temLat);
//            houseLongitude = Double.parseDouble(temLon);
//        } else {
//            XToastUtils.error("民宿定位失败！");
//        }

        BDLocation bdLocation = new BDLocation();
        bdLocation.setLatitude(houseLatitude);
        bdLocation.setLongitude(houseLongitude);
        navigateTo(bdLocation);

        baiduMap.setMyLocationEnabled(true);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.confirmLatLng);

        //搜索附近景点
        poiSearch(bdLocation);

        bottomSheetBehavior = BottomSheetBehavior.from(bottomSheetLayout);
        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);

        fab.setOnClickListener(view -> {
            if (bottomSheetBehavior.getState() == BottomSheetBehavior.STATE_HIDDEN
                    || bottomSheetBehavior.getState() == BottomSheetBehavior.STATE_COLLAPSED) {
                //如果当前是隐藏的,或者是折叠的 就完全展开
                bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
            } else if (bottomSheetBehavior.getState() == BottomSheetBehavior.STATE_EXPANDED) {
                //如果是完全展开的 就隐藏
                bottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
            }
        });

        BaiduMap.OnMapClickListener listener = new BaiduMap.OnMapClickListener() {
            /**
             * 地图单击事件回调函数
             *
             * @param point 点击的地理坐标
             */
            @Override
            public void onMapClick(LatLng point) {
//                tempAuToLocation = point.longitude + "," + point.latitude;
                if (bottomSheetBehavior.getState() == BottomSheetBehavior.STATE_EXPANDED) {
                    //如果是展开的 将他收起
                    bottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
                    return;
                }
                //移动到指定的位置
                MapStatusUpdate update = MapStatusUpdateFactory.newLatLng(point);
                baiduMap.animateMapStatus(update);
                update = MapStatusUpdateFactory.zoomTo(16.8f);
                baiduMap.animateMapStatus(update);

                MyLocationData.Builder locationBuilder = new MyLocationData.Builder();
                locationBuilder.latitude(point.latitude);
                locationBuilder.longitude(point.longitude);
                MyLocationData myLocation = locationBuilder.build();
                baiduMap.setMyLocationData(myLocation);
            }

            /**
             * 地图内 Poi 单击事件回调函数
             *
             * @param mapPoi 点击的 poi 信息
             */
            @Override
            public void onMapPoiClick(MapPoi mapPoi) {
            }
        };
        //设置地图单击事件监听
        baiduMap.setOnMapClickListener(listener);

        FloatingActionButton backToMyLocation = (FloatingActionButton) findViewById(R.id.back_to_my_location);
        backToMyLocation.setOnClickListener(view -> {
            navigateTo(bdLocation);
        });
    }

    private PoiSearch mPoiSearch;

    /**
     * 标记是否是去详情页面
     */
    private boolean isGoToDetailWebView;

    private void poiSearch(BDLocation location) {
        mPoiSearch = PoiSearch.newInstance();
        OnGetPoiSearchResultListener listener = new OnGetPoiSearchResultListener() {
            @Override
            public void onGetPoiResult(PoiResult poiResult) {
                if (poiResult == null) {
                    initViewByIsHaveAttraction(false);
                    return;
                }
                List<PoiInfo> allPoi = poiResult.getAllPoi();
                if (allPoi.size() == 0) {
                    initViewByIsHaveAttraction(false);
                    return;
                }
                initViewByIsHaveAttraction(true);
                for (PoiInfo poiInfo : allPoi) {
                    BitmapDescriptor bitmapDescriptor = BitmapDescriptorFactory
                            .fromBitmap(getBitmapFromVectorDrawable(MapActivity.this, R.drawable.houseindo_icon_locate_scenic));
                    OverlayOptions options1 = new MarkerOptions()
                            .position(poiInfo.location)
                            .icon(bitmapDescriptor);
                    baiduMap.addOverlay(options1);
                }
                poiInfoList = allPoi;
                attractionsAdapter = new AttractionsAdapter(poiInfoList, MapActivity.this);
                //子项点击监听
                attractionsAdapter.setOnItemClickListener(new AttractionsAdapter.OnItemClickListener() {
                    @Override
                    public void onItemTextClick(View view, int position) {
                        //移动地图视野到点击的景点的回调
                        PoiInfo poiInfo = allPoi.get(position);
                        if (poiInfo != null) {
                            isGoToDetailWebView = false;
                            PoiDetailSearchOption option = new PoiDetailSearchOption();
                            option.poiUid(poiResult.getAllPoi().get(position).getUid());
                            mPoiSearch.searchPoiDetail(option);
                        }
                    }

                    @Override
                    public void onItemDetailClick(View view, int position) {
                        //跳转详情网页的回调
                        PoiInfo poiInfo = allPoi.get(position);
                        if (poiInfo != null) {
                            isGoToDetailWebView = true;
                            PoiDetailSearchOption option = new PoiDetailSearchOption();
                            option.poiUid(poiResult.getAllPoi().get(position).getUid());
                            mPoiSearch.searchPoiDetail(option);
                        }
                    }
                });
                attractionsRecycler.setAdapter(attractionsAdapter);
                attractionsRecycler.setLayoutManager(new LinearLayoutManager(MapActivity.this, LinearLayoutManager.VERTICAL, false));
                attractionsRecycler.addItemDecoration(new DividerItemDecoration(MapActivity.this, DividerItemDecoration.VERTICAL));
            }

            @Override
            public void onGetPoiDetailResult(PoiDetailSearchResult poiDetailSearchResult) {
            }

            @Override
            public void onGetPoiIndoorResult(PoiIndoorResult poiIndoorResult) {
            }

            @Override
            public void onGetPoiDetailResult(PoiDetailResult poiDetailResult) {
                //uid搜索详情回调
                if (poiDetailResult == null) {
                    return;
                }
                if (isGoToDetailWebView) {
                    Intent intent = new Intent(MapActivity.this, AttractionDetailWebActivity.class);
                    intent.putExtra("detailUrl", poiDetailResult.getDetailUrl());
                    startActivity(intent);
                } else {
                    LatLng latLng = poiDetailResult.getLocation();
                    MapStatusUpdate update = MapStatusUpdateFactory.newLatLng(new LatLng(latLng.latitude - 0.00015, latLng.longitude));
                    baiduMap.animateMapStatus(update);
                    update = MapStatusUpdateFactory.zoomTo(20.0f);
                    baiduMap.animateMapStatus(update);
                }
            }
        };
        mPoiSearch.setOnGetPoiSearchResultListener(listener);
        //发出搜索请求
        mPoiSearch.searchNearby(new PoiNearbySearchOption()
                .location(new LatLng(location.getLatitude(), location.getLongitude()))
                .keyword("景点")
                .radius(12000));
    }

    /**
     * 根据附近有没有景点变更bottomSheet的View
     * TODO 其实只是简化代码让上面poiSearch()方法的代码少点
     */
    private void initViewByIsHaveAttraction(boolean isHaveAttraction) {
        if (!isHaveAttraction) {
            bottomSheetTitle.setText("附近没有景点");
            bottomSheetHint.setVisibility(View.GONE);
            noAttractionAnim.setVisibility(View.VISIBLE);
            noAttractionAnim.playAnimation();
            attractionsRecycler.setVisibility(View.GONE);
        } else {
            bottomSheetTitle.setText("附近景点");
            bottomSheetHint.setVisibility(View.VISIBLE);
            noAttractionAnim.pauseAnimation();
            noAttractionAnim.setVisibility(View.GONE);
            attractionsRecycler.setVisibility(View.VISIBLE);
        }
    }

    private void navigateTo(BDLocation location) {
        //定义Maker坐标点
        LatLng point = new LatLng(location.getLatitude(), location.getLongitude());
        //构建Marker图标
        BitmapDescriptor bitmap = BitmapDescriptorFactory
                .fromBitmap(getBitmapFromVectorDrawable(this, R.drawable.houseinfo_icon_locate_mapsvg));

        //构建MarkerOption，用于在地图上添加Marker
        OverlayOptions option = new MarkerOptions()
                .position(point)
                .icon(bitmap)
                .flat(true)
                .animateType(MarkerOptions.MarkerAnimateType.grow)
                .perspective(true);
        //在地图上添加Marker，并显示
        baiduMap.addOverlay(option);

        //用来构造InfoWindow的Button
        Button button = new Button(getApplicationContext());
        button.setBackgroundResource(R.drawable.houseinfo_coupon_background);
        button.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        button.setText("民宿定位");
        button.setTextSize(13);
        button.requestLayout();
        button.setOnClickListener(view -> {
            button.setVisibility(View.GONE);
            button.postDelayed(() -> button.setVisibility(View.VISIBLE), 2500);
        });

        //构造InfoWindow
        //point 描述的位置点
        //-100 InfoWindow相对于point在y轴的偏移量
        InfoWindow mInfoWindow = new InfoWindow(button, point, -100);

        //使InfoWindow生效
        baiduMap.showInfoWindow(mInfoWindow);

        // 将地图移动到当前位置
        MapStatusUpdate mapStatusUpdate = MapStatusUpdateFactory.newLatLng(point);
        baiduMap.setMapStatus(mapStatusUpdate);
        mapStatusUpdate = MapStatusUpdateFactory.zoomTo(16.8f);
        baiduMap.setMapStatus(mapStatusUpdate);
    }

    public static Bitmap getBitmapFromVectorDrawable(Context context, int drawableId) {
        Drawable drawable = ContextCompat.getDrawable(context, drawableId);

        Bitmap bitmap = Bitmap.createBitmap(Objects.requireNonNull(drawable).getIntrinsicWidth(),
                drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
//        canvas.drawColor(Color.WHITE);//背景
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        drawable.draw(canvas);

        return bitmap;
    }

    @Override
    protected void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
        //关闭 显示自身位置 的功能
        baiduMap.setMyLocationEnabled(false);
        mPoiSearch.destroy();
    }
}