package com.qiantang.partybuilding.module.index.viewmodel;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.qiantang.partybuilding.BaseBindActivity;
import com.qiantang.partybuilding.BaseBindFragment;
import com.qiantang.partybuilding.base.ViewModel;
import com.qiantang.partybuilding.modle.RxIndexSpeak;
import com.qiantang.partybuilding.modle.RxSpeechInfo;
import com.qiantang.partybuilding.module.index.adapter.IndexCommonAdapter;
import com.qiantang.partybuilding.module.index.adapter.SpechAdapter;
import com.qiantang.partybuilding.network.NetworkSubscriber;
import com.qiantang.partybuilding.network.retrofit.ApiWrapper;
import com.qiantang.partybuilding.utils.ActivityUtil;
import com.trello.rxlifecycle2.android.ActivityEvent;
import com.trello.rxlifecycle2.android.FragmentEvent;

import java.util.List;

/**
 * Created by zhaoyong bai on 2018/5/22.
 */
public class SpeechStudyViewModel implements ViewModel {
    private BaseBindActivity activity;
    private SpechAdapter adapter;
    private int pageNo = 1;
    private BaseBindFragment fragment;

    public SpeechStudyViewModel(BaseBindActivity activity, SpechAdapter adapter) {
        this.activity = activity;
        this.adapter = adapter;
    }
    public SpeechStudyViewModel(BaseBindFragment activity, SpechAdapter adapter) {
        this.fragment = activity;
        this.adapter = adapter;
        this.activity= (BaseBindActivity) fragment.getActivity();
    }
    public void onLoadMore() {
        pageNo++;
        testData();
    }

    public void testData() {
        ApiWrapper.getInstance().speechList(pageNo)
                .compose(fragment==null?activity.bindUntilEvent(ActivityEvent.DESTROY):fragment.bindUntilEvent(FragmentEvent.DESTROY))
                .subscribe(new NetworkSubscriber<List<RxIndexSpeak>>() {
                    @Override
                    public void onSuccess(List<RxIndexSpeak> data) {
                        adapter.setPagingData(data, pageNo);
                    }
                });
    }

    public RecyclerView.OnItemTouchListener onItemTouchListener() {
        return new OnItemClickListener() {
            @Override
            public void onSimpleItemClick(BaseQuickAdapter adaptera, View view, int position) {
                RxIndexSpeak rxIndexSpeak=adapter.getData().get(position);
                if (rxIndexSpeak.getSpeaktype()==1){
                    //视频
                    ActivityUtil.startVideoSpeechDetialActivity(activity,rxIndexSpeak.getSpeakurl(),rxIndexSpeak.getTitle(),rxIndexSpeak.getSpeak_id());
                }else {
                    ActivityUtil.startVoiceSpeechDetialActivity(activity,rxIndexSpeak.getSpeakurl(),rxIndexSpeak.getTitle(),rxIndexSpeak.getSpeak_id());
                }
            }
        };
    }

    @Override
    public void destroy() {

    }
}
