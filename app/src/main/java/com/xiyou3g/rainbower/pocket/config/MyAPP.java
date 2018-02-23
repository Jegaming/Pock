package com.xiyou3g.rainbower.pocket.config;

import android.app.Application;

/**
 * Created by dell2014 on 2017/5/14.
 */

public class MyAPP extends Application {
    private static MyAPP myApplication = null;

    private Boolean isLogin = false;

    @Override
    public void onCreate() {
        super.onCreate();
    }

    public static MyAPP getApplication() {
        if (myApplication == null) {
            myApplication = new MyAPP();
        }
        return myApplication;
    }

    public boolean getLoginStatus() {
        return isLogin;
    }

    public void setLoginStatus(boolean isLogin) {
        this.isLogin = isLogin;
    }

}