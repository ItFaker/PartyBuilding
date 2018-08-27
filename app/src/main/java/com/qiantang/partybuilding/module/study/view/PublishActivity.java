package com.qiantang.partybuilding.module.study.view;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;


import com.qiantang.partybuilding.BaseBindActivity;
import com.qiantang.partybuilding.R;
import com.qiantang.partybuilding.config.Config;
import com.qiantang.partybuilding.databinding.ActivityPublishBinding;
import com.qiantang.partybuilding.module.study.adapter.PublishAdapter;
import com.qiantang.partybuilding.module.study.viewmodel.PublishViewModel;
import com.qiantang.partybuilding.utils.MyTextUtils;
import com.qiantang.partybuilding.utils.StringUtil;
import com.qiantang.partybuilding.utils.ToastUtil;
import com.qiantang.partybuilding.utils.permissions.EasyPermission;
import com.qiantang.partybuilding.utils.permissions.PermissionCode;

import java.util.List;


public class PublishActivity extends BaseBindActivity implements EasyPermission.PermissionCallback {
    private PublishAdapter adapter;
    private ActivityPublishBinding binding;
    private PublishViewModel viewModel;

    @Override
    protected void initBind() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_publish);
        adapter = new PublishAdapter(R.layout.item_publish);
        viewModel = new PublishViewModel(this, adapter);
        binding.setViewModel(viewModel);
    }

    @Override
    public void initView() {
        initToolBar();
        initRV();
    }

    private void initToolBar() {
        binding.toolbar.setTitle("发表感悟");
        binding.toolbar.setRight("发表");
    }

    private void initRV() {
        binding.rv.setLayoutManager(new GridLayoutManager(this, 3, LinearLayoutManager.VERTICAL, false));
        binding.rv.setAdapter(adapter);
        binding.rv.addOnItemTouchListener(viewModel.itemClickListener());
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onClick(View view) {
        closeInput();
        switch (view.getId()) {
            case R.id.tv_right:
                viewModel.content = MyTextUtils.getEditTextString(binding.etContent);
                viewModel.uploadImage();
                break;
            case R.id.iv_back:
                onBackPressed();
                break;
        }
    }

    @Override
    public void onPermissionGranted(int requestCode, List<String> perms) {
        if (requestCode == PermissionCode.RG_CAMERA_PERM) {
            if (viewModel != null) {
                viewModel.openCamera();
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        EasyPermission.onRequestPermissionsResult(this, requestCode, permissions, grantResults);
    }

    @Override
    public void onPermissionDenied(int requestCode, List<String> perms) {
        switch (requestCode) {
            case PermissionCode.RG_CAMERA_PERM:
                ToastUtil.toast(getString(R.string.rationale_camera));
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        binding.rv.removeAllViews();
        adapter = null;
    }

    @Override
    protected void viewModelDestroy() {
        if (viewModel != null) viewModel.destroy();
    }
}
