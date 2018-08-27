package com.qiantang.partybuilding.module.index.view;

import android.databinding.DataBindingUtil;

import com.qiantang.partybuilding.BaseBindActivity;
import com.qiantang.partybuilding.R;
import com.qiantang.partybuilding.databinding.ActivityLearningListBinding;
import com.qiantang.partybuilding.module.index.adapter.LearningViewPagerAdapter;
import com.qiantang.partybuilding.module.index.viewmodel.LearningListViewModel;
import com.qiantang.partybuilding.module.index.viewmodel.OnlineListViewModel;

/**
 * Created by zhaoyong bai on 2018/6/13.
 */
public class OnlineListActivity extends BaseBindActivity {
    private ActivityLearningListBinding binding;
    private LearningViewPagerAdapter viewPagerAdapter;
    private OnlineListViewModel viewModel;

    @Override
    protected void initBind() {
        viewPagerAdapter = new LearningViewPagerAdapter(getSupportFragmentManager());
        viewModel = new OnlineListViewModel(this, viewPagerAdapter);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_learning_list);
    }

    @Override
    public void initView() {
        binding.toolbar.setTitle("理论在线");
        binding.toolbar.setResId(R.mipmap.icon_search_black);
        binding.tablayout.setupWithViewPager(binding.viewpager);
        binding.viewpager.setAdapter(viewPagerAdapter);
        viewModel.getData();
    }


    @Override
    protected void viewModelDestroy() {
        viewModel.destroy();
    }
}
