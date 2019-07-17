package com.dozen.dozenworld.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.dozen.dozenworld.R;
import com.dozen.dozenworld.bean.StartItem;

import java.util.List;

/**
 * Created by Dozen on 19-7-17.
 * Describe:
 */
public class StartAdapter extends BaseQuickAdapter<StartItem,BaseViewHolder> {

    public StartAdapter(List<StartItem> data) {
        super(R.layout.item_start,data);
    }

    @Override
    protected void convert(BaseViewHolder helper, StartItem item) {
        helper.setText(R.id.tv_id, item.getID()+"");
        helper.setText(R.id.tv_name,item.getName());
    }
}
