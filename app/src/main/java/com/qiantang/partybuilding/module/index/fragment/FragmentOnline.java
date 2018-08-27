package com.qiantang.partybuilding.module.index.fragment;

import android.databinding.DataBindingUtil;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.qiantang.partybuilding.BaseBindFragment;
import com.qiantang.partybuilding.R;
import com.qiantang.partybuilding.databinding.FragmentRecycleviewBinding;
import com.qiantang.partybuilding.databinding.ViewHeadImageBinding;
import com.qiantang.partybuilding.module.index.adapter.LearnAdapter;
import com.qiantang.partybuilding.module.index.viewmodel.LearnFragmentViewModel;
import com.qiantang.partybuilding.module.index.viewmodel.OnlineFragmentViewModel;
import com.qiantang.partybuilding.utils.AutoUtils;
import com.qiantang.partybuilding.utils.RecycleViewUtils;

/**
 * Created by zhaoyong bai on 2018/6/13.
 */
public class FragmentOnline extends BaseBindFragment {
    private FragmentRecycleviewBinding binding;
    private LearnAdapter learnAdapter;
    private OnlineFragmentViewModel viewModel;
    private ViewHeadImageBinding headImageBinding;

    @Override
    public View initBinding(LayoutInflater inflater, ViewGroup container) {
        learnAdapter = new LearnAdapter(R.layout.item_online_list);
        viewModel = new OnlineFragmentViewModel(this, learnAdapter);
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_recycleview, container, false);
        headImageBinding=DataBindingUtil.inflate(inflater,R.layout.view_head_image,container,false);
        return binding.getRoot();
    }

    @Override
    public void initView() {
        AutoUtils.auto(headImageBinding.getRoot());

        binding.rv.setLayoutManager(new LinearLayoutManager(getContext()));
        learnAdapter.addHeaderView(headImageBinding.getRoot());
        binding.rv.setAdapter(learnAdapter);
        learnAdapter.setEnableLoadMore(true);
        learnAdapter.setLoadMoreView(RecycleViewUtils.getLoadMoreView());
        learnAdapter.setOnLoadMoreListener(() -> viewModel.loadMore(), binding.rv);
        binding.rv.addOnItemTouchListener(viewModel.onItemTouchListener());
    }

    public void setHeadImage(String url){
        headImageBinding.sdv.setImageURI(url);
    }

    @Override
    protected void viewModelDestroy() {
        viewModel.destroy();
    }
}
