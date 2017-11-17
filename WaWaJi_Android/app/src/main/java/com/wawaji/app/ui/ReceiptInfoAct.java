package com.wawaji.app.ui;

import com.wawaji.app.R;
import com.wawaji.common.base.BaseActivity;
import butterknife.ButterKnife;

public class ReceiptInfoAct extends BaseActivity {

    @Override
    protected void initView() {
        setContentView(R.layout.activity_receipt_info);
        ButterKnife.bind(this);
        setImmersionStatus(R.id.layout);//设置澄清栏
    }

    @Override
    protected void setView() {
        setHead(getString(R.string.info_receipt), R.drawable.ic_back);//设置标题栏信息
    }

    @Override
    protected void setListener() {

    }
}
