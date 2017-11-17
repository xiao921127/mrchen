package com.wawaji.app.contract;

import com.wawaji.app.entity.MyEntity;
import com.wawaji.app.interf.BaseListChangeListener;
import com.wawaji.common.base.BaseListContract;
import com.wawaji.common.base.BaseModel;

/**
 * 我的页面MVP架构
 *
 * Created by admin on 2017/10/25.
 */

public interface MyContract {

    /**
     * 视图
     *
     * @param <MyMuppetEntity>数据实体
     */
    interface View<MyMuppetEntity> extends BaseListContract.View<MyMuppetEntity> {}

    /**
     * 控制器
     */
    interface Presenter extends BaseListContract.Presenter {
    }

    /**
     * 模型
     */
    interface Model extends BaseModel {

        /**
         * 获取首页数据
         *
         * @param page 页数
         * @param listener 回调
         */
        void getMyData(int page, BaseListChangeListener<MyEntity> listener);

    }

}
