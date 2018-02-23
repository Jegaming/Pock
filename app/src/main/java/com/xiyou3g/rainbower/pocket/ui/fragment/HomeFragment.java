package com.xiyou3g.rainbower.pocket.ui.fragment;

import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.xiyou3g.rainbower.pocket.R;
import com.xiyou3g.rainbower.pocket.adapter.HomeRecyclerViewAdapter;
import com.xiyou3g.rainbower.pocket.adapter.OnHomeItemClickListener;
import com.xiyou3g.rainbower.pocket.model.HomeActItem;
import com.xiyou3g.rainbower.pocket.model.HomeArticleItem;
import com.xiyou3g.rainbower.pocket.model.HomePagerItem;
import com.xiyou3g.rainbower.pocket.ui.activity.SpotActivity;

import java.util.ArrayList;
import java.util.List;

import static com.xiyou3g.rainbower.pocket.adapter.HomeRecyclerViewAdapter.TYPE_ACT;
import static com.xiyou3g.rainbower.pocket.adapter.HomeRecyclerViewAdapter.TYPE_ACT_MORE;
import static com.xiyou3g.rainbower.pocket.adapter.HomeRecyclerViewAdapter.TYPE_ANC;
import static com.xiyou3g.rainbower.pocket.adapter.HomeRecyclerViewAdapter.TYPE_ARTICLE;
import static com.xiyou3g.rainbower.pocket.adapter.HomeRecyclerViewAdapter.TYPE_MENU;
import static com.xiyou3g.rainbower.pocket.adapter.HomeRecyclerViewAdapter.TYPE_MENU_SPOT;
import static com.xiyou3g.rainbower.pocket.adapter.HomeRecyclerViewAdapter.TYPE_MENU_SUP;
import static com.xiyou3g.rainbower.pocket.adapter.HomeRecyclerViewAdapter.TYPE_MENU_TICKET;
import static com.xiyou3g.rainbower.pocket.adapter.HomeRecyclerViewAdapter.TYPE_PAGER;

/**
 * Created by dell2014 on 2017/5/9.
 */

public class HomeFragment extends Fragment implements OnHomeItemClickListener {


    private View rootView;
    private RecyclerView recyclerView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_home, container, false);

        initRecycleView();
        return rootView;
    }


    private void initRecycleView() {
        recyclerView = (RecyclerView) rootView.findViewById(R.id.home_recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(rootView.getContext()));

        Drawable drawableA, drawableB, drawableC;
        drawableA = this.getResources().getDrawable(R.drawable.use_spots);
        drawableB = this.getResources().getDrawable(R.drawable.use_ticket);
        drawableC = this.getResources().getDrawable(R.drawable.use_support);

        List<HomePagerItem> homePagerItems = new ArrayList<>();
        HomePagerItem homePagerItem;

        homePagerItem = new HomePagerItem();
        homePagerItem.setPic(((BitmapDrawable) drawableA).getBitmap());
        homePagerItem.setDesc("这是第一张图片哦");
        homePagerItems.add(homePagerItem);

        homePagerItem = new HomePagerItem();
        homePagerItem.setPic(((BitmapDrawable) drawableB).getBitmap());
        homePagerItem.setDesc("这是第二张图片哦");
        homePagerItems.add(homePagerItem);

        homePagerItem = new HomePagerItem();
        homePagerItem.setPic(((BitmapDrawable) drawableC).getBitmap());
        homePagerItem.setDesc("这是第三张图片哦");
        homePagerItems.add(homePagerItem);


        String announcement = "呀，没题目\n大家好啊，我是公告栏的公告！ ";

        List<HomeActItem> homeActItems = new ArrayList<>();
        HomeActItem homeActItem;

        homeActItem = new HomeActItem();
        homeActItem.setDesc("岳阳县荣家湾长丰南路与融新西路交汇处");
        homeActItem.setTitle("兵临城下，岳阳冰雕");
        homeActItem.setsPrice(99);
        homeActItem.setaPrice(149);
        homeActItem.setTime(" 2017.8.8-9.15 ");
        homeActItem.setPeople(179);
        homeActItem.setPic(((BitmapDrawable) drawableA).getBitmap());
        homeActItems.add(homeActItem);

        homeActItem = new HomeActItem();
        homeActItem.setDesc("东濒山西临汾市吉县壶口镇");
        homeActItem.setTitle("壶口瀑布");
        homeActItem.setsPrice(80);
        homeActItem.setaPrice(120);
        homeActItem.setTime("  随买随用  ");
        homeActItem.setPeople(20);
        homeActItem.setPic(((BitmapDrawable) drawableB).getBitmap());
        homeActItems.add(homeActItem);

        List<HomeArticleItem> homeArticleItems = new ArrayList<>();
        HomeArticleItem homeArticleItem;

        homeArticleItem = new HomeArticleItem();
        homeArticleItem.setPicA(((BitmapDrawable) drawableB).getBitmap());
        homeArticleItem.setPicB(((BitmapDrawable) drawableC).getBitmap());
        homeArticleItem.setDesc("华清池杨贵妃的故事赋予了华清池浪漫唯美的色彩，唐风建筑很多，园林设计很美，景点维护的也不错。");
        homeArticleItem.setTime("2017-05-21 晚上 11:27");
        homeArticleItem.setLike(13);
        homeArticleItems.add(homeArticleItem);

        homeArticleItem = new HomeArticleItem();
        homeArticleItem.setPicA(((BitmapDrawable) drawableC).getBitmap());
        homeArticleItem.setPicB(((BitmapDrawable) drawableA).getBitmap());
        homeArticleItem.setDesc("林芝地区的饮食除沿袭西藏的传统风味外，如青稞酒、糌粑、酥油和酥油茶等，还有一些少数民族的特色食品。");
        homeArticleItem.setTime("2017-03-21 晚上 02:32");
        homeArticleItem.setLike(20);
        homeArticleItems.add(homeArticleItem);

        HomeRecyclerViewAdapter homeRecyclerViewAdapter = new HomeRecyclerViewAdapter(rootView.getContext(), homePagerItems, announcement, homeActItems, homeArticleItems);
        homeRecyclerViewAdapter.setOnHomeItemClickListener(this);
        recyclerView.setAdapter(homeRecyclerViewAdapter);
    }

    @Override
    public void onClick(int TYPE, int index) {
        switch (TYPE) {
            //轮播图部分的点击事件
            case TYPE_PAGER:
                break;
            //三个按钮的点击事件
            case TYPE_MENU:
                //火车票
                if (index == TYPE_MENU_TICKET) {
                    Toast.makeText(rootView.getContext(), "火车票", Toast.LENGTH_SHORT).show();
                }
                //景点娱乐
                if (index == TYPE_MENU_SPOT) {
                    Intent intent = new Intent();
                    intent.setClass(getActivity(), SpotActivity.class);
                    getActivity().startActivity(intent);
                }
                //第三方
                if (index == TYPE_MENU_SUP) {
                    Toast.makeText(rootView.getContext(), "第三方", Toast.LENGTH_SHORT).show();
                }
                break;
            //公告栏的点击事件
            case TYPE_ANC:
                Toast.makeText(rootView.getContext(), "公告栏", Toast.LENGTH_SHORT).show();
                break;
            //活动的点击事件
            case TYPE_ACT:
                //查看更多活动
                if (index == TYPE_ACT_MORE) {
                    Toast.makeText(rootView.getContext(), "更多", Toast.LENGTH_SHORT).show();
                }
                //第index条活动
                else {
                    Toast.makeText(rootView.getContext(), "活动条目" + (index + 1), Toast.LENGTH_SHORT).show();
                }
                break;
            //文案的点击事件
            case TYPE_ARTICLE:
                Toast.makeText(rootView.getContext(), "文案条目" + (index + 1), Toast.LENGTH_SHORT).show();
                break;

            default:
                break;
        }
    }

}

