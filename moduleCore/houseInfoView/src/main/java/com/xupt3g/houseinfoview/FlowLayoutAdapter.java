package com.xupt3g.houseinfoview;

import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.xuexiang.xui.widget.flowlayout.BaseTagAdapter;

import java.util.List;
import java.util.Random;

/**
 * 项目名: HeartTrip
 * 文件名: com.xupt3g.houseinfoview.FlowLayoutAdapter
 *
 * @author: shallew
 * @data: 2024/3/7 16:55
 * @about: TODO 用来显示标签的流布局的适配器
 */
public class FlowLayoutAdapter extends BaseTagAdapter<String, TextView> {

    private List<String> dataList;
    /**
     * 是否启用多色彩标签
     */
    private boolean useColorfulTags;
    /**
     * 默认颜色
     */
    private final int defaultColor = Color.parseColor("#299ee3");
    private int[] colors = {Color.parseColor("#299ee3"), Color.parseColor("#f9748f"),
            Color.parseColor("#0ba360"),Color.parseColor("#fda085")};
    public FlowLayoutAdapter(Context context, boolean useColorfulTags) {
        super(context);
        this.useColorfulTags = useColorfulTags;
    }

    public FlowLayoutAdapter(Context context, List<String> data) {
        super(context, data);
        this.dataList = data;
    }

    public FlowLayoutAdapter(Context context, String[] data) {
        super(context, data);
    }

    @Override
    protected TextView newViewHolder(View convertView) {
        return (TextView) convertView.findViewById(R.id.tag_item);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.houseinfo_tags_item;
    }

    @Override
    protected void convert(TextView holder, String item, int position) {
        holder.setText(item);
        if (useColorfulTags) {
            Random random = new Random();
            int randomColor = random.nextInt(colors.length);
            holder.setTextColor(colors[randomColor]);
            Drawable drawable = getDrawable(R.drawable.houseinfo_tags_border);
            GradientDrawable gradientDrawable = new GradientDrawable();
            //设置填充颜色
            gradientDrawable.setColor(Color.WHITE);
            //设置边框颜色和宽度
            gradientDrawable.setStroke(2, colors[randomColor]);
            //设置圆角
            gradientDrawable.setCornerRadius(10);
            holder.setBackground(gradientDrawable);
        } else {
            holder.setTextSize(TypedValue.COMPLEX_UNIT_SP, 13);
        }

    }
}
