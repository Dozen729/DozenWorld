package com.dozen.dozenworld.activity;

import android.app.Activity;
import android.os.Bundle;

import com.dozen.dozenworld.R;

/**
 * Created by Dozen on 19-7-17.
 * Describe:
 */
public class HelloActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sample_hello_view);
    }
}
