package com.mvp.master.ui.precent;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;

import com.mvp.master.ui.UIUtils;
import com.tencent.mars.xlog.Log;

/**
 * @author iqiao
 * @date 2020-03-10 11:31
 * @desc
 */
public class ResizeLinearLayout extends LinearLayout {
    private final String TAG = this.getClass().getSimpleName();

    public ResizeLinearLayout(Context context) {
        super(context);
    }

    public ResizeLinearLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ResizeLinearLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public ResizeLinearLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        if (isInEditMode()) {
            return;
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        if (isInEditMode()) {
            return;
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        float scaleX = UIUtils.getInstance().getHorizontalValue();
        float scaleY = UIUtils.getInstance().getVerticalValue();
        Log.e(TAG, "scaleX:" + scaleX + "   scaleY:" + scaleY);
        //获取所有子view
        int count = this.getChildCount();
        for (int i = 0; i < count; i++) {
            View child = this.getChildAt(i);
            LayoutParams layoutParams = (LayoutParams) child.getLayoutParams();
            Log.e(TAG, "top:" + layoutParams.topMargin);
            layoutParams.width = (int) (layoutParams.width * scaleX);
            layoutParams.height = (int) (layoutParams.height * scaleY);
            layoutParams.topMargin = (int) (layoutParams.topMargin * scaleY);
            layoutParams.rightMargin = (int) (layoutParams.rightMargin * scaleX);
            layoutParams.bottomMargin = (int) (layoutParams.bottomMargin * scaleY);
            layoutParams.leftMargin = (int) (layoutParams.leftMargin * scaleX);
        }
    }
}
