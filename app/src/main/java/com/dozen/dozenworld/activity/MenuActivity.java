package com.dozen.dozenworld.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import com.dozen.dozenworld.R;
import com.dozen.dozenworld.custom.menu.DropDownMenu;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Dozen on 19-7-18.
 * Describe:
 */
public class MenuActivity extends Activity {

    private DropDownMenu dropDownMenu;
    private String headers[]={"城市","年龄","性别","星座"};
    private List<View> popViews=new ArrayList<>();


    private String citys[]={"不限","武汉","北京","上海","成都","广州","深圳","重庆","天津","西安","南宁"};
    private String ages[]={"不限","18岁以下","18-22岁","23-26岁","27-30岁","31岁以上"};
    private String sexs[]={"不限","男","女"};
    private String constellations[]={"不限","白羊座","金牛座","双子座","巨蟹座","狮子座","处女座","天秤座","天蝎座","射手座","摩羯座","水瓶座","双鱼座"};
    private int imageIds[]={R.drawable.icon,R.drawable.icon,R.drawable.icon,R.drawable.icon};

    private int constellationPosition=0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        dropDownMenu=findViewById(R.id.dropDownMenu);

        initView();


    }

    private void initView() {
        ListView ivCity=new ListView(this);
        ivCity.setDividerHeight(0);

    }
}
