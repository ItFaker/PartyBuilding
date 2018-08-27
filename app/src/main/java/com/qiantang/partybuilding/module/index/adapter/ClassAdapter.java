package com.qiantang.partybuilding.module.index.adapter;

import com.qiantang.partybuilding.BR;
import com.qiantang.partybuilding.adapter.BindingViewHolder;
import com.qiantang.partybuilding.adapter.EasyBindQuickAdapter;
import com.qiantang.partybuilding.modle.RxIndexClass;

import java.util.List;

/**
 * Created by zhaoyong bai on 2018/5/23.
 */
public class ClassAdapter extends EasyBindQuickAdapter<RxIndexClass> {
    public ClassAdapter(int layoutResId, List<RxIndexClass> data) {
        super(layoutResId, data);
    }

    @Override
    protected void easyConvert(BindingViewHolder holder, RxIndexClass item) {
        holder.getBinding().setVariable(BR.item, item);
        holder.getBinding().executePendingBindings();
    }
}
