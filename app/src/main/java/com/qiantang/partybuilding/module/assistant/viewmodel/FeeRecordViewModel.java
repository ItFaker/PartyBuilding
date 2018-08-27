package com.qiantang.partybuilding.module.assistant.viewmodel;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.qiantang.partybuilding.BaseBindActivity;
import com.qiantang.partybuilding.base.ViewModel;
import com.qiantang.partybuilding.modle.RxFeeRecord;
import com.qiantang.partybuilding.modle.RxIndexCommon;
import com.qiantang.partybuilding.module.assistant.adapter.FeeRecordAdapter;
import com.qiantang.partybuilding.network.NetworkSubscriber;
import com.qiantang.partybuilding.network.retrofit.ApiWrapper;
import com.trello.rxlifecycle2.android.ActivityEvent;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhaoyong bai on 2018/5/29.
 */
public class FeeRecordViewModel implements ViewModel {
    private BaseBindActivity activity;
    private FeeRecordAdapter adapter;
    private int pageNum = 1;

    public FeeRecordViewModel(BaseBindActivity activity, FeeRecordAdapter adapter) {
        this.activity = activity;
        this.adapter = adapter;
    }

    public void loadMore() {
        pageNum++;
        testData();
    }

    public RecyclerView.OnItemTouchListener onItemTouchListener() {
        return new OnItemClickListener() {
            @Override
            public void onSimpleItemClick(BaseQuickAdapter adapter, View view, int position) {

            }
        };
    }

    @Override
    public void destroy() {

    }

    public void testData() {
        ApiWrapper.getInstance().partyMoneyList(pageNum)
                .compose(activity.bindUntilEvent(ActivityEvent.DESTROY))
                .subscribe(new NetworkSubscriber<List<RxFeeRecord>>() {
                    @Override
                    public void onSuccess(List<RxFeeRecord> data) {
                        adapter.setPagingData(data, pageNum);
                    }
                });
    }
}
