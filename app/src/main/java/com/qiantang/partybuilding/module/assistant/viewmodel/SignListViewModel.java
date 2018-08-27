package com.qiantang.partybuilding.module.assistant.viewmodel;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.qiantang.partybuilding.BaseBindActivity;
import com.qiantang.partybuilding.base.ViewModel;
import com.qiantang.partybuilding.modle.RxSignList;
import com.qiantang.partybuilding.module.assistant.adapter.SignListAdapter;
import com.qiantang.partybuilding.network.NetworkSubscriber;
import com.qiantang.partybuilding.network.retrofit.ApiWrapper;
import com.trello.rxlifecycle2.android.ActivityEvent;

import java.util.List;

/**
 * Created by zhaoyong bai on 2018/6/10.
 */
public class SignListViewModel implements ViewModel {
    private BaseBindActivity activity;
    private SignListAdapter signListAdapter;
    private int pageNo = 1;
    private String id;

    public SignListViewModel(BaseBindActivity activity, SignListAdapter signListAdapter) {
        this.activity = activity;
        this.signListAdapter = signListAdapter;
        initData();
    }

    private void initData() {
        id = activity.getIntent().getStringExtra("id");
    }

    public void loadMore() {
        pageNo++;
        getdata();
    }

    public void getdata() {
        ApiWrapper.getInstance().tzSign(id, pageNo)
                .compose(activity.bindUntilEvent(ActivityEvent.DESTROY))
                .subscribe(new NetworkSubscriber<List<RxSignList>>() {
                    @Override
                    public void onSuccess(List<RxSignList> data) {
                        signListAdapter.setPagingData(data, pageNo);
                    }
                });
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
}
