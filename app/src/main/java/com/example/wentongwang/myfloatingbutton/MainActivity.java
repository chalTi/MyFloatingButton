package com.example.wentongwang.myfloatingbutton;

import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {
    private ImageView iv;

    private MenuButtonLayout menuButtonLayout;
    private MyTabLayout layout;
    private ViewPager mVp;

    private FragmentPagerAdapter myAdapter;
    private List<Fragment> mlist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
        initDatas();

    }

    private void initViews() {
        iv = (ImageView) findViewById(R.id.fab);
        menuButtonLayout = (MenuButtonLayout) findViewById(R.id.my_menu);
        layout = (MyTabLayout) findViewById(R.id.tab_layout);
        mVp = (ViewPager) findViewById(R.id.my_view_pager);
        iv.setVisibility(View.GONE);
    }

    private void initDatas() {
        for (int i = 0; i < 5; i++) {
            TextView imageView = new TextView(this);
            int index = i+1;
            imageView.setText(index + "");
            imageView.setGravity(Gravity.CENTER);
            imageView.setBackground(getDrawable(R.drawable.already_bid_bade));
            menuButtonLayout.addItemView(imageView);

        }
        menuButtonLayout.setCurrentView(0);

        mlist = new ArrayList<>();
        List<String> titles = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            MyFragment f1 = new MyFragment();

            String str = "选项" + (i + 1);
            f1.setTitle(str);
            titles.add(str);
            mlist.add(f1);
        }
        layout.setVisiableTabCount(3);
        layout.setTabItems(titles);

        myAdapter = new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return mlist.get(position);
            }

            @Override
            public int getCount() {
                return mlist.size();
            }
        };

        mVp.setAdapter(myAdapter);

        initEvent();
        initAnim();
    }

    private void initEvent() {
        menuButtonLayout.setOnItemClickListenr(new MenuButtonLayout.onItemClickListener() {
            @Override
            public void changeBaseView(int index) {
                Toast.makeText(MainActivity.this,"第"+(index+1)+"个按钮被点击了",Toast.LENGTH_SHORT).show();
                mVp.setCurrentItem(index);
            }
        });

        mVp.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                //处理顶部Tab滑动
                layout.scroll(position, positionOffset);
            }

            @Override
            public void onPageSelected(int position) {
                menuButtonLayout.setCurrentView(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private void initAnim() {
        ScaleAnimation animation1 = new ScaleAnimation(1, 1.2F, 1, 1.2F,
                Animation.RELATIVE_TO_SELF, 0.5F, Animation.RELATIVE_TO_SELF, 0.5F);
        animation1.setDuration(3000 + 3000);
        animation1.setRepeatCount(Animation.INFINITE);

        ScaleAnimation animation2 = new ScaleAnimation(1.2F, 1, 1.2F, 1,
                Animation.RELATIVE_TO_SELF, 0.5F, Animation.RELATIVE_TO_SELF, 0.5F);
        animation2.setDuration(3000);
        animation2.setStartOffset(3000);
        animation2.setRepeatCount(Animation.INFINITE);

        RotateAnimation rotateAnimation = new RotateAnimation(0, 360,
                Animation.RELATIVE_TO_SELF, 0.4F, Animation.RELATIVE_TO_SELF, 0.4F);
        rotateAnimation.setDuration(24000);
        LinearInterpolator lir = new LinearInterpolator();
        rotateAnimation.setInterpolator(lir);
        rotateAnimation.setRepeatCount(Animation.INFINITE);


        AnimationSet set = new AnimationSet(true);
        set.addAnimation(animation1);
        set.addAnimation(animation2);
        set.addAnimation(rotateAnimation);
        set.setRepeatMode(Animation.RESTART);
        iv.startAnimation(set);

    }


//    private void myAnimation(View v) {
//
//
//        for (int i = 0; i < btns.size(); i++) {
//            if (v.getTag() == i) {
//                ViewWrapper wrapper = new ViewWrapper(btns.get(i));
//                ObjectAnimator.ofInt(wrapper, "width", 250).setDuration(500).start();
//            } else {
//                ViewWrapper wrapper = new ViewWrapper(btns.get(i));
//                ObjectAnimator.ofInt(wrapper, "width", 60).setDuration(500).start();
//            }
//        }
//
//    }
//
//    private static class ViewWrapper {
//        private View traget;
//
//        public ViewWrapper(View traget) {
//            this.traget = traget;
//        }
//
//        public int getWidth() {
//            return traget.getLayoutParams().width;
//        }
//
//        public void setWidth(int width) {
//            traget.getLayoutParams().width = width;
//            traget.requestLayout();
//        }
//    }

}
