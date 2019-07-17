package com.dozen.dozenworld.activity;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;

import com.dozen.dozenworld.R;
import com.dozen.dozenworld.custom.histogram.ChartView;

/**
 * Created by Dozen on 19-7-17.
 * Describe:
 */
public class ChartActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chart);
        ChartView chartView = findViewById(R.id.one_chart);

        chartView.setMaxValueX(18, 18);
        chartView.setMaxValueY(12, 12);

        int columnInfo[][] = new int[][]{
                {10, Color.BLUE},
                {9, Color.LTGRAY},
                {8, Color.RED},
                {7, Color.BLUE},
                {6, Color.YELLOW},
                {5, Color.LTGRAY},
                {4, Color.BLUE},
                {4, Color.RED},
                {5, Color.BLUE},
                {6, Color.YELLOW},
                {7, Color.LTGRAY},
                {8, Color.BLUE},
                {9, Color.YELLOW},
                {10, Color.LTGRAY},};
        chartView.setColumnInfo(columnInfo);

        chartView.setTitle("hello dozen");
    }
}
