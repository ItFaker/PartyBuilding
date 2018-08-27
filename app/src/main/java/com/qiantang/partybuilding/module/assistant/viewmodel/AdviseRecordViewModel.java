package com.qiantang.partybuilding.module.assistant.viewmodel;

import com.qiantang.partybuilding.BaseBindActivity;
import com.qiantang.partybuilding.base.ViewModel;
import com.qiantang.partybuilding.modle.RxAdviseRecord;
import com.qiantang.partybuilding.module.assistant.adapter.AdviseRecordAdapter;
import com.qiantang.partybuilding.network.NetworkSubscriber;
import com.qiantang.partybuilding.network.retrofit.ApiWrapper;
import com.qiantang.partybuilding.network.retrofit.RetrofitUtil;
import com.trello.rxlifecycle2.android.ActivityEvent;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhaoyong bai on 2018/6/1.
 */
public class AdviseRecordViewModel implements ViewModel {
    private BaseBindActivity activity;
    private AdviseRecordAdapter adviseRecordAdapter;
    private int pageNo = 1;

    public AdviseRecordViewModel(BaseBindActivity activity, AdviseRecordAdapter adviseRecordAdapter) {
        this.activity = activity;
        this.adviseRecordAdapter = adviseRecordAdapter;
    }

    public void onLoadMore() {
        pageNo++;
        getData();
    }

    @Override
    public void destroy() {

    }

    public void getData() {
        ApiWrapper.getInstance().ideaList(pageNo)
                .compose(activity.bindUntilEvent(ActivityEvent.DESTROY))
                .subscribe(new NetworkSubscriber<List<RxAdviseRecord>>() {
                    @Override
                    public void onFail(RetrofitUtil.APIException e) {
                        super.onFail(e);
                        adviseRecordAdapter.loadMoreEnd();
                    }

                    @Override
                    public void onSuccess(List<RxAdviseRecord> data) {
                        adviseRecordAdapter.setPagingData(data,pageNo);
                    }
                });
    }
}
