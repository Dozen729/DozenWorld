package com.dozen.dozenworld.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.dozen.dozenworld.R;
import com.dozen.dozenworld.adapter.DropMenuAdapter;
import com.dozen.dozenworld.custom.DropDownMenu;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Dozen on 19-7-18.
 * Describe:
 */
public class MenuActivity extends Activity {

    private DropDownMenu dropDownMenu;
    private String headers[]={"城市","年龄","性别","星座","学校"};
    private List<View> popViews=new ArrayList<>();


    private String citys[]={"不限","武汉","北京","上海","成都","广州","深圳","重庆","天津","西安","南宁",
            "玉林","桂林","四川","博白","凤山","东莞","澳门","香港","天津","哈尔滨"};
    private String ages[]={"不限","18岁以下","18-22岁","23-26岁","27-30岁","31岁以上"};
    private String sexs[]={"不限","男","女"};
    private String constellations[]={"不限","白羊座","金牛座","双子座","巨蟹座","狮子座","处女座","天秤座","天蝎座","射手座","摩羯座","水瓶座","双鱼座"};
    private String schools[]={"不限","桂林理工大学","深圳大学","上海对外经贸大学"};

    private int constellationPosition=0;

    private DropMenuAdapter cityAdapter;
    private DropMenuAdapter ageAdapter;
    private DropMenuAdapter sexAdapter;
    private DropMenuAdapter constellationAdapter;
    private DropMenuAdapter schoolAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        dropDownMenu=findViewById(R.id.dropDownMenu);

        initView();


    }

    @SuppressLint("ResourceType")
    private void initView() {

        RecyclerView ivCity=new RecyclerView(this);
        ivCity.setLayoutManager(new LinearLayoutManager(this));
        cityAdapter=new DropMenuAdapter(new ArrayList<String>(Arrays.asList(citys)));
        ivCity.setAdapter(cityAdapter);


        RecyclerView ivAge=new RecyclerView(this);
        ivAge.setLayoutManager(new LinearLayoutManager(this));
        ageAdapter=new DropMenuAdapter(new ArrayList<String>(Arrays.asList(ages)));
        ivAge.setAdapter(ageAdapter);

        RecyclerView ivSex=new RecyclerView(this);
        ivSex.setLayoutManager(new LinearLayoutManager(this));
        sexAdapter=new DropMenuAdapter(new ArrayList<String>(Arrays.asList(sexs)));
        ivSex.setAdapter(sexAdapter);

        View constellationView=getLayoutInflater().inflate(R.layout.item_constellation,null);
        RecyclerView rv=constellationView.findViewById(R.id.rv_constellation);
        TextView tvOk=constellationView.findViewById(R.id.tv_ok);
        rv.setLayoutManager(new GridLayoutManager(this,5));
        constellationAdapter=new DropMenuAdapter(new ArrayList<String>(Arrays.asList(constellations)));
        rv.setAdapter(constellationAdapter);

        RecyclerView ivSchool=new RecyclerView(this);
        ivSchool.setLayoutManager(new LinearLayoutManager(this));
        schoolAdapter=new DropMenuAdapter(new ArrayList<String>(Arrays.asList(schools)));
        ivSchool.setAdapter(schoolAdapter);


        cityAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                dropDownMenu.setContentView(citys[position]);
                dropDownMenu.closeMenu();
            }
        });
        ageAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                dropDownMenu.setContentView(ages[position]);
                dropDownMenu.closeMenu();
            }
        });
        sexAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                dropDownMenu.setContentView(sexs[position]);
                dropDownMenu.closeMenu();
            }
        });
        constellationAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                constellationPosition=position;
            }
        });
        tvOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dropDownMenu.setContentView(constellations[constellationPosition]);
                dropDownMenu.closeMenu();
            }
        });

        schoolAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                dropDownMenu.setContentView(schools[position]);
                dropDownMenu.closeMenu();
            }
        });


        popViews.add(ivCity);
        popViews.add(ivAge);
        popViews.add(ivSex);
        popViews.add(constellationView);
        popViews.add(ivSchool);


        TextView tv=new TextView(this);
        tv.setTextColor(Color.RED);
        tv.setGravity(Gravity.CENTER);
        tv.setTextSize(TypedValue.COMPLEX_UNIT_PX,200);
        tv.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.MATCH_PARENT));

        dropDownMenu.setDropDownMenu(Arrays.asList(headers),popViews,tv);

    }

    @Override
    public void onBackPressed() {
        if (dropDownMenu.isShowing()){
            dropDownMenu.closeMenu();
        }else {
            super.onBackPressed();
        }
    }
}
