package com.qiantang.partybuilding.module.mine.fragment;

import android.databinding.DataBindingUtil;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.qiantang.partybuilding.BaseBindFragment;
import com.qiantang.partybuilding.R;
import com.qiantang.partybuilding.databinding.FragmentBindPhoneBinding;
import com.qiantang.partybuilding.databinding.FragmentVerifyPhoneBinding;
import com.qiantang.partybuilding.module.mine.viewmodel.FragmentBindViewModel;
import com.qiantang.partybuilding.module.mine.viewmodel.FragmentVerifyViewModel;

/**
 * Created by zhaoyong bai on 2018/5/31.
 */
public class BindPhoneFragment extends BaseBindFragment {
    private FragmentBindViewModel viewModel;
    private FragmentBindPhoneBinding binding;

    @Override
    public View initBinding(LayoutInflater inflater, ViewGroup container) {
        viewModel = new FragmentBindViewModel(this);
        binding=DataBindingUtil.inflate(inflater,R.layout.fragment_bind_phone,container,false);
        binding.setViewModel(viewModel);
        return binding.getRoot();
    }

    @Override
    public void initView() {

    }

    @Override
    protected void viewModelDestroy() {
        viewModel.destroy();
    }
}
