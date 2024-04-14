package com.xupt3g.searchresultview.View.Adapter;

import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.xuexiang.xui.adapter.listview.BaseListAdapter;
import com.xupt3g.searchresultview.R;

/**
 * 项目名: HeartTrip
 * 文件名: ListDropDownAdapter
 *
 * @author: lukecc0
 * @data:2024/4/11 下午3:35
 * @about: TODO 智能排序下拉适配器
 */

public class ListDropDownAdapter extends BaseListAdapter<String, ListDropDownAdapter.ViewHolder> {

    public ListDropDownAdapter(Context context, String[] data) {
        super(context, data);
    }

    @Override
    protected ViewHolder newViewHolder(View convertView) {
        return new ViewHolder(convertView);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.adapter_drop_down_list_item;
    }

    @Override
    protected void convert(ViewHolder holder, String item, int position) {
        holder.mText.setText(item);
        if (mSelectPosition != -1) {
            if (mSelectPosition == position) {
                holder.mText.setSelected(true);
                holder.mText.setBackgroundResource(R.color.check_bg);
            } else {
                holder.mText.setSelected(false);
                holder.mText.setBackgroundResource(R.color.white);
            }
        }
    }

    static class ViewHolder {
        TextView mText;

        ViewHolder(View view) {
            mText = view.findViewById(R.id.text);
            mText.setGravity(Gravity.CENTER);
        }
    }
}
