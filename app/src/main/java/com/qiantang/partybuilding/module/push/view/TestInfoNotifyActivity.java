package com.qiantang.partybuilding.module.push.view;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.text.TextUtils;
import android.view.View;

import com.google.gson.Gson;
import com.qiantang.partybuilding.BaseBindActivity;
import com.qiantang.partybuilding.BaseNotifyBindActivity;
import com.qiantang.partybuilding.R;
import com.qiantang.partybuilding.databinding.ActivityTestInfoBinding;
import com.qiantang.partybuilding.databinding.ActivityTestInfoNotifyBinding;
import com.qiantang.partybuilding.modle.RxMessageExtra;
import com.qiantang.partybuilding.module.index.viewmodel.TestInfoViewModel;
import com.qiantang.partybuilding.module.push.viewmodel.TestInfoNotifyViewModel;
import com.qiantang.partybuilding.utils.ActivityUtil;
import com.qiantang.partybuilding.utils.ToastUtil;

import org.android.agoo.common.AgooConstants;

/**
 * Created by zhaoyong bai on 2018/6/14.
 */
public class TestInfoNotifyActivity extends BaseNotifyBindActivity {
    private ActivityTestInfoNotifyBinding binding;
    private TestInfoNotifyViewModel viewModel;
    @Override
    protected void initBind() {
        viewModel=new TestInfoNotifyViewModel(this);
        binding=DataBindingUtil.setContentView(this, R.layout.activity_test_info_notify);
        binding.setViewModel(viewModel);
    }

    @Override
    public void initView() {
        binding.toolbar.setTitle("考试评测");
    }

    @Override
    public void onMessage(Intent intent) {
        super.onMessage(intent);
        String body=intent.getStringExtra(AgooConstants.MESSAGE_ACCS_EXTRA);
        if (!TextUtils.isEmpty(body)){
            RxMessageExtra rxMessageExtra=new Gson().fromJson(body,RxMessageExtra.class);
            if (rxMessageExtra!=null){
                viewModel.getData(rxMessageExtra.getCountId());
            }
        }
    }

    @Override
    public void onClick(View view) {
        super.onClick(view);
        switch (view.getId()){
            case R.id.btn_confirm:
                ActivityUtil.startTestDeitalInfoActivity(this,viewModel.getTestInfo().getQuestionnaire_id(),viewModel.getTestInfo().getClippingtime()*60,viewModel.getTestInfo().getNumber(),false);
                break;
        }
    }

    @Override
    protected void viewModelDestroy() {
        viewModel.destroy();
    }
}
