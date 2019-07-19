package com.dozen.dozenworld.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.dozen.dozenworld.R;

import java.util.List;

/**
 * Created by Dozen on 19-7-19.
 * Describe:
 */
public class DropMenuAdapter extends BaseQuickAdapter<String,BaseViewHolder> {

    public DropMenuAdapter(@Nullable List<String> data) {
        super(R.layout.item_menu_one,data);
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {
        helper.setText(R.id.tv_menu_name,item);
    }
}
