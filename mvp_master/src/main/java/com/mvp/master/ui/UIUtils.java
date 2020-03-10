package com.mvp.master.ui;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.WindowManager;

import java.lang.reflect.Field;

/**
 * @author iqiao
 * @date 2020-03-10 10:00
 * @desc 适配工具类
 */
public class UIUtils {


    /**
     * 获取设备状态栏高度的反射类
     */
    private static final String DIMEN_CLASS = "com.android.internal.R$dimen";
    /**
     * 设计稿中的宽度px
     */
    private int width;
    /**
     * 设计稿中的高度px
     */
    private int height;
    /**
     * 设备真实宽度
     */
    public float displayMetricsWidth;
    /**
     * 设备真是高度
     */
    public float displayMetricsHeight;


    private static final class SingleInstance {
        private static final UIUtils INSTANCE = new UIUtils();
    }

    public static UIUtils getInstance() {
        return SingleInstance.INSTANCE;
    }

    /**
     * @param context 上下文
     * @param width   设计稿中的基准宽
     * @param height  设计稿中的基准高
     */
    public void init(Context context, int width, int height) {
        //获取屏幕真是宽高
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics displayMetrices = new DisplayMetrics();
        if (displayMetricsWidth == 0.0F || displayMetricsHeight == 0.0F) {
            windowManager.getDefaultDisplay().getMetrics(displayMetrices);
            //获取状态栏高度
            int statusBarHeight = getSystemBarHeight(context);
            //处理真实宽高
            if (displayMetrices.widthPixels > displayMetrices.heightPixels) {
                //横屏
                this.displayMetricsWidth = (float) displayMetrices.heightPixels;
                this.displayMetricsHeight = (float) displayMetrices.widthPixels - statusBarHeight;
            } else {
                //竖屏
                this.displayMetricsWidth = displayMetrices.widthPixels;
                this.displayMetricsHeight = displayMetrices.heightPixels - statusBarHeight;
            }
            this.width = width;
            //动态获取状态栏高度
            this.height = height - statusBarHeight;
        }
    }

    /**
     * 获取等比例缩放宽度
     *
     * @param width
     * @return
     */
    public int getHorizontal(int width) {
        return (int) (width * this.displayMetricsWidth / this.width);
    }

    /**
     * 获取等比例缩放高度
     *
     * @param height
     * @return
     */
    public int getVertical(int height) {
        return (int) (height * this.displayMetricsHeight / this.height);
    }

    /**
     * 获取等比例缩放宽度
     *
     * @return
     */
    public float getHorizontalValue() {
        return  this.displayMetricsWidth / this.width;
    }

    /**
     * 获取等比例缩放高度
     *
     * @return
     */
    public float getVerticalValue() {
        return this.displayMetricsHeight / this.height;
    }


    /**
     * 获取状态栏高度
     *
     * @param context
     * @return
     */
    private int getSystemBarHeight(Context context) {
        return getValue(context, DIMEN_CLASS, "system_bar_height", 48);
    }

    /**
     * @param context        上下文
     * @param attrGroupClass 安卓源码中存放围度
     * @param attrName       状态栏信息
     * @param i              默认值
     * @return 真实状态栏高度
     */
    private int getValue(Context context, String attrGroupClass, String attrName, int i) {
        try {
            Class e = Class.forName(attrGroupClass);
            Object object = e.newInstance();
            Field field = e.getField(attrName);
            int height = Integer.parseInt(field.get(object).toString());
            return context.getResources().getDimensionPixelOffset(height);
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
        return i;
    }


}
