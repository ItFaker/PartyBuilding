package com.qiantang.partybuilding.module.mine.viewmodel;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.qiantang.partybuilding.BaseBindActivity;
import com.qiantang.partybuilding.BaseBindFragment;
import com.qiantang.partybuilding.base.ViewModel;
import com.qiantang.partybuilding.modle.RxBookRecommend;
import com.qiantang.partybuilding.module.index.adapter.BookRecommendAdapter;
import com.qiantang.partybuilding.network.NetworkSubscriber;
import com.qiantang.partybuilding.network.retrofit.ApiWrapper;
import com.qiantang.partybuilding.utils.ActivityUtil;
import com.trello.rxlifecycle2.android.ActivityEvent;
import com.trello.rxlifecycle2.android.FragmentEvent;

import java.util.List;

/**
 * Created by zhaoyong bai on 2018/6/17.
 */
public class BookFragmentViewModel implements ViewModel {
    private BaseBindFragment activity;
    private BookRecommendAdapter recommendAdapter;
    private int pageNo = 1;

    public BookFragmentViewModel(BaseBindFragment activity, BookRecommendAdapter recommendAdapter) {
        this.activity = activity;
        this.recommendAdapter = recommendAdapter;
    }

    @Override
    public void destroy() {

    }

    public void loadMore() {
        pageNo++;
        getData();
    }

    public void getData() {
        ApiWrapper.getInstance().userRecommend(pageNo)
                .compose(activity.bindUntilEvent(FragmentEvent.DESTROY))
                .subscribe(new NetworkSubscriber<List<RxBookRecommend>>() {
                    @Override
                    public void onSuccess(List<RxBookRecommend> data) {
                        recommendAdapter.setPagingData(data, pageNo);
                    }
                });
    }

    public RecyclerView.OnItemTouchListener onItemTouchListener() {
        return new OnItemClickListener() {
            @Override
            public void onSimpleItemClick(BaseQuickAdapter adaptera, View view, int position) {
                ActivityUtil.startBookDetialActivity(activity.getActivity(),recommendAdapter.getData().get(position).getContentId());
            }
        };
    }
}
