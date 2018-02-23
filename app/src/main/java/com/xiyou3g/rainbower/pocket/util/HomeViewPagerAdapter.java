package com.xiyou3g.rainbower.pocket.util;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.List;

/**
 * Created by dell2014 on 2017/5/12.
 */

public class HomeViewPagerAdapter extends PagerAdapter {

    private List<ImageView> imageViews;
    private Context context;

    public HomeViewPagerAdapter(Context context, List<ImageView> imageViews) {
        this.context = context;
        this.imageViews = imageViews;
    }

    public void setImageViews(List<ImageView> imageViews) {
        this.imageViews = imageViews;
    }

    @Override
    public int getCount() {
        return imageViews.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((ImageView) object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        if (imageViews.get(position).getParent() != null) {
            ((ViewGroup) imageViews.get(position).getParent()).removeView(imageViews.get(position));
        }
        container.addView(imageViews.get(position));
        return imageViews.get(position);
    }
}
