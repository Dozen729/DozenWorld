package com.dozen.dozenworld.bean;

import java.io.Serializable;

/**
 * Created by Dozen on 19-7-17.
 * Describe:
 */
public class StartItem implements Serializable {
    private int ID;
    private String name;
    private Class cla;
    private String title;
    private String tip;

    public StartItem() {
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Class getCla() {
        return cla;
    }

    public void setCla(Class cla) {
        this.cla = cla;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTip() {
        return tip;
    }

    public void setTip(String tip) {
        this.tip = tip;
    }
}
