package com.xiyou3g.rainbower.pocket.util;

import android.support.v4.view.ViewPager;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.xiyou3g.rainbower.pocket.R;
import com.xiyou3g.rainbower.pocket.model.HomePagerItem;

import java.util.List;

/**
 * Created by dell2014 on 2017/5/17.
 */

public class AutoScrollPagerListener implements ViewPager.OnPageChangeListener {

    private ImageView[] mCircleImages;
    private ViewPager viewPager;
    private String[] textList;
    private TextView textView;
    private int[] currentIndex;

    public AutoScrollPagerListener(ViewPager viewPager, int[] currentIndex) {
        this.viewPager = viewPager;
        this.currentIndex = currentIndex;
    }

    public AutoScrollPagerListener(ViewPager viewPager, int[] currentIndex, ImageView[] mCircleImages) {
        this(viewPager, currentIndex);
        this.mCircleImages = mCircleImages;
    }

    public AutoScrollPagerListener(ViewPager viewPager, int[] currentIndex, TextView textView) {
        this(viewPager, currentIndex);
        this.textView = textView;
    }

    public AutoScrollPagerListener(ViewPager viewPager, int[] currentIndex, ImageView[] mCircleImages, TextView textView) {
        this(viewPager, currentIndex, mCircleImages);
        this.textView = textView;
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {

        //如果有小圆点，更新小圆点
        if (mCircleImages != null) {
            //改变小圆点
            for (int i = 0; i < mCircleImages.length; i++) {
                if (i + 1 == position) {
                    mCircleImages[i].setBackgroundResource(R.drawable.indicator_selected);
                } else {
                    mCircleImages[i].setBackgroundResource(R.drawable.indicator_normal);
                }
            }
        }

        //如果有文本，更新文本
        if (textView != null && textList != null && textList.length > 0) {
            textView.setText(textList[position]);
        }

        //设置当前页码
        currentIndex[0] = position;
        Log.i("ViewPager", currentIndex + "is currentIndex; " + position + "is currentPosition");
    }

    @Override
    public void onPageScrollStateChanged(int state) {
        //实现切换到末尾后返回到第一张
        switch (state) {
            // 手势滑动
            case ViewPager.SCROLL_STATE_DRAGGING:
                break;

            // 界面切换中
            case ViewPager.SCROLL_STATE_SETTLING:
                break;

            // 滑动结束，即切换完毕或者加载完毕
            case ViewPager.SCROLL_STATE_IDLE:
                // 当前为最后一张，此时从右向左滑，则切换到第一张
                if (viewPager.getCurrentItem() == viewPager.getAdapter()
                        .getCount() - 1) {
                    viewPager.setCurrentItem(1, false);//1原来是0
                }
                // 当前为第一张，此时从左向右滑，则切换到最后一张
                else if (viewPager.getCurrentItem() == 0) {
                    viewPager.setCurrentItem(viewPager.getAdapter()
                            .getCount() - 2, false);//2原来是1
                }
                break;

            default:
                break;
        }
    }

    public void setTextList(List<HomePagerItem> itemList) {
        if (itemList.get(0).getDesc() == null) return;
        textList = new String[itemList.size() + 2];

        for (int i = 0; i < itemList.size() + 2; i++) {
            if (i == 0) {
                textList[i] = itemList.get(i).getDesc();
                continue;
            }
            if (i == itemList.size() + 1) {
                textList[i] = itemList.get(0).getDesc();
                continue;
            }
            textList[i] = itemList.get(i - 1).getDesc();
        }

    }

}
