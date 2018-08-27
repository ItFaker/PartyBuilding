package com.qiantang.partybuilding.module.assistant.viewmodel;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.qiantang.partybuilding.BaseBindActivity;
import com.qiantang.partybuilding.base.ViewModel;
import com.qiantang.partybuilding.modle.RxIndexCommon;
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
public class CharacterViewModel implements ViewModel {
    private BaseBindActivity activity;
    private IndexCommonAdapter adapter;
    private int pageNo = 1;
    private int type;

    public CharacterViewModel(BaseBindActivity activity, IndexCommonAdapter adapter) {
        this.activity = activity;
        this.adapter = adapter;
    }

    public void loadMore() {
        pageNo++;
        getData(pageNo, type);
    }

    public void getData(int pageNo, int type) {
        this.type = type;
        this.pageNo = pageNo;
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
            public void onSimpleItemClick(BaseQuickAdapter adaptert, View view, int position) {
                ActivityUtil.startCharacterDetialActivity(activity, adapter.getData().get(position).getContentId(), adapter.getData().get(position).getPrinturl());
            }
        };
    }

    @Override
    public void destroy() {

    }
}
