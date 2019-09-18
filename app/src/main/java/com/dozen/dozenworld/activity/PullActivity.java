package com.dozen.dozenworld.activity;

import android.annotation.SuppressLint;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;

import com.dozen.dozenworld.R;
import com.dozen.dozenworld.custom.TouchPullView;

public class PullActivity extends AppCompatActivity {

    private TouchPullView touchView;

    private static final float TOUCH_MOVE_MAX_Y=800;
    private float mTouchStartY=0;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pull);

        touchView = findViewById(R.id.tp_touch_pull);


        findViewById(R.id.ac_pull).setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                int actionMasked = event.getActionMasked();
                switch (actionMasked) {
                    case MotionEvent.ACTION_DOWN:
                        mTouchStartY = event.getY();
                        return true;
                    case MotionEvent.ACTION_MOVE:
                        float y = event.getY();
                        if (y >= mTouchStartY) {  //表示向下移动
                            float moveSize = y - mTouchStartY;
                            float progress = moveSize > TOUCH_MOVE_MAX_Y ?
                                    1 : moveSize / TOUCH_MOVE_MAX_Y;   //计算进度值
                            touchView.setProgress(progress);
                            return true;
                        }
                        break;
                    case MotionEvent.ACTION_UP:
                        touchView.release();
                        return true;
                    default:
                        break;
                }
                return false;
            }
        });

    }
}
