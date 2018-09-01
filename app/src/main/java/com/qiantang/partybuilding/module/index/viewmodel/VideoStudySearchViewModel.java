package com.qiantang.partybuilding.module.index.viewmodel;

import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
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

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

/**
 * Author:    Jintf
 * Date:      2018/8/31 0031 下午 5:35
 */
public class VideoStudySearchViewModel implements ViewModel {
    private VideoStudyAdapter adapter;
    private BaseBindFragment fragment;
    private VideoStudyFragmentAdapter fragmentAdapter;
    private int pageNo = 1;
    private String keyword;


    public VideoStudySearchViewModel(BaseBindFragment activity) {
        this.fragment = activity;
        EventBus.getDefault().register(this);
    }

    public void setAdapter(VideoStudyAdapter adapter){
        this.adapter=adapter;
    }
    public void onLoadMore() {
        pageNo++;
        getData(pageNo,keyword);
    }

    public void getData(int pageNo,String keyword) {
        this.pageNo = pageNo;
        this.keyword = keyword;
        ApiWrapper.getInstance().videoList(pageNo,keyword)
                .compose(fragment == null ? fragment.bindUntilEvent(FragmentEvent.DESTROY) : fragment.bindUntilEvent(FragmentEvent.DESTROY))
                .subscribe(new NetworkSubscriber<List<RxVideoStudy>>() {
                    @Override
                    public void onFail(RetrofitUtil.APIException e) {
                        super.onFail(e);
                        if (fragmentAdapter != null) {
                            fragmentAdapter.loadMoreEnd();
                            return;
                        }
                        adapter.loadMoreEnd();
                        fragment.refreshFail();
                    }

                    @Override
                    public void onSuccess(List<RxVideoStudy> data) {
                        fragment.refreshOK();
                        if (fragmentAdapter != null) {
                            fragmentAdapter.setPagingData(data, pageNo);
                            return;
                        }
                        adapter.setPagingData(data, pageNo);
                    }

                               @Override
                               public void onComplete() {
                                   super.onComplete();
                               }

                               @Override
                               public void onError(Throwable e) {
                                   super.onError(e);
                               }

                               @Override
                               public void onNext(List<RxVideoStudy> data) {
                                   super.onNext(data);
                               }
                           }
                );
    }

    public RecyclerView.OnItemTouchListener onItemTouchListener() {
        return new OnItemClickListener() {
            @Override
            public void onSimpleItemClick(BaseQuickAdapter adapterQ, View view, int position) {
                if (fragmentAdapter != null) {
                    RxVideoStudy rxVideoStudy = fragmentAdapter.getData().get(position);
                    ActivityUtil.startVideoDetialActivity(fragment.getActivity(), rxVideoStudy.getVideourl(), rxVideoStudy.getTitle(), rxVideoStudy.getVideo_id());
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
                        ActivityUtil.startVideoDetialActivity(fragment.getActivity(), rxVideoStudy.getVideourl(), rxVideoStudy.getTitle(), rxVideoStudy.getVideo_id());
                        break;
                }
            }
        };
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(String keyword) {
        if (TextUtils.isEmpty(keyword)) {
            return;
        }
        getData(1, keyword);
    }

    @Override
    public void destroy() {
        EventBus.getDefault().unregister(this);
    }
}
