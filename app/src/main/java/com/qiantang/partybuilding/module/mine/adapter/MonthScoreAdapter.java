package com.qiantang.partybuilding.module.mine.adapter;

import com.android.databinding.library.baseAdapters.BR;
import com.qiantang.partybuilding.adapter.BindingViewHolder;
import com.qiantang.partybuilding.adapter.EasyBindQuickAdapter;
import com.qiantang.partybuilding.modle.RxMonthScore;

/**
 * Created by zhaoyong bai on 2018/5/22.
 */
public class MonthScoreAdapter extends EasyBindQuickAdapter<RxMonthScore> {
    public MonthScoreAdapter(int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void easyConvert(BindingViewHolder holder, RxMonthScore item) {
        holder.getBinding().setVariable(BR.item,item);
        holder.getBinding().executePendingBindings();
    }
}
