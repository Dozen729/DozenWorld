package com.dozen.dozenworld;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.dozen.dozenworld.adapter.StartAdapter;
import com.dozen.dozenworld.bean.StartItem;
import com.dozen.dozenworld.rxpermissions2.RxPermissions;
import com.dozen.dozenworld.utils.Constant;

import java.util.List;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * Created by Dozen on 19-7-16.
 * Describe:
 */
@SuppressLint("Registered")
public class StartActivity extends AppCompatActivity {

    private RecyclerView rv;
    private List<StartItem> mData;
    private RxPermissions rxPermissions;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        rxPermissions=new RxPermissions(this);
        rxPermissions.setLogging(true);

        rxPermissions.request(Manifest.permission.INTERNET).subscribe(new Observer<Boolean>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(Boolean aBoolean) {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        });

        mData=Constant.getDataList();

        rv=findViewById(R.id.rv_start);

        GridLayoutManager layoutManager=new GridLayoutManager(getApplicationContext(),5);

        rv.setLayoutManager(layoutManager);

        StartAdapter startAdapter=new StartAdapter(mData);
        rv.setAdapter(startAdapter);
        startAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                StartItem item=mData.get(position);
                Intent intent=new Intent();
                intent.setClass(StartActivity.this,item.getCla());

                Bundle bundle=new Bundle();
                bundle.putSerializable(item.getName(),item);

                intent.putExtras(bundle);

                startActivity(intent);

            }
        });


    }
}
