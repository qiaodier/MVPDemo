package com.mvp.cn.view;

import android.app.Dialog;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.mvp.cn.utils.CustomDialogUtils;


/**
 * 作者： qiaohao
 * 时间： 2017/4/27 13:19
 * 说明：BaseActivity
 */
public abstract class BaseActivity extends AppCompatActivity {


    public Dialog mLoadding;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(layoutResID());
        initViews();
        initPresenter();
    }

    // 该方法必须重写，返回acitivity中对应的xml的id
    protected abstract int layoutResID();

    // 初始化组件的方法
    protected abstract void initViews();

    //初始化presenter操作类
    protected  abstract void initPresenter();


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
