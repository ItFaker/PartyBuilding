package com.qiantang.partybuilding.module.mine.viewmodel;

import android.databinding.ObservableInt;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.qiantang.partybuilding.BaseBindActivity;
import com.qiantang.partybuilding.base.ViewModel;
import com.qiantang.partybuilding.modle.HttpResult;
import com.qiantang.partybuilding.modle.RxMonthScore;
import com.qiantang.partybuilding.modle.RxTotalScore;
import com.qiantang.partybuilding.module.mine.adapter.TotalScoreAdapter;
import com.qiantang.partybuilding.network.NetworkSubscriber;
import com.qiantang.partybuilding.network.retrofit.ApiWrapper;
import com.qiantang.partybuilding.network.retrofit.RetrofitUtil;
import com.qiantang.partybuilding.utils.ActivityUtil;
import com.trello.rxlifecycle2.android.ActivityEvent;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhaoyong bai on 2018/5/22.
 */
public class TotalViewModel implements ViewModel {
    private BaseBindActivity activity;
    private TotalScoreAdapter adapter;
    private int pageNo = 1;
    public ObservableInt score = new ObservableInt(0);

    public TotalViewModel(BaseBindActivity activity, TotalScoreAdapter adapter) {
        this.activity = activity;
        this.adapter = adapter;
    }

    public void onLoadMore() {
        pageNo++;
        testData();
    }

    public void testData() {
        ApiWrapper.getInstance().learningability(pageNo)
                .compose(activity.bindUntilEvent(ActivityEvent.DESTROY))
                .subscribe(new NetworkSubscriber<List<RxTotalScore>>() {
                    @Override
                    public void onFail(RetrofitUtil.APIException e) {
                        super.onFail(e);
                        adapter.loadMoreEnd();
                    }

                    @Override
                    public void onSuccess(List<RxTotalScore> data) {
                        adapter.setPagingData(data, pageNo);
                        if (data.size() > 0) {
                            score.set(data.get(0).getCount());
                        }
                    }
                });
    }

    public RecyclerView.OnItemTouchListener onItemTouchListener() {
        return new OnItemClickListener() {
            @Override
            public void onSimpleItemClick(BaseQuickAdapter adaptera, View view, int position) {
                ActivityUtil.startMonthScoreActivity(activity, adapter.getData().get(position).getMonths());
            }
        };
    }

    @Override
    public void destroy() {

    }
}
