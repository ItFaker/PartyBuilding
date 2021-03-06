package com.qiantang.partybuilding.module.assistant.viewmodel;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.qiantang.partybuilding.BaseBindActivity;
import com.qiantang.partybuilding.base.ViewModel;
import com.qiantang.partybuilding.modle.RxIndexCommon;
import com.qiantang.partybuilding.modle.RxMsg;
import com.qiantang.partybuilding.module.assistant.adapter.MsgAdapter;
import com.qiantang.partybuilding.module.index.adapter.IndexCommonAdapter;
import com.qiantang.partybuilding.network.NetworkSubscriber;
import com.qiantang.partybuilding.network.URLs;
import com.qiantang.partybuilding.network.retrofit.ApiWrapper;
import com.qiantang.partybuilding.network.retrofit.RetrofitUtil;
import com.qiantang.partybuilding.utils.ActivityUtil;
import com.trello.rxlifecycle2.android.ActivityEvent;

import java.util.List;

/**
 * Created by zhaoyong bai on 2018/6/8.
 */
public class MienViewModel implements ViewModel {
    private BaseBindActivity activity;
    private IndexCommonAdapter adapter;
    private int pageNo = 1;
    private int type;

    public MienViewModel(BaseBindActivity activity, IndexCommonAdapter adapter) {
        this.activity = activity;
        this.adapter = adapter;
    }

    public void loadMore() {
        pageNo++;
        if (type == 7) {
            getThinkingData(pageNo, type);
        } else {
            getData(pageNo, type);
        }
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
                        activity.refreshOK();
                        adapter.setPagingData(data, pageNo);
                    }
                });
    }

    public void getThinkingData(int pageNo, int type) {
        this.type = type;
        this.pageNo = pageNo;
        ApiWrapper.getInstance().thinking(pageNo, type)
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

    public void insertData(RxIndexCommon rxIndexCommon) {
        if (type == 7) {
            adapter.getData().add(0, rxIndexCommon);
            adapter.notifyItemInserted(0);
        }
    }

    public RecyclerView.OnItemTouchListener onItemTouchListener() {
        return new OnItemClickListener() {
            @Override
            public void onSimpleItemClick(BaseQuickAdapter adaptert, View view, int position) {
                String id = adapter.getData().get(position).getContentId();
                String title = "";
                switch (type) {
                    case 4: //党建风采
                        title = "党建风采";
                        ActivityUtil.startHeadWebActivity(activity, id, title, URLs.NOTICE_DETIAL, 0, adapter.getData().get(position).getImgSrc());
                        break;
                    case 6: //会议纪要
                        title = "会议纪要";
                        ActivityUtil.startHeadWebActivity(activity, id, title, URLs.NOTICE_DETIAL, 0, adapter.getData().get(position).getImgSrc());
                        break;
                    case 7://思想汇报
                        title = "思想汇报";
                        ActivityUtil.startThinkingDetialActivity(activity, id);
                        break;
                }

            }
        };
    }

    @Override
    public void destroy() {

    }
}
