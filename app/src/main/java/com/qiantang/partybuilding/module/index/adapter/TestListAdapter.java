package com.qiantang.partybuilding.module.index.adapter;

import com.qiantang.partybuilding.BR;
import com.qiantang.partybuilding.R;
import com.qiantang.partybuilding.adapter.BindingViewHolder;
import com.qiantang.partybuilding.adapter.EasyBindQuickAdapter;
import com.qiantang.partybuilding.modle.RxTest;

/**
 * Created by zhaoyong bai on 2018/6/14.
 */
public class TestListAdapter extends EasyBindQuickAdapter<RxTest> {
    public TestListAdapter(int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void easyConvert(BindingViewHolder holder, RxTest item) {
        holder.getBinding().setVariable(BR.item, item);
        holder.getBinding().executePendingBindings();
        holder.addOnClickListener(R.id.btn_test)
                .addOnClickListener(R.id.tv_check);
    }
}
