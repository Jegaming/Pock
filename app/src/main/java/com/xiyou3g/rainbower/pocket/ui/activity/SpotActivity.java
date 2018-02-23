package com.xiyou3g.rainbower.pocket.ui.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.SearchView;

import com.xiyou3g.rainbower.pocket.R;
import com.xiyou3g.rainbower.pocket.util.SpotRecyclerViewAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dell2014 on 2017/5/19.
 */


public class SpotActivity extends AppCompatActivity {

    private SearchView mSearchView;
    private ImageView backIV;

    private RecyclerView mRecyclerView;
    private SpotRecyclerViewAdapter mRecyclerViewAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_spot);
        backIV = (ImageView) findViewById(R.id.iv_back_spot);
        backIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        initViews();
    }

    private void initViews() {
//        mSearchView = (SearchView) findViewById(R.id.spot_search_view);

        mRecyclerView = (RecyclerView) findViewById(R.id.spot_recyclerview);
        initRecyclerView();

    }

    private void initRecyclerView() {
        mRecyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));

        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        List<ImageView> testImageViews = new ArrayList<>();
        ImageView iv1 = new ImageView(this);
        iv1.setScaleType(ImageView.ScaleType.CENTER_CROP);
        iv1.setImageResource(R.drawable.use_spots);
        testImageViews.add(iv1);
        ImageView iv2 = new ImageView(this);
        iv2.setScaleType(ImageView.ScaleType.CENTER_CROP);
        iv2.setImageResource(R.drawable.use_support);
        testImageViews.add(iv2);
        ImageView iv3 = new ImageView(this);
        iv3.setScaleType(ImageView.ScaleType.CENTER_CROP);
        iv3.setImageResource(R.drawable.use_ticket);
        testImageViews.add(iv3);

        mRecyclerViewAdapter = new SpotRecyclerViewAdapter(this, testImageViews);
        mRecyclerView.setAdapter(mRecyclerViewAdapter);
    }
}
