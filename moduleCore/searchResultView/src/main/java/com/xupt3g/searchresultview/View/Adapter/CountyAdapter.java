package com.xupt3g.searchresultview.View.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.xupt3g.searchresultview.R;
import com.xupt3g.searchresultview.model.CountyData;

import java.util.ArrayList;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.subjects.PublishSubject;

/**
 * 项目名: HeartTrip
 * 文件名: CountyAdapter
 *
 * @author: lukecc0
 * @data:2024/4/12 下午8:26
 * @about: TODO 下级行政区选择器适配器
 */

public class CountyAdapter extends RecyclerView.Adapter<CountyAdapter.ViewHolder> {

    private PublishSubject<TextView> publishSubject;

    private ArrayList<CountyData.DistrictsDTO> mList;

    public CountyAdapter(@NonNull ArrayList<CountyData.DistrictsDTO> mList) {
        this.mList = mList;
        publishSubject = PublishSubject.create();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_drop_down_list_item, null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        CountyData.DistrictsDTO countyData = mList.get(position);
        holder.textView.setText(countyData.getName());
        holder.itemView.setOnClickListener(v -> publishSubject.onNext(holder.textView));
    }

    @Override
    public int getItemCount() {
        return mList == null ? 0 : mList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        View itemView;
        TextView textView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.itemView = itemView;
            textView = itemView.findViewById(R.id.text);
        }
    }

    public Observable<TextView> getCountyObservable() {
        return publishSubject.hide();
    }
}
