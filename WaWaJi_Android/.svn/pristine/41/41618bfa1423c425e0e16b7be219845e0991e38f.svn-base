package com.wawaji.app.presenter;
import com.wawaji.app.contract.MyContract;
import com.wawaji.app.entity.MyEntity;
import com.wawaji.app.interf.BaseListChangeListener;
import com.wawaji.app.model.MyModel;
import com.wawaji.common.https.entity.HttpResponse;

import java.util.ArrayList;

/**
 * 在此写用途
 *
 * @author admin
 * @version V1.0 <描述当前版本功能>
 * @since 2017/11/10 10:22
 */

public class MyPresenter implements MyContract.Presenter {

    private MyContract.View<MyEntity> mView;

    private MyModel myMuppetModel;

    public MyPresenter(MyContract.View<MyEntity> view) {
        mView = view;
        myMuppetModel = new MyModel();
    }

    /**
     * 点击事件
     *
     * @param type 点击类型
     */
    @Override
    public void onClick(int type) {

    }

    @Override
    public void onRefresh() {
        myMuppetModel.getMyData(1, dataListener);
    }

    @Override
    public void onLoad() {

    }

    private BaseListChangeListener<MyEntity> dataListener = new BaseListChangeListener<MyEntity>() {
        @Override
        public void onRefresh(ArrayList<MyEntity> list, int more) {
            mView.onRefreshList(list);
        }

        @Override
        public void onLoad(ArrayList<MyEntity> list) {

        }

        @Override
        public void onFailure() {

        }

        @Override
        public void onError(HttpResponse result) {

        }

        @Override
        public void onNoMoreData() {

        }
    };
}
