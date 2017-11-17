package com.wawaji.app.ui.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.wawaji.app.R;
import com.wawaji.app.entity.PlayerRecordEntity;
import com.wawaji.app.ui.widget.CircleImageView;
import com.wawaji.common.base.BaseListAdapter;
import com.zhy.autolayout.utils.AutoUtils;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 抓住记录适配器
 * <p>
 * Created by admin on 2017/11/7.
 */

public class PlayerRecordAdapter extends BaseListAdapter<PlayerRecordEntity> {
    /**
     * 构造方法
     *
     * @param context 上下文
     * @param datas
     */
    public PlayerRecordAdapter(Context context, ArrayList<PlayerRecordEntity> datas) {
        super(context, datas);
    }

    @Override
    protected View getRealView(int position, View view, ViewGroup parent) {
        ViewHolder holder;
        if (null == view || null == view.getTag()) {
            view = getLayoutInflater().inflate(
                    R.layout.listview_player_record_item, parent, false);
            holder = new ViewHolder(view);
            view.setTag(holder);
            //对于listview，注意添加这一行，即可在item上使用高度
            AutoUtils.autoSize(view);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        PlayerRecordEntity entity = mDatas.get(position);
        if (!TextUtils.isEmpty(entity.avatar)) {
            Picasso.with(mContext)
                    .load(entity.avatar)
                    .fit().priority(Picasso.Priority.NORMAL)
//                    .tag(StringConfig.KEY_SUPER_TAG)
                    .placeholder(R.drawable.ic_default_image)
                    .error(R.drawable.ic_default_image)
                    .centerInside()
                    .into(holder.ivPlayerRecordImg);
        }
        holder.tvPlayerRecordUser.setText(entity.nickname);
        holder.tvPlayerRecordTime.setText(entity.ctime);
        return view;
    }


    static class ViewHolder {

        @BindView(R.id.iv_player_record_img)
        CircleImageView ivPlayerRecordImg;

        @BindView(R.id.tv_player_record_user)
        TextView tvPlayerRecordUser;

        @BindView(R.id.tv_player_record_time)
        TextView tvPlayerRecordTime;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
