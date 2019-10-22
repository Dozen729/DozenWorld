package com.dozen.dozenworld.activity;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import com.dozen.dozenworld.R;
import com.dozen.dozenworld.adapter.LooperAdapter;
import com.dozen.dozenworld.adapter.LooperLayoutManager;
import com.dozen.dozenworld.bean.LooperItem;
import com.dozen.dozenworld.http.GetRequest_Interface;
import com.dozen.dozenworld.utils.L;
import com.google.gson.Gson;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class LooperActivity extends AppCompatActivity {

    private RecyclerView rlShow;
    private LooperAdapter looperAdapter;
    private List<LooperItem> looperItemList;
    private int testId=1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_looper);

        rlShow=findViewById(R.id.rl_looper);

        looperItemList=new ArrayList<>();

        for (int i = 0; i < 3; i++) {
            LooperItem looperItem=new LooperItem();

            Bitmap bitmap= BitmapFactory.decodeResource(getResources(),R.drawable.icon);

            looperItem.setBitmap(bitmap);
            looperItem.setName("Dozen"+i);

            looperItemList.add(looperItem);

        }


        looperAdapter=new LooperAdapter(this,looperItemList);
        LooperLayoutManager layoutManager = new LooperLayoutManager();
        layoutManager.setLooperEnable(true);
        rlShow.setLayoutManager(layoutManager);
        rlShow.setAdapter(looperAdapter);


        Button btnAdd=findViewById(R.id.btn_looper_add);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LooperItem looperItem=new LooperItem();
                Bitmap bitmap= BitmapFactory.decodeResource(getResources(),R.drawable.icon);

                looperItem.setBitmap(bitmap);

                int a= (int) (Math.random()*1000);

                looperItem.setName("Dozen"+a);

                looperItemList.add(0,looperItem);
                looperAdapter.notifyDataSetChanged();
            }
        });

        Button btnNet=findViewById(R.id.btn_looper_add_net);
        btnNet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addData(testId++);
            }
        });


    }


    private void addData(int id){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://gank.io/")
                .client(new OkHttpClient.Builder().build())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();

        GetRequest_Interface service = retrofit.create(GetRequest_Interface.class);

        Call<ResponseBody> call = service.getGankFuLi("10/"+id);

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                String s = null;
                try {
                    s = response.body().string();
                    Bitmap bitmap= BitmapFactory.decodeResource(getResources(),R.drawable.icon);
                    try {
                        JSONObject jsonObject=new JSONObject(s);
                        JSONArray jsonArray=jsonObject.getJSONArray("results");

                        for (int i = 0; i < jsonArray.length(); i++) {

                            JSONObject jo= (JSONObject) jsonArray.get(i);
                            LooperItem looperItem=new LooperItem();

                            looperItem.setName(jo.getString("createdAt"));
                            looperItem.setPicture(jo.getString("url"));


                            looperItem.setBitmap(bitmap);

                            looperItemList.add(0,looperItem);

                            L.d(jo.getString("who"));

                        }

                        handler.sendEmptyMessage(0);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                } catch (IOException e) {
                    e.printStackTrace();
                }
                L.d(s);
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }

    @SuppressLint("HandlerLeak")
    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            looperAdapter.notifyDataSetChanged();

        }
    };

}
