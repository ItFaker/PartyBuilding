package com.qiantang.partybuilding.module.index.view;

import android.databinding.DataBindingUtil;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.qiantang.partybuilding.BaseBindActivity;
import com.qiantang.partybuilding.R;
import com.qiantang.partybuilding.config.Config;
import com.qiantang.partybuilding.config.Event;
import com.qiantang.partybuilding.databinding.ActivitySpeechStudyBinding;
import com.qiantang.partybuilding.databinding.ActivityVideoStudyBinding;
import com.qiantang.partybuilding.module.index.adapter.IndexCommonAdapter;
import com.qiantang.partybuilding.module.index.adapter.SpechAdapter;
import com.qiantang.partybuilding.module.index.viewmodel.SpeechStudyViewModel;
import com.qiantang.partybuilding.module.index.viewmodel.VideoStudyViewModel;
import com.qiantang.partybuilding.utils.ActivityUtil;
import com.qiantang.partybuilding.utils.RecycleViewUtils;

/**
 * Created by zhaoyong bai on 2018/5/22.
 * 系列讲话
 */
public class SpeechStudyActivity extends BaseBindActivity {
    private SpeechStudyViewModel viewModel;
    private SpechAdapter adapter;
    private ActivitySpeechStudyBinding binding;

    @Override
    protected void initBind() {
        adapter = new SpechAdapter(R.layout.item_index_speech);
        viewModel = new SpeechStudyViewModel(this, adapter);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_speech_study);
        binding.setViewModel(viewModel);
    }

    @Override
    public void initView() {
        binding.toolbar.setTitle("系列讲话");
        binding.toolbar.setIsHide(false);
        binding.toolbar.setResId(R.mipmap.icon_search_black);
//        binding.toolbar.ivRight.setOnClickListener(this::onClick);
        initRv(binding.rv);
        viewModel.testData();
    }

    private void initRv(RecyclerView rv) {
        adapter.setLoadMoreView(RecycleViewUtils.getLoadMoreView());
        adapter.setEnableLoadMore(true);
        adapter.setOnLoadMoreListener(() -> viewModel.onLoadMore(), rv);
        rv.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        rv.setAdapter(adapter);
        rv.addOnItemTouchListener(viewModel.onItemTouchListener());
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                onBackPressed();
                break;
            case R.id.iv_right:
                ActivityUtil.startSearchActivity(this, Event.SEARCH_SPEECH_STUDY);
                break;
        }
    }

    @Override
    protected void viewModelDestroy() {
        viewModel.destroy();
    }
}
