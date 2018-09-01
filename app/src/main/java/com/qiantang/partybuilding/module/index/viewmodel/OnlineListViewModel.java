package com.qiantang.partybuilding.module.index.viewmodel;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.qiantang.partybuilding.BaseBindActivity;
import com.qiantang.partybuilding.base.ViewModel;
import com.qiantang.partybuilding.modle.RxLearningClass;
import com.qiantang.partybuilding.module.index.adapter.LearningViewPagerAdapter;
import com.qiantang.partybuilding.module.index.fragment.FragmentLearn;
import com.qiantang.partybuilding.module.index.fragment.FragmentOnline;
import com.qiantang.partybuilding.module.index.view.LearningListActivity;
import com.qiantang.partybuilding.module.index.view.OnlineListActivity;
import com.qiantang.partybuilding.network.NetworkSubscriber;
import com.qiantang.partybuilding.network.retrofit.ApiWrapper;
import com.trello.rxlifecycle2.android.ActivityEvent;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhaoyong bai on 2018/6/13.
 */
public class OnlineListViewModel implements ViewModel {
    private BaseBindActivity activity;
    private LearningViewPagerAdapter viewPagerAdapter;
    private int classId;
    private List<Integer> classifyIdList;

    public OnlineListViewModel(BaseBindActivity activity, LearningViewPagerAdapter viewPagerAdapter) {
        this.activity = activity;
        this.viewPagerAdapter = viewPagerAdapter;
        initData();
    }

    private void initData() {
        classId = activity.getIntent().getIntExtra("id", -1);
        classifyIdList = new ArrayList<>();
    }

    @Override
    public void destroy() {

    }

    public void getData() {
        ApiWrapper.getInstance().theoryClassify()
                .compose(activity.bindUntilEvent(ActivityEvent.DESTROY))
                .subscribe(new NetworkSubscriber<List<RxLearningClass>>() {
                    @Override
                    public void onSuccess(List<RxLearningClass> data) {
                        if (data.size() > 0) {
                            viewPagerAdapter.setData(getFragments(data), data);
                            for (int i = 0; i < data.size(); i++) {
                                classifyIdList.add(i,data.get(i).getClassifyId());
                                if (classId == data.get(i).getClassifyId()) {
                                    ((OnlineListActivity) activity).setPagerPos(i);
                                    break;
                                }
                            }
                        }

                    }
                });
    }

    public List<Integer> getCurrentClassifyIdList(){
        return classifyIdList;
    }

    private List<Fragment> getFragments(List<RxLearningClass> classList) {
        List<Fragment> fragments = new ArrayList<>();
        for (int i = 0; i < classList.size(); i++) {

            Fragment fragment = new FragmentOnline();
            Bundle bundle = new Bundle();
            bundle.putInt("id", classList.get(i).getClassifyId());
            bundle.putString("img", classList.get(i).getPictureurl());
            fragment.setArguments(bundle);
            fragments.add(fragment);
        }
        return fragments;
    }
}
