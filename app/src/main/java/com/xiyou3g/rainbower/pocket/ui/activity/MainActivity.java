package com.xiyou3g.rainbower.pocket.ui.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.xiyou3g.rainbower.pocket.ui.fragment.CardFragment;
import com.xiyou3g.rainbower.pocket.ui.fragment.HomeFragment;
import com.xiyou3g.rainbower.pocket.ui.fragment.SceneFragment;
import com.xiyou3g.rainbower.pocket.config.MyAPP;
import com.xiyou3g.rainbower.pocket.R;
import com.xiyou3g.rainbower.pocket.ui.fragment.UserFragment;
import com.xiyou3g.rainbower.pocket.util.BitmapFactory;

public class MainActivity extends AppCompatActivity
        implements View.OnClickListener {

    private HomeFragment mHomeFragment;
    private CardFragment mCardFragment;
    private SceneFragment mSceneFragment;
    private UserFragment mUserFragment;
    private FragmentManager mFragmentManager;

    //顶部控件
    private TextView fragmentTitleTV;
    private ImageView iconIV, msgIV;

    //底部控件
    private ImageView bottomCardIV, bottomHomeIV, bottomSceneIV, bottomUserIV;
    private TextView bottomCardTV, bottomHomeTV, bottomSceneTV, bottomUserTV;
    private LinearLayout bottomCardLayout, bottomHomeLayout, bottomSceneLayout, bottomUserLayout, bottomAddLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
    }


    private void initViews() {
        //顶部菜单
        fragmentTitleTV = (TextView) findViewById(R.id.main_title);
//        fragmentTitleTV.setText("首页");
        iconIV = (ImageView) findViewById(R.id.iv_icon_toolbar);
        msgIV = (ImageView) findViewById(R.id.iv_msg_toolbar);
        iconIV.setOnClickListener(this);
        msgIV.setOnClickListener(this);

        //底部菜单
        bottomCardLayout = (LinearLayout) findViewById(R.id.bottom_card_layout);
        bottomHomeLayout = (LinearLayout) findViewById(R.id.bottom_home_layout);
        bottomSceneLayout = (LinearLayout) findViewById(R.id.bottom_scene_layout);
        bottomUserLayout = (LinearLayout) findViewById(R.id.bottom_user_layout);
        bottomAddLayout = (LinearLayout) findViewById(R.id.bottom_add_layout);
        bottomCardIV = (ImageView) findViewById(R.id.bottom_card_iv);
        bottomHomeIV = (ImageView) findViewById(R.id.bottom_home_iv);
        bottomSceneIV = (ImageView) findViewById(R.id.bottom_scene_iv);
        bottomUserIV = (ImageView) findViewById(R.id.bottom_user_iv);
        bottomCardTV = (TextView) findViewById(R.id.bottom_card_tv);
        bottomHomeTV = (TextView) findViewById(R.id.bottom_home_tv);
        bottomSceneTV = (TextView) findViewById(R.id.bottom_scene_tv);
        bottomUserTV = (TextView) findViewById(R.id.bottom_user_tv);
        bottomCardLayout.setOnClickListener(this);
        bottomHomeLayout.setOnClickListener(this);
        bottomSceneLayout.setOnClickListener(this);
        bottomUserLayout.setOnClickListener(this);
        bottomAddLayout.setOnClickListener(this);

        //获取FragmentManager
        mFragmentManager = getSupportFragmentManager();
        //将当前fragment页设置为0
        setFragmentSelection(0);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    /**
     * 底部菜单的点击事件。
     *
     * @param v
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bottom_card_layout:
                setFragmentSelection(1);
                break;
            case R.id.bottom_scene_layout:
                setFragmentSelection(2);
                break;
            case R.id.bottom_user_layout:
                setFragmentSelection(3);
                break;
            case R.id.bottom_home_layout:
                setFragmentSelection(0);
                break;
            case R.id.bottom_add_layout:
                //todo 发表游记
                break;
            case R.id.iv_icon_toolbar:
                if (!MyAPP.getApplication().getLoginStatus()) {
                    //如果未登录
                    Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                    startActivity(intent);
                    return;
                } else {
                    //如果已经登陆
                    setFragmentSelection(3);
                }
                break;
            default:
                break;
        }
    }

    /**
     * 设置切换fragment事务
     *
     * @param index index=0 mHomeFragment
     *              index=1 mUseFragment
     *              index=2 mSceneFragment
     *              index=3 mCardFragment
     */
    private void setFragmentSelection(int index) {
        //开启事务
        FragmentTransaction transaction = mFragmentManager.beginTransaction();

        //清除底部选中状态
        clearBottomSelection();
        //隐藏Fragment
        hideFragments(transaction);

        switch (index) {
            case 0:
                setBottomSelection(0);
                fragmentTitleTV.setText("首页");
                if (mHomeFragment == null) {
                    mHomeFragment = new HomeFragment();
                    transaction.add(R.id.content, mHomeFragment);
                } else {
                    transaction.show(mHomeFragment);
                }
                break;
            case 1:
                setBottomSelection(1);
                fragmentTitleTV.setText("电子证书");
                if (mCardFragment == null) {
                    mCardFragment = new CardFragment();
                    transaction.add(R.id.content, mCardFragment);
                } else {
                    transaction.show(mCardFragment);
                }
                break;
            case 2:
                setBottomSelection(2);
                fragmentTitleTV.setText("圈子");
                if (mSceneFragment == null) {
                    mSceneFragment = new SceneFragment();
                    transaction.add(R.id.content, mSceneFragment);
                } else {
                    transaction.show(mSceneFragment);
                }
                break;
            case 3:
                setBottomSelection(3);
                fragmentTitleTV.setText("个人中心");
                if (mUserFragment == null) {
                    mUserFragment = new UserFragment();
                    transaction.add(R.id.content, mUserFragment);
                } else {
                    transaction.show(mUserFragment);
                }
                break;
            default:
                break;
        }

        transaction.commit();
    }

    /**
     * 底部菜单全部清楚为未选中状态
     */
    private void clearBottomSelection() {
        bottomCardIV.setImageResource(R.drawable.bottom_card);
        bottomHomeIV.setImageResource(R.drawable.bottom_home);
        bottomSceneIV.setImageResource(R.drawable.bottom_scene);
        bottomUserIV.setImageResource(R.drawable.bottom_user);
        bottomCardTV.setTextColor(Color.parseColor("#777777"));
        bottomHomeTV.setTextColor(Color.parseColor("#777777"));
        bottomSceneTV.setTextColor(Color.parseColor("#777777"));
        bottomUserTV.setTextColor(Color.parseColor("#777777"));
    }

    /**
     * 设置底部菜单选中状态
     *
     * @param index
     */
    private void setBottomSelection(int index) {
        switch (index) {
            case 0:
                bottomHomeIV.setImageResource(R.drawable.bottom_home_selected);
                bottomHomeTV.setTextColor(Color.parseColor("#50d27c"));
                break;
            case 1:
                bottomCardIV.setImageResource(R.drawable.bottom_card_selected);
                bottomCardTV.setTextColor(Color.parseColor("#50d27c"));
                break;
            case 2:
                bottomSceneIV.setImageResource(R.drawable.bottom_scene_selected);
                bottomSceneTV.setTextColor(Color.parseColor("#50d27c"));
                break;
            case 3:
                bottomUserIV.setImageResource(R.drawable.bottom_user_selected);
                bottomUserTV.setTextColor(Color.parseColor("#50d27c"));
                break;
            default:
                break;
        }
    }

    /**
     * 将所有的Fragment都置为隐藏状态。
     *
     * @param transaction
     */
    private void hideFragments(FragmentTransaction transaction) {
        if (mCardFragment != null) {
            transaction.hide(mCardFragment);
        }
        if (mHomeFragment != null) {
            transaction.hide(mHomeFragment);
        }
        if (mSceneFragment != null) {
            transaction.hide(mSceneFragment);
        }
        if (mUserFragment != null) {
            transaction.hide(mUserFragment);
        }
    }

}
