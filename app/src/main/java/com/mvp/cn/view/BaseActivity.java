package com.mvp.cn.view;

import android.app.Dialog;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.mvp.cn.R;
import com.mvp.cn.utils.CustomDialogUtils;
import com.mvp.cn.utils.NavigationBarUtil;

import java.lang.reflect.Field;

import butterknife.BindView;


/**
 * 作者： qiaohao
 * 时间： 2017/4/27 13:19
 * 说明：BaseActivity
 */
public abstract class BaseActivity extends AppCompatActivity {


    public Dialog mLoadding;

    /**
     * titlebar 内容
     */
    @BindView(R.id.tv_title_content)
    TextView titleContent;
    @BindView(R.id.iv_icon_back)
    ImageView imageBack;
    @BindView(R.id.ll_padding_layout)
    LinearLayout paddingLayout;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(layoutResID());

        if(NavigationBarUtil.hasNavigationBar(this)){
            NavigationBarUtil.initActivity(findViewById(android.R.id.content));
        }
        initStatusBar();
        initPresenter();
        initViews();
    }

    // 该方法必须重写，返回acitivity中对应的xml的id
    protected abstract int layoutResID();

    // 初始化组件的方法
    protected abstract void initViews();

    //初始化presenter操作类
    protected  abstract void initPresenter();


    public void setImageBackDisplay(boolean flag) {
        imageBack.setVisibility(flag ? View.VISIBLE : View.GONE);
    }
    public void initStatusBar() {
        //当系统版本为4.4或者4.4以上时可以使用沉浸式状态栏
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            //透明状态栏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            //透明导航栏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
            //
            paddingLayout.setVisibility(View.VISIBLE);
            //获取到状态栏的高度
            int statusHeight = getStatusBarHeight();
            //动态的设置隐藏布局的高度
            LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) paddingLayout.getLayoutParams();
            params.height = statusHeight;
            paddingLayout.setLayoutParams(params);
        }
    }

    /**
     * 通过反射的方式获取状态栏高度
     *
     * @return
     */
    private int getStatusBarHeight() {
        try {
            Class<?> c = Class.forName("com.android.internal.R$dimen");
            Object obj = c.newInstance();
            Field field = c.getField("status_bar_height");
            int x = Integer.parseInt(field.get(obj).toString());
            return getResources().getDimensionPixelSize(x);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }
    /**
     * toast参数是字符串
     * 显示默认位置
     *
     * @param text
     */
    public void showMessage(CharSequence text) {
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
    }

    public void showLoadingDialog(){
        if (mLoadding == null) {
            mLoadding = CustomDialogUtils.getInstance(this).showLoaddingDialog();
            mLoadding.show();
        }else if(mLoadding!=null&&!mLoadding.isShowing()){
            mLoadding.show();
        }
    }
    /**
     * 取消loadding 对话框的显示
     */
    public void dismissLoaddingDialog() {
        if (mLoadding != null&&mLoadding.isShowing()) {
            mLoadding.cancel();
            mLoadding=null;
        }

    }


}
