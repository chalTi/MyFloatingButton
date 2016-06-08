package com.example.wentongwang.myfloatingbutton;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.CornerPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Wentong WANG on 2016/5/5.
 */
public class MyTabLayout extends LinearLayout {

    private Paint mPaint;
    private Path mPath;
    //三角形的宽，高
    private int mTriangleWidth;
    private int mTriangleHeight;

    //底部小三角对选项宽的比例
    private static final float RADIO_TRIANGLE_WIDTH = 1 / 6F;
    //选项的宽
    private int mTabWidth;
    //三角形初始位置
    private int triangleStartX;
    //移动位置
    private int moveX = 0;


    private int visiableCount = VISIABLE_COUNT_DEFAUT;
    private static final int VISIABLE_COUNT_DEFAUT = 3;


    public MyTabLayout(Context context) {
        this(context, null);
    }

    public MyTabLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setColor(Color.parseColor("#ffffffff"));
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setPathEffect(new CornerPathEffect(3));

        titles = new ArrayList<>();
    }


    /**
     * 根据控件的宽高设置东西时候可以在这个方法里设置
     *
     * @param w
     * @param h
     * @param oldw
     * @param oldh
     */
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        mTabWidth = w / visiableCount;
        mTriangleWidth = (int) (mTabWidth * RADIO_TRIANGLE_WIDTH);
        mTriangleHeight = mTriangleWidth * 2 / 3;

        triangleStartX = mTabWidth / 2 - mTriangleWidth / 2;
        initTriangle();
    }

    /**
     * 初始化三角形
     */
    private void initTriangle() {
        mPath = new Path();
        mPath.moveTo(0, 0);
        mPath.lineTo(mTriangleWidth, 0);
        mPath.lineTo(mTriangleWidth / 2, -mTriangleHeight);
        mPath.close();
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        canvas.save();

        canvas.translate(triangleStartX + moveX, getHeight() + 2);
        canvas.drawPath(mPath, mPaint);

        canvas.restore();


        super.dispatchDraw(canvas);
    }

    /**
     * 滑动三角形，
     *
     * @param position       当前选中的tab
     * @param positionOffset 滑动时的变化量 (0-1)
     */
    public void scroll(int position, float positionOffset) {
        //滑动距离 = positionOffset * tabWidth
        moveX = (int) (mTabWidth * (position + positionOffset));

        if (position >= (visiableCount - 2) && position < (getChildCount()- 2) && positionOffset > 0 && getChildCount() > visiableCount) {
            int dx = (position - (visiableCount - 2)) * mTabWidth + (int) (positionOffset * mTabWidth);
            this.scrollTo(dx, 0);
        }


        //数据值改变，需要重新绘制
        invalidate();
    }


    private List<String> titles;

    /**
     * 根据传入字符串数组，动态生成Tab
     *
     * @param titles
     */
    public void setTabItems(List<String> titles) {
        if (titles != null) {
            this.titles = titles;
            this.removeAllViews();
            if (titles.size() < visiableCount) {
                visiableCount = titles.size();
            }
            for (String title : titles) {
                addView(generateTextView(title));
            }
        }

    }

    private static final int TEXT_COLOR_NORMAL = 0x77FFFFFF;

    /**
     * 生成Tab
     *
     * @param title
     * @return
     */
    private TextView generateTextView(String title) {
        TextView tv = new TextView(getContext());
        LinearLayout.LayoutParams lp = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        lp.width = getScreenWidth() / visiableCount;
        tv.setText(title);
        tv.setGravity(Gravity.CENTER);
        tv.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
        tv.setTextColor(TEXT_COLOR_NORMAL);
        tv.setLayoutParams(lp);

        return tv;
    }

    /**
     * 获取宽度
     *
     * @return
     */
    private int getScreenWidth() {
        WindowManager wm = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics metrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(metrics);
        return metrics.widthPixels;
    }

    public void setVisiableTabCount(int count) {
        this.visiableCount = count;
    }
}
