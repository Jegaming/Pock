package com.xiyou3g.rainbower.pocket.ui.fragment;

import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.xiyou3g.rainbower.pocket.R;

/**
 * Created by dell2014 on 2017/5/9.
 */

public class CardFragment extends Fragment {


    private View rootView;

    private RecyclerView recyclerView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_card, container, false);
        recyclerView = (RecyclerView) rootView.findViewById(R.id.home_recyclerview);

        return rootView;
    }


}
