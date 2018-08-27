package com.qiantang.partybuilding.module.assistant.view;

import android.databinding.DataBindingUtil;

import com.qiantang.partybuilding.BaseBindActivity;
import com.qiantang.partybuilding.R;
import com.qiantang.partybuilding.databinding.ActivityThinkingDetialBinding;
import com.qiantang.partybuilding.module.assistant.viewmodel.ThinkDetialViewModel;

/**
 * Created by zhaoyong bai on 2018/6/11.
 */
public class ThinkingDetialActivity extends BaseBindActivity {
    private ThinkDetialViewModel viewModel;
    private ActivityThinkingDetialBinding binding;

    @Override
    protected void initBind() {
        viewModel = new ThinkDetialViewModel(this);
        binding=DataBindingUtil.setContentView(this,R.layout.activity_thinking_detial);
        binding.setViewModel(viewModel);
    }

    @Override
    public void initView() {
        binding.toolbar.setTitle("思想汇报");
        viewModel.getData();
    }

    @Override
    protected void viewModelDestroy() {
        viewModel.destroy();
    }
}
