package com.qiantang.partybuilding.module.mine.fragment;

import android.databinding.DataBindingUtil;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.qiantang.partybuilding.BaseBindActivity;
import com.qiantang.partybuilding.BaseBindFragment;
import com.qiantang.partybuilding.R;
import com.qiantang.partybuilding.config.Config;
import com.qiantang.partybuilding.databinding.ActivitySpeechStudyBinding;
import com.qiantang.partybuilding.databinding.FragmentRecycleviewBinding;
import com.qiantang.partybuilding.module.index.adapter.SpechAdapter;
import com.qiantang.partybuilding.module.index.viewmodel.SpeechStudyViewModel;
import com.qiantang.partybuilding.module.mine.viewmodel.SpeechFragmentViewModel;
import com.qiantang.partybuilding.utils.ActivityUtil;
import com.qiantang.partybuilding.utils.RecycleViewUtils;

/**
 * Created by zhaoyong bai on 2018/5/22.
 * 系列讲话
 */
public class SpeechStudyFragment extends BaseBindFragment {
    private SpeechFragmentViewModel viewModel;
    private SpechAdapter adapter;
    private FragmentRecycleviewBinding binding;


    @Override
    public View initBinding(LayoutInflater inflater, ViewGroup container) {
        adapter = new SpechAdapter(R.layout.item_index_speech);
        viewModel = new SpeechFragmentViewModel(this, adapter);
        binding = DataBindingUtil.inflate(getLayoutInflater(),R.layout.fragment_recycleview,container,false);
        return binding.getRoot();
    }

    @Override
    public void initView() {
        initRv(binding.rv);
        viewModel.testData();
    }

    private void initRv(RecyclerView rv) {
        adapter.setLoadMoreView(RecycleViewUtils.getLoadMoreView());
        adapter.setEnableLoadMore(true);
        adapter.setOnLoadMoreListener(() -> viewModel.onLoadMore(), rv);
        rv.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        rv.setAdapter(adapter);
        rv.addOnItemTouchListener(viewModel.onItemTouchListener());
    }

    @Override
    protected void viewModelDestroy() {
        viewModel.destroy();
    }
}
