package com.xupt3g.searchresultview.View.Adapter;

import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.xuexiang.xui.adapter.listview.BaseListAdapter;
import com.xuexiang.xui.widget.picker.XRangeSlider;
import com.xupt3g.searchresultview.R;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 项目名: HeartTrip
 * 文件名: PriceRangeAdapter
 *
 * @author: lukecc0
 * @data:2024/4/11 下午8:15
 * @about: TODO 价格范围适配器
 */

public class PriceRangeAdapter extends BaseListAdapter<String, PriceRangeAdapter.ViewHolder> {

    private static final Pattern pattern = Pattern.compile("￥?(\\d+)-?￥?(\\d+)?");
    private final XRangeSlider xrsBubble;
    View priceRangeView;

    public PriceRangeAdapter(Context context, View priceRangeView, String[] data) {
        super(context, data);
        this.priceRangeView = priceRangeView;
        xrsBubble = priceRangeView.findViewById(R.id.xrs_bubble);
    }

    @Override
    protected ViewHolder newViewHolder(View convertView) {
        return new ViewHolder(convertView);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.adapter_drop_down_price;
    }


    @Override
    protected void convert(ViewHolder holder, String item, int position) {
        holder.mText.setText(item);
        if (mSelectPosition != -1) {
            if (mSelectPosition == position) {
//                holder.mText.setSelected(true);
                int number[] = regularExtraction(holder.mText.getText().toString());
                xrsBubble.setStartingMinMax(number[0], number[1]);
            } else {
                holder.mText.setSelected(false);
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


    /**
     * 提取数据值
     *
     * @param price
     */
    private static int[] regularExtraction(String price) {
        int[] number = new int[2];
        Matcher matcher = pattern.matcher(price);
        if (matcher.find()) {
            String startStr = matcher.group(1);
            String endStr = matcher.group(2);

            // 处理价格字符串可能存在的 '￥' 符号
            if (startStr.startsWith("￥")) {
                startStr = startStr.substring(1);
            }
            if (endStr != null && endStr.startsWith("￥")) {
                endStr = endStr.substring(1);
            }

            // 为了确保正确处理 "150以下" 这样的情况，我们首先解析数字字符串
            int start = parsePriceString(startStr);
            int end = endStr != null ? parsePriceString(endStr) : start;

            // 处理价格范围为 '150以下' 和 '600以上' 的情况
            if (price.endsWith("以下")) {
                end = start;
                start = 0;
            } else if (price.endsWith("以上")) {
                end = 1000;
            }

            number[0] = start;
            number[1] = end;
        }
        return number;
    }

    private static int parsePriceString(String priceStr) {
        if (priceStr.length() > 2 && priceStr.charAt(0) == '1') {
            return Integer.parseInt(priceStr.substring(0, 3));
        } else {
            return Integer.parseInt(priceStr);
        }
    }
}
