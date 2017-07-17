package com.mvp.cn.utils;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.mvp.cn.R;


/**
 * Created by qiaohao on 2016/10/14.
 * 说明：负责显示对话框
 */
public class CustomDialogUtils {

    static CustomDialogUtils utils;
    private Context mContext;
    public static CustomDialogUtils getInstance(Context context) {
            utils = new CustomDialogUtils(context);
        return utils;
    }

    public CustomDialogUtils(Context context) {
        mContext = context;
    }

//    /**
//     * 自定义确定，取消对话框
//     * 创建
//     *
//     * @param title
//     * @param message
//     * @param onclickok
//     * @return
//     */
//    public CustomDialog createCustomDialog(Context context, String title, String message, DialogInterface.OnClickListener onclickok, DialogInterface.OnClickListener onclickcancel) {
//        CustomDialog.Builder builder = new CustomDialog.Builder(context);
//        builder.setMessage(message);
//        builder.setTitle(title);
//        builder.setPositiveButton(R.string.button_ok, onclickok);
//        builder.setNegativeButton(R.string.button_cancel, onclickcancel);
//        return builder.create();
//    }

//    /**
//     * 自定义对话框提供两个选项按钮的文字自定义设置
//     * @param context
//     * @param title
//     * @param message
//     * @param okText
//     * @param cancleText
//     * @param onclickok
//     * @param onclickcancel
//     * @return
//     */
//    public CustomDialog createCustomDialog(Context context, String title, String message, String okText,String cancleText,DialogInterface.OnClickListener onclickok, DialogInterface.OnClickListener onclickcancel) {
//        CustomDialog.Builder builder = new CustomDialog.Builder(context);
//        builder.setMessage(message);
//        builder.setTitle(title);
//        builder.setPositiveButton(okText, onclickok);
//        builder.setNegativeButton(cancleText, onclickcancel);
//        return builder.create();
//    }

//    /**
//     * 自定义对话框
//     * show
//     *
//     * @param title
//     * @param message
//     * @param onclickok
//     * @return
//     */
//    public CustomDialog showCustomDialog(Context context, String title, String message, DialogInterface.OnClickListener onclickok, DialogInterface.OnClickListener onclickcancel) {
//        CustomDialog dialog = createCustomDialog(context, title, message, onclickok, onclickcancel);
//        dialog.show();
//        return dialog;
//    }


    /**
     * 显示正在加载对话框
     *
     * @return
     */
    public Dialog showLoaddingDialog() {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View loaddingView =inflater .inflate(R.layout.common_dialog_loading_layout, null);
        Dialog animationDialog = new Dialog(mContext, R.style.tip_dialog);
        animationDialog.setCancelable(true);
        animationDialog.setContentView(loaddingView);
        ImageView imageView = (ImageView) loaddingView.findViewById(R.id.iv_route);
        RotateAnimation an = new RotateAnimation(0, 360, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
//        an.setInterpolator(new LinearInterpolator());//不停顿
        an.setRepeatCount(-1);//重复次数
//        an.setFillAfter(true);//停在最后
        an.setDuration(1500);
        imageView.startAnimation(an);
        return animationDialog;
    }


//    /**
//     * 显示listview对话框
//     * 需要传入adapter
//     *
//     * @param context
//     * @param adapter
//     * @param title
//     * @param onclickok
//     * @param onclickcancel
//     * @return
//     */
//
//    public ListViewDialogUtils showListDialog(Context context, BaseAdapter adapter, String title, DialogInterface.OnClickListener onclickok, DialogInterface.OnClickListener onclickcancel) {
//        ListViewDialogUtils.Builder listViewDialog = new ListViewDialogUtils.Builder(context);
//        listViewDialog.setMessage(adapter);
//        listViewDialog.setTitle(title);
//        listViewDialog.setPositiveButton(R.string.button_ok, onclickok);
//        listViewDialog.setNegativeButton(R.string.button_cancel, onclickcancel);
//        return listViewDialog.create();
//    }

   /* *//**
     * 弹出支付密码输入dialog
     * @param context
     * @param title
     * @param message
     * @param editLisener
     * @param closeDialog
     * @return
     *//*
    public CustomPayPwdDialog showPayDialog(Context context, String title, String message, CustomPayPwdDialog.Builder.IPayPwdEditLisener editLisener, View.OnClickListener closeDialog){
        CustomPayPwdDialog.Builder payDialog = new CustomPayPwdDialog.Builder(context);
        payDialog.setTitle(title);
        payDialog.setMessage(message);
        payDialog.setColseDialogButton(closeDialog);
        payDialog.setOnEditFinish(editLisener);
        return payDialog.create();
    }
*/


}
