package com.example.wentongwang.myfloatingbutton;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Wentong WANG on 2016/5/5.
 */
public class FlexibleTabLayout extends LinearLayout implements View.OnClickListener {

    private int padding = 10;
    private int showWidth = 300;
    private int hiddenWidth = 100;
    private int mWidth = 0;
    private int mHeight = 0;

    private List<View> mViews;
    private int mChildCount = 0;

    private LinearLayout.LayoutParams params;

    public FlexibleTabLayout(Context context) {
        this(context, null);
    }

    public FlexibleTabLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public FlexibleTabLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

    }

    private void init() {
        mViews = new ArrayList<View>();
        setClickable(true);
    }

    public void addItemView(View view) {
        view.setTag(mChildCount);
        view.setOnClickListener(this);
        addView(view, mChildCount);
        mViews.add(view);
        mChildCount = getChildCount();
        Log.i("xxxx", "ChildCount=" + mChildCount);
    }

//    @Override
//    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
//
//        measureChildren(widthMeasureSpec, heightMeasureSpec);
//        mChildCount = getChildCount();
//        for (int i = 0; i < mChildCount; i++) {
//            View child = getChildAt(i);
//            int childHeight = child.getMeasuredHeight();
//            if (mHeight < childHeight) {
//                mHeight = childHeight;
//            }
//        }
//        mHeight = mHeight + padding * 2;
//        mWidth = (mChildCount - 1) * hiddenWidth + showWidth + padding * 2;
//
//        super.onMeasure(mWidth, mHeight); //继承Linearlayout时候需要调用父类的测量方法，否则程序会报错
//    }

    @Override
    public void onClick(View v) {
        Log.i("xxxx", "onclickIndex");
        onAnimation((Integer) v.getTag());

    }


    public void onAnimation(int index) {
        Log.i("xxxx", "onclickIndex =" + index);
        for (int i = 0; i < mViews.size(); i++) {
            if (index == i) {
                ViewWrapper wrapper = new ViewWrapper(mViews.get(i));
                ObjectAnimator.ofInt(wrapper, "width", showWidth).setDuration(500).start();
            } else {
                ViewWrapper wrapper = new ViewWrapper(mViews.get(i));
                ObjectAnimator.ofInt(wrapper, "width", hiddenWidth).setDuration(500).start();
            }
        }

    }

    private static class ViewWrapper {
        private View traget;

        public ViewWrapper(View traget) {
            this.traget = traget;
        }

        public int getWidth() {
            return traget.getLayoutParams().width;
        }

        public void setWidth(int width) {
            traget.getLayoutParams().width = width;
            traget.requestLayout();
        }
    }

}
