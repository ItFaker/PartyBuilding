package com.qiantang.partybuilding.module.index.adapter;

import com.qiantang.partybuilding.BR;
import com.qiantang.partybuilding.R;
import com.qiantang.partybuilding.adapter.BindingViewHolder;
import com.qiantang.partybuilding.adapter.EasyBindQuickAdapter;
import com.qiantang.partybuilding.modle.RxQuestion;
import com.qiantang.partybuilding.modle.RxTestRecord;

import java.util.List;

/**
 * Created by zhaoyong bai on 2018/6/15.
 */
public class TestRecordQuestionAdapter extends EasyBindQuickAdapter<RxTestRecord> {
    public TestRecordQuestionAdapter(int layoutResId) {
        super(layoutResId);
    }

    public TestRecordQuestionAdapter(int layoutResId, List<RxTestRecord> data) {
        super(layoutResId, data);
    }

    @Override
    protected void easyConvert(BindingViewHolder holder, RxTestRecord item) {
        holder.getBinding().setVariable(BR.item,item);
        holder.getBinding().executePendingBindings();
    }
}
