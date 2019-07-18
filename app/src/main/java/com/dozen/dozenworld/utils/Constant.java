package com.dozen.dozenworld.utils;

import com.dozen.dozenworld.activity.ChartActivity;
import com.dozen.dozenworld.activity.HeadActivity;
import com.dozen.dozenworld.activity.HelloActivity;
import com.dozen.dozenworld.activity.MainActivity;
import com.dozen.dozenworld.activity.MenuActivity;
import com.dozen.dozenworld.activity.SectorActivity;
import com.dozen.dozenworld.bean.StartItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Dozen on 19-7-17.
 * Describe:
 */
public class Constant {

    private static String[] mList=new String[]{
        "MAIN","HELLO","CHART","SECTOR","HEAD",

        "MENU",
    };

    public static List<StartItem> getDataList(){
        List<StartItem> list=new ArrayList<>();

        for (int i = 0; i < mList.length; i++) {

            StartItem item=new StartItem();
            item.setID(i+1);
            item.setTitle(mList[i]);

            switch (mList[i]){
                case "MAIN":
                    item.setCla(MainActivity.class);
                    item.setName("Hello C++");
                    break;
                case "HELLO":
                    item.setCla(HelloActivity.class);
                    item.setName("Hello world");
                    break;
                case "CHART":
                    item.setCla(ChartActivity.class);
                    item.setName("柱状图");
                    break;
                case "SECTOR":
                    item.setCla(SectorActivity.class);
                    item.setName("饼状图");
                    break;
                case "HEAD":
                    item.setCla(HeadActivity.class);
                    item.setName("圆形头像");
                    break;
                case "MENU":
                    item.setCla(MenuActivity.class);
                    item.setName("下拉菜单");
                    break;
                    default:continue;
            }

            list.add(item);
        }



        return list;
    }
}
