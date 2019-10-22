package com.dozen.dozenworld.activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.dozen.dozenworld.R;
import com.dozen.dozenworld.custom.HeadView;
import com.dozen.dozenworld.rxpermissions2.RxPermissions;

import java.util.concurrent.ExecutionException;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * Created by Dozen on 19-7-17.
 * Describe:
 */
public class HeadActivity extends AppCompatActivity {


    private HeadView hv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_head);


        Button show=findViewById(R.id.btn_head);
        hv=findViewById(R.id.hv_head_show);

        show.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                start();

            }
        });

    }

    private void start(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {

                    String url="https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1572333087&di=7a39dd87e682e4f2c893c60fbafb8ce0&imgtype=jpg&er=1&src=http%3A%2F%2Fwww.chmotor.cn%2Fuploads%2F20141124%2F45f004ffaee8024531073fb57cd68c18.jpg";

                    Bitmap myBitmap = Glide.with(HeadActivity.this)
                            .asBitmap()
//                            .load(R.drawable.icon)
                            .load(url)
                            .submit(500, 500).get();
                    Message msg=new Message();
                    msg.obj=myBitmap;
                    handler.sendMessage(msg);

                } catch (ExecutionException | InterruptedException e) {
                    e.printStackTrace();
                }



            }
        }).start();
    }


    @SuppressLint("HandlerLeak")
    Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

//            Bitmap drawable= BitmapFactory.decodeResource(getResources(),R.drawable.icon);
            Bitmap bitmap= (Bitmap) msg.obj;
            Drawable drawable1=new BitmapDrawable(bitmap);
            hv.setPicture(drawable1);

        }
    };
}
