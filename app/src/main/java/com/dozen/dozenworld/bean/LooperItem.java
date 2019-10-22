package com.dozen.dozenworld.bean;

import android.graphics.Bitmap;

/**
 * Created by Hugo on 19-10-22.
 * Describe:
 */
public class LooperItem {

    private int id;
    private String picture;
    private Bitmap bitmap;
    private String name;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
