package com.qiantang.partybuilding.module.assistant.view;

import android.databinding.DataBindingUtil;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.qiantang.partybuilding.BaseBindActivity;
import com.qiantang.partybuilding.R;
import com.qiantang.partybuilding.databinding.ActivityRecycleviewBinding;
import com.qiantang.partybuilding.module.assistant.adapter.StructureAdapter;
import com.qiantang.partybuilding.module.assistant.viewmodel.StructureViewModel;

/**
 * Created by zhaoyong bai on 2018/6/17.
 */
public class StructureActivity extends BaseBindActivity {
    private StructureAdapter adapter;
    private StructureViewModel viewModel;
    private ActivityRecycleviewBinding binding;

    @Override
    protected void initBind() {
        adapter = new StructureAdapter(null);
        viewModel = new StructureViewModel(adapter, this);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_recycleview);
    }

    @Override
    public void initView() {
        binding.toolbar.setTitle("组织架构");
        initRv(binding.rv);
    }

    private void initRv(RecyclerView rv) {
//        GridLayoutManager manager = new GridLayoutManager(this, 4);
//        manager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
//            @Override
//            public int getSpanSize(int position) {
//                return adapter.getItemViewType(position) == StructureAdapter.TYPE_PERSON ? 1 : manager.getSpanCount();
//            }
//        });
        rv.setAdapter(adapter);
        rv.setLayoutManager(new LinearLayoutManager(this));
        rv.addOnItemTouchListener(viewModel.onItemTouchListener());
    }

    @Override
    protected void viewModelDestroy() {
        viewModel.destroy();
    }
}
