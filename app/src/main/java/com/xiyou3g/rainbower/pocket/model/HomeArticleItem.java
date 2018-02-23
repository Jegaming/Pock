package com.xiyou3g.rainbower.pocket.model;

import android.graphics.Bitmap;

/**
 * Created by dell2014 on 2017/6/13.
 */

public class HomeArticleItem {

    private Bitmap picA, picB;   //文案的左右两张图片
    private String desc;        //文案的描述
    private String time;        //文案时间
    private String like;        //文案点赞的数量

    public void setLike(int like) {
        this.like = like + "";
    }

    public Bitmap getPicA() {
        return picA;
    }

    public void setPicA(Bitmap picA) {
        this.picA = picA;
    }

    public Bitmap getPicB() {
        return picB;
    }

    public void setPicB(Bitmap picB) {
        this.picB = picB;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getLike() {
        return like;
    }

    public void setLike(String like) {
        this.like = like;
    }
}
