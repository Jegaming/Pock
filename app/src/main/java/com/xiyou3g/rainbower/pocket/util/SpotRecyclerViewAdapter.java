package com.xiyou3g.rainbower.pocket.util;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.xiyou3g.rainbower.pocket.R;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by dell2014 on 2017/5/19.
 */

public class SpotRecyclerViewAdapter extends RecyclerView.Adapter {

    private final int TYPE_PAGER = 0;//列表第一部分 ViewPager部分
    private final int TYPE_CATEGORY = 1;//列表第二部分 分类按钮菜单
    private final int TYPE_ACTIVITY = 2;//列表第三部分，各个活动
    private final int TYPE_CONTENT = 3;//列表第四部分，按类别划分景点

    private Context context;
    private LayoutInflater layoutInflater;

    List<ImageView> pagerImages;

    /**
     * ViewPager 相关组件
     * Variables:
     * NUM_IMAGE:ViewPager 的图片数量
     * currentIndex:当前被选中的页
     * viewPagerAdapter:ViewPager适配器
     * homeViewPager:ViewPager实例，从mHolder取得
     * viewpagerListener:ViewPager监听器，处理第一页和最后一页的滑动，以及更新圆点
     * timer:计时器，定时更新ViewPager，自动滚动
     * mCircleImages[]:存放小圆点，添加到LinearLayout中
     */
    private int NUM_IMAGE = 3;
    private int[] currentIndex = {1};
    private HomeViewPagerAdapter viewPagerAdapter;
    private ViewPager spotViewPager;
    private AutoScrollPagerListener viewpagerListener;
    private Timer timer;
    private ImageView[] mCircleImages;
    private static final int UPTATE_VIEWPAGER = 0x0123;

    public SpotRecyclerViewAdapter(Context context, List<ImageView> pagerImages) {
        this.context = context;
        this.pagerImages = pagerImages;
        this.layoutInflater = LayoutInflater.from(context);
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = null;

        if (viewType == TYPE_CONTENT) {
            view = layoutInflater.inflate(R.layout.spot_rcl_content, parent, false);
            return new ContentViewHolder(view);
        }

        if (viewType == TYPE_PAGER) {
            view = layoutInflater.inflate(R.layout.spot_rcl_pager, parent, false);
            return new PagerViewHolder(view);
        }

        if (viewType == TYPE_ACTIVITY) {
            view = layoutInflater.inflate(R.layout.spot_rcl_activity, parent, false);
            return new ActivityViewHolder(view);
        }

        if (viewType == TYPE_CATEGORY) {
            view = layoutInflater.inflate(R.layout.spot_rcl_category, parent, false);
            return new CatagoryViewHolder(view);
        }

        return null;
    }

    @Override
    public int getItemCount() {
        //// TODO: 2017/5/13  这里需要修改
        return 7;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return TYPE_PAGER;
        } else if (position == 1) {
            return TYPE_CATEGORY;
        } else if (position == 2) {
            return TYPE_ACTIVITY;
        } else {
            return TYPE_CONTENT;
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        //此项是content项
        if (holder instanceof ContentViewHolder) {
            ContentViewHolder mHolder = (ContentViewHolder) holder;
            //// TODO: 2017/5/21
        }
        //此项是catagory项
        if (holder instanceof CatagoryViewHolder) {
            CatagoryViewHolder mHolder = (CatagoryViewHolder) holder;
            //// TODO: 2017/5/21
        }
        if (holder instanceof ActivityViewHolder) {
            ActivityViewHolder mHolder = (ActivityViewHolder) holder;
        }
        if (holder instanceof PagerViewHolder) {
            PagerViewHolder mHolder = (PagerViewHolder) holder;

            //将imageViewList首尾添加ImageView构成循环
            List<ImageView> recyclerImageView = ImageViewListFactory.convertImageViewListByImageView(context, pagerImages);

            spotViewPager = mHolder.viewPager;
            if (viewPagerAdapter == null) {
                viewPagerAdapter = new HomeViewPagerAdapter(context, recyclerImageView);
                spotViewPager.setAdapter(viewPagerAdapter);
            }
            if (mCircleImages == null) {
                setViewPagerIndicator(mHolder.indicator);
            }
            if (viewpagerListener == null) {
                viewpagerListener = new AutoScrollPagerListener(spotViewPager, currentIndex, mCircleImages);
                spotViewPager.setOnPageChangeListener(viewpagerListener);
            }
            if (timer == null) {
                timer = new Timer();
                timer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        Message message = new Message();
                        message.what = UPTATE_VIEWPAGER;
                        if (currentIndex[0] == NUM_IMAGE) {
                            currentIndex[0] = 0;
                        }
                        message.arg1 = currentIndex[0] + 1;
                        mHandler.sendMessage(message);
                    }
                }, 3000, 5000);
            }
        }


    }

    private void setViewPagerIndicator(LinearLayout linearLayout) {
        mCircleImages = new ImageView[NUM_IMAGE];

        for (int i = 0; i < mCircleImages.length; i++) {
            ImageView imageView = new ImageView(context);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(10, 10);
            params.setMargins(5, 0, 5, 0);
            imageView.setLayoutParams(params);
            if (i == 0) {
                imageView.setBackgroundResource(R.drawable.indicator_selected);
            } else {
                imageView.setBackgroundResource(R.drawable.indicator_normal);
            }
            mCircleImages[i] = imageView;
            //把指示作用的原点图片加入底部的视图中
            linearLayout.addView(mCircleImages[i]);
        }
    }

    final Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case UPTATE_VIEWPAGER:
                    if (spotViewPager != null) {
                        if (msg.arg1 < NUM_IMAGE + 1) {
                            spotViewPager.setCurrentItem(msg.arg1);
                        } else {
                            spotViewPager.setCurrentItem(msg.arg1, false);
                        }
                    }
                    break;
            }
        }
    };

    class PagerViewHolder extends RecyclerView.ViewHolder {

        ViewPager viewPager;
        TextView textView;
        LinearLayout indicator;

        public PagerViewHolder(View view) {
            super(view);
            this.viewPager = (ViewPager) view.findViewById(R.id.spot_rcl_pager_vp);
            this.indicator = (LinearLayout) view.findViewById(R.id.spot_rcl_pager_indicator);
        }
    }

    class CatagoryViewHolder extends RecyclerView.ViewHolder {


        public CatagoryViewHolder(View itemView) {
            super(itemView);
        }
    }

    class ActivityViewHolder extends RecyclerView.ViewHolder {

        public ActivityViewHolder(View itemView) {
            super(itemView);
        }
    }

    class ContentViewHolder extends RecyclerView.ViewHolder {

        public ContentViewHolder(View itemView) {
            super(itemView);
        }
    }

}
