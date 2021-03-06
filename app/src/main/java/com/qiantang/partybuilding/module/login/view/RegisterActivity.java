package com.qiantang.partybuilding.module.login.view;

import android.databinding.DataBindingUtil;
import android.view.View;

import com.qiantang.partybuilding.BaseBindActivity;
import com.qiantang.partybuilding.R;
import com.qiantang.partybuilding.databinding.ActivityRegisterBinding;
import com.qiantang.partybuilding.module.login.viewmodel.RegisterViewModel;
import com.qiantang.partybuilding.network.URLs;
import com.qiantang.partybuilding.utils.ActivityUtil;
import com.qiantang.partybuilding.utils.WebUtil;

/**
 * Created by zhaoyong bai on 2018/6/4.
 */
public class RegisterActivity extends BaseBindActivity {
    private RegisterViewModel viewModel;
    private ActivityRegisterBinding binding;

    @Override
    protected void initBind() {
        viewModel = new RegisterViewModel(this);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_register);
        binding.setViewModel(viewModel);
    }

    @Override
    public void initView() {
        binding.toolbar.setTitle("注册");
    }

    @Override
    public void onClick(View view) {
        super.onClick(view);
        switch (view.getId()) {
            case R.id.btn_confirm:
                viewModel.register();
                break;
            case R.id.tv_count:
                viewModel.sendMsg();
                closeInput();
                break;
            case R.id.tv_protocal:
                WebUtil.jumpWeb(this, URLs.USER_PROTOCOL+4, "用户协议");
                break;
        }
    }

    @Override
    protected void viewModelDestroy() {
        viewModel.destroy();
    }
}
