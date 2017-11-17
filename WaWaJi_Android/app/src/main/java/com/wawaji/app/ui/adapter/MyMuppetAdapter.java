package com.wawaji.app.ui.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.wawaji.app.R;
import com.wawaji.app.entity.MyEntity;
import com.wawaji.common.base.BaseListAdapter;

import java.util.ArrayList;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 娃娃列表适配器
 * <p>
 * Created by admin on 2017/10/23.
 */

public class MyMuppetAdapter extends BaseListAdapter<MyEntity> {
    /**
     * 构造方法
     *
     * @param context 上下文
     * @param datas
     */
    public MyMuppetAdapter(Context context, ArrayList<MyEntity> datas) {
        super(context, datas);
    }

    @Override
    protected View getRealView(int position, View view, ViewGroup parent) {
        ViewHolder holder;
        if (null == view || null == view.getTag()) {
            view = getLayoutInflater().inflate(
                    R.layout.item_my_muppet, parent, false);
            holder = new ViewHolder(view);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        MyEntity entity = mDatas.get(position);
        if (!TextUtils.isEmpty(entity.img)) {
            Picasso.with(mContext)
                    .load(entity.img)
                    .fit().priority(Picasso.Priority.NORMAL)
//                    .tag(StringConfig.KEY_SUPER_TAG)
                    .placeholder(R.drawable.ic_default_image)
                    .error(R.drawable.ic_default_image)
                    .centerInside()
                    .into(holder.itemMyMuppetImg);
        }
        holder.itemMyMuppetName.setText(entity.title);//娃娃名字
        holder.itemMyMuppetTime.setText(entity.ctime);//时间
        return view;
    }


    static class ViewHolder {

        @BindView(R.id.item_my_muppet_img)
        ImageView itemMyMuppetImg;//娃娃图片

        @BindView(R.id.item_my_muppet_name)
        TextView itemMyMuppetName;//娃娃名字

        @BindView(R.id.item_my_muppet_time)
        TextView itemMyMuppetTime;//时间

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}

