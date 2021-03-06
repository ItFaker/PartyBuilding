package com.qiantang.partybuilding.module.assistant.view;

import android.databinding.DataBindingUtil;
import android.view.View;

import com.qiantang.partybuilding.BaseBindActivity;
import com.qiantang.partybuilding.R;
import com.qiantang.partybuilding.databinding.ActivityReportBinding;
import com.qiantang.partybuilding.module.assistant.viewmodel.ReportViewModel;

/**
 * Created by zhaoyong bai on 2018/6/1.
 */
public class ReportActivity extends BaseBindActivity {
    private ReportViewModel viewModel;
    private ActivityReportBinding binding;

    @Override
    protected void initBind() {
        viewModel = new ReportViewModel(this);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_report);
        binding.setViewModel(viewModel);
    }

    @Override
    public void initView() {
        binding.toolbar.setTitle("思想汇报");
        binding.toolbar.setIsHide(true);
    }

    @Override
    public void onClick(View view) {
        super.onClick(view);
        switch (view.getId()) {
            case R.id.btn_confirm:
                viewModel.publish();
                break;
        }
    }

    @Override
    protected void viewModelDestroy() {
        viewModel.destroy();
    }
}
