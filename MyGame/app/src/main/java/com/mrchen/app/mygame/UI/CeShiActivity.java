package com.mrchen.app.mygame.UI;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.widget.ImageView;
import android.widget.Toast;

import com.mrchen.app.mygame.R;
import com.mrchen.app.mygame.UI.adapter.HeaderBottomAdapter;
import com.mrchen.app.mygame.UI.adapter.TestRecyclerViewAdapter;
import com.mrchen.app.mygame.UI.view.BaseRefreshRecyclerView;
import com.mrchen.app.mygame.base.BaseActivity;
import com.zhy.adapter.recyclerview.wrapper.HeaderAndFooterWrapper;
import com.zhy.adapter.recyclerview.wrapper.LoadMoreWrapper;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class CeShiActivity extends BaseActivity {

    @Bind(R.id.rl)
    BaseRefreshRecyclerView rcv_test;

    private TestRecyclerViewAdapter madapter;
    ////////////////////////////////
    private HeaderAndFooterWrapper mHeaderAndFooterWrapper;
    private LoadMoreWrapper mLoadMoreWrapper;
    /////////////////////////////////////
    HeaderBottomAdapter headerBottomAdapter;
    GridLayoutManager gridLayoutManager;
    LinearLayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ce_shi);
        ButterKnife.bind(this);

        initView();

    }

    private void initView() {
        madapter = new TestRecyclerViewAdapter();
//        mHeaderAndFooterWrapper = new HeaderAndFooterWrapper(madapter);
//        ImageView t1 = new ImageView(this);
////        t1.setText("Header 1");
//        t1.setImageResource(R.mipmap.ic_launcher_round);
//        mHeaderAndFooterWrapper.addHeaderView(t1);
//        mLoadMoreWrapper = new LoadMoreWrapper(mHeaderAndFooterWrapper);


        StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        rcv_test.setLayoutManager(staggeredGridLayoutManager);
//        rcv_test.addItemDecoration(new SimpleItemDecoration(20,3));
        staggeredGridLayoutManager.setGapStrategy(StaggeredGridLayoutManager.GAP_HANDLING_NONE);
        ArrayList list = new ArrayList();
        for (int i = 0; i < 15; i++) {
            list.add(i);
        }
        madapter.setData(list);
        //////////////////////////////////////////

//List布局
//        layoutManager=new LinearLayoutManager(this);
//        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
//        rcv_test.setLayoutManager(layoutManager);
//        rcv_test.setAdapter(headerBottomAdapter=new HeaderBottomAdapter(this));
////////////////////////////////////////////////////
        rcv_test.setAdapter(madapter);
        rcv_test.setOnRefreshAndLoadMoreListener(new BaseRefreshRecyclerView.OnRefreshAndLoadMoreListener() {
            @Override
            public void onRefresh() {
                Toast.makeText(CeShiActivity.this, "Refreshing", Toast.LENGTH_SHORT).show();
                rcv_test.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        rcv_test.completeRefresh();
                    }
                }, 3000);
            }

            @Override
            public void onLoadMore() {
                rcv_test.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        List data = madapter.getData();
                        for (int i = 0; i < 9; i++) {
                            data.add(i * 1000);
                        }
                        madapter.setData(data);
                        madapter.notifyDataSetChanged();
                        rcv_test.completeLoadMore();
                    }
                }, 3000);
            }
        });

    }
}
