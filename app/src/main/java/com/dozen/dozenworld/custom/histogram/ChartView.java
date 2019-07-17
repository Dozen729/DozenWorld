package com.dozen.dozenworld.custom.histogram;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;

/**
 * Created by Dozen on 19-7-17.
 * Describe:
 */
public class ChartView extends BaseChartView {
    public ChartView(Context context) {
        this(context,null);
    }

    public ChartView(Context context, AttributeSet attrs) {
        this(context, attrs,-1);
    }

    public ChartView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void drawColumn(Canvas canvas, Paint mPaint) {
        Log.d("ChartView","test1");
        if (columnInfo==null)
            return;
        Log.d("ChartView","test2");
        mPaint.setStrokeWidth(1);
        float cellWidth=widthLength/divideSizeX;
        for (int i=0;i<columnInfo.length;i++){
            mPaint.setColor(columnInfo[i][1]);
            float leftTopY=originalY-heightLength*columnInfo[i][0]/maxValueY;
            canvas.drawRect(originalX+cellWidth*(i+1),leftTopY,originalX+cellWidth*(i+2)-2,originalY-2,mPaint);
        }
    }

    @Override
    protected void drawValueY(Canvas canvas, Paint mPaint) {
        float cellHeight=heightLength/divideSizeY;
        float cellValue=maxValueY/divideSizeY;
        for (int i=1;i<divideSizeY;i++){
            canvas.drawText(String.valueOf(i),originalX-30,originalY-cellHeight*i+10,mPaint);
        }
    }

    @Override
    protected void drawValueX(Canvas canvas, Paint mPaint) {
        mPaint.setColor(Color.RED);
        mPaint.setTextSize(12);
        mPaint.setFakeBoldText(true);

        float cellwidth=widthLength/divideSizeX;
        float cellValue=maxValueY/divideSizeX;
        for (int i=1;i<divideSizeX;i++){
            canvas.drawText(String.valueOf(i),cellwidth*i+originalX,originalY+30,mPaint);
        }
    }

    @Override
    protected void drawMarkY(Canvas canvas, Paint mPaint) {
        float cellHeight=heightLength/divideSizeY;
        for (int i=0;i<divideSizeY-1;i++){
            canvas.drawLine(originalX,originalY-cellHeight*(i+1),originalX+10,originalY-cellHeight*(i+1),mPaint);
        }
    }

    @Override
    protected void drawMarkX(Canvas canvas, Paint mPaint) {
        float cellWidth=widthLength/divideSizeX;
        for (int i=0;i<divideSizeX-1;i++){
            canvas.drawLine(cellWidth*(i+1)+originalX,originalY,cellWidth*(i+1)+originalX,originalY-10,mPaint);
        }
    }
}
