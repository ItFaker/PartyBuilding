package com.qiantang.partybuilding.module.mine.view;

import android.databinding.DataBindingUtil;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.qiantang.partybuilding.BaseBindFragment;
import com.qiantang.partybuilding.R;
import com.qiantang.partybuilding.databinding.FragmentMineBinding;
import com.qiantang.partybuilding.module.mine.viewmodel.MineViewModel;

/**
 * Created by zhaoyong bai on 2018/5/21.
 */
public class MineFragment extends BaseBindFragment {
    private FragmentMineBinding binding;
    private MineViewModel viewModel;

    @Override
    public View initBinding(LayoutInflater inflater, ViewGroup container) {
        viewModel = new MineViewModel(this);
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_mine, container, false);
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
