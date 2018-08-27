package com.qiantang.partybuilding.module.assistant.adapter;

import com.qiantang.partybuilding.adapter.BindingViewHolder;
import com.qiantang.partybuilding.adapter.EasyBindQuickAdapter;
import com.qiantang.partybuilding.modle.RxPartyFee;
import com.qiantang.partybuilding.BR;

/**
 * Created by zhaoyong bai on 2018/5/29.
 */
public class PartyFeeAdapter extends EasyBindQuickAdapter<RxPartyFee> {
    public PartyFeeAdapter(int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void easyConvert(BindingViewHolder holder, RxPartyFee item) {
        holder.getBinding().setVariable(BR.item,item);
        holder.getBinding().executePendingBindings();
    }
}
