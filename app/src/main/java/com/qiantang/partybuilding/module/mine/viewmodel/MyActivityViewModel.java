package com.qiantang.partybuilding.module.mine.viewmodel;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.qiantang.partybuilding.BaseBindActivity;
import com.qiantang.partybuilding.R;
import com.qiantang.partybuilding.base.ViewModel;
import com.qiantang.partybuilding.modle.RxActivity;
import com.qiantang.partybuilding.module.assistant.adapter.ActivityAdapter;
import com.qiantang.partybuilding.module.mine.adapter.MyActivityAdapter;
import com.qiantang.partybuilding.network.NetworkSubscriber;
import com.qiantang.partybuilding.network.retrofit.ApiWrapper;
import com.qiantang.partybuilding.network.retrofit.RetrofitUtil;
import com.qiantang.partybuilding.utils.ActivityUtil;
import com.trello.rxlifecycle2.android.ActivityEvent;

import java.util.List;

/**
 * Created by zhaoyong bai on 2018/5/30.
 */
public class MyActivityViewModel implements ViewModel {
    private BaseBindActivity activity;
    private MyActivityAdapter adapter;
    private int pageNo = 1;
    private boolean isDealing = false;
    private int pos;

    public MyActivityViewModel(BaseBindActivity activity, MyActivityAdapter adapter) {
        this.activity = activity;
        this.adapter = adapter;
        getData();
    }

    public void getData() {
        ApiWrapper.getInstance().myActivity(pageNo)
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

    private void delete(String id) {
        ApiWrapper.getInstance().deleteActivity(id)
                .compose(activity.bindUntilEvent(ActivityEvent.DESTROY))
                .doOnTerminate(() -> isDealing = false)
                .subscribe(new NetworkSubscriber<String>() {
                    @Override
                    public void onSuccess(String data) {
                        adapter.getData().remove(pos);
                        adapter.notifyItemRemoved(pos);
                    }
                });
    }

    public RecyclerView.OnItemTouchListener onItemTouchListener() {
        return new OnItemClickListener() {
            @Override
            public void onSimpleItemClick(BaseQuickAdapter adapter, View view, int position) {

            }

            @Override
            public void onItemChildClick(BaseQuickAdapter adapterq, View view, int position) {
                super.onItemChildClick(adapterq, view, position);
                switch (view.getId()) {
                    case R.id.tv_check:
                        ActivityUtil.startActivityDetialActivity(activity, adapter.getData().get(position).getActivityId(), adapter.getData().get(position).getStatus());
                        break;
                    case R.id.tv_del:
                        isDealing = true;
                        pos = position;
                        delete(adapter.getData().get(position).getActivityId());
                        break;
                }
            }
        };
    }

    @Override
    public void destroy() {

    }

    public void loadMore() {
        pageNo++;
        getData();
    }
}
