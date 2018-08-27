package com.qiantang.partybuilding.module.mine.viewmodel;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.qiantang.partybuilding.BaseBindActivity;
import com.qiantang.partybuilding.BaseBindFragment;
import com.qiantang.partybuilding.R;
import com.qiantang.partybuilding.base.ViewModel;
import com.qiantang.partybuilding.modle.RxVideoStudy;
import com.qiantang.partybuilding.module.index.adapter.VideoStudyAdapter;
import com.qiantang.partybuilding.module.mine.adapter.VideoStudyFragmentAdapter;
import com.qiantang.partybuilding.network.NetworkSubscriber;
import com.qiantang.partybuilding.network.retrofit.ApiWrapper;
import com.qiantang.partybuilding.network.retrofit.RetrofitUtil;
import com.qiantang.partybuilding.utils.ActivityUtil;
import com.trello.rxlifecycle2.android.ActivityEvent;
import com.trello.rxlifecycle2.android.FragmentEvent;

import java.util.List;

/**
 * Created by zhaoyong bai on 2018/5/22.
 */
public class VideoStudyFragmentViewModel implements ViewModel {
    private BaseBindActivity activity;
    private BaseBindFragment fragment;
    private VideoStudyFragmentAdapter fragmentAdapter;
    private int pageNo = 1;

    public VideoStudyFragmentViewModel(BaseBindFragment activity, VideoStudyFragmentAdapter adapter) {
        this.fragment = activity;
        this.fragmentAdapter = adapter;
        this.activity = (BaseBindActivity) fragment.getActivity();
    }

    public void onLoadMore() {
        pageNo++;
        getData();
    }

    public void getData() {
        ApiWrapper.getInstance().videoUserList(pageNo)
                .compose(fragment.bindUntilEvent(FragmentEvent.DESTROY))
                .subscribe(new NetworkSubscriber<List<RxVideoStudy>>() {
                    @Override
                    public void onFail(RetrofitUtil.APIException e) {
                        super.onFail(e);
                        fragmentAdapter.loadMoreEnd();
                    }

                    @Override
                    public void onSuccess(List<RxVideoStudy> data) {
                        fragmentAdapter.setPagingData(data, pageNo);
                    }
                });
    }

    public RecyclerView.OnItemTouchListener onItemTouchListener() {
        return new OnItemClickListener() {
            @Override
            public void onSimpleItemClick(BaseQuickAdapter adapterQ, View view, int position) {
                RxVideoStudy rxVideoStudy = fragmentAdapter.getData().get(position);
                ActivityUtil.startVideoDetialActivity(activity, rxVideoStudy.getVideourl(), rxVideoStudy.getTitle(), rxVideoStudy.getVideo_id());
            }

        };
    }

    @Override
    public void destroy() {

    }
}
