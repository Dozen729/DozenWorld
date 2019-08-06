package com.dozen.dozenworld;

import android.app.Application;
import android.content.Context;

import com.dozen.dozenworld.utils.T;

/**
 * Created by Dozen on 19-7-19.
 * Describe:
 */
public class MyApplication extends Application {

    public static MyApplication sContext;
    public static int sHeight;
    public static int sWidth;
    private static final String TAG = "App";

    @Override
    public void onCreate() {
        super.onCreate();
        T.setContext(getApplicationContext());

        sContext=this;
    }

    public static Context getAppContext() {
        return sContext;
    }

}
