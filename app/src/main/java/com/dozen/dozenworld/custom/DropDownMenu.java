package com.dozen.dozenworld.custom;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dozen.dozenworld.R;

import java.util.List;

/**
 * Created by Dozen on 19-7-18.
 * Describe:
 */
public class DropDownMenu extends LinearLayout {

    //顶部菜单布局
    private LinearLayout tabMenuView;
    //底部容器包含内容区域遮罩区域菜单弹出区域
    private FrameLayout containerView;
    //内容区域
    private View contentView;
    //遮罩区域
    private View maskView;
    //菜单弹出区域
    private FrameLayout popupMenuViews;

    //分割线颜色
    private  int dividerColor =0xffcccccc;
    //文本选中颜色
    private int textSelectColor = 0xff890c85;
    //文本未选中颜色
    private  int textUnSelectColor = 0xff111111;
    //遮罩颜色
    private  int maskColor=0x88888888;
    //水平分割线颜色
    private int underlineColor=0xffcccccc;

    //菜单背景颜色
    private int menuBackgroundColor=0xffffffff;

    //字体大小
    private int menuTextSize=14;
    //tab选中图标
    private int menuSelectedIcon;
    //tab未选中图标
    private int menuUnSelectedIcon;

    //菜单项被 选中位置 初始没有菜单 被 选中记为-1
    private int currentTabPosition=-1;


    public DropDownMenu(Context context) {
        this(context,null);
    }

    public DropDownMenu(Context context,AttributeSet attrs) {
        this(context, attrs,-1);
    }

    public DropDownMenu(Context context,AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setOrientation(VERTICAL);

        TypedArray a=context.obtainStyledAttributes(attrs, R.styleable.DropDownMenu);

        underlineColor=a.getColor(R.styleable.DropDownMenu_underlineColor,underlineColor);
        dividerColor=a.getColor(R.styleable.DropDownMenu_dividerColor,dividerColor);
        textSelectColor=a.getColor(R.styleable.DropDownMenu_textSelectedColor,textSelectColor);
        textUnSelectColor=a.getColor(R.styleable.DropDownMenu_textUnSelectedColor,textUnSelectColor);

        menuBackgroundColor=a.getColor(R.styleable.DropDownMenu_menuBackgroundColor,menuBackgroundColor);
        maskColor=a.getColor(R.styleable.DropDownMenu_maskColor,maskColor);

        menuTextSize=a.getDimensionPixelSize(R.styleable.DropDownMenu_menuTextSize,menuTextSize);
        menuSelectedIcon=a.getResourceId(R.styleable.DropDownMenu_menuSelectedIcon,menuSelectedIcon);
        menuUnSelectedIcon=a.getResourceId(R.styleable.DropDownMenu_menuUnSelectedIcon,menuUnSelectedIcon);

        a.recycle();

        initView(context);

    }

    private void initView(Context context) {
        //创建顶部菜单
        tabMenuView =new LinearLayout(context);
        LayoutParams lp=new LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.WRAP_CONTENT);
        tabMenuView.setOrientation(HORIZONTAL);
        tabMenuView.setLayoutParams(lp);
        addView(tabMenuView,0);

        //创建下划线
        View underlineView=new View(context);
        underlineView.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,dp2Px(2.0f)));
        underlineView.setBackgroundColor(underlineColor);
        addView(underlineView,1);

        //初始化containerView
        containerView=new FrameLayout(context);
        containerView.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.MATCH_PARENT));
        addView(containerView,2);


    }

    private int dp2Px(float value) {
        DisplayMetrics dm=getResources().getDisplayMetrics();
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,value,dm);
    }


    /*
    * 初始化DropDownMenu显示具体的内容
    * */
    public void setDropDownMenu(List<String> tabTextsList,List<View> popupViews,View contentView){
        this.contentView=contentView;
        if (tabTextsList.size()!=popupViews.size()){
            throw new IllegalArgumentException("tabTexts.size() should be equal popupView.size()");
        }

        for (int i = 0; i < tabTextsList.size(); i++) {
            addTab(tabTextsList,tabTextsList.get(i),i);
        }

        containerView.addView(contentView,0);

        maskView=new View(getContext());
        maskView.setLayoutParams(new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT,FrameLayout.LayoutParams.MATCH_PARENT));
        maskView.setBackgroundColor(maskColor);
        maskView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                closeMenu();
            }
        });
        maskView.setVisibility(GONE);
        containerView.addView(maskView,1);


        popupMenuViews=new FrameLayout(getContext());
        popupMenuViews.setVisibility(GONE);


        for (int i = 0; i < popupViews.size(); i++) {
            popupViews.get(i).setLayoutParams(new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT,FrameLayout.LayoutParams.WRAP_CONTENT));
            popupViews.get(i).setBackgroundColor(Color.WHITE);
            popupMenuViews.addView(popupViews.get(i),i);
        }

        containerView.addView(popupMenuViews,2);

    }


    private void addTab(List<String> tabTextsList, String s, int i) {
        final TextView tab=new TextView(getContext());
        tab.setSingleLine();
        tab.setEllipsize(TextUtils.TruncateAt.END);
        tab.setGravity(Gravity.CENTER);
        tab.setTextSize(TypedValue.COMPLEX_UNIT_PX,menuTextSize);
        tab.setLayoutParams(new LayoutParams(0,LayoutParams.WRAP_CONTENT,1.0f));
        tab.setTextColor(textUnSelectColor);
        tab.setCompoundDrawablesWithIntrinsicBounds(null,null,getResources().getDrawable(menuUnSelectedIcon),null);
        tab.setText(s);
        tab.setPadding(dp2Px(5),dp2Px(12),dp2Px(5),dp2Px(12));
        tab.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                switchMenu(tab);
            }
        });
        tabMenuView.addView(tab);

        //添加分割线
        if (i<tabTextsList.size()-1){
            View underlineView=new View(getContext());
            underlineView.setLayoutParams(new LayoutParams(dp2Px(1f),LayoutParams.MATCH_PARENT));
            underlineView.setBackgroundColor(dividerColor);
            tabMenuView.addView(underlineView);
        }

    }

    /*
    * 切换菜单
    * */
    private void switchMenu(TextView tab) {

        for (int i = 0; i < tabMenuView.getChildCount(); i=i+2) {
            if (tab==tabMenuView.getChildAt(i)){
                if (currentTabPosition==i){
                    //关闭菜单
                    closeMenu();
                }else {
                    //弹出菜单
                    if (currentTabPosition==-1){
                        //初始状态
                        popupMenuViews.setVisibility(VISIBLE);
                        popupMenuViews.setAnimation(AnimationUtils.loadAnimation(getContext(),R.anim.dd_menu_in));
                        maskView.setVisibility(VISIBLE);
                        maskView.setAnimation(AnimationUtils.loadAnimation(getContext(),R.anim.dd_mask_in));
                        popupMenuViews.getChildAt(i/2).setVisibility(VISIBLE);

                    }else {

                        popupMenuViews.getChildAt(i/2).setVisibility(VISIBLE);

                    }

                    currentTabPosition=i;
                    ((TextView)tabMenuView.getChildAt(i)).setTextColor(textSelectColor);
                    ((TextView)tabMenuView.getChildAt(i)).setCompoundDrawablesWithIntrinsicBounds(null,null,getResources().getDrawable(menuSelectedIcon),null);


                }
            }else {
                ((TextView)tabMenuView.getChildAt(i)).setTextColor(textUnSelectColor);
                ((TextView)tabMenuView.getChildAt(i)).setCompoundDrawablesWithIntrinsicBounds(null,null,getResources().getDrawable(menuUnSelectedIcon),null);
                popupMenuViews.getChildAt(i/2).setVisibility(GONE);

            }

        }

    }


    public void closeMenu() {
        if (currentTabPosition!=-1){
            ((TextView)tabMenuView.getChildAt(currentTabPosition)).setTextColor(textUnSelectColor);
            ((TextView)tabMenuView.getChildAt(currentTabPosition)).setCompoundDrawablesWithIntrinsicBounds(null,null,getResources().getDrawable(menuUnSelectedIcon),null);
            popupMenuViews.setVisibility(GONE);
            popupMenuViews.setAnimation(AnimationUtils.loadAnimation(getContext(),R.anim.dd_menu_out));

            maskView.setVisibility(GONE);
            maskView.setAnimation(AnimationUtils.loadAnimation(getContext(),R.anim.dd_mask_out));

            currentTabPosition=-1;

        }
    }

    public boolean isShowing(){
        return currentTabPosition!=-1;
    }

    public void setContentView(String city) {
        ((TextView)contentView).setText(city);
    }
}
