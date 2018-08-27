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
import com.qiantang.partybuilding.databinding.FragmentRecycleviewBinding;
import com.qiantang.partybuilding.module.index.adapter.IndexCommonAdapter;
import com.qiantang.partybuilding.module.index.viewmodel.ParagonViewModel;
import com.qiantang.partybuilding.module.mine.viewmodel.ParagonFragmentViewModel;
import com.qiantang.partybuilding.utils.RecycleViewUtils;

/**
 * Created by zhaoyong bai on 2018/6/17.
 */
public class ParagonFragment extends BaseBindFragment {
    private IndexCommonAdapter adapter;
    private ParagonFragmentViewModel viewModel;
    private FragmentRecycleviewBinding binding;



    @Override
    public View initBinding(LayoutInflater inflater, ViewGroup container) {
        adapter = new IndexCommonAdapter(R.layout.item_paragon);
        viewModel = new ParagonFragmentViewModel(this, adapter);
        binding = DataBindingUtil.inflate(getLayoutInflater(), R.layout.fragment_recycleview,container,false);
        return binding.getRoot();
    }

    @Override
    public void initView() {
        initRv(binding.rv);
        viewModel.getData();
    }

    private void initRv(RecyclerView rv) {
        adapter.setEnableLoadMore(true);
        adapter.setLoadMoreView(RecycleViewUtils.getLoadMoreView());
        rv.setLayoutManager(new LinearLayoutManager(getContext()));
        rv.setAdapter(adapter);
        rv.addOnItemTouchListener(viewModel.onItemTouchListener());
        adapter.setOnLoadMoreListener(()->viewModel.loadMore(),rv);
    }

    @Override
    protected void viewModelDestroy() {
        viewModel.destroy();
    }
}
