package com.qiantang.partybuilding.module.index.viewmodel;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.qiantang.partybuilding.BaseBindActivity;
import com.qiantang.partybuilding.BaseBindFragment;
import com.qiantang.partybuilding.R;
import com.qiantang.partybuilding.base.ViewModel;
import com.qiantang.partybuilding.modle.RxVideoStudy;
import com.qiantang.partybuilding.module.index.adapter.IndexCommonAdapter;
import com.qiantang.partybuilding.module.index.adapter.VideoStudyAdapter;
import com.qiantang.partybuilding.module.mine.adapter.VideoStudyFragmentAdapter;
import com.qiantang.partybuilding.network.NetworkSubscriber;
import com.qiantang.partybuilding.network.retrofit.ApiWrapper;
import com.qiantang.partybuilding.network.retrofit.RetrofitUtil;
import com.qiantang.partybuilding.utils.ActivityUtil;
import com.trello.rxlifecycle2.android.ActivityEvent;
import com.trello.rxlifecycle2.android.FragmentEvent;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhaoyong bai on 2018/5/22.
 */
public class VideoStudyViewModel implements ViewModel {
    private BaseBindActivity activity;
    private VideoStudyAdapter adapter;
    private BaseBindFragment fragment;
    private VideoStudyFragmentAdapter fragmentAdapter;
    private int pageNo = 1;

    public VideoStudyViewModel(BaseBindActivity activity, VideoStudyAdapter adapter) {
        this.activity = activity;
        this.adapter = adapter;
    }

    public VideoStudyViewModel(BaseBindFragment activity, VideoStudyFragmentAdapter adapter) {
        this.fragment = activity;
        this.fragmentAdapter = adapter;
        this.activity = (BaseBindActivity) fragment.getActivity();
    }

    public void onLoadMore() {
        pageNo++;
        getData(pageNo);
    }

    public void getData(int pageNo) {
        this.pageNo = pageNo;
        ApiWrapper.getInstance().videoList(pageNo)
                .compose(fragment == null ? activity.bindUntilEvent(ActivityEvent.DESTROY) : fragment.bindUntilEvent(FragmentEvent.DESTROY))
                .subscribe(new NetworkSubscriber<List<RxVideoStudy>>() {
                    @Override
                    public void onFail(RetrofitUtil.APIException e) {
                        super.onFail(e);
                        if (fragmentAdapter != null) {
                            fragmentAdapter.loadMoreEnd();
                            return;
                        }
                        adapter.loadMoreEnd();
                        activity.refreshFail();
                    }

                    @Override
                    public void onSuccess(List<RxVideoStudy> data) {
                        activity.refreshOK();
                        if (fragmentAdapter != null) {
                            fragmentAdapter.setPagingData(data, pageNo);
                            return;
                        }
                        adapter.setPagingData(data, pageNo);
                    }
                });
    }

    public RecyclerView.OnItemTouchListener onItemTouchListener() {
        return new OnItemClickListener() {
            @Override
            public void onSimpleItemClick(BaseQuickAdapter adapterQ, View view, int position) {
                if (fragmentAdapter != null) {
                    RxVideoStudy rxVideoStudy = fragmentAdapter.getData().get(position);
                    ActivityUtil.startVideoDetialActivity(activity, rxVideoStudy.getVideourl(), rxVideoStudy.getTitle(), rxVideoStudy.getVideo_id());
                }
            }

            @Override
            public void onItemChildClick(BaseQuickAdapter qadapter, View view, int position) {
                super.onItemChildClick(qadapter, view, position);
                RxVideoStudy rxVideoStudy;
                if (fragmentAdapter != null) {
                    rxVideoStudy = fragmentAdapter.getData().get(position);
                } else {
                    rxVideoStudy = adapter.getData().get(position);
                }
                switch (view.getId()) {
                    case R.id.rl_parent:
                        ActivityUtil.startVideoDetialActivity(activity, rxVideoStudy.getVideourl(), rxVideoStudy.getTitle(), rxVideoStudy.getVideo_id());
                        break;
                }
            }
        };
    }

    @Override
    public void destroy() {

    }
}
