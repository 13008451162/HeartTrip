package com.xupt3g.homepageview.view.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.xupt3g.homepageview.R;
import com.xupt3g.homepageview.model.room.Data.HistoryData;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.subjects.PublishSubject;

/**
 * 项目名: HeartTrip
 * 文件名: HistoryAdapter
 *
 * @author: lukecc0
 * @data:2024/4/10 下午8:52
 * @about: TODO 历史记录的适配器
 */

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.ViewHolder> {

    private List<HistoryData> mList;

    private Context context;

    private PublishSubject<TextView> publishSubject = PublishSubject.create();

    public HistoryAdapter() {
        mList = new ArrayList<>();
    }

    public void setmList(@NonNull List<HistoryData> mList) {
        this.mList = mList;
    }

    public HistoryAdapter(List<HistoryData> mList) {
        this.mList = mList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();

        View view = LayoutInflater.from(context).inflate(R.layout.box_text, parent, false);

        ViewHolder holder = new ViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        HistoryData historyData = mList.get(position);

        holder.textView.setText(historyData.locationName);
        holder.itemView.setOnClickListener(v -> publishSubject.onNext(holder.textView));
    }

    @Override
    public int getItemCount() {
        return mList != null ? mList.size() : 0;
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        View itemView;

        TextView textView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.itemView = itemView;
            textView = itemView.findViewById(R.id.box_textView);
        }
    }

    public Observable<TextView> getClickObservable() {
        return publishSubject.hide();
    }
}
