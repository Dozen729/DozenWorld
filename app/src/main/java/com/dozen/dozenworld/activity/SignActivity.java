package com.dozen.dozenworld.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.dozen.dozenworld.R;
import com.dozen.dozenworld.project.sign.AppInfo;
import com.dozen.dozenworld.project.sign.SignAdapter;
import com.dozen.dozenworld.project.sign.SignModel;

import java.util.List;

/**
 * Created by Hugo on 19-8-22.
 * Describe:
 */
public class SignActivity extends Activity {

    private TextView tvShow;
    private RecyclerView rvAppShow;
    private List<AppInfo> appList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_sign);
        super.onCreate(savedInstanceState);

        tvShow=findViewById(R.id.tv_sign);
        rvAppShow=findViewById(R.id.rv_sign);

        appList=SignModel.getAllPrograms(this);

        LinearLayoutManager layoutManager=new LinearLayoutManager(this);
        rvAppShow.setLayoutManager(layoutManager);

        SignAdapter signAdapter=new SignAdapter(appList);
        rvAppShow.setAdapter(signAdapter);

        signAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter baseQuickAdapter, View view, int i) {
                String sign=SignModel.getRawSignature(SignActivity.this,appList.get(i).getPackname());
                tvShow.setText(sign);

                Log.d("Dozen",sign);
            }
        });


    }
}
