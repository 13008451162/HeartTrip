package com.xupt3g.homepageview.view.Adapter;

import static com.xupt3g.homepageview.model.CityOfCollect.TYPE_DATA;
import static com.xupt3g.homepageview.model.CityOfCollect.TYPE_HEADER;
import static com.xupt3g.homepageview.model.CityOfCollect.TYPE_RECOMMEND_HEADER;

import android.content.Context;
import android.database.Observable;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.xupt3g.homepageview.R;
import com.xupt3g.homepageview.model.CityData;
import com.xupt3g.homepageview.model.CityOfCollect;

import java.util.List;

import io.reactivex.rxjava3.subjects.PublishSubject;

/**
 * 项目名: HeartTrip
 * 文件名: CityAdapter
 *
 * @author: lukecc0
 * @data:2024/3/14 上午10:57
 * @about: TODO 选择页的城市信息
 */

public class CityAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;

    private List<CityData> simpleDatas;

    private final PublishSubject<TextView> clickSubject = PublishSubject.create();

    public CityAdapter(Context context, List<CityData> simpleDatas) {
        this.context = context;
        this.simpleDatas = simpleDatas;
    }

    @Override
    public int getItemViewType(int position) {
        return simpleDatas.get(position).getType();
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        RecyclerView.ViewHolder viewHolder;

        if (viewType == TYPE_RECOMMEND_HEADER) {
            viewHolder = new PopViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.popular_cities, parent, false));
        } else {
            viewHolder = new OrdViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.city_simle_list, parent, false));
        }

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        if (holder instanceof PopViewHolder) {

            PopViewHolder popViewHolder = (PopViewHolder) holder;
            popViewHolder.textView.setTypeface(null, Typeface.BOLD);
            popViewHolder.textView.setTextSize(20);
            popViewHolder.textView.setText(simpleDatas.get(position).getString());

            PopularCityAdpter popularCityAdpter =
                    new PopularCityAdpter(CityOfCollect.getRecommendCity(), clickSubject);
            popViewHolder.recyclerView.setLayoutManager(new GridLayoutManager(context, 3));
            popViewHolder.recyclerView.setAdapter(popularCityAdpter);


        } else if (holder instanceof OrdViewHolder) {

            OrdViewHolder ordViewHolder = (OrdViewHolder) holder;

            ordViewHolder.cityData = simpleDatas.get(position);
            ordViewHolder.textView.setText(simpleDatas.get(position).getString());

            switch (ordViewHolder.cityData.getType()) {
                case TYPE_HEADER:
                    ordViewHolder.textView.setTypeface(null, Typeface.BOLD);
                    ordViewHolder.textView.setTextSize(20);
                    break;
                case TYPE_DATA:
                    ordViewHolder.view.setOnClickListener(v -> clickSubject.onNext(ordViewHolder.textView));
                    break;
                default:
                    break;
            }

        }

    }

    @Override
    public int getItemCount() {
        return simpleDatas != null ? simpleDatas.size() : 0;
    }

    public String getLetterForPosition(int firstVisibleItemPosition) {
        return simpleDatas.get(firstVisibleItemPosition).getString();
    }

    public class PopViewHolder extends RecyclerView.ViewHolder {

        View view;
        RecyclerView recyclerView;
        TextView textView;

        public CityData cityData;

        public PopViewHolder(@NonNull View itemView) {
            super(itemView);
            this.view = itemView;
            this.textView = itemView.findViewById(R.id.popCity_list_view);
            this.recyclerView = itemView.findViewById(R.id.popular_city_recyclerView);
        }
    }

    public class OrdViewHolder extends RecyclerView.ViewHolder {

        public View view;
        public TextView textView;
        public CityData cityData;

        public OrdViewHolder(@NonNull View itemView) {
            super(itemView);
            this.view = itemView;
            this.textView = itemView.findViewById(R.id.City_list_view);

        }
    }

    /**
     * 获取字母位置
     *
     * @param header 传入字母
     * @return int 字母位置
     */
    public int getLetterPosition(String header) {
        for (int i = 0; i < simpleDatas.size(); i++) {

            if (simpleDatas.get(i).getString().equals(header)) {
                return i;
            }

        }
        return -1;

    }


    /**
     * 获取点击的视图
     *
     * @return {@link Observable}<{@link TextView}>
     */
    public io.reactivex.rxjava3.core.Observable<TextView> getClickObservable(){
        return clickSubject.hide();
    }
}
