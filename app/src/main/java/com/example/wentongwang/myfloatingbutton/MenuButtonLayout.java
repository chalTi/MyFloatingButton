package com.example.wentongwang.myfloatingbutton;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.BounceInterpolator;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Wentong WANG on 2016/5/3.
 */
public class MenuButtonLayout extends ViewGroup implements View.OnClickListener{
    private boolean isExpanded = false;// 菜单是否打开
    private int mMaxnWidth;// 总宽度
    private int mMaxHeight;// 总高度
    private int mChildCount = 0;
    private int expandPadding = 200;
    private int padding = 20;


    private List<View> myViews;
    private int childMaxWidth;

    private int currentItem;

    public MenuButtonLayout(Context context) {
        this(context, null);
    }

    public MenuButtonLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MenuButtonLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setClickable(true);
        myViews = new ArrayList<View>();
    }

    /**
     * 设置收起后显示的那个view
     *
     * @param index
     */
    public void setCurrentView(int index) {
        View mBaseView = myViews.get(index);
        bringChildToFront(mBaseView);
        invalidate();
    }


    /**
     * 加入View
     *
     * @param view
     */
    public void addItemView(View view) {
        view.setTag(mChildCount);
        view.setOnClickListener(this);
        addView(view, mChildCount);
        myViews.add(view);
        mChildCount++;
    }

    /**
     * 测量
     *
     * @param widthMeasureSpec
     * @param heightMeasureSpec
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        measureChildren(widthMeasureSpec, heightMeasureSpec);
        childMaxWidth = 0;
        mMaxHeight = 0;
        for (int i = 0; i < mChildCount; i++) {
            View child = getChildAt(i);
            mMaxHeight = Math.max(mMaxHeight, child.getMeasuredHeight());
            childMaxWidth = Math.max(childMaxWidth, child.getMeasuredHeight());
        }
        expandPadding = childMaxWidth + padding;
        mMaxnWidth = expandPadding * (mChildCount - 1) + childMaxWidth;
        setMeasuredDimension(mMaxnWidth, mMaxHeight);
    }

    /**
     * 布局
     *
     * @param changed
     * @param l
     * @param t
     * @param r
     * @param b
     */
    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int left = 0;
        int top = 0;
        int right = 0;
        int bottom = 0;

        if (mChildCount % 2 != 0) {
            left = (mMaxnWidth / 2) - (childMaxWidth / 2);
            top = 0;
            right = (mMaxnWidth / 2) + (childMaxWidth / 2);
            bottom = mMaxHeight;

            for (int i = 0; i < mChildCount; i++) {
                View child = getChildAt(i);
                child.layout(left, top, right, bottom);
            }
        } else {
            left = (mMaxnWidth / 2) + padding / 2;
            top = 0;
            right = left + childMaxWidth;
            bottom = mMaxHeight;

            for (int i = 0; i < mChildCount; i++) {
                View child = getChildAt(i);
                child.layout(left, top, right, bottom);
            }
        }
    }

    /**
     * 展开或者收起
     */
    private void doMenuAction() {
        if (isExpanded) {
            close();
        } else {
            open();
        }
    }

    private void open() {
        ObjectAnimator[] oas = new ObjectAnimator[mChildCount];
        //整除获取中间值
        int middle = mChildCount / 2;
        for (int i = 0; i < mChildCount; i++) {
            View child = myViews.get(i);
            int times = i - middle;
            ObjectAnimator oa = ObjectAnimator.ofFloat(child, "translationX", expandPadding * times);
            oas[i] = oa;
        }
        AnimatorSet set = new AnimatorSet();
        set.setInterpolator(new BounceInterpolator());
        set.setDuration(500);
        set.playTogether(oas);
        set.start();
        isExpanded = true;
    }

    private void close() {
        ObjectAnimator[] oas = new ObjectAnimator[mChildCount];
        for (int i = 0; i < mChildCount; i++) {
            View child = getChildAt(i);
            ObjectAnimator oa = ObjectAnimator.ofFloat(child, "translationX", 0);
            oas[i] = oa;
        }

        AnimatorSet set = new AnimatorSet();
        set.setDuration(500);
        set.playTogether(oas);
        set.start();

        isExpanded = false;
        if (listener != null) {
            listener.changeBaseView(currentItem);
        }
    }

    @Override
    public void onClick(View v) {
        int index = (int) v.getTag();
        currentItem = index;
        setCurrentView(index);
        doMenuAction();
    }


    /**
     * 设置按钮间距
     */
    public void setPadding(int padding) {
        this.padding = padding;
    }

    private onItemClickListener listener;
    public interface onItemClickListener {
        /**
         * 按钮改变回调
         *
         * @param index 改变按钮的序号
         */
        void changeBaseView(int index);
    }

    public void setOnItemClickListenr(onItemClickListener listenr) {
        this.listener = listenr;
    }

}
