package com.qiantang.partybuilding.module.index.viewmodel;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.qiantang.partybuilding.BaseBindActivity;
import com.qiantang.partybuilding.base.ViewModel;
import com.qiantang.partybuilding.modle.RxIndexCommon;
import com.qiantang.partybuilding.module.index.adapter.IndexCommonAdapter;
import com.qiantang.partybuilding.network.NetworkSubscriber;
import com.qiantang.partybuilding.network.URLs;
import com.qiantang.partybuilding.network.retrofit.ApiWrapper;
import com.qiantang.partybuilding.network.retrofit.RetrofitUtil;
import com.qiantang.partybuilding.utils.ActivityUtil;
import com.trello.rxlifecycle2.android.ActivityEvent;

import java.util.List;

/**
 * Created by zhaoyong bai on 2018/5/22.
 */
public class NewsViewModel implements ViewModel {
    private BaseBindActivity activity;
    private IndexCommonAdapter adapter;
    private int pageNo = 1;
    private int type;

    public NewsViewModel(BaseBindActivity activity, IndexCommonAdapter adapter) {
        this.activity = activity;
        this.adapter = adapter;
    }

    public void onLoadMore() {
        pageNo++;
        testData(type, pageNo);
    }

    public void testData(int type, int pageNo) {
        this.pageNo = pageNo;
        this.type = type;
        ApiWrapper.getInstance().fcNotice(pageNo, type)
                .compose(activity.bindUntilEvent(ActivityEvent.DESTROY))
                .subscribe(new NetworkSubscriber<List<RxIndexCommon>>() {
                    @Override
                    public void onFail(RetrofitUtil.APIException e) {
                        super.onFail(e);
                        adapter.loadMoreEnd();
                        activity.refreshFail();
                    }

                    @Override
                    public void onSuccess(List<RxIndexCommon> data) {
                        adapter.setPagingData(data, pageNo);
                        activity.refreshOK();
                    }
                });
    }

    public RecyclerView.OnItemTouchListener onItemTouchListener() {
        return new OnItemClickListener() {
            @Override
            public void onSimpleItemClick(BaseQuickAdapter adapterq, View view, int position) {
                String title = "";
                switch (type) {
                    case 8:
                        title = "新闻快报";
                        break;
                    case 9:
                        title = "学习动态";
                        break;
                }
                ActivityUtil.startHeadWebActivity(activity, adapter.getData().get(position).getContentId(),
                        title, URLs.NOTICE_DETIAL, 0,adapter.getData().get(position).getImgSrc());
            }
        };
    }

    @Override
    public void destroy() {

    }
}
