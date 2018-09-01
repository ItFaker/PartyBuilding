package com.qiantang.partybuilding.module.assistant.view;

import android.databinding.DataBindingUtil;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.qiantang.partybuilding.BaseBindActivity;
import com.qiantang.partybuilding.R;
import com.qiantang.partybuilding.config.Config;
import com.qiantang.partybuilding.config.Event;
import com.qiantang.partybuilding.databinding.ActivityRecycleviewBinding;
import com.qiantang.partybuilding.module.assistant.viewmodel.MienViewModel;
import com.qiantang.partybuilding.module.index.adapter.IndexCommonAdapter;
import com.qiantang.partybuilding.utils.ActivityUtil;
import com.qiantang.partybuilding.utils.RecycleViewUtils;

/**
 * Created by zhaoyong bai on 2018/6/8.
 */
public class MeetingActivity extends BaseBindActivity {
    private IndexCommonAdapter adapter;
    private MienViewModel viewModel;
    private ActivityRecycleviewBinding binding;

    @Override
    protected void initBind() {
        adapter = new IndexCommonAdapter(R.layout.item_meeting_record);
        viewModel = new MienViewModel(this, adapter);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_recycleview);
    }

    @Override
    public void initView() {
        binding.toolbar.setTitle("会议纪要");
        binding.toolbar.setResId(R.mipmap.icon_search_black);
        initRv(binding.rv);
        viewModel.getData(1,6);
        initRefresh(binding.cptr);
    }

    @Override
    public void refreshData() {
        super.refreshData();
        viewModel.getData(1,6);
    }

    private void initRv(RecyclerView rv) {
        rv.setLayoutManager(new LinearLayoutManager(this));
        rv.setAdapter(adapter);
        adapter.setEnableLoadMore(true);
        adapter.setLoadMoreView(RecycleViewUtils.getLoadMoreView());
        rv.addOnItemTouchListener(viewModel.onItemTouchListener());
        adapter.setOnLoadMoreListener(() -> viewModel.loadMore(), rv);
    }

    @Override
    public void onClick(View view) {
        super.onClick(view);
        switch (view.getId()) {
            case R.id.iv_right:
                ActivityUtil.startSearchActivity(this, Event.SEARCH_MEETING);
                break;
        }
    }

    @Override
    protected void viewModelDestroy() {
        viewModel.destroy();
    }
}
