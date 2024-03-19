package com.xupt3g.homepageview.view.Adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.xupt3g.homepageview.R;
import com.xupt3g.homepageview.model.SearchedLocationData;

import java.util.ArrayList;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.subjects.PublishSubject;

/**
 * 项目名: HeartTrip
 * 文件名: SearchedAdpter
 *
 * @author: lukecc0
 * @data:2024/3/17 下午8:51
 * @about: TODO
 */

public class SearchedAdpter extends RecyclerView.Adapter<SearchedAdpter.ViewHolder> {

    private ArrayList<SearchedLocationData.ResultDTO> locationData;

    private PublishSubject<TextView> publishSubject = PublishSubject.create();

    public SearchedAdpter(ArrayList<SearchedLocationData.ResultDTO> locationData) {

        this.locationData = locationData;
    }

    public SearchedAdpter() {
    }

    public void setLocationData(ArrayList<SearchedLocationData.ResultDTO> locationData) {
        this.locationData = locationData;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.searched_city, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Log.e("TAG", "onBindViewHolder: "+locationData.get(position));
        SearchedLocationData.ResultDTO resultDTO = locationData.get(position);

        holder.mainView.setText(resultDTO.getName());
        holder.secondaryView.setText(resultDTO.getProvince() + "-" + resultDTO.getProvince() + "-" + resultDTO.getDistrict());
        holder.tagView.setText(resultDTO.getTag());

        holder.itemView.setOnClickListener(v -> {
            publishSubject.onNext(holder.mainView);
        });
    }

    @Override
    public int getItemCount() {
        return locationData != null ? locationData.size() : 0;
    }


    public Observable<TextView> getClickObservable() {
        return publishSubject.hide();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        View itemView;
        TextView mainView;
        TextView secondaryView;
        TextView tagView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.itemView = itemView;
            mainView = itemView.findViewById(R.id.search_city_list);
            secondaryView = itemView.findViewById(R.id.province_location);
            tagView = itemView.findViewById(R.id.tag_view);
        }
    }
}
