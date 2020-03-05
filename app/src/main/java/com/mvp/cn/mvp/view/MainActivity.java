package com.mvp.cn.mvp.view;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.mvp.cn.R;
import com.mvp.cn.mvp.base.BaseActivity;
import com.mvp.cn.mvp.base.BasePresenter;
import com.mvp.compile.Route;

import androidx.appcompat.widget.Toolbar;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;
import butterknife.BindView;

/**
 * Created by iqiao on 2020-03-04 10:35
 * Desc: 该类主要完成fragment展示和切换
 *
 * @author iqiao
 */
@Route("/main")
public class MainActivity extends BaseActivity {

    @BindView(R.id.bottomNavigationView)
    BottomNavigationView bottomNavigationView;
    @BindView(R.id.toolbar2)
    Toolbar toolbar;

    @Override
    protected int layoutResID() {
        return R.layout.activity_main;
    }

//    @Override
//    public boolean onSupportNavigateUp() {
//        return Navigation.findNavController(this, R.id.fragment).navigateUp();
//    }

    @Override
    protected void initViews() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        NavController navController = Navigation.findNavController(this, R.id.fragment);
//        NavigationUI.setupActionBarWithNavController(this, navController);
        NavigationUI.setupWithNavController(bottomNavigationView, navController);

    }

    @Override
    protected BasePresenter initPresenter() {
        return null;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
