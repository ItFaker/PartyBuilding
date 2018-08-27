package com.qiantang.partybuilding.module.index.adapter;

import com.qiantang.partybuilding.BR;
import com.qiantang.partybuilding.adapter.BindingViewHolder;
import com.qiantang.partybuilding.adapter.EasyBindQuickAdapter;
import com.qiantang.partybuilding.modle.RxRankBranchList;
import com.qiantang.partybuilding.modle.RxRankPersonal;

/**
 * Created by zhaoyong bai on 2018/5/28.
 */
public class RankAdapter extends EasyBindQuickAdapter<RxRankBranchList> {
    public RankAdapter(int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void easyConvert(BindingViewHolder holder, RxRankBranchList item) {
        holder.getBinding().setVariable(BR.item,item);
        holder.getBinding().executePendingBindings();
    }
}
