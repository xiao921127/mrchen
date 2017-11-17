package com.wawaji.app.ui.adapter;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.wawaji.app.AppApplication;
import com.wawaji.app.R;
import com.wawaji.app.entity.DollsEntity;
import com.wawaji.common.base.BaseListAdapter;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 娃娃列表适配器
 * <p>
 * Created by admin on 2017/10/23.
 */

public class DollsAdapter extends BaseListAdapter<DollsEntity> {
    /**
     * 构造方法
     *
     * @param context 上下文
     * @param datas   数据
     */
    public DollsAdapter(Context context, ArrayList<DollsEntity> datas) {
        super(context, datas);
    }

    @Override
    protected View getRealView(int position, View view, ViewGroup parent) {
        ViewHolder holder;
        if (null == view || null == view.getTag()) {
            view = getLayoutInflater().inflate(
                    R.layout.listview_dolls_item, parent, false);
            holder = new ViewHolder(view);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        DollsEntity entity = mDatas.get(position);
        if (!TextUtils.isEmpty(entity.img)) {
            Picasso.with(mContext)
                    .load(entity.img)
                    .fit().priority(Picasso.Priority.NORMAL)
//                    .tag(StringConfig.KEY_SUPER_TAG)
                    .placeholder(R.drawable.ic_default_image)
                    .error(R.drawable.ic_default_image)
                    .centerInside()
                    .into(holder.ivDollsItemImg);
        }
        holder.tvDollsItemName.setText(entity.title);
        if (!TextUtils.isEmpty(entity.price)) {
            String expense = String.format(AppApplication.getStringById(R.string.expense), entity.price);
            holder.tvDollsItemPrice.setText(expense);
        }
        if (entity.playing == 1) {
            //游戏中
            holder.tvDollsItemStatus.setText(AppApplication.getStringById(R.string.playing));
            holder.tvDollsItemStatus.setTextColor(ContextCompat.getColor(mContext, R.color.playing_text));
            holder.tvDollsItemStatus.setBackgroundResource(R.drawable.shape_playing_bg);
        } else {
            //空闲中
            holder.tvDollsItemStatus.setText(AppApplication.getStringById(R.string.empty));
            holder.tvDollsItemStatus.setTextColor(ContextCompat.getColor(mContext, R.color.white));
            holder.tvDollsItemStatus.setBackgroundResource(R.drawable.shape_empty_bg);
        }
        return view;
    }

    static class ViewHolder {

        @BindView(R.id.iv_dolls_item_img)
        ImageView ivDollsItemImg;//娃娃图片

        @BindView(R.id.tv_dolls_item_name)
        TextView tvDollsItemName;//娃娃名字

        @BindView(R.id.tv_dolls_item_price)
        TextView tvDollsItemPrice;//房间价格

        @BindView(R.id.tv_dolls_item_status)
        TextView tvDollsItemStatus;//房间状态

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
