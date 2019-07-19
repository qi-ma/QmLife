package com.qm.qmlife.business.model;

/**
 * Created by syt on 2019/7/2.
 */

public class Img {
    private String imgSrc;
    private String date;
    private int state;

    public Img(String imgSrc, String date, int state) {
        this.imgSrc = imgSrc;
        this.date = date;
        this.state = state;
    }

    public String getImgSrc() {
        return imgSrc;
    }

    public void setImgSrc(String imgSrc) {
        this.imgSrc = imgSrc;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }
}
