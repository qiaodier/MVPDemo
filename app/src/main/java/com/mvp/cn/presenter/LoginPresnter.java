package com.mvp.cn.presenter;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.mvp.cn.interfacem.ILoginInterface;
import com.mvp.cn.model.BaseResponseEntity;
import com.mvp.cn.net.HttpRequestUtil;
import com.mvp.cn.utils.DownloadUtil;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * 作者： qiaohao
 * 时间： 2017/4/27 14:02
 * 说明：LoginPresnter
 */
public class LoginPresnter extends BasePresenter<ILoginInterface> {

    private static final int LOGIN_REQUEST_CODE = 0x01;
    public ILoginInterface mLoginInterface;
    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            mLoginInterface.dismissLoadingDialog();
            switch (msg.what){
                case 1:
                    mLoginInterface.requestSuccess();
                    break;
                case 3:
                    int progress = (int) msg.obj;
                    mLoginInterface.onProgress(progress);
                    break;
                case 2:
                    mLoginInterface.requestFail();
                    break;
            }
        }
    };



    public LoginPresnter(ILoginInterface loginInterface) {
        this.mLoginInterface = loginInterface;
    }

    public void login() {
//        String userName = mLoginInterface.getUserName();
//        String userPwd = mLoginInterface.getUserPwd();
//        if (userName != null && !userName.equals("") && userPwd != null && !userPwd.equals("")) {
//            HttpRequestUtil.getOkClient().login(userPwd).enqueue(new Callback<String>() {
//                @Override
//                public void onResponse(Call<String> call, Response<String> response) {
//                    mHandler.sendEmptyMessage(1);
//                }
//
//                @Override
//                public void onFailure(Call<String> call, Throwable t) {
//                    mHandler.sendEmptyMessage(2);
//                }
//            });
//
//
//        }
        Log.e("下载进度","开始");
        DownloadUtil.get().download("http://192.168.11.127:9000/equ.apk", "download", new DownloadUtil.OnDownloadListener() {
            @Override
            public void onDownloadSuccess() {
                Log.e("下载进度","完成");
                mHandler.sendEmptyMessage(1);
            }
            @Override
            public void onDownloading(int progress) {
                Log.e("下载进度","正在   "+progress);
                Message msg = new Message();
                msg.obj=progress;
                msg.what=3;
                mHandler.sendMessage(msg);
            }
            @Override
            public void onDownloadFailed() {
                Log.e("下载进度","失败");
                mHandler.sendEmptyMessage(2);
            }
        });

    }
}
