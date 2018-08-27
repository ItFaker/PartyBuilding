package com.qiantang.partybuilding.module.study.viewmodel;

import com.qiantang.partybuilding.BaseBindActivity;
import com.qiantang.partybuilding.base.ViewModel;
import com.qiantang.partybuilding.modle.RxStudyUnreadMsg;
import com.qiantang.partybuilding.module.study.adapter.UnreadAdapter;
import com.qiantang.partybuilding.network.NetworkSubscriber;
import com.qiantang.partybuilding.network.retrofit.ApiWrapper;
import com.qiantang.partybuilding.network.retrofit.RetrofitUtil;
import com.trello.rxlifecycle2.android.ActivityEvent;

import java.util.List;

/**
 * Created by zhaoyong bai on 2018/6/6.
 */
public class UnreadMsgViewModel implements ViewModel {
    private BaseBindActivity activity;
    private UnreadAdapter adapter;
    private int pageNo = 1;

    public UnreadMsgViewModel(BaseBindActivity activity, UnreadAdapter adapter) {
        this.activity = activity;
        this.adapter = adapter;
    }

    public void loadMore() {
        pageNo++;
        getData(pageNo);
    }

    public void getData(int pageNo) {
        this.pageNo = pageNo;
        ApiWrapper.getInstance().getUnreadMsg()
                .compose(activity.bindUntilEvent(ActivityEvent.DESTROY))
                .subscribe(new NetworkSubscriber<List<RxStudyUnreadMsg>>() {
                    @Override
                    public void onFail(RetrofitUtil.APIException e) {
                        super.onFail(e);
                        adapter.loadMoreEnd();
                        activity.refreshFail();
                    }

                    @Override
                    public void onSuccess(List<RxStudyUnreadMsg> data) {
                        adapter.setPagingData(data, pageNo);
                        adapter.loadMoreEnd();
                        activity.refreshOK();
                    }
                });
    }

    @Override
    public void destroy() {

    }
}
