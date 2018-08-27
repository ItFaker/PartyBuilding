package com.qiantang.partybuilding.module.index.adapter;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.qiantang.partybuilding.BR;
import com.qiantang.partybuilding.R;
import com.qiantang.partybuilding.adapter.BindingViewHolder;
import com.qiantang.partybuilding.adapter.EasyBindQuickAdapter;
import com.qiantang.partybuilding.modle.RxQuestion;
import com.qiantang.partybuilding.modle.RxRecordDetial;
import com.qiantang.partybuilding.modle.RxTestDetial;
import com.qiantang.partybuilding.modle.RxTestRecord;

import java.util.List;

/**
 * Created by zhaoyong bai on 2018/6/14.
 */
public class TestRecordAdapter extends EasyBindQuickAdapter<RxRecordDetial> {
    public TestRecordAdapter(int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void easyConvert(BindingViewHolder holder, RxRecordDetial item) {
        holder.getBinding().setVariable(BR.item, item);
        holder.getBinding().executePendingBindings();
        List<RxTestRecord> questions = item.getOption();
        for (int i = 0; i < questions.size(); i++) {
            questions.get(i).setPos(i);
        }
        TestRecordQuestionAdapter adapter = new TestRecordQuestionAdapter(R.layout.item_test_record_question, questions);
        RecyclerView recyclerView = holder.getBinding().getRoot().findViewById(R.id.rv);
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        recyclerView.setAdapter(adapter);
    }
}
