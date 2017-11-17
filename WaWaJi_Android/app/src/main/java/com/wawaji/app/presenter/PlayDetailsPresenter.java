package com.wawaji.app.presenter;

import android.os.Bundle;
import android.util.Log;

import com.wawaji.app.AppApplication;
import com.wawaji.app.R;
import com.wawaji.app.contract.PlayDetailsContract;
import com.wawaji.app.entity.PlayEntity;
import com.wawaji.app.interf.OnRequestChangeListener;
import com.wawaji.app.model.PlayDetailsModel;
import com.wawaji.app.ui.WebAct;

/**
 * 在此写用途
 *
 * @author admin
 * @version V1.0 <描述当前版本功能>
 * @since 2017/11/13 11:52
 */

public class PlayDetailsPresenter implements PlayDetailsContract.Presenter {

    private PlayDetailsContract.View mView;

    private PlayDetailsModel mModel;

    public PlayDetailsPresenter(PlayDetailsContract.View view) {
        mView = view;
        mModel = new PlayDetailsModel();
    }

    /**
     * 点击事件
     *
     * @param type 点击类型
     */
    @Override
    public void onClick(int type) {
        switch (type) {
            case 1:
                Log.e("MrChen", "地址选填");
                Bundle bundle = new Bundle();
                bundle.putString("url", AppApplication.getStringById(R.string.url_my_address));
                mView.startAct(WebAct.class, bundle);
                break;
            case 2:
                Log.e("MrChen", "兑换娃娃币");
                break;
            case 3:
                Log.e("MrChen", "发货给客户");
                break;
        }

    }

    /**
     * 获取相关数据
     *
     * @param id 接口需要的id
     */
    @Override
    public void getPlayDetails(String id) {
        mModel.getPlayDetails(id, RequestBarrage);
    }

    /**
     * 发送弹幕回调
     */
    private OnRequestChangeListener<PlayEntity> RequestBarrage = new OnRequestChangeListener<PlayEntity>() {

        @Override
        public void onSuccess(PlayEntity playDetailsEntity) {
            mView.onBarrage(playDetailsEntity);
        }

        @Override
        public void onFailure() {

        }

        @Override
        public void onError() {

        }
    };
}
