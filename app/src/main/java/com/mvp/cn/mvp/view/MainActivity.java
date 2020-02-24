package com.mvp.cn.mvp.view;

import com.mvp.cn.R;
import com.mvp.cn.mvp.base.BaseActivity;
import com.mvp.cn.mvp.base.BasePresenter;
import com.mvp.compile.Route;

/**
 * Created by iqiao on 2020-02-24 14:17
 * Desc:
 */
@Route("/main")
public class MainActivity extends BaseActivity {
    @Override
    protected int layoutResID() {
        return R.layout.activity_main;
    }

    @Override
    protected void initViews() {

    }

    @Override
    protected BasePresenter initPresenter() {
        return null;
    }
}
