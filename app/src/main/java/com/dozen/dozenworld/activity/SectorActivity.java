package com.dozen.dozenworld.activity;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;

import com.dozen.dozenworld.R;
import com.dozen.dozenworld.custom.SectorView;

/**
 * Created by Dozen on 19-7-17.
 * Describe:
 */
public class SectorActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sector);


        SectorView circleView=findViewById(R.id.sector);

        int[][] datas=new int[][]{
                {2,Color.BLACK},
                {4,Color.DKGRAY},
                {6,Color.GRAY},
                {2,Color.RED},
                {8,Color.GREEN},
                {13,Color.BLUE},
                {1,Color.YELLOW},
                {7,Color.CYAN},
                {5,Color.MAGENTA},
                {8,Color.GREEN},
                {13,Color.BLUE},
                {1,Color.YELLOW},
                {7,Color.CYAN},
                {5,Color.MAGENTA},
                {2,Color.RED},
                {8,Color.GREEN},
                {13,Color.BLUE},
                {1,Color.YELLOW},
                {7,Color.CYAN},
                {5,Color.MAGENTA},
                {8,Color.GREEN},
                {13,Color.BLUE},
                {1,Color.YELLOW},
                {7,Color.CYAN},
                {5,Color.MAGENTA},
        };

        circleView.setData(datas);
    }
}
