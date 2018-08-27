package com.qiantang.partybuilding.module.assistant.adapter;

import com.facebook.drawee.view.SimpleDraweeView;
import com.qiantang.partybuilding.R;
import com.qiantang.partybuilding.adapter.BindingViewHolder;
import com.qiantang.partybuilding.adapter.EasyBindQuickAdapter;
import com.qiantang.partybuilding.config.Config;
import com.qiantang.partybuilding.modle.RxAdviseRecord;
import com.qiantang.partybuilding.BR;

/**
 * Created by zhaoyong bai on 2018/6/1.
 */
public class AdviseRecordAdapter extends EasyBindQuickAdapter<RxAdviseRecord> {
    public AdviseRecordAdapter(int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void easyConvert(BindingViewHolder holder, RxAdviseRecord item) {
        holder.getBinding().setVariable(BR.item, item);
        holder.getBinding().executePendingBindings();
        ((SimpleDraweeView)holder.getBinding().getRoot().findViewById(R.id.sdv)).setImageURI(Config.IMAGE_HOST+item.getAvatar());
    }
}
