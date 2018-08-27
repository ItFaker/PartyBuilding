package com.qiantang.partybuilding.module.index.adapter;

import com.qiantang.partybuilding.BR;
import com.qiantang.partybuilding.adapter.BindingViewHolder;
import com.qiantang.partybuilding.adapter.EasyBindQuickAdapter;
import com.qiantang.partybuilding.modle.RxRankPersonal;
import com.qiantang.partybuilding.modle.RxRankPersonalList;

/**
 * Created by zhaoyong bai on 2018/5/28.
 */
public class RankPersonalAdapter extends EasyBindQuickAdapter<RxRankPersonalList> {
    public RankPersonalAdapter(int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void easyConvert(BindingViewHolder holder, RxRankPersonalList item) {
        holder.getBinding().setVariable(BR.item,item);
        holder.getBinding().executePendingBindings();
    }
}
