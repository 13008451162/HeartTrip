package com.xupt3g.searchresultview.View.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.android.arouter.launcher.ARouter;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.example.libbase.BuildConfig;
import com.xuexiang.xui.utils.XToastUtils;
import com.xupt3g.searchresultview.R;
import com.xupt3g.searchresultview.model.HousingData;

import java.util.ArrayList;

/**
 * 项目名: HeartTrip
 * 文件名: housesAdapter
 *
 * @author: lukecc0
 * @data:2024/4/16 下午12:37
 * @about: TODO 搜索房屋页面展示功能
 */

public class HousesAdapter extends RecyclerView.Adapter<HousesAdapter.ViewHolder> {

    Context context;

    ArrayList<HousingData.ListDTO> mList;

    public HousesAdapter(ArrayList<HousingData.ListDTO> mList) {
        this.mList = mList;
    }

    public HousesAdapter() {
        mList = new ArrayList<>();
    }

    public void setmList(ArrayList<HousingData.ListDTO> mList) {
        this.mList = mList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.searc_houses_item, parent, false);
        context = parent.getContext();
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        HousingData.ListDTO housingData = mList.get(position);
        //使用Glide加载可以优化加载流程，Glide使用异步操作
        Glide.with(context).load(housingData.getCover()).transform(new RoundedCorners(20)).into(holder.imageView);
        holder.textView1.setText(housingData.getLocation());
        holder.textView2.setText(housingData.getFacilities());
        holder.textView3.setText(" ￥ " + housingData.getPriceAfter().toString() + " /晚");

        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!BuildConfig.isModule) {
                    //判断当前是不是集成开发模式
                    ARouter.getInstance().build("/houseInfoView/HouseInfoActivity")
                            .withInt("HouseId", housingData.getId()).navigation();
                } else {
                    XToastUtils.error("当前不能跳转！");
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mList != null ? mList.size() : 0;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        View view;
        ImageView imageView;
        TextView textView1;
        TextView textView2;
        TextView textView3;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            view = itemView;
            imageView = itemView.findViewById(R.id.viewPager2);
            textView1 = itemView.findViewById(R.id.introduction);
            textView2 = itemView.findViewById(R.id.titleTags);
            textView3 = itemView.findViewById(R.id.price);
        }
    }
}
