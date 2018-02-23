package com.xiyou3g.rainbower.pocket.model;

import android.graphics.Bitmap;

/**
 * Created by dell2014 on 2017/6/13.
 */

public class HomeActItem {

    private Bitmap pic;     //活动项的图片
    private String title;   //活动项的题目
    private String desc;    //活动项的描述
    private String time;    //活动项的时间（红色方框内）
    private String sPrice;  //活动项的学生价格
    private String aPrice;  //活动项的成人价格
    private String people;  //活动项的消费人数

    public void setaPrice(int price) {
        this.aPrice = price + "";
    }

    public void setsPrice(int price) {
        this.sPrice = price + "";
    }

    public void setPeople(int people) {
        this.people = people + "";
    }

    public Bitmap getPic() {
        return pic;
    }

    public void setPic(Bitmap pic) {
        this.pic = pic;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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

    public String getsPrice() {
        return sPrice;
    }

    public void setsPrice(String sPrice) {
        this.sPrice = sPrice;
    }

    public String getaPrice() {
        return aPrice;
    }

    public void setaPrice(String aPrice) {
        this.aPrice = aPrice;
    }

    public String getPeople() {
        return people;
    }

    public void setPeople(String people) {
        this.people = people;
    }
}
