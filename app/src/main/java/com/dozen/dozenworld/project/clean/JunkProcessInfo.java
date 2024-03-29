package com.dozen.dozenworld.project.clean;

import com.dozen.dozenworld.utils.Constant;

public class JunkProcessInfo implements MultiItemEntity {

    public static final int PROCESS = 0;
    public static final int CACHE = 1;
    public static final int APK = 2;
    public static final int TEMP = 3;
    public static final int LOG = 4;
    public static final int BIG_FILE = 5;
    private int type;
    private JunkInfo junkInfo;
    private AppProcessInfo appProcessInfo;
    private boolean isCheck;

    public JunkProcessInfo(JunkInfo junkInfo, int type) {
        this.junkInfo = junkInfo;
        this.type = type;
    }

    public JunkProcessInfo(AppProcessInfo appProcessInfo) {
        this.appProcessInfo = appProcessInfo;
        this.isCheck = true;
        this.type = PROCESS;
    }

    public JunkProcessInfo(JunkInfo junkInfo, AppProcessInfo appProcessInfo) {
        this.junkInfo = junkInfo;
        this.appProcessInfo = appProcessInfo;
    }

    public JunkInfo getJunkInfo() {
        return junkInfo;
    }

    public JunkProcessInfo setJunkInfo(JunkInfo junkInfo) {
        this.junkInfo = junkInfo;
        return this;
    }

    public AppProcessInfo getAppProcessInfo() {
        return appProcessInfo;
    }

    public JunkProcessInfo setAppProcessInfo(AppProcessInfo appProcessInfo) {
        this.appProcessInfo = appProcessInfo;
        return this;
    }

    public boolean isCheck() {
        return isCheck;
    }

    public JunkProcessInfo setCheck(boolean check) {
        isCheck = check;
        return this;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    @Override
    public int getItemType() {
        return Constant.TYPE_CHILD;
    }
}
