package com.dozen.dozenworld;

import android.app.Application;

import com.dozen.dozenworld.utils.T;

/**
 * Created by Dozen on 19-7-19.
 * Describe:
 */
public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        T.setContext(getApplicationContext());
    }
}
