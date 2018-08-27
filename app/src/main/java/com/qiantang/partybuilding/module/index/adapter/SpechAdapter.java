package com.qiantang.partybuilding.module.index.adapter;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.facebook.drawee.view.SimpleDraweeView;
import com.qiantang.partybuilding.BR;
import com.qiantang.partybuilding.R;
import com.qiantang.partybuilding.adapter.BindingViewHolder;
import com.qiantang.partybuilding.adapter.EasyBindQuickAdapter;
import com.qiantang.partybuilding.config.Config;
import com.qiantang.partybuilding.modle.RxIndexNews;
import com.qiantang.partybuilding.modle.RxIndexSpeak;

import java.util.List;

/**
 * Created by zhaoyong bai on 2018/5/23.
 */
public class SpechAdapter extends EasyBindQuickAdapter<RxIndexSpeak> {

    public SpechAdapter(int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void easyConvert(BindingViewHolder holder, RxIndexSpeak item) {
        holder.getBinding().setVariable(BR.item, item);
        holder.getBinding().executePendingBindings();
        ((SimpleDraweeView)holder.getBinding().getRoot().findViewById(R.id.sdv)).setImageURI(Config.IMAGE_HOST+item.getImg());
    }
}
