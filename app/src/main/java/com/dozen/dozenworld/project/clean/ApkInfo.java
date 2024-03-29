package com.dozen.dozenworld.project.clean;


public class ApkInfo {

    private String packageName;
    private String versionName;
    private int versionCode;

    public ApkInfo() {
    }

    public ApkInfo(String packageName, String versionName, int versionCode) {
        this.packageName = packageName;
        this.versionName = versionName;
        this.versionCode = versionCode;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public String getVersionName() {
        return versionName;
    }

    public void setVersionName(String versionName) {
        this.versionName = versionName;
    }

    public int getVersionCode() {
        return versionCode;
    }

    public void setVersionCode(int versionCode) {
        this.versionCode = versionCode;
    }
}
