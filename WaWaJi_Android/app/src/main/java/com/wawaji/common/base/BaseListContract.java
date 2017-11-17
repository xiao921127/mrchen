package com.wawaji.common.base;


import com.wawaji.app.interf.BaseListChangeListener;

import java.util.ArrayList;

/**
 * 基础列表页面MVP架构
 *
 * Created by m on 2017/1/17.
 */

public interface BaseListContract {

    interface View<T> extends BaseView {

        void onRefreshList(ArrayList<T> list);

        void onLoadList(ArrayList<T> list);

        void onNoLoad();

        void onNoData();


    }

    interface Presenter extends BasePresenter {

        void onRefresh();

        void onLoad();

//        void onCancel();

    }

    interface Model<T> extends BaseModel {

        void onLoad(BaseListChangeListener<T> l);

        void onRefresh(BaseListChangeListener<T> l);
    }
}
