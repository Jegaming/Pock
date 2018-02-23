package com.xiyou3g.rainbower.pocket.ui.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.xiyou3g.rainbower.pocket.R;

/**
 * Created by dell2014 on 2017/6/14.
 */

public class UserFragment extends Fragment {
    View rootView;

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_user, container, false);

        return rootView;
    }
}
