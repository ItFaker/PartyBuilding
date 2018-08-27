package com.qiantang.partybuilding.module.assistant.viewmodel;

import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.qiantang.partybuilding.BaseBindActivity;
import com.qiantang.partybuilding.MyApplication;
import com.qiantang.partybuilding.base.ViewModel;
import com.qiantang.partybuilding.config.CacheKey;
import com.qiantang.partybuilding.modle.RxIndexCommon;
import com.qiantang.partybuilding.modle.RxMsg;
import com.qiantang.partybuilding.module.assistant.adapter.MsgAdapter;
import com.qiantang.partybuilding.network.NetworkSubscriber;
import com.qiantang.partybuilding.network.URLs;
import com.qiantang.partybuilding.network.retrofit.ApiWrapper;
import com.qiantang.partybuilding.utils.ActivityUtil;
import com.qiantang.partybuilding.utils.ToastUtil;
import com.trello.rxlifecycle2.android.ActivityEvent;

import java.util.List;

/**
 * Created by zhaoyong bai on 2018/6/8.
 */
public class MsgViewModel implements ViewModel {
    private BaseBindActivity activity;
    private MsgAdapter adapter;
    private int pageNo = 1;

    public MsgViewModel(BaseBindActivity activity, MsgAdapter adapter) {
        this.activity = activity;
        this.adapter = adapter;
    }

    public void loadMore() {
        pageNo++;
        getData(pageNo);
    }

    public void getData(int pageNo) {
        this.pageNo = pageNo;
        ApiWrapper.getInstance().tzNotice(pageNo)
                .compose(activity.bindUntilEvent(ActivityEvent.DESTROY))
                .subscribe(new NetworkSubscriber<List<RxMsg>>() {
                    @Override
                    public void onSuccess(List<RxMsg> data) {
                        adapter.setPagingData(data, pageNo);
                    }
                });
    }

    public RecyclerView.OnItemTouchListener onItemTouchListener() {
        return new OnItemClickListener() {
            @Override
            public void onSimpleItemClick(BaseQuickAdapter adapterq, View view, int position) {
                if (!MyApplication.isLogin()) {
                    ActivityUtil.startLoginActivity(activity);
                    return;
                }
                if (TextUtils.equals(MyApplication.mCache.getAsString(CacheKey.DEPT_ID), "1")) {
                    ToastUtil.toast("仅内部人员可查看");
                    return;
                }
                if (((int) MyApplication.mCache.getAsObject(CacheKey.STATUS)) > 0) {
                    ToastUtil.toast("您尚未通过审核，请耐心等待");
                    return;
                }
                ActivityUtil.jumpWeb(activity, URLs.MESSAGE_DETIAL + (adapter.getData().get(position)).getNoticeId(), "公告详情");
            }
        };
    }

    @Override
    public void destroy() {

    }
}
