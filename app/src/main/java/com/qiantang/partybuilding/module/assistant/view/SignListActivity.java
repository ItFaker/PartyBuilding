package com.qiantang.partybuilding.module.assistant.view;

import android.databinding.DataBindingUtil;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.qiantang.partybuilding.BaseBindActivity;
import com.qiantang.partybuilding.R;
import com.qiantang.partybuilding.module.assistant.adapter.SignListAdapter;
import com.qiantang.partybuilding.module.assistant.viewmodel.SignListViewModel;
import com.qiantang.partybuilding.databinding.ActivityRecycleviewBinding;
import com.qiantang.partybuilding.utils.RecycleViewUtils;

/**
 * Created by zhaoyong bai on 2018/6/10.
 */
public class SignListActivity extends BaseBindActivity {
    private SignListAdapter adapter;
    private SignListViewModel viewModel;
    private ActivityRecycleviewBinding binding;

    @Override
    protected void initBind() {
        adapter = new SignListAdapter(R.layout.item_sign_list);
        viewModel = new SignListViewModel(this, adapter);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_recycleview);
    }

    @Override
    public void initView() {
        binding.toolbar.setTitle("签到记录");
        initRv(binding.rv);
        viewModel.getdata();
    }

    private void initRv(RecyclerView rv) {
        rv.setLayoutManager(new LinearLayoutManager(this));
        rv.setAdapter(adapter);
        adapter.setLoadMoreView(RecycleViewUtils.getLoadMoreView());
        adapter.setEnableLoadMore(true);
        adapter.setOnLoadMoreListener(() -> viewModel.loadMore(), rv);
        rv.addOnItemTouchListener(viewModel.onItemTouchListener());
    }

    @Override
    protected void viewModelDestroy() {
        viewModel.destroy();
    }
}
