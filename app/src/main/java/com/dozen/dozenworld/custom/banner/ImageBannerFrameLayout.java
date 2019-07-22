package com.dozen.dozenworld.custom.banner;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Build;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.dozen.dozenworld.R;

import java.util.List;

/**
 * Created by Dozen on 19-7-22.
 * Describe:
 */
public class ImageBannerFrameLayout extends FrameLayout implements ImageBannerViewGroup.ImageBannerSelect,ImageBannerViewGroup.ImageBannerLister {

    private ImageBannerViewGroup imageBannerViewGroup;
    private LinearLayout linearLayout;
    private int width=0;

    public void setWidth(int width) {
        this.width = width;
    }

    private ImageBannerDotLister dotLister;

    public void setDotLister(ImageBannerDotLister dotLister) {
        this.dotLister = dotLister;
    }

    @Override
    public void clickImageIndex(int pos) {
        dotLister.clickImage(pos);
    }

    public interface ImageBannerDotLister{
        void clickImage(int pos);
    }

    public ImageBannerFrameLayout(Context context) {
        this(context,null);
    }

    public ImageBannerFrameLayout( Context context, AttributeSet attrs) {
        this(context, attrs,-1);
    }

    public ImageBannerFrameLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        initImageGroup();
        initDot();

    }

    private void initDot() {
        linearLayout=new LinearLayout(getContext());
        linearLayout.setLayoutParams(new FrameLayout.LayoutParams(LayoutParams.MATCH_PARENT,40));

        linearLayout.setOrientation(LinearLayout.HORIZONTAL);
        linearLayout.setGravity(Gravity.CENTER);
        linearLayout.setBackgroundColor(Color.RED);
        addView(linearLayout);

        FrameLayout.LayoutParams layoutParams= (LayoutParams) linearLayout.getLayoutParams();
        layoutParams.gravity=Gravity.BOTTOM;

        linearLayout.setLayoutParams(layoutParams);

        if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.HONEYCOMB){
            linearLayout.setAlpha(0.5f);
        }else {
            linearLayout.getBackground().setAlpha(100);
        }

    }

    private void initImageGroup() {
        imageBannerViewGroup=new ImageBannerViewGroup(getContext());

        FrameLayout.LayoutParams lp=new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT,FrameLayout.LayoutParams.WRAP_CONTENT);
        imageBannerViewGroup.setLayoutParams(lp);
        imageBannerViewGroup.setImageBannerSelect(this);
        imageBannerViewGroup.setLister(this);
        addView(imageBannerViewGroup);

    }


    public void addBitmaps(List<Bitmap> list) {
        for (int i = 0; i < list.size(); i++) {
            Bitmap bitmap=list.get(i);
            addBitmapToImageGroup(bitmap);
            addDot();
        }
    }

    private void addDot() {
        ImageView imageView=new ImageView(getContext());
        LinearLayout.LayoutParams lp=new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT);
        lp.setMargins(5,5,5,5);
        imageView.setLayoutParams(lp);

        imageView.setImageResource(R.drawable.dot_normal);
        linearLayout.addView(imageView);

    }

    private void addBitmapToImageGroup(Bitmap bitmap) {

        ImageView imageView=new ImageView(getContext());
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        imageView.setLayoutParams(new ViewGroup.LayoutParams(width,ViewGroup.LayoutParams.WRAP_CONTENT));
        imageView.setImageBitmap(bitmap);
        imageBannerViewGroup.addView(imageView);
    }

    @Override
    public void selectIndex(int index) {
        int count=linearLayout.getChildCount();
        for (int i = 0; i < count; i++) {
            ImageView iv= (ImageView) linearLayout.getChildAt(i);
            if (i==index){
                iv.setImageResource(R.drawable.dot_select);
            }else {
                iv.setImageResource(R.drawable.dot_normal);
            }
        }
    }
}
