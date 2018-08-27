package com.qiantang.partybuilding.module.mine.adapter;

import com.qiantang.partybuilding.BR;
import com.qiantang.partybuilding.adapter.BindingViewHolder;
import com.qiantang.partybuilding.adapter.EasyBindQuickAdapter;
import com.qiantang.partybuilding.modle.RxMonthScore;
import com.qiantang.partybuilding.modle.RxTotalScore;

/**
 * Created by zhaoyong bai on 2018/5/22.
 */
public class TotalScoreAdapter extends EasyBindQuickAdapter<RxTotalScore> {
    public TotalScoreAdapter(int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void easyConvert(BindingViewHolder holder, RxTotalScore item) {
        holder.getBinding().setVariable(BR.item,item);
        holder.getBinding().executePendingBindings();
    }
}
