package com.dozen.dozenworld.custom;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by Dozen on 19-7-17.
 * Describe:
 */
public class SectorView extends View {
    private int width;
    private int height;

    private float originX;
    private float originY;

    private int[][] data;

    private Paint mPaint;
    private RectF round;
    private int distance=200;
    private float radius;


    public SectorView(Context context) {
        this(context,null);
    }

    public SectorView(Context context,AttributeSet attrs) {
        this(context, attrs,-1);
    }

    public SectorView(Context context,AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        initPaint();

    }

    public void setData(int[][] data) {
        this.data = data;
    }

    private void initPaint() {
        if (mPaint==null){
            mPaint=new Paint();
            mPaint.setStyle(Paint.Style.FILL);
            mPaint.setDither(true);
            mPaint.setStrokeWidth(1);
            mPaint.setColor(Color.BLUE);
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        width=getSize(100,widthMeasureSpec);
        height=getSize(100,heightMeasureSpec);

    }

    private int getSize(int defaultSize, int measureSpec) {
        int mySize = defaultSize;

        int mode = MeasureSpec.getMode(measureSpec);
        int size = MeasureSpec.getSize(measureSpec);

        switch (mode) {
            case MeasureSpec.UNSPECIFIED:
                mySize = dip2px(defaultSize);
                break;
            case MeasureSpec.AT_MOST:
                mySize = dip2px(size);
                break;
            case MeasureSpec.EXACTLY:
                mySize = size;
                break;
        }

        return mySize;
    }

    private int dip2px(float dpValue){
        float scale=this.getResources().getDisplayMetrics().density;
        return (int)(dpValue/scale+0.5f);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setAntiAlias(true);
        if (round==null){
            round=new RectF();
        }
        float x,y;
        if (width>height){
            y=distance;
            x=(width-(height-distance*2))/2;
            radius=(height-y*2)/2;
        }else {
            x=distance;
            y=(height-(width-distance*2))/2;
            radius=(width-x*2)/2;
        }

        originX=width/2;
        originY=height/2;

        round.set(x,y,width-x,height-y);

//        canvas.drawArc(round,0,80,true,mPaint);

        drawCircle(canvas,mPaint);
        drawTitle(canvas,mPaint);
    }

    private void drawTitle(Canvas canvas, Paint mPaint) {

        mPaint.setColor(Color.BLACK);
        mPaint.setStrokeWidth(3);
        mPaint.setAntiAlias(true);

    }

    private void drawCircle(Canvas canvas, Paint mPaint) {

        if (data==null){
            return;
        }

        float all=0,c=180;

        for (int i=0;i<data.length;i++){
            all+=data[i][0];
        }



        for (int i = 0; i < data.length; i++) {
            float a=(data[i][0]/all)*360;
            mPaint.setColor(data[i][1]);
            canvas.drawArc(round,c,a,true,mPaint);
            drawText(canvas,c+a/2);
            c+=a;
        }

    }

    private void drawText(Canvas canvas, double v) {
        v=v-180;

        mPaint.setStrokeWidth(3);
        mPaint.setTextSize(12);

        double x,y;
        float a=0,b=0;

        int pointx=0,pointy=0;

        if (v>0&&v<90){
            x=radius*Math.cos(v*2*Math.PI/360);//这里的Math.PI()是android api提供的方法，就是圆周率：2π；
            y=radius*Math.sin(v*2*Math.PI/360);

            if (x<0) x=-x;
            if (y<0) y=-y;

            a= (float) (originX-x);
            b= (float) (originY-y);
            canvas.drawLine(a,b,a-distance/10,b-distance/15,mPaint);
            canvas.drawLine(a-distance/10,b-distance/15,a-distance*4/5,b-distance/15,mPaint);
            pointx= (int) (a-distance*7/10);
            pointy= (int) b-distance/8;

        }else if (v>90&&v<180){
            v=180-v;
            x=radius*Math.cos(v*2*Math.PI/360);//这里的Math.PI()是android api提供的方法，就是圆周率：2π；
            y=radius*Math.sin(v*2*Math.PI/360);
            if (x<0) x=-x;
            if (y<0) y=-y;

            a= (float) (originX+x);
            b= (float) (originY-y);
//            canvas.drawLine(a,b,a+distance4/5,b,mPaint);
            canvas.drawLine(a,b,a+distance/10,b-distance/15,mPaint);
            canvas.drawLine(a+distance/10,b-distance/15,a+distance*4/5,b-distance/15,mPaint);
            pointx= (int) (a+distance*7/10);
            pointy= (int) b-distance/8;
        }else if (v>180&&v<270){
            v=v-180;
            x=radius*Math.cos(v*2*Math.PI/360);//这里的Math.PI()是android api提供的方法，就是圆周率：2π；
            y=radius*Math.sin(v*2*Math.PI/360);
            if (x<0) x=-x;
            if (y<0) y=-y;

            a= (float) (originX+x);
            b= (float) (originY+y);
//            canvas.drawLine(a,b,a+distance4/5,b,mPaint);
            canvas.drawLine(a,b,a+distance/10,b+distance/15,mPaint);
            canvas.drawLine(a+distance/10,b+distance/15,a+distance*4/5,b+distance/15,mPaint);
            pointx= (int) (a+distance*7/10);
            pointy= (int) b+distance/8;
        }else if (v>270&&v<360){
            v=360-v;
            x=radius*Math.cos(v*2*Math.PI/360);//这里的Math.PI()是android api提供的方法，就是圆周率：2π；
            y=radius*Math.sin(v*2*Math.PI/360);
            if (x<0) x=-x;
            if (y<0) y=-y;

            a= (float) (originX-x);
            b= (float) (originY+y);
//            canvas.drawLine(a,b,a-distance4/5,b,mPaint);
            canvas.drawLine(a,b,a-distance/10,b+distance/15,mPaint);
            canvas.drawLine(a-distance/10,b+distance/15,a-distance*4/5,b+distance/15,mPaint);
            pointx= (int) (a-distance*7/10);
            pointy= (int) b+distance/8;
        }
        mPaint.setColor(Color.BLACK);
        canvas.drawText("("+pointx+","+pointy+")",pointx,pointy-10,mPaint);
    }
}
