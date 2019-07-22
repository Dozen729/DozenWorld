package com.dozen.dozenworld.activity;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.dozen.dozenworld.R;
import com.dozen.dozenworld.custom.banner.ImageBannerFrameLayout;
import com.dozen.dozenworld.custom.banner.ImageBannerViewGroup;
import com.dozen.dozenworld.utils.L;
import com.dozen.dozenworld.utils.T;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Dozen on 19-7-22.
 * Describe:
 */
public class BannerActivity extends Activity implements ImageBannerViewGroup.ImageBannerLister {

    private ImageBannerViewGroup mGroup;
    private ImageBannerFrameLayout mDot;

    private int[] ide=new int[]{
            R.drawable.banner1,
            R.drawable.banner2,
            R.drawable.banner3
    };

    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_banner);

        mGroup=findViewById(R.id.image_group);
        mDot=findViewById(R.id.image_dot);

        DisplayMetrics dm =new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int width=dm.widthPixels;


        for (int i = 0; i < ide.length; i++) {
            ImageView imageView=new ImageView(this);
            imageView.setLayoutParams(new ViewGroup.LayoutParams(width,ViewGroup.LayoutParams.WRAP_CONTENT));
            imageView.setClickable(true);
            imageView.setBackgroundResource(ide[i]);
            mGroup.addView(imageView);
        }
        mGroup.setLister(this);

        List<Bitmap> list=new ArrayList<>();

        for (int i = 0; i < ide.length; i++) {
            Bitmap bitmap=BitmapFactory.decodeResource(getResources(),ide[i]);
            list.add(bitmap);
        }

        mDot.setWidth(width);
        mDot.addBitmaps(list);
        mDot.setDotLister(new ImageBannerFrameLayout.ImageBannerDotLister() {
            @Override
            public void clickImage(int pos) {
                T.showLongToast("dot:"+pos);
            }
        });

    }

    @Override
    public void clickImageIndex(int pos) {
        T.showLongToast("pos"+pos);
    }
}
