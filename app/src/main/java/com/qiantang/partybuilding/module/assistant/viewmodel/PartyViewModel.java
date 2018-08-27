package com.qiantang.partybuilding.module.assistant.viewmodel;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.qiantang.partybuilding.BaseBindActivity;
import com.qiantang.partybuilding.base.ViewModel;
import com.qiantang.partybuilding.modle.RxActivity;
import com.qiantang.partybuilding.modle.RxIndexCommon;
import com.qiantang.partybuilding.module.assistant.adapter.ActivityAdapter;
import com.qiantang.partybuilding.module.index.adapter.IndexCommonAdapter;
import com.qiantang.partybuilding.network.NetworkSubscriber;
import com.qiantang.partybuilding.network.retrofit.ApiWrapper;
import com.qiantang.partybuilding.network.retrofit.RetrofitUtil;
import com.qiantang.partybuilding.utils.ActivityUtil;
import com.trello.rxlifecycle2.android.ActivityEvent;

import java.util.List;

/**
 * Created by zhaoyong bai on 2018/6/8.
 */
public class PartyViewModel implements ViewModel {
    private BaseBindActivity activity;
    private ActivityAdapter adapter;
    private int pageNo = 1;

    public PartyViewModel(BaseBindActivity activity, ActivityAdapter adapter) {
        this.activity = activity;
        this.adapter = adapter;
    }

    public void loadMore() {
        pageNo++;
        getData();
    }

    public void getData() {
        ApiWrapper.getInstance().djActivity(pageNo)
                .compose(activity.bindUntilEvent(ActivityEvent.DESTROY))
                .subscribe(new NetworkSubscriber<List<RxActivity>>() {
                    @Override
                    public void onFail(RetrofitUtil.APIException e) {
                        super.onFail(e);
                        adapter.loadMoreEnd();
                    }

                    @Override
                    public void onSuccess(List<RxActivity> data) {
                        adapter.setPagingData(data, pageNo);
                    }
                });
    }

    public RecyclerView.OnItemTouchListener onItemTouchListener() {
        return new OnItemClickListener() {
            @Override
            public void onSimpleItemClick(BaseQuickAdapter adapterT, View view, int position) {
                ActivityUtil.startActivityDetialActivity(activity, adapter.getData().get(position).getActivityId(), adapter.getData().get(position).getStatus());
            }
        };
    }

    @Override
    public void destroy() {

    }
}
