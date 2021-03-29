package com.mvp.cn.mvp.view.ui;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.mvp.cn.R;


public class SurfaceViewCamera extends View {

    /**
     * 灰色背景 默认宽高
     */
    private static int BACKGROUND_WIDTH = 440;
    private static int BACKGROUND_HEIGHT = 600;
    /**
     * 左60 上60 右60 下140 px
     */
    private static int HORIZONTAL_MARGIN = 20;
    private static int VERTICAL_MARGIN = 0;
    private static int VERTICAL_BOTTOM_MARGIN = 60;
    private Paint mBgPaint, mTextPaint;
    private float downX;
    private float downY;

    @SuppressLint("ResourceAsColor")
    private void initPaint() {
        //禁用硬件加速
        setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        //使用混合模式
        PorterDuffXfermode duffXfermode = new PorterDuffXfermode(PorterDuff.Mode.DST_OUT);
        mBgPaint = new Paint();
        mBgPaint.setStyle(Paint.Style.FILL);
        mBgPaint.setXfermode(duffXfermode);
        mTextPaint = new Paint();
        mTextPaint.setTextSize(20);
        mTextPaint.setTypeface(Typeface.DEFAULT);
        mTextPaint.setTextAlign(Paint.Align.CENTER);
        mTextPaint.setColor(R.color.colorAccent);
    }

    public SurfaceViewCamera(Context context) {
        super(context);
        initPaint();
    }

    public SurfaceViewCamera(Context context, AttributeSet attrs) {
        super(context, attrs);
        initPaint();
    }

    public SurfaceViewCamera(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initPaint();
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        // 获取宽的测量模式
        int wSpecMode = MeasureSpec.getMode(widthMeasureSpec);
        // 获取符控件提供的 view 宽的最大值
        BACKGROUND_WIDTH = MeasureSpec.getSize(widthMeasureSpec);
        int hSpecMode = MeasureSpec.getMode(heightMeasureSpec);
        BACKGROUND_HEIGHT = MeasureSpec.getSize(heightMeasureSpec);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {

    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public void setOnTouchListener(OnTouchListener l) {
        super.setOnTouchListener(l);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                downX = event.getX();
                downY = event.getY();
                break;
            case MotionEvent.ACTION_UP:
                Log.e("click", "111");
                if (downX > BACKGROUND_WIDTH / 2 - 60 && downX < BACKGROUND_WIDTH / 2 && downY > BACKGROUND_HEIGHT / 2 + 10 && downY < BACKGROUND_HEIGHT / 2 + 30) {
                    Log.e("click", "我知道了！");
                }

                break;
            case MotionEvent.ACTION_MOVE:
                downX = event.getX();
                downY = event.getY();
                invalidate();
                break;
        }
        return true;
    }

    @SuppressLint("ResourceAsColor")
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //先绘制背景色
        canvas.drawColor(getResources().getColor(R.color.trance));
        //圆形
//        canvas.drawCircle(BACKGROUND_WIDTH / 2, BACKGROUND_HEIGHT / 2 - 90, 50, mBgPaint);
        canvas.drawCircle(downX,downY,50,mBgPaint);
        //hello world！
        canvas.drawText("hello world!", BACKGROUND_WIDTH / 2 - 20, BACKGROUND_HEIGHT / 2, mTextPaint);
        //我知道了！ 的背景
        canvas.drawRoundRect(BACKGROUND_WIDTH / 2 - 60, BACKGROUND_HEIGHT / 2 + 10, BACKGROUND_WIDTH / 2 + 40, BACKGROUND_HEIGHT / 2 + 40, 15, 15, mBgPaint);
        mTextPaint.setColor(R.color.white);
        //我知道了！  字样
        canvas.drawText("我知道了！", BACKGROUND_WIDTH / 2, BACKGROUND_HEIGHT / 2 + 30, mTextPaint);

//        //左边
//        canvas.drawRect(0,0,HORIZONTAL_MARGIN,BACKGROUND_HEIGHT,mBgPaint);
//        //上边
//        canvas.drawRect(HORIZONTAL_MARGIN,0,BACKGROUND_WIDTH,VERTICAL_MARGIN,mBgPaint);
//        //右边
//        canvas.drawRect(BACKGROUND_WIDTH-HORIZONTAL_MARGIN,VERTICAL_MARGIN,BACKGROUND_WIDTH,BACKGROUND_HEIGHT,mBgPaint);
//        //底部
//        canvas.drawRect(HORIZONTAL_MARGIN,BACKGROUND_HEIGHT-VERTICAL_BOTTOM_MARGIN,BACKGROUND_WIDTH-HORIZONTAL_MARGIN,BACKGROUND_HEIGHT,mBgPaint);
    }
}
