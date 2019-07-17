package com.dozen.dozenworld.custom.histogram;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.dozen.dozenworld.R;
import com.dozen.dozenworld.utils.L;

/**
 * Created by Dozen on 19-7-17.
 * Describe:
 */
public abstract class BaseChartView extends View {
    private Paint mPaint;


    private int width;
    private int height;

    private int paddingX=100;
    private int paddingY=150;

    public int originalX=100;
    public int originalY=1200;

    public float maxValueX=900;
    public float maxValueY=700;

    public int divideSizeX=9;
    public int divideSizeY=7;

    public int columnInfo[][];

    public int widthLength;
    public int heightLength;

    private int strokeWidth=2;

    private String title;
    private String xName;
    private String yName;
    private int titleSize;



    public BaseChartView(Context context) {
        this(context,null);
    }

    public BaseChartView(Context context, AttributeSet attrs) {
        this(context, attrs,-1);
    }

    public BaseChartView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        @SuppressLint("Recycle") TypedArray typedArray=context.obtainStyledAttributes(attrs, R.styleable.BaseChartView);
        title=typedArray.getString(R.styleable.BaseChartView_title);
        xName=typedArray.getString(R.styleable.BaseChartView_xName);
        yName=typedArray.getString(R.styleable.BaseChartView_yName);
        titleSize= (int) typedArray.getDimension(R.styleable.BaseChartView_titleSize,12);

        initPaint(context);

    }

    private void initPaint(Context context) {

        if (mPaint==null){
            mPaint=new Paint();
            mPaint.setAntiAlias(true);
            mPaint.setDither(true);
        }
    }

    public void setMaxValueX(float maxValueX,int divideSizeX) {
        this.maxValueX = maxValueX;
        this.divideSizeX=divideSizeX;
    }

    public void setMaxValueY(float maxValueY,int divideSizeY) {
        this.maxValueY = maxValueY;
        this.divideSizeY=divideSizeY;
    }

    public void setColumnInfo(int[][] columnInfo) {
        this.columnInfo = columnInfo;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setxName(String xName) {
        this.xName = xName;
    }

    public void setyName(String yName) {
        this.yName = yName;
    }

    public void setTitleSize(int titleSize) {
        this.titleSize = titleSize;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        width=getSize(500,widthMeasureSpec);
        height=getSize(500,heightMeasureSpec);
        L.d(""+width+"--"+height);

        originalX=paddingX;
        originalY=height-paddingY;

        widthLength=width-paddingX*2;
        heightLength=height-paddingY*2;

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

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        drawOriginal(canvas,mPaint);
        drawX(canvas,mPaint);
        drawY(canvas,mPaint);
        drawMarkX(canvas,mPaint);
        drawMarkY(canvas,mPaint);
        drawArrowsX(canvas,mPaint);
        drawArrowsY(canvas,mPaint);
        drawValueX(canvas,mPaint);
        drawValueY(canvas,mPaint);
        drawColumn(canvas,mPaint);
        drawTitle(canvas,mPaint);

    }

    private void drawOriginal(Canvas canvas, Paint mPaint) {
        canvas.drawCircle(originalX,originalY,strokeWidth,mPaint);
    }

    private void drawTitle(Canvas canvas, Paint mPaint) {
        if (title!=null){
            mPaint.setColor(Color.BLUE);
            mPaint.setStrokeWidth(strokeWidth);
            mPaint.setTextSize(titleSize);
            mPaint.setFakeBoldText(true);
            canvas.drawText(title,width/2-(mPaint.measureText(title)/2),originalY+100,mPaint);
        }
    }

    protected abstract void drawColumn(Canvas canvas, Paint mPaint);

    protected abstract void drawValueY(Canvas canvas, Paint mPaint);

    protected abstract void drawValueX(Canvas canvas, Paint mPaint);

    private void drawArrowsY(Canvas canvas, Paint mPaint) {

        mPaint.setColor(Color.BLACK);
        mPaint.setStyle(Paint.Style.FILL);
        Path path =new Path();
        path.moveTo(originalX,height-originalY-30);
        path.lineTo(originalX-10,height-originalY);
        path.lineTo(originalX+10,height-originalY);
        path.close();
        canvas.drawPath(path,mPaint);
        canvas.drawText(yName,originalX-50,height-originalY-30,mPaint);

    }

    private void drawArrowsX(Canvas canvas, Paint mPaint) {

        mPaint.setColor(Color.BLACK);
        mPaint.setStyle(Paint.Style.FILL);
        Path path =new Path();
        path.moveTo(width-originalX+30,originalY);
        path.lineTo(width-originalX,originalY-10);
        path.lineTo(width-originalX,originalY+10);
        path.close();
        canvas.drawPath(path,mPaint);
        canvas.drawText(xName,width-originalX,originalY+30,mPaint);

    }

    protected abstract void drawMarkY(Canvas canvas, Paint mPaint);

    protected abstract void drawMarkX(Canvas canvas, Paint mPaint);


    protected void drawY(Canvas canvas, Paint mPaint) {
        canvas.drawLine(originalX,originalY,originalX,height-originalY,mPaint);
    }

    protected void drawX(Canvas canvas, Paint mPaint) {
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setColor(Color.BLACK);
        mPaint.setStrokeWidth(5);
        mPaint.setAntiAlias(true);
        canvas.drawLine(originalX,originalY,width-originalX,originalY,mPaint);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
    }
}
