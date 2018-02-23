package com.xiyou3g.rainbower.pocket.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.xiyou3g.rainbower.pocket.R;
import com.xiyou3g.rainbower.pocket.adapter.SceneRecyclerViewAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dell2014 on 2017/5/9.
 */

public class SceneFragment extends Fragment {


    private View rootView;

    private RecyclerView mRecyclerView;
    private SceneRecyclerViewAdapter mRecyclerViewAdapter;
    private List<ImageView> testImageViews;
    private List<String> testStrings;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_scene, container, false);

        initViews();
        return rootView;
    }

    private void initViews() {
        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.scene_recyclerview);

        mRecyclerView.addItemDecoration(new DividerItemDecoration(rootView.getContext(), DividerItemDecoration.VERTICAL));

        mRecyclerView.setLayoutManager(new LinearLayoutManager(rootView.getContext()));
        testImageViews = new ArrayList<>();
        ImageView iv2 = new ImageView(rootView.getContext());
        iv2.setScaleType(ImageView.ScaleType.CENTER_CROP);
        iv2.setImageResource(R.drawable.use_spots);
        testImageViews.add(iv2);
        ImageView iv3 = new ImageView(rootView.getContext());
        iv3.setScaleType(ImageView.ScaleType.CENTER_CROP);
        iv3.setImageResource(R.drawable.test);
        testImageViews.add(iv3);
        ImageView iv4 = new ImageView(rootView.getContext());
        iv4.setScaleType(ImageView.ScaleType.CENTER_CROP);
        iv4.setImageResource(R.drawable.use_support);
        testImageViews.add(iv4);
        ImageView iv5 = new ImageView(rootView.getContext());
        iv5.setScaleType(ImageView.ScaleType.CENTER_CROP);
        iv5.setImageResource(R.drawable.use_ticket);
        testImageViews.add(iv5);

        testStrings = new ArrayList<>();
        testStrings.add("1");
        testStrings.add("2");
        testStrings.add("3");
        testStrings.add("4");
        testStrings.add("5");
        testStrings.add("6");
        testStrings.add("7");
        testStrings.add("8");

        mRecyclerViewAdapter = new SceneRecyclerViewAdapter(rootView.getContext(), testImageViews, testStrings);
        mRecyclerView.setAdapter(mRecyclerViewAdapter);

    }


}

