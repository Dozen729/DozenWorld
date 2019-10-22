package com.dozen.dozenworld.adapter;

import android.content.Context;
import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.dozen.dozenworld.R;
import com.dozen.dozenworld.bean.LooperItem;

import java.util.List;

/**
 * Created by Hugo on 19-10-22.
 * Describe:
 */
public class LooperAdapter extends BaseQuickAdapter<LooperItem, BaseViewHolder> {

    private Context context;

    public LooperAdapter(Context context,@Nullable List<LooperItem> data) {
        super(R.layout.item_looper,data);
        this.context=context;
    }

    @Override
    protected void convert(BaseViewHolder helper, LooperItem item) {

        if (item.getPicture()==null){
            helper.setImageBitmap(R.id.iv_looper_pic,item.getBitmap());
        }else {
            Glide.with(context).load(item.getPicture()).into((ImageView) helper.getView(R.id.iv_looper_pic));
        }


        helper.setText(R.id.tv_looper_name,item.getName());

    }
}
