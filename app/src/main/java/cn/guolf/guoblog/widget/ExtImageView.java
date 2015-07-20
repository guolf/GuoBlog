/*
 * Copyright (c) 2015. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package cn.guolf.guoblog.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.widget.ImageView;

import cn.guolf.guoblog.R;

/**
 * Created by guolf on 7/20/15.
 */
public class ExtImageView extends ImageView {

    private boolean reversal = false;
    private float proportion = 1;
    private boolean ex_enable = true;

    public ExtImageView(Context context) {
        super(context);
        initView(context, null);
    }

    public ExtImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context, attrs);
    }

    public ExtImageView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initView(context, attrs);
    }

    private void initView(Context context, AttributeSet attrs) {
        if (attrs != null) {
            TypedArray a = context.obtainStyledAttributes(attrs,
                    R.styleable.ExtImageView);
            reversal = a.getBoolean(
                    R.styleable.ExtImageView_ex_Reversal, false);
            proportion = a.getFloat(R.styleable.ExtImageView_ex_proportion, 1);
            ex_enable = a.getBoolean(R.styleable.ExtImageView_ex_enable, true);
            a.recycle();
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        if (ex_enable) {
            setMeasuredDimension(getDefaultSize(0,
                    widthMeasureSpec), getDefaultSize(0,
                    heightMeasureSpec));

            // Children are just made to fill our space.

            int childWidthSize = getMeasuredWidth();

            int childHeightSize = getMeasuredHeight();

            //高度和宽度一样
            if (reversal) {
                widthMeasureSpec = MeasureSpec.makeMeasureSpec((int) (childHeightSize * proportion), MeasureSpec.EXACTLY);
                heightMeasureSpec = MeasureSpec.makeMeasureSpec(childHeightSize, MeasureSpec.EXACTLY);
            } else {
                widthMeasureSpec = MeasureSpec.makeMeasureSpec(childWidthSize, MeasureSpec.EXACTLY);
                heightMeasureSpec = MeasureSpec.makeMeasureSpec((int) (childWidthSize * proportion), MeasureSpec.EXACTLY);
            }
        }
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    public boolean isReversal() {
        return reversal;
    }

    public void setReversal(boolean reversal) {
        this.reversal = reversal;
    }

    public float getProportion() {
        return proportion;
    }

    public void setProportion(float proportion) {
        this.proportion = proportion;
    }
}
