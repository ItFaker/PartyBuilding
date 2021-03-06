package com.qiantang.partybuilding.module.assistant.view;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.qiantang.partybuilding.BaseBindActivity;
import com.qiantang.partybuilding.R;
import com.qiantang.partybuilding.config.Config;
import com.qiantang.partybuilding.databinding.ActivityRecycleviewBinding;
import com.qiantang.partybuilding.modle.RxIndexCommon;
import com.qiantang.partybuilding.module.assistant.viewmodel.MienViewModel;
import com.qiantang.partybuilding.module.index.adapter.IndexCommonAdapter;
import com.qiantang.partybuilding.utils.ActivityUtil;
import com.qiantang.partybuilding.utils.AppUtil;
import com.qiantang.partybuilding.utils.RecycleViewUtils;

/**
 * Created by zhaoyong bai on 2018/6/8.
 */
public class ThinkingActivity extends BaseBindActivity {
    private IndexCommonAdapter adapter;
    private MienViewModel viewModel;
    private ActivityRecycleviewBinding binding;

    @Override
    protected void initBind() {
        adapter = new IndexCommonAdapter(R.layout.item_meeting_record);
        viewModel = new MienViewModel(this, adapter);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_recycleview);
    }

    @Override
    public void initView() {
        binding.toolbar.setTitle("思想汇报");
        binding.toolbar.setRight("发表");
        initRv(binding.rv);
        viewModel.getThinkingData(1, 7);
        initRefresh(binding.cptr);
    }

    @Override
    public void refreshData() {
        super.refreshData();
        viewModel.getThinkingData(1, 7);
    }

    private void initRv(RecyclerView rv) {
        rv.setLayoutManager(new LinearLayoutManager(this));
        rv.setAdapter(adapter);
        adapter.setEnableLoadMore(true);
        adapter.setLoadMoreView(RecycleViewUtils.getLoadMoreView());
        rv.addOnItemTouchListener(viewModel.onItemTouchListener());
        adapter.setOnLoadMoreListener(() -> viewModel.loadMore(), rv);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100 && resultCode == RESULT_OK) {
            String title = data.getStringExtra("title");
            String id = data.getStringExtra("id");
            String time = AppUtil.getNowDate();
            time = time.substring(time.indexOf("-") + 1, time.indexOf(" "));
            RxIndexCommon indexCommon = new RxIndexCommon();
            indexCommon.setContentId(id);
            indexCommon.setTitle(title);
            indexCommon.setCreateTime(time);
            viewModel.insertData(indexCommon);
        }
    }

    @Override
    public void onClick(View view) {
        super.onClick(view);
        switch (view.getId()) {
            case R.id.tv_right:
                ActivityUtil.startReportActivity(this);
                break;
        }
    }

    @Override
    protected void viewModelDestroy() {
        viewModel.destroy();
    }
}
