package com.qiantang.partybuilding.module.assistant.view;

import android.databinding.DataBindingUtil;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.qiantang.partybuilding.BaseBindActivity;
import com.qiantang.partybuilding.R;
import com.qiantang.partybuilding.databinding.ActivityRecycleviewBinding;
import com.qiantang.partybuilding.module.assistant.adapter.MsgAdapter;
import com.qiantang.partybuilding.module.assistant.viewmodel.MsgViewModel;
import com.qiantang.partybuilding.utils.RecycleViewUtils;

/**
 * Created by zhaoyong bai on 2018/6/8.
 */
public class MsgActivity extends BaseBindActivity {
    private MsgAdapter adapter;
    private MsgViewModel viewModel;
    private ActivityRecycleviewBinding binding;

    @Override
    protected void initBind() {
        adapter = new MsgAdapter(R.layout.item_msg_list);
        viewModel = new MsgViewModel(this, adapter);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_recycleview);
    }

    @Override
    public void initView() {
        binding.toolbar.setTitle("通知公告");
        initRv(binding.rv);
        viewModel.getData(1);
        initRefresh(binding.cptr);
    }

    @Override
    public void refreshData() {
        super.refreshData();
        viewModel.getData(1);
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
    protected void viewModelDestroy() {
        viewModel.destroy();
    }
}
