package com.qiantang.partybuilding.module.login.view;

import android.databinding.DataBindingUtil;
import android.view.View;

import com.qiantang.partybuilding.BaseBindActivity;
import com.qiantang.partybuilding.R;
import com.qiantang.partybuilding.module.login.viewmodel.SimpleViewModel;
import com.qiantang.partybuilding.databinding.ActivitySimpleInfoBinding;

/**
 * Created by zhaoyong bai on 2018/6/4.
 */
public class SimpleInfoActivity extends BaseBindActivity {
    private SimpleViewModel viewModel;
    private ActivitySimpleInfoBinding binding;

    @Override
    protected void initBind() {
        viewModel = new SimpleViewModel(this);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_simple_info);
        binding.setViewModel(viewModel);
    }

    @Override
    public void initView() {
        binding.toolbar.setTitle("完善信息");
    }

    @Override
    public void onClick(View view) {
        super.onClick(view);
        switch (view.getId()) {
            case R.id.iv_arrow:
            case R.id.tv_nation:
                viewModel.nation();
                break;
            case R.id.iv_arrow_id:
            case R.id.tv_id:
                viewModel.idPop();
                break;
            case R.id.btn_confirm:
                viewModel.commit();
                break;
        }
    }

    @Override
    protected void viewModelDestroy() {
        viewModel.destroy();
    }
}
