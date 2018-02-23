package com.xiyou3g.rainbower.pocket.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Message;
import android.provider.ContactsContract;
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
import com.xiyou3g.rainbower.pocket.model.HomeActItem;
import com.xiyou3g.rainbower.pocket.model.HomeArticleItem;
import com.xiyou3g.rainbower.pocket.model.HomePagerItem;
import com.xiyou3g.rainbower.pocket.util.AutoScrollPagerListener;
import com.xiyou3g.rainbower.pocket.util.HomeViewPagerAdapter;
import com.xiyou3g.rainbower.pocket.util.ImageViewListFactory;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.MemoryHandler;

/**
 * Created by dell2014 on 2017/6/12.
 */

public class HomeRecyclerViewAdapter extends RecyclerView.Adapter {

    public static final int TYPE_PAGER = 0x0011;//列表第一部分，ViewPager部分
    public static final int TYPE_MENU = 0x0012;//列表第二部分，三个菜单
    public static final int TYPE_MENU_TICKET = 0x0112;//三个菜单的第一个，火车票
    public static final int TYPE_MENU_SPOT = 0x0212;//三个菜单的第二个，景点娱乐
    public static final int TYPE_MENU_SUP = 0x0312;//三个菜单的第三个，第三方
    public static final int TYPE_ANC = 0x0013;//列表第三部分，公告栏
    public static final int TYPE_ACT = 0x0014;//列表第三部分，各项活动
    public static final int TYPE_ACT_MORE = 0x0114;//活动的查看更多
    public static final int TYPE_ARTICLE = 0x0015;//列表第三部分，各项文案


    private Context context;
    private LayoutInflater mLayoutInflater;
    private OnHomeItemClickListener mListener;

    private List<HomePagerItem> pagerItemList;
    private List<HomeActItem> actItemList;
    private List<HomeArticleItem> articleItemList;
    private String announcement;

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
    private static final int NUM_IMAGE = 3;
    private int[] currentIndex = {1};
    private HomeViewPagerAdapter viewPagerAdapter;
    private ViewPager homeViewPager;
    private AutoScrollPagerListener viewpagerListener;
    private Timer timer;
    private ImageView[] mCircleImages;
    private TextView descTV;
    //Handler 用到的参数值
    private static final int UPTATE_VIEWPAGER = 0x2580;


    public HomeRecyclerViewAdapter(Context context, List<HomePagerItem> pagerItemList, String announcement, List<HomeActItem> actItemList, List<HomeArticleItem> articleItemList) {
        this.context = context;
        mLayoutInflater = LayoutInflater.from(context);
        this.pagerItemList = pagerItemList;
        this.announcement = announcement;
        this.actItemList = actItemList;
        this.articleItemList = articleItemList;
    }

    public void setOnHomeItemClickListener(OnHomeItemClickListener listener) {
        this.mListener = listener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = null;
        if (viewType == TYPE_PAGER) {
            view = mLayoutInflater.inflate(R.layout.item_pager_home, parent, false);
            return new PagerViewHolder(view);
        }
        if (viewType == TYPE_MENU) {
            view = mLayoutInflater.inflate(R.layout.item_menu_home, parent, false);
            return new MenuViewHolder(view);
        }
        if (viewType == TYPE_ANC) {
            view = mLayoutInflater.inflate(R.layout.item_anc_home, parent, false);
            return new AncViewHolder(view);
        }
        if (viewType == TYPE_ACT) {
            view = mLayoutInflater.inflate(R.layout.item_layout_act_home, parent, false);
            return new ActViewHolder(view);
        }
        if (viewType == TYPE_ARTICLE) {
            view = mLayoutInflater.inflate(R.layout.item_layout_article_home, parent, false);
            return new ArticleViewHolder(view);
        }
        return null;
    }

    @Override
    public int getItemCount() {
        return 5;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return TYPE_PAGER;
        }
        if (position == 1) {
            return TYPE_MENU;
        }
        if (position == 2) {
            return TYPE_ANC;
        }
        if (position == 3) {
            return TYPE_ACT;
        }
        if (position == 4) {
            return TYPE_ARTICLE;
        }
        return super.getItemViewType(position);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        //处理Pager（轮播图）的ViewHolder
        if (holder instanceof PagerViewHolder) {
            PagerViewHolder mHolder = (PagerViewHolder) holder;

            //通过ImageViewListFactory将viewpager构成循环
            List<ImageView> recyclerImageView = ImageViewListFactory.convertImageViewListByItem(context, pagerItemList);

            //组件初始化
            homeViewPager = mHolder.viewPager;
            descTV = mHolder.textView;
            //配置Adapt
            //// TODO: 2017/6/13 如果数据更新viewpager没更新，这里可能需要重新配置adapter
            if (viewPagerAdapter == null) {
                viewPagerAdapter = new HomeViewPagerAdapter(context, recyclerImageView);
                homeViewPager.setAdapter(viewPagerAdapter);
            }
            //配置指示器白点
            if (mCircleImages == null) {
                setViewPagerIndicator(mHolder.indicator);
            }
            //配置监听器Listener
            if (viewpagerListener == null) {
                viewpagerListener = new AutoScrollPagerListener(homeViewPager, currentIndex, mCircleImages, descTV);
                viewpagerListener.setTextList(pagerItemList);
                homeViewPager.setOnPageChangeListener(viewpagerListener);
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

        }

        //处理Menu（三个菜单）的ViewHolder
        if (holder instanceof MenuViewHolder) {
            MenuViewHolder mHolder = (MenuViewHolder) holder;
            if (mListener == null) {
                Log.e("HomeRecyclerViewAdapter", "there is no onHomeItemClickListener");
                return;
            }
            mHolder.ticketLL.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mListener.onClick(TYPE_MENU, TYPE_MENU_TICKET);
                }
            });
            mHolder.spotLL.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mListener.onClick(TYPE_MENU, TYPE_MENU_SPOT);
                }
            });
            mHolder.supLL.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mListener.onClick(TYPE_MENU, TYPE_MENU_SUP);
                }
            });

        }

        //处理Announcement（公告）的ViewHolder
        if (holder instanceof AncViewHolder) {
            AncViewHolder mHolder = (AncViewHolder) holder;
            mHolder.textView.setText(announcement);
            if (mListener == null) {
                Log.e("HomeRecyclerViewAdapter", "there is no onHomeItemClickListener");
                return;
            }
            mHolder.textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mListener.onClick(TYPE_ANC, 0);
                }
            });
        }

        //处理Activity（热门活动）的ViewHolder
        if (holder instanceof ActViewHolder) {
            ActViewHolder mHolder = (ActViewHolder) holder;
            mHolder.moreTV.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mListener.onClick(TYPE_ACT, TYPE_ACT_MORE);
                }
            });

            for (int i = 0; i < actItemList.size(); i++) {
                final int index = i;
                LinearLayout innerLL = (LinearLayout) mLayoutInflater.inflate(R.layout.inner_item_act_home, mHolder.rootLayout, false);
                if (mListener == null) {
                    Log.e("HomeRecyclerViewAdapter", "there is no onHomeItemClickListener");
                    continue;
                }
                ImageView picIV = (ImageView) innerLL.findViewById(R.id.iv_act);
                TextView titleTV = (TextView) innerLL.findViewById(R.id.tv_title_act);
                TextView descTV = (TextView) innerLL.findViewById(R.id.tv_desc_act);
                TextView sPriceTV = (TextView) innerLL.findViewById(R.id.tv_sprice_act);
                TextView aPriceTV = (TextView) innerLL.findViewById(R.id.tv_aprice_act);
                TextView timeTV = (TextView) innerLL.findViewById(R.id.tv_time_act);
                TextView peopleTV = (TextView) innerLL.findViewById(R.id.tv_people_act);

                picIV.setImageBitmap(actItemList.get(i).getPic());
                titleTV.setText(actItemList.get(i).getTitle());
                descTV.setText(actItemList.get(i).getDesc());
                sPriceTV.setText(actItemList.get(i).getsPrice());
                aPriceTV.setText(actItemList.get(i).getaPrice());
                timeTV.setText(actItemList.get(i).getTime());
                peopleTV.setText(actItemList.get(i).getPeople());

                innerLL.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mListener.onClick(TYPE_ACT, index);
                    }
                });

                mHolder.rootLayout.addView(innerLL);
            }

        }

        //处理Article（文案精选）的ViewHolder
        if (holder instanceof ArticleViewHolder) {
            ArticleViewHolder mHolder = (ArticleViewHolder) holder;

            for (int i = 0; i < articleItemList.size(); i++) {
                final int index = i;
                LinearLayout innerLL = (LinearLayout) mLayoutInflater.inflate(R.layout.inner_item_atricle_home, mHolder.rootLayout, false);
                if (mListener == null) {
                    Log.e("HomeRecyclerViewAdapter", "there is no onHomeItemClickListener");
                    continue;
                }
                ArticleInnerHolder innerHolder = new ArticleInnerHolder(innerLL);

                innerHolder.initHolder(articleItemList.get(i));

                innerLL.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mListener.onClick(TYPE_ARTICLE, index);
                    }
                });

                mHolder.rootLayout.addView(innerLL);
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

    //用于更新pager小白点的Handler
    final Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case UPTATE_VIEWPAGER:
                    if (homeViewPager != null) {
                        if (msg.arg1 < NUM_IMAGE + 1) {
                            homeViewPager.setCurrentItem(msg.arg1);
                        } else {
                            homeViewPager.setCurrentItem(msg.arg1, false);
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

        public PagerViewHolder(View itemView) {
            super(itemView);
            viewPager = (ViewPager) itemView.findViewById(R.id.vp_pager_home);
            textView = (TextView) itemView.findViewById(R.id.tv_pager_home);
            indicator = (LinearLayout) itemView.findViewById(R.id.indicator_pager_home);
        }
    }

    class MenuViewHolder extends RecyclerView.ViewHolder {

        LinearLayout ticketLL, spotLL, supLL;

        public MenuViewHolder(View itemView) {
            super(itemView);
            ticketLL = (LinearLayout) itemView.findViewById(R.id.ll_menu_ticket);
            spotLL = (LinearLayout) itemView.findViewById(R.id.ll_menu_spot);
            supLL = (LinearLayout) itemView.findViewById(R.id.ll_menu_sup);
        }
    }

    class AncViewHolder extends RecyclerView.ViewHolder {

        TextView textView;

        public AncViewHolder(View itemView) {
            super(itemView);
            textView = (TextView) itemView.findViewById(R.id.tv_anc_home);
        }
    }

    class ActViewHolder extends RecyclerView.ViewHolder {

        LinearLayout rootLayout;
        TextView moreTV;

        public ActViewHolder(View itemView) {
            super(itemView);
            rootLayout = (LinearLayout) itemView;
            moreTV = (TextView) itemView.findViewById(R.id.tv_more_act_home);
        }
    }

    class ActInnerHolder {
        ImageView picIV;
        TextView titleTV;
        TextView descTV;
        TextView sPriceTV;
        TextView aPriceTV;
        TextView timeTV;
        TextView peopleTV;

        public ActInnerHolder(View rootView) {
            picIV = (ImageView) rootView.findViewById(R.id.iv_act);
            titleTV = (TextView) rootView.findViewById(R.id.tv_title_act);
            descTV = (TextView) rootView.findViewById(R.id.tv_desc_act);
            sPriceTV = (TextView) rootView.findViewById(R.id.tv_sprice_act);
            aPriceTV = (TextView) rootView.findViewById(R.id.tv_aprice_act);
            timeTV = (TextView) rootView.findViewById(R.id.tv_time_act);
            peopleTV = (TextView) rootView.findViewById(R.id.tv_people_act);
        }

        public void initHolder(HomeActItem homeActItem) {
            picIV.setImageBitmap(homeActItem.getPic());
            titleTV.setText(homeActItem.getTitle());
            descTV.setText(homeActItem.getDesc());
            sPriceTV.setText(homeActItem.getsPrice());
            aPriceTV.setText(homeActItem.getaPrice());
            timeTV.setText(homeActItem.getTime());
            peopleTV.setText(homeActItem.getPeople());
        }

    }

    class ArticleViewHolder extends RecyclerView.ViewHolder {

        LinearLayout rootLayout;

        public ArticleViewHolder(View itemView) {
            super(itemView);
            rootLayout = (LinearLayout) itemView;
        }
    }

    class ArticleInnerHolder {
        ImageView picAIV, picBIV;
        TextView descTV, timeTV, likeTV;

        public ArticleInnerHolder(View rootView) {
            picAIV = (ImageView) rootView.findViewById(R.id.iv_pica_artcle);
            picBIV = (ImageView) rootView.findViewById(R.id.iv_picb_artcle);
            descTV = (TextView) rootView.findViewById(R.id.tv_desc_artcle);
            timeTV = (TextView) rootView.findViewById(R.id.tv_time_article);
            likeTV = (TextView) rootView.findViewById(R.id.tv_like_article);
        }

        public void initHolder(HomeArticleItem homeArticleItem) {
            picAIV.setImageBitmap(homeArticleItem.getPicA());
            picBIV.setImageBitmap(homeArticleItem.getPicB());
            descTV.setTag(homeArticleItem.getDesc());
            timeTV.setText(homeArticleItem.getTime());
            likeTV.setText(homeArticleItem.getLike());
        }

    }

}
