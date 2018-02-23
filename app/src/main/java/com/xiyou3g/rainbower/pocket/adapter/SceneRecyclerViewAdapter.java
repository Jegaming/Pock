package com.xiyou3g.rainbower.pocket.adapter;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.xiyou3g.rainbower.pocket.R;
import com.xiyou3g.rainbower.pocket.util.AutoScrollPagerListener;
import com.xiyou3g.rainbower.pocket.util.HomeViewPagerAdapter;
import com.xiyou3g.rainbower.pocket.util.ImageViewListFactory;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by dell2014 on 2017/5/13.
 */

public class SceneRecyclerViewAdapter extends RecyclerView.Adapter {

    private final int TYPE_PAGER = 0;//列表的第一部分pager
    private final int TYPE_CONTENT = 1;//列表第二部分content

    private Context context;
    private LayoutInflater layoutInflater;

    List<ImageView> pagerTestImages;
    List<String> articleTestData;

    /**
     * ViewPager 相关组件
     * Variables:
     * NUM_IMAGE:ViewPager 的图片数量
     * currentIndex:数组的第一位即currentIndex[0]保存当前页。包含首位，所以1是第一页
     * viewPagerAdapter:ViewPager适配器
     * sceneViewPager:ViewPager实例，从mHolder取得
     * viewpagerListener:ViewPager监听器，处理第一页和最后一页的滑动，以及更新圆点
     * timer:计时器，定时更新ViewPager，自动滚动
     * mCircleImages[]:存放小圆点，添加到LinearLayout中
     * 还需要存放文字数据
     */
    private static final int NUM_IMAGE = 4;
    private int[] currentIndex = {1};
    private HomeViewPagerAdapter viewPagerAdapter;
    private ViewPager sceneViewPager;
    private AutoScrollPagerListener viewpagerListener;
    private Timer timer;
    private ImageView[] mCircleImages;
    //Handler 用到的参数值
    private static final int UPTATE_VIEWPAGER = 3388;

    public SceneRecyclerViewAdapter(Context context, List<ImageView> pagerTestImages, List<String> articleTestData) {
        this.context = context;
        this.articleTestData = articleTestData;
        this.pagerTestImages = pagerTestImages;
        layoutInflater = LayoutInflater.from(context);
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = null;

        if (viewType == TYPE_CONTENT) {
            view = layoutInflater.inflate(R.layout.scene_rcl_content, parent, false);
            return new ContentViewHolder(view);
        }

        if (viewType == TYPE_PAGER) {
            view = layoutInflater.inflate(R.layout.scene_rcl_pager, parent, false);
            return new PagerViewHolder(view);
        }

        return null;
    }

    @Override
    public int getItemCount() {
        //// TODO: 2017/5/13  这里需要修改
        return articleTestData.size() + 1;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return TYPE_PAGER;
        } else {
            return TYPE_CONTENT;
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        //此项是content项
        if (holder instanceof ContentViewHolder) {
            ContentViewHolder mHolder = (ContentViewHolder) holder;

            mHolder.likesTV.setText(articleTestData.get(position - 1));
            mHolder.moreLL.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d("test", "more clicked");
                }
            });
        }
        //此项是pager项
        else if (holder instanceof PagerViewHolder) {
            PagerViewHolder mHolder = (PagerViewHolder) holder;

            //通过ImageViewListFactory将ImageViewListFactoryimageViewList首尾添加ImageView构成循环
            List<ImageView> recyclerImageView = ImageViewListFactory.convertImageViewListByImageView(context, pagerTestImages);

            //判断各组件是否初始化
            sceneViewPager = mHolder.viewPager;
            if (viewPagerAdapter == null) {
                viewPagerAdapter = new HomeViewPagerAdapter(context, recyclerImageView);
                sceneViewPager.setAdapter(viewPagerAdapter);
            }
            if (mCircleImages == null) {
                setViewPagerIndicator(mHolder.indicator);
            }
            if (viewpagerListener == null) {
                viewpagerListener = new AutoScrollPagerListener(sceneViewPager, currentIndex, mCircleImages);
                sceneViewPager.setOnPageChangeListener(viewpagerListener);
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
                }, 2000, 4000);
            }
//            sceneViewPager.setCurrentItem(1);

        }
    }

    final Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case UPTATE_VIEWPAGER:
                    if (sceneViewPager != null) {
                        if (msg.arg1 < NUM_IMAGE + 1) {
                            sceneViewPager.setCurrentItem(msg.arg1);
                        } else {
                            sceneViewPager.setCurrentItem(msg.arg1, false);
                        }
                    }
                    break;
            }
        }
    };

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

    class SceneViewPagerChangeListener implements ViewPager.OnPageChangeListener {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            if (mCircleImages == null) return;
            //改变小圆点
            for (int i = 0; i < NUM_IMAGE; i++) {
                if (i + 1 == position) {
                    mCircleImages[i].setBackgroundResource(R.drawable.indicator_selected);
                } else {
                    mCircleImages[i].setBackgroundResource(R.drawable.indicator_normal);
                }
            }

            //TODO 改变文字

            currentIndex[0] = position;
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
                    if (sceneViewPager.getCurrentItem() == sceneViewPager.getAdapter()
                            .getCount() - 1) {
                        sceneViewPager.setCurrentItem(1, false);//1原来是0
                    }
                    // 当前为第一张，此时从左向右滑，则切换到最后一张
                    else if (sceneViewPager.getCurrentItem() == 0) {
                        sceneViewPager.setCurrentItem(sceneViewPager.getAdapter()
                                .getCount() - 2, false);//2原来是1
                    }
                    break;

                default:
                    break;
            }
        }
    }

    class PagerViewHolder extends RecyclerView.ViewHolder {
        ViewPager viewPager;
        TextView textView;
        LinearLayout indicator;

        public PagerViewHolder(View view) {
            super(view);
            this.viewPager = (ViewPager) view.findViewById(R.id.scene_rcl_pager_vp);
            this.textView = (TextView) view.findViewById(R.id.scene_rcl_pager_text);
            this.indicator = (LinearLayout) view.findViewById(R.id.scene_rcl_pager_indicator);
        }


    }

    class ContentViewHolder extends RecyclerView.ViewHolder {
        TextView titleTV;
        ImageView iconIV;
        TextView nameTV;
        TextView articleTV;
        ImageView pictureIVA, pictureIVB;
        TextView likesTV;
        LinearLayout moreLL;

        public ContentViewHolder(View view) {
            super(view);
            titleTV = (TextView) view.findViewById(R.id.scene_rcl_content_title);
            iconIV = (ImageView) view.findViewById(R.id.scene_rcl_content_icon);
            nameTV = (TextView) view.findViewById(R.id.scene_rcl_content_name);
            articleTV = (TextView) view.findViewById(R.id.scene_rcl_content_article);
            pictureIVA = (ImageView) view.findViewById(R.id.scene_rcl_content_image_a);
            pictureIVB = (ImageView) view.findViewById(R.id.scene_rcl_content_image_b);
            likesTV = (TextView) view.findViewById(R.id.scene_rcl_content_like);
            moreLL = (LinearLayout) view.findViewById(R.id.scene_rcl_content_more);
        }

    }

}
