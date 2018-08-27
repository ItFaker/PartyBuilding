package com.qiantang.partybuilding.module.index.viewmodel;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.qiantang.partybuilding.BaseBindFragment;
import com.qiantang.partybuilding.base.ViewModel;
import com.qiantang.partybuilding.modle.RxLearningList;
import com.qiantang.partybuilding.module.index.adapter.LearnAdapter;
import com.qiantang.partybuilding.network.NetworkSubscriber;
import com.qiantang.partybuilding.network.URLs;
import com.qiantang.partybuilding.network.retrofit.ApiWrapper;
import com.qiantang.partybuilding.network.retrofit.RetrofitUtil;
import com.qiantang.partybuilding.utils.ActivityUtil;
import com.trello.rxlifecycle2.android.FragmentEvent;

import java.util.List;

/**
 * Created by zhaoyong bai on 2018/6/13.
 */
public class LearnFragmentViewModel implements ViewModel {
    private BaseBindFragment fragment;
    private LearnAdapter learnAdapter;
    private int pageNo = 1;
    private int classId = -1;

    public LearnFragmentViewModel(BaseBindFragment fragment, LearnAdapter learnAdapter) {
        this.fragment = fragment;
        this.learnAdapter = learnAdapter;
        initData();
    }

    private void initData() {
        classId = fragment.getArguments().getInt("id", -1);
        if (classId > 0) {
            getData(1);
        }
    }

    @Override
    public void destroy() {

    }

    public void loadMore() {
        pageNo++;
        getData(pageNo);
    }

    public void getData(int pageNo) {
        this.pageNo=pageNo;
        ApiWrapper.getInstance().special(pageNo, classId)
                .compose(fragment.bindUntilEvent(FragmentEvent.DESTROY))
                .subscribe(new NetworkSubscriber<List<RxLearningList>>() {
                    @Override
                    public void onFail(RetrofitUtil.APIException e) {
                        super.onFail(e);
                        learnAdapter.loadMoreEnd();
                        fragment.refreshFail();
                    }

                    @Override
                    public void onSuccess(List<RxLearningList> data) {
                        fragment.refreshOK();
                        learnAdapter.setPagingData(data, pageNo);
                    }
                });
    }

    public RecyclerView.OnItemTouchListener onItemTouchListener() {
        return new OnItemClickListener() {
            @Override
            public void onSimpleItemClick(BaseQuickAdapter adapter, View view, int position) {
                ActivityUtil.startHeadWebActivity(fragment.getActivity(), learnAdapter.getData().get(position).getContentId(),
                        "专题学习", URLs.SPECIALORTHEORY,3,learnAdapter.getData().get(position).getPrinturl());
            }
        };
    }
}
