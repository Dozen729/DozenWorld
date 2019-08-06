package com.dozen.dozenworld.utils;

import com.dozen.dozenworld.activity.BannerActivity;
import com.dozen.dozenworld.activity.ChartActivity;
import com.dozen.dozenworld.activity.CleanActivity;
import com.dozen.dozenworld.activity.HeadActivity;
import com.dozen.dozenworld.activity.HelloActivity;
import com.dozen.dozenworld.activity.MainActivity;
import com.dozen.dozenworld.activity.MenuActivity;
import com.dozen.dozenworld.activity.MusicActivity;
import com.dozen.dozenworld.activity.SectorActivity;
import com.dozen.dozenworld.activity.SuspendActivity;
import com.dozen.dozenworld.bean.StartItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Dozen on 19-7-17.
 * Describe:
 */
public class Constant {

    public static final int TYPE_TITLE = 0;
    public static final int TYPE_CHILD = 1;


    private static String[] mList=new String[]{
        "MAIN","HELLO","CHART","SECTOR","HEAD",

        "MENU","BANNER","MUSIC","SUSPEND","CLEAN"
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
                case "BANNER":
                    item.setCla(BannerActivity.class);
                    item.setName("轮播图");
                    break;
                case "MUSIC":
                    item.setCla(MusicActivity.class);
                    item.setName("音乐播放");
                    break;
                case "SUSPEND":
                    item.setCla(SuspendActivity.class);
                    item.setName("悬浮球");
                    break;
                case "CLEAN":
                    item.setCla(CleanActivity.class);
                    item.setName("清理垃圾");
                    break;
                    default:continue;
            }

            list.add(item);
        }



        return list;
    }
}
