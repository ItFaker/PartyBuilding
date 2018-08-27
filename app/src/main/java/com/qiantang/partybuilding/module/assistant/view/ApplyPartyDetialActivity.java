package com.qiantang.partybuilding.module.assistant.view;

import android.databinding.DataBindingUtil;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.qiantang.partybuilding.BaseBindActivity;
import com.qiantang.partybuilding.R;
import com.qiantang.partybuilding.databinding.ActivityApplyPartyDetialBinding;
import com.qiantang.partybuilding.module.assistant.viewmodel.ApplyDetailViewModel;
import com.qiantang.partybuilding.module.study.adapter.StudyImageAdapter;

/**
 * Created by zhaoyong bai on 2018/5/30.
 */
public class ApplyPartyDetialActivity extends BaseBindActivity {
    private ApplyDetailViewModel viewModel;
    private ActivityApplyPartyDetialBinding binding;
    private StudyImageAdapter adapter;
    @Override
    protected void initBind() {
        adapter=new StudyImageAdapter(R.layout.item_study_image);
        viewModel=new ApplyDetailViewModel(this,adapter);
        binding= DataBindingUtil.setContentView(this, R.layout.activity_apply_party_detial);
        binding.setViewModel(viewModel);
    }

    @Override
    public void initView() {
        binding.toolbar.setTitle("入党申请");
        binding.toolbar.setIsHide(false);
        initRv(binding.rv);
    }

    private void initRv(RecyclerView rv) {
        rv.setLayoutManager(new GridLayoutManager(this,3));
        rv.setAdapter(adapter);
        rv.addOnItemTouchListener(viewModel.onItemTouchListener());
    }

    @Override
    protected void viewModelDestroy() {
        viewModel.destroy();
    }
}
