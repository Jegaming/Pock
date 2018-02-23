package com.xiyou3g.rainbower.pocket.util;

import android.support.v4.view.ViewPager;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Timer;

/**
 * Created by dell2014 on 2017/5/18.
 */

public class MyPagerKit {

    //图片的数量
    private int NUM_IMAGE = 3;
    //存放当前页码，通过数组能够进行传参
    private int[] currentIndex = {1};
    private HomeViewPagerAdapter viewPagerAdapter;
    private ViewPager homeViewPager;
    private TextView textView;
    private AutoScrollPagerListener viewpagerListener;
    private Timer timer;
    private ImageView[] mCircleImages;


}
