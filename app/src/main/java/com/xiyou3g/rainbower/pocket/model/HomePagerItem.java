package com.xiyou3g.rainbower.pocket.model;

import android.graphics.Bitmap;

/**
 * Created by dell2014 on 2017/6/13.
 */

public class HomePagerItem {
    private Bitmap pic;//viewpager展示的图片
    private String desc;//pager下面TextView的描述

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public Bitmap getPic() {

        return pic;
    }

    public void setPic(Bitmap pic) {
        this.pic = pic;
    }
}
