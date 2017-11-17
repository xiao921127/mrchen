package com.mrchen.app.mygame.UI.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.mrchen.app.mygame.R;
import com.mrchen.app.mygame.UI.User;
import com.mrchen.app.mygame.UI.mvp.AdpterCeShi;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jianghejie on 15/11/26.
 */
public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {
    public ArrayList<User.DataBean.WawaList> datas = null;
    public Context context;
    private View view;
    private AdpterCeShi adpterCeShi;
    public MyAdapter(ArrayList<User.DataBean.WawaList> datas,Context context,AdpterCeShi adpterCeShi) {
        this.datas = datas;
        this.context=context;
        this.adpterCeShi=adpterCeShi;
    }
    //创建新View，被LayoutManager所调用
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup,int viewType) {
        view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item,viewGroup,false);
        return new ViewHolder(view);
    }
    //将数据与界面进行绑定的操作
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {
        viewHolder.mTextView.setText(datas.get(position).title);
        viewHolder.playing.setText(datas.get(position).playing);
        viewHolder.price.setText(datas.get(position).price+"/每次");

        Glide.with(context.getApplicationContext()).load(datas.get(position).img).asBitmap() .
                error(R.drawable.ic_loading_rotate).
                centerCrop().diskCacheStrategy(DiskCacheStrategy.RESULT ) .into( viewHolder.img);

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adpterCeShi.jiekouff(String.valueOf(position));
            }
        });
    }
    //获取数据的数量
    @Override
    public int getItemCount() {
        return datas.size();
    }
    //自定义的ViewHolder，持有每个Item的的所有界面元素
    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView mTextView;
        public TextView playing;
        public TextView price;
        public ImageView img;
        public ViewHolder(View view){
            super(view);
            mTextView = (TextView) view.findViewById(R.id.text);
            playing = (TextView) view.findViewById(R.id.youxi);
            price=(TextView)view.findViewById(R.id.price);

            img=(ImageView) view.findViewById(R.id.img);
        }

    }

}
