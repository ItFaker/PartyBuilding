package com.qiantang.partybuilding.module.mine.view;

import android.databinding.DataBindingUtil;
import android.support.v4.app.Fragment;

import com.qiantang.partybuilding.BaseBindActivity;
import com.qiantang.partybuilding.R;
import com.qiantang.partybuilding.adapter.SimpleFragmentAdapter;
import com.qiantang.partybuilding.databinding.ActivityModifyPhoneBinding;
import com.qiantang.partybuilding.module.mine.fragment.BindPhoneFragment;
import com.qiantang.partybuilding.module.mine.fragment.VerifyPhoneFragment;
import com.qiantang.partybuilding.module.mine.viewmodel.ModifyPhoneViewModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhaoyong bai on 2018/5/31.
 */
public class ModifyPhoneActivity extends BaseBindActivity {
    private ModifyPhoneViewModel viewModel;
    private ActivityModifyPhoneBinding binding;

    @Override
    protected void initBind() {
        viewModel = new ModifyPhoneViewModel(this);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_modify_phone);
        binding.setViewModel(viewModel);
    }

    @Override
    public void initView() {
        binding.toolbar.setTitle("修改手机号");
        binding.toolbar.setIsHide(true);
        binding.viewpager.setAdapter(new SimpleFragmentAdapter(getSupportFragmentManager(), getFragments(), new String[]{"", ""}));
    }

    private List<Fragment> getFragments() {
        List<Fragment> fragments = new ArrayList<>();
        fragments.add(new VerifyPhoneFragment());
        fragments.add(new BindPhoneFragment());
        return fragments;
    }

    public void nextStep() {
        binding.viewpager.setCurrentItem(1);
        viewModel.setStepNext(false);
    }

    @Override
    protected void viewModelDestroy() {
        viewModel.destroy();
    }
}
