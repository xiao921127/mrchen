package com.mrchen.app.mygame.UI;

import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import com.mrchen.app.mygame.OnHttpPresenter;
import com.mrchen.app.mygame.R;
import com.mrchen.app.mygame.base.BaseActivity;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SetUpActivity extends BaseActivity{

    @Bind(R.id.one)
    RelativeLayout one;
    @Bind(R.id.two)
    RelativeLayout two;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_up);
        ButterKnife.bind(this);
        setImmersionStatus(new View(this),R.id.rootview);
    }

    @OnClick({R.id.one,R.id.two})
    public void onViewClicked(View view) {
        switch (view.getId()){
            case R.id.one:
                Jump(HomeUIActivity.class);
                break;
            case R.id.two:
                Jump(MyHomeActivity.class);
                break;
        }
    }

}
