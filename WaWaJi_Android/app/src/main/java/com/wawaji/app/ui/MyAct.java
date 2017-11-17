package com.wawaji.app.ui;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.wawaji.app.AppApplication;
import com.wawaji.app.R;
import com.wawaji.app.contract.MyContract;
import com.wawaji.app.contract.PlayDetailsContract;
import com.wawaji.app.entity.MyEntity;
import com.wawaji.app.entity.PlayEntity;
import com.wawaji.app.entity.UserEntity;
import com.wawaji.app.presenter.MyPresenter;
import com.wawaji.app.presenter.PlayDetailsPresenter;
import com.wawaji.app.ui.adapter.MyMuppetAdapter;
import com.wawaji.app.ui.widget.CircleImageView;
import com.wawaji.common.base.BaseActivity;
import com.wawaji.common.gridview.XStaggerView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 我的页面
 * <p>
 * Created by admin on 2017/10/25.
 */

public class MyAct extends BaseActivity implements MyContract.View<MyEntity>, AdapterView.OnItemClickListener, PlayDetailsContract.View {

    @BindView(R.id.iv_my_back)
    ImageView ivMyBack;//返回

    @BindView(R.id.tv_my_title)
    TextView tvMyTitle;//

    @BindView(R.id.iv_my_record)
    ImageView ivMyRecord;//

    @BindView(R.id.iv_my_head)
    CircleImageView ivMyHead;//头像

    @BindView(R.id.tv_my_nickname)
    TextView tvMyNickname;//用户名

    @BindView(R.id.tv_my_capture)
    TextView tvMyCapture;//抓住次数

    @BindView(R.id.tv_my_coins)
    TextView tvMyCoins;//金币数

    @BindView(R.id.gv_base_gridview)
    XStaggerView gvBaseGridview;

    @BindView(R.id.recharge_rl)
    ImageView rechargeRl;

    private ArrayList<MyEntity> mList = new ArrayList<>();

    private MyMuppetAdapter myMuppetAdapter;

    private MyPresenter myPresenter = new MyPresenter(this);

    private PlayDetailsPresenter playDetailsPresenter = new PlayDetailsPresenter(this);

    @Override
    protected void initView() {
        setContentView(R.layout.activity_my);
        ButterKnife.bind(this);
        setImmersionStatus(R.id.layout);
    }

    @Override
    protected void setView() {
        setActivityName(MyAct.class.getSimpleName());

        myMuppetAdapter = new MyMuppetAdapter(this, mList);
        gvBaseGridview.setAdapter(myMuppetAdapter);
        gvBaseGridview.setMode(XStaggerView.Mode.DISABLED);

        UserEntity entity = UserEntity.getCurUser();
        if (!TextUtils.isEmpty(entity.avatar)) {
            Picasso.with(this)
                    .load(entity.avatar)
                    .fit().priority(Picasso.Priority.NORMAL)
//                    .tag(StringConfig.KEY_SUPER_TAG)
                    .placeholder(R.drawable.ic_default_image)
                    .error(R.drawable.ic_default_image)
                    .centerInside()
                    .into(ivMyHead);
        }
        setText(tvMyNickname, entity.nickname);
        setText(tvMyCoins, entity.wmoney+"");
        setText(tvMyCapture, String.format(AppApplication.getStringById(R.string.caught_count), entity.catch_count));
        myPresenter.onRefresh();
    }

    @Override
    protected void setListener() {
        gvBaseGridview.setOnItemClickListener(this);
    }

    @OnClick({R.id.iv_my_back, R.id.tv_my_title, R.id.iv_my_record, R.id.iv_my_head, R.id.tv_my_nickname
            , R.id.tv_my_capture, R.id.tv_my_coins, R.id.recharge_rl})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_my_back:
                finish();
                break;
            case R.id.tv_my_title:
                break;
            case R.id.iv_my_record:
                break;
            case R.id.iv_my_head:
                break;
            case R.id.tv_my_nickname:
                break;
            case R.id.tv_my_capture:
                break;
            case R.id.tv_my_coins:
                break;
            case R.id.recharge_rl:
                startIntent(RechargeActivity.class, null);
                break;
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        MyEntity myEntity = (MyEntity) parent.getAdapter().getItem(position);
        playDetailsPresenter.getPlayDetails(myEntity.id);
    }

    /**
     * 跳转页面
     *
     * @param cls    页面class
     * @param bundle 数据
     */
    @Override
    public void startAct(Class<?> cls, Bundle bundle) {

    }

    @Override
    public void onRefreshList(ArrayList<MyEntity> list) {
        mList.clear();
        mList.addAll(list);
        myMuppetAdapter.notifyDataSetChanged();
    }

    @Override
    public void onLoadList(ArrayList<MyEntity> list) {

    }

    @Override
    public void onNoLoad() {

    }

    @Override
    public void onNoData() {

    }

    /**
     * 传入各个属性数据到娃娃详情界面
     * @param playDetailsEntity
     */
    @Override
    public void onBarrage(PlayEntity playDetailsEntity) {
        Bundle bundle = new Bundle();
        bundle.putSerializable("entity", playDetailsEntity);
        startIntent(PlayDetailsAct.class, bundle);
    }
}