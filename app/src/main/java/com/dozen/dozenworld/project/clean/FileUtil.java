package com.dozen.dozenworld.project.clean;

import android.annotation.SuppressLint;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

import com.dozen.dozenworld.MyApplication;

import java.io.File;

public class FileUtil {

    public static String getExtension(String name) {
        String ext;

        if (name.lastIndexOf(".") == -1) {
            ext = "";

        } else {
            int index = name.lastIndexOf(".");
            ext = name.substring(index + 1, name.length());
        }
        return ext;
    }

    public static ApkInfo getApkInfo(String path) {
        ApkInfo apkInfo = new ApkInfo();
        PackageManager pm = MyApplication.sContext.getApplicationContext().getPackageManager();
        PackageInfo packageInfo = pm.getPackageArchiveInfo(path, PackageManager.GET_ACTIVITIES);

        if (packageInfo != null) {
            ApplicationInfo applicationInfo = packageInfo.applicationInfo;
            apkInfo.setPackageName(applicationInfo.packageName);
            apkInfo.setVersionName(packageInfo.versionName);
            apkInfo.setVersionCode(packageInfo.versionCode);
            return apkInfo;
        }

        return null;
    }

    public static void deleteTarget(String path) {
        File target = new File(path);

        if (target.isFile() && target.canWrite()) {
            target.delete();
        } else if (target.isDirectory() && target.canRead() && target.canWrite()) {
            String[] fileList = target.list();

            if (fileList != null && fileList.length == 0) {
                target.delete();
                return;
            } else if (fileList != null && fileList.length > 0) {
                for (String aFile_list : fileList) {
                    File tempF = new File(target.getAbsolutePath() + "/"
                            + aFile_list);

                    if (tempF.isDirectory())
                        deleteTarget(tempF.getAbsolutePath());
                    else if (tempF.isFile()) {
                        tempF.delete();
                    }
                }
            }

            if (target.exists())
                target.delete();
        }
//        else if (!target.delete() && Settings.rootAccess()) {
//            RootCommands.deleteRootFileOrDir(target);
//        }
    }


    /**
     * 用于转换文件单位
     *
     * @param size 以Byte为单位的文件大小
     * @return FileSize 包含了文件转换后的大小及单位
     */
    @SuppressLint("DefaultLocale")
    public static FileSize formatFileSize(long size) {
        float result = size;
        Unit unit = Unit.B;
        if (result > 900) {
            unit = Unit.KB;
            result = result / 1024;
        }
        if (result > 900) {
            unit = Unit.MB;
            result = result / 1024;
        }
        if (result > 900) {
            unit = Unit.GB;
            result = result / 1024;
        }
        String value = null;
        switch (unit) {
            case B:
            case KB:
                value = String.valueOf((int) result);
                break;
            case MB:
                value = String.format("%.1f", result);
                break;
            case GB:
                value = String.format("%.2f", result);
                break;
            default:
                break;
        }
        return new FileSize(value, unit);
    }

    /**
     * 单位
     */
    public enum Unit {
        B("B", "B"), KB("KB", "K"), MB("MB", "M"), GB("GB", "G");

        public String mFullValue;
        public String mShortValue;

        Unit(String fullValue, String shortValue) {
            mFullValue = fullValue;
            mShortValue = shortValue;
        }
    }

    /**
     * 文件大小
     */
    public static class FileSize {
        public String mSize;
        public Unit mUnit;

        public FileSize(String size, Unit unit) {
            mSize = size;
            mUnit = unit;
        }

        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder();
            return sb.append(mSize).append(" ").append(mUnit.mShortValue)
                    .toString();
        }

        /**
         * 返回全称单位的字符串
         */
        public String toFullString() {
            StringBuilder sb = new StringBuilder();
            return sb.append(mSize).append(" ").append(mUnit.mFullValue)
                    .toString();
        }
    }
}