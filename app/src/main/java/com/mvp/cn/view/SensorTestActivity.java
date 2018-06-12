package com.mvp.cn.view;

import android.graphics.Matrix;
import android.graphics.PointF;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorListener;
import android.hardware.SensorManager;
import android.util.Log;
import android.view.animation.Animation;
import android.view.animation.Transformation;
import android.widget.ImageView;
import android.widget.Scroller;

import com.mvp.cn.R;

/**
 * 作者： qiaohao
 * 时间： 2017/10/12 09:49
 * 说明：SensorTestActivity
 */
public class SensorTestActivity extends BaseActivity implements SensorListener {
    private static final int SHAKE_THRESHOLD = 50;
    //这个控制精度，越小表示反应越灵敏
    private float x, y, z, last_x, last_y, last_z;
    private long lastUpdate;
    private ImageView ivBg;
//
//    private Matrix mMatrix;
//
//    private SensorManager sensorManager;
//
//    //以速度1向左移动
//    private static final int LEFT_1 = 1;
//
//    //以速度2向左移动
//    private static final int LEFT_2 = 2;
//
//    //以速度1向右移动
//    private static final int RIGHT_1 = 3;
//
//    //以速度2向右移动
//    private static final int RIGHT_2 = 4;
//
//    private int orientation;
    @Override
    protected int layoutResID() {
        return R.layout.sensor_layout;
    }

    @Override
    protected void initViews() {
        ivBg = (ImageView) findViewById(R.id.iv_bg);


//        //设置图片的缩放模式和显示位置并设置
//        mMatrix = new Matrix();
//        mMatrix.postScale(1.5f, 1.5f, 0.5f, 0.5f);
//        mMatrix.postTranslate(-540, 0);
//        ivBg.setImageMatrix(mMatrix);
//        ivBg.invalidate();
//
//        //为图片控件设置动画
//        ivBg.startAnimation(getAnimation());
//
//        //获取系统传感器服务
//        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
//
//        //设置监听器监听加速度传感器（重力感应器）的状态，精度为普通（SENSOR_DELAY_NORMAL）
//        sensorManager.registerListener(this, sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_NORMAL);

        SensorManager sensorMgr = (SensorManager) getSystemService(SENSOR_SERVICE);
        Sensor sensor = sensorMgr.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        sensorMgr.registerListener(this, SensorManager.SENSOR_ACCELEROMETER,
                SensorManager.SENSOR_DELAY_GAME);


    }

//    /**
//     * 设置动画属性
//     * 将时间设置稍长以避免动画执行完成后重复执行的时候的停顿现象
//     *
//     * @return
//     */
//    private Animation getAnimation() {
//        MAnimation animation = new MAnimation();
//        animation.setDuration(600000);
//        animation.setRepeatMode(Animation.REVERSE);
//        animation.setRepeatCount(Animation.INFINITE);
//        return animation;
//    }
//
//    /**
//     * 自定义动画
//     */
//    public class MAnimation extends Animation {
//        @Override
//        protected void applyTransformation(float interpolatedTime, Transformation t) {
//            //运行方法，获取左上点的坐标，用于设置边界
//            getLeftPointF();
//
//            if (orientation == LEFT_1) {
//                mMatrix.postTranslate(interpolatedTime * 2, 0);
//            }
//            if (orientation == RIGHT_1) {
//                mMatrix.postTranslate(-interpolatedTime * 2, 0);
//            }
//            if (orientation == LEFT_2) {
//                mMatrix.postTranslate(interpolatedTime * 5, 0);
//            }
//            if (orientation == RIGHT_2) {
//                mMatrix.postTranslate(-interpolatedTime * 5, 0);
//            }
//
//            ivBg.setImageMatrix(mMatrix);
//            ivBg.invalidate();
//        }
//    }
//
//    /**
//     * 传感器监听
//     *
//     * @param event
//     */
//    @Override
//    public void onSensorChanged(SensorEvent event) {
//
//        //若传感器类型为加速度传感器（重力感应器）
//        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
//
//            //获取X坐标
//            int x = (int) event.values[SensorManager.DATA_X];
//
//            if (x == 0) orientation = RIGHT_1;//默认向左移动
//
//            if (x < 2 && x > 0) orientation = RIGHT_1;
//
//            if (x > 2) orientation = RIGHT_2;
//
//            if (x < 0 && x > -2) orientation = LEFT_1;
//
//            if (x < -2) orientation = LEFT_2;
//
//
//        }
//    }
//
//    @Override
//    public void onAccuracyChanged(Sensor sensor, int accuracy) {
//
//    }
//
//    /**
//     * 获取左上坐标，用于边界条件的判断
//     *
//     * @return
//     */
//    private PointF getLeftPointF() {
//
//        float[] values = new float[9];
//        float[] rightValues = {1.5f, 0, -1080f, 0, 1.5f, -0.25f, 0, 0, 1.0f};
//        float[] leftValues = {1.5f, 0, 0, 0, 1.5f, -0.25f, 0, 0, 1.0f};
//
//        mMatrix.getValues(values);
//
//        //若超出边界，为其设置自定义的位置
//        if (values[2] < -200) {
//            mMatrix.setValues(rightValues);
//        }
//        if (values[2] > 0) {
//            mMatrix.setValues(leftValues);
//        }
//
//        float leftX = values[2];
//        float leftY = values[5];
//        return new PointF(leftX, leftY);
//    }
    @Override
    protected void initPresenter() {

    }

    @Override
    public void onSensorChanged(int sensor, float[] values) {
        if (sensor == SensorManager.SENSOR_ACCELEROMETER) {
            long curTime = System.currentTimeMillis();
            //每100毫秒检测一次
            if ((curTime - lastUpdate) > 100) {
                long diffTime = (curTime - lastUpdate);
                lastUpdate = curTime;
                x = values[SensorManager.DATA_X];
                y = values[SensorManager.DATA_Y];
                z = values[SensorManager.DATA_Z];
//                x=ivBg.getX();
//                y=ivBg.getY();
//                z=ivBg.getZ();
                float speed = Math.abs(x + y + z - last_x - last_y - last_z) / diffTime * 10000;
                if (speed > SHAKE_THRESHOLD) {
                    //这里写上自己的功能代码
//                    setTitle("x=" + (int) x + "," + "y=" + (int) y + "," + "z=" + (int) z);
                    Log.e("sensor","x=" + (int) x + "," + "y=" + (int) y + "," + "z=" + (int) z);

                    ivBg.setScrollX((int)x*5);
                    ivBg.setScrollY((int)y*5);

//                    ivBg.setZ(z*5);
                }
                last_x = x;
                last_y = y;
                last_z = z;
            }
        }

}

    @Override
    public void onAccuracyChanged(int sensor, int accuracy) {

    }
}
