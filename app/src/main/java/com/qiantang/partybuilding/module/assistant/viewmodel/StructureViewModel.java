package com.qiantang.partybuilding.module.assistant.viewmodel;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.qiantang.partybuilding.BaseBindActivity;
import com.qiantang.partybuilding.R;
import com.qiantang.partybuilding.base.ViewModel;
import com.qiantang.partybuilding.modle.RxStructureLevelOne;
import com.qiantang.partybuilding.modle.RxStructureLevelTwo;
import com.qiantang.partybuilding.modle.RxStructurePerson;
import com.qiantang.partybuilding.module.assistant.adapter.StructureAdapter;
import com.qiantang.partybuilding.network.NetworkSubscriber;
import com.qiantang.partybuilding.network.retrofit.ApiWrapper;
import com.qiantang.partybuilding.utils.ToastUtil;
import com.trello.rxlifecycle2.android.ActivityEvent;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhaoyong bai on 2018/6/17.
 */
public class StructureViewModel implements ViewModel {
    private StructureAdapter adapter;
    private BaseBindActivity activity;
    private List<MultiItemEntity> dataList = new ArrayList<>();
    private boolean isDealing = false;

    public StructureViewModel(StructureAdapter adapter, BaseBindActivity activity) {
        this.adapter = adapter;
        this.activity = activity;
        getOneData();
    }

    private void getOneData() {
        ApiWrapper.getInstance().dept1()
                .compose(activity.bindUntilEvent(ActivityEvent.DESTROY))
                .subscribe(new NetworkSubscriber<List<RxStructureLevelOne>>() {
                    @Override
                    public void onSuccess(List<RxStructureLevelOne> data) {
                        dataList.addAll(data);
                        adapter.setNewData(dataList);
                    }
                });
    }

    private void getTwoData(String id, int pos) {
        isDealing = true;
        ApiWrapper.getInstance().dept2(id)
                .compose(activity.bindUntilEvent(ActivityEvent.DESTROY))
                .doOnTerminate(() -> isDealing = false)
                .subscribe(new NetworkSubscriber<List<RxStructureLevelTwo>>() {
                    @Override
                    public void onSuccess(List<RxStructureLevelTwo> data) {
                        ((RxStructureLevelOne) dataList.get(pos)).setSubItems(data);
                        adapter.notifyDataSetChanged();
                        adapter.expand(pos);
                    }
                });
    }

    private void getPerson(String id, int pos) {
        isDealing = true;
        ApiWrapper.getInstance().dept3(id)
                .compose(activity.bindUntilEvent(ActivityEvent.DESTROY))
                .doOnTerminate(() -> isDealing = false)
                .subscribe(new NetworkSubscriber<List<RxStructurePerson>>() {
                    @Override
                    public void onSuccess(List<RxStructurePerson> data) {
                        String dept = ((RxStructureLevelTwo) dataList.get(pos)).getDept_name();
                        for (int i = 0; i < data.size(); i++) {
                            data.get(i).setDept(dept);
                        }
                        ((RxStructureLevelTwo) dataList.get(pos)).setSubItems(data);
                        adapter.notifyDataSetChanged();
                        adapter.expand(pos);
                    }
                });
    }

    public RecyclerView.OnItemTouchListener onItemTouchListener() {
        return new OnItemClickListener() {
            @Override
            public void onSimpleItemClick(BaseQuickAdapter adapter, View view, int position) {

            }

            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                super.onItemChildClick(adapter, view, position);
                switch (view.getId()) {
                    case R.id.iv_level_one:
                    case R.id.ll_level_one:
                        RxStructureLevelOne rxStructureLevelOne = (RxStructureLevelOne) dataList.get(position);
                        if (rxStructureLevelOne.getSubItems() == null || rxStructureLevelOne.getSubItems().size() == 0) {
                            getTwoData(rxStructureLevelOne.getDept_id(), position);
                        } else {
                            if (rxStructureLevelOne.isExpanded()) {
                                adapter.collapse(position);
                            } else {
                                adapter.expand(position);
                            }
                        }
                        break;
                    case R.id.iv_level_two:
                    case R.id.ll_level_two:
                        RxStructureLevelTwo rxStructureLevelTwo = (RxStructureLevelTwo) dataList.get(position);
                        if (rxStructureLevelTwo.getSubItems() == null || rxStructureLevelTwo.getSubItems().size() == 0) {
                            getPerson(rxStructureLevelTwo.getDept_id(), position);
                        } else {
                            if (rxStructureLevelTwo.isExpanded()) {
                                adapter.collapse(position);
                            } else {
                                adapter.expand(position);
                            }
                        }
                        break;
                }
            }
        };
    }

    @Override
    public void destroy() {

    }
}
