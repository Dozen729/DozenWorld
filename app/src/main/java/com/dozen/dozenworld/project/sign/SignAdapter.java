package com.dozen.dozenworld.project.sign;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.dozen.dozenworld.R;

import java.util.List;

/**
 * Created by Hugo on 19-8-22.
 * Describe:
 */
public class SignAdapter extends BaseQuickAdapter<AppInfo,BaseViewHolder> {


    public SignAdapter(@Nullable List<AppInfo> data) {
        super(R.layout.item_sign,data);
    }

    @Override
    protected void convert(BaseViewHolder baseViewHolder, AppInfo appInfo) {

        baseViewHolder.setImageDrawable(R.id.iv_sign_item_picture,appInfo.getAppIcon());
        baseViewHolder.setText(R.id.tv_sign_item_name,appInfo.getAppName());
        baseViewHolder.setText(R.id.tv_sign_item_package,appInfo.getPackname());
    }
}
