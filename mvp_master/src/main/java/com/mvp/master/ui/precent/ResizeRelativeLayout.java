package com.mvp.master.ui.precent;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RelativeLayout;

import com.mvp.master.ui.UIUtils;

/**
 * @author iqiao
 * @date 2020-03-10 11:31
 * @desc
 */
public class ResizeRelativeLayout extends RelativeLayout {
    public ResizeRelativeLayout(Context context) {
        super(context);
    }

    public ResizeRelativeLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ResizeRelativeLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public ResizeRelativeLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        float scaleX = UIUtils.getInstance().getHorizontalValue();
        float scaleY = UIUtils.getInstance().getVerticalValue();
        //获取所有子view
        int count = this.getChildCount();
        for (int i = 0; i < count; i++) {
            View child = this.getChildAt(i);
            LayoutParams layoutParams = (LayoutParams) child.getLayoutParams();
            layoutParams.width = (int) (layoutParams.width * scaleX);
            layoutParams.height = (int) (layoutParams.height * scaleY);
            layoutParams.topMargin = (int) (layoutParams.topMargin * scaleY);
            layoutParams.rightMargin = (int) (layoutParams.rightMargin * scaleX);
            layoutParams.bottomMargin = (int) (layoutParams.bottomMargin * scaleY);
            layoutParams.leftMargin = (int) (layoutParams.leftMargin * scaleY);
        }
    }
}
