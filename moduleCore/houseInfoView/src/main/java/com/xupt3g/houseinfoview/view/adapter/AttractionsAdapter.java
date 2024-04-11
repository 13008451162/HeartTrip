package com.xupt3g.houseinfoview.view.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.baidu.mapapi.search.core.PoiInfo;
import com.xuexiang.xutil.tip.ToastUtils;
import com.xupt3g.houseinfoview.R;
import com.xupt3g.houseinfoview.view.MapActivity;

import java.util.List;

/**
 * 项目名: HeartTrip
 * 文件名: com.xupt3g.houseinfoview.view.adapter.AttractionsAdapter
 *
 * @author: shallew
 * @data: 2024/4/9 21:47
 * @about: TODO
 */
public class AttractionsAdapter extends RecyclerView.Adapter<AttractionsAdapter.ViewHolder> {
    List<PoiInfo> poiInfoList;
    MapActivity mapActivity;

    public AttractionsAdapter(List<PoiInfo> poiInfoList, MapActivity mapActivity) {
        this.poiInfoList = poiInfoList;
        this.mapActivity = mapActivity;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.houseinfo_adapter_attractions_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        PoiInfo poiInfo = poiInfoList.get(position);
        holder.attractionName.setText(poiInfo.getName());
        holder.attractionAddress.setText(poiInfo.getAddress());
        holder.attractionLayout.setOnClickListener(view -> {
            //点击移动地图视野的文本
            mOnItemClickListener.onItemTextClick(view, position);
        });
        holder.attractionDetail.setOnClickListener(view -> {
            //点击跳转到景点详情网页 webview
            mOnItemClickListener.onItemDetailClick(view, position);
        });

    }

    public interface OnItemClickListener {
        void onItemTextClick(View view, int position);
        void onItemDetailClick(View view, int position);
    }
    private OnItemClickListener mOnItemClickListener;
    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }

    @Override
    public int getItemCount() {
        return poiInfoList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView attractionName;
        private TextView attractionAddress;
        private TextView attractionDetail;
        private LinearLayout attractionLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            attractionName = itemView.findViewById(R.id.attractions_tv_name);
            attractionAddress = itemView.findViewById(R.id.attractions_tv_address);
            attractionDetail = itemView.findViewById(R.id.attractions_iv_detail);
            attractionLayout = itemView.findViewById(R.id.attractions_ll);
        }
    }
}
