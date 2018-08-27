package com.qiantang.partybuilding.module.mine.view;

import android.databinding.DataBindingUtil;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.qiantang.partybuilding.BaseBindActivity;
import com.qiantang.partybuilding.R;
import com.qiantang.partybuilding.databinding.ActivityRecycleviewBinding;
import com.qiantang.partybuilding.module.assistant.adapter.ActivityAdapter;
import com.qiantang.partybuilding.module.mine.adapter.MyActivityAdapter;
import com.qiantang.partybuilding.module.mine.viewmodel.MyActivityViewModel;
import com.qiantang.partybuilding.utils.RecycleViewUtils;

/**
 * Created by zhaoyong bai on 2018/5/30.
 * 我的活动
 */
public class MyActivity extends BaseBindActivity {
    private MyActivityViewModel viewModel;
    private MyActivityAdapter adapter;
    private ActivityRecycleviewBinding binding;

    @Override
    protected void initBind() {
        adapter=new MyActivityAdapter(R.layout.item_my_activity);
        viewModel = new MyActivityViewModel(this, adapter);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_recycleview);
    }

    @Override
    public void initView() {
        binding.toolbar.setTitle("我的活动");
        binding.toolbar.setIsHide(true);
        initRv(binding.rv);
    }

    private void initRv(RecyclerView rv) {
        rv.setLayoutManager(new LinearLayoutManager(this));
        rv.setAdapter(adapter);
        rv.addOnItemTouchListener(viewModel.onItemTouchListener());
        adapter.setEnableLoadMore(true);
        adapter.setLoadMoreView(RecycleViewUtils.getLoadMoreView());
        adapter.setOnLoadMoreListener(()->viewModel.loadMore(),rv);
    }

    @Override
    protected void viewModelDestroy() {
        viewModel.destroy();
    }
}
