package com.qiantang.partybuilding.module.assistant.adapter;

import com.qiantang.partybuilding.BR;
import com.qiantang.partybuilding.adapter.BindingViewHolder;
import com.qiantang.partybuilding.adapter.EasyBindQuickAdapter;
import com.qiantang.partybuilding.modle.RxMsg;

/**
 * Created by zhaoyong bai on 2018/5/28.
 */
public class MsgAdapter extends EasyBindQuickAdapter<RxMsg> {
    public MsgAdapter(int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void easyConvert(BindingViewHolder holder, RxMsg item) {
        holder.getBinding().setVariable(BR.item, item);
        holder.getBinding().executePendingBindings();
    }
}
