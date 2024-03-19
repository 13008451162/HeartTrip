package com.xupt3g.homepageview.view.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.xupt3g.homepageview.R;
import com.xupt3g.homepageview.model.CityData;

import java.util.ArrayList;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.subjects.PublishSubject;

/**
 * 项目名: HeartTrip
 * 文件名: PopularCityAdpter
 *
 * @author: lukecc0
 * @data:2024/3/14 下午8:40
 * @about: TODO
 */

public class PopularCityAdpter extends RecyclerView.Adapter<PopularCityAdpter.ViewHolder> {

    private ArrayList<CityData> cityList;

    private PublishSubject<TextView> clickSubject;

    public PopularCityAdpter(ArrayList<CityData> cityList, PublishSubject<TextView> clickSubject) {
        this.cityList = cityList;
        this.clickSubject = clickSubject;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        ViewHolder viewHolder = new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.box_text, parent, false));

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.textView.setText(cityList.get(position).getString());
        holder.cardView.setOnClickListener(v -> clickSubject.onNext(holder.textView));
    }


    @Override
    public int getItemCount() {
        return cityList != null ? cityList.size() : 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        View cardView;

        TextView textView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.cardView = itemView;
            this.textView = itemView.findViewById(R.id.box_textView);
        }
    }
}
