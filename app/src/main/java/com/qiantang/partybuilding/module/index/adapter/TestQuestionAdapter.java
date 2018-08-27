package com.qiantang.partybuilding.module.index.adapter;

import com.qiantang.partybuilding.BR;
import com.qiantang.partybuilding.R;
import com.qiantang.partybuilding.adapter.BindingViewHolder;
import com.qiantang.partybuilding.adapter.EasyBindQuickAdapter;
import com.qiantang.partybuilding.modle.RxQuestion;

import java.util.List;

/**
 * Created by zhaoyong bai on 2018/6/15.
 */
public class TestQuestionAdapter extends EasyBindQuickAdapter<RxQuestion> {
    public TestQuestionAdapter(int layoutResId) {
        super(layoutResId);
    }

    public TestQuestionAdapter(int layoutResId, List<RxQuestion> data) {
        super(layoutResId, data);
    }

    @Override
    protected void easyConvert(BindingViewHolder holder, RxQuestion item) {
        holder.getBinding().setVariable(BR.item,item);
        holder.getBinding().executePendingBindings();
        holder.addOnClickListener(R.id.chb);
    }
}
