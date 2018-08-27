package com.qiantang.partybuilding.module.study.adapter;

import com.qiantang.partybuilding.BR;
import com.qiantang.partybuilding.adapter.BindingViewHolder;
import com.qiantang.partybuilding.adapter.EasyBindQuickAdapter;
import com.qiantang.partybuilding.modle.RxStudyComment;

import java.util.List;

/**
 * Created by zhaoyong bai on 2018/6/6.
 */
public class StudyCommentAdapter extends EasyBindQuickAdapter<RxStudyComment> {
    public StudyCommentAdapter(int layoutResId) {
        super(layoutResId);
    }

    public StudyCommentAdapter(int layoutResId, List<RxStudyComment> data) {
        super(layoutResId, data);
    }

    @Override
    protected void easyConvert(BindingViewHolder holder, RxStudyComment item) {
        holder.getBinding().setVariable(BR.item, item);
        holder.getBinding().executePendingBindings();
    }
}
