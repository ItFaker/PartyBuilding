package com.qiantang.partybuilding.module.assistant.view;

import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.WindowManager;

import com.qiantang.partybuilding.BaseBindActivity;
import com.qiantang.partybuilding.R;
import com.qiantang.partybuilding.adapter.PublishImgAdapter;
import com.qiantang.partybuilding.databinding.ActivityApplyPartyBinding;
import com.qiantang.partybuilding.module.assistant.viewmodel.ApplyPartyViewModel;
import com.qiantang.partybuilding.utils.ToastUtil;
import com.qiantang.partybuilding.utils.permissions.EasyPermission;
import com.qiantang.partybuilding.utils.permissions.PermissionCode;

import java.util.List;

/**
 * Created by zhaoyong bai on 2018/5/30.
 */
public class ApplyPartyActivity extends BaseBindActivity implements EasyPermission.PermissionCallback {
    private PublishImgAdapter adapter;
    private ApplyPartyViewModel viewModel;
    private ActivityApplyPartyBinding binding;

    @Override
    protected void initBind() {
        adapter = new PublishImgAdapter(R.layout.item_publish);
        viewModel = new ApplyPartyViewModel(this, adapter);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_apply_party);
        binding.setViewModel(viewModel);
    }

    @Override
    public void initView() {
        mImmersionBar.keyboardMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN).init();
        binding.toolbar.setTitle("入党申请");
        binding.toolbar.setIsHide(true);
        binding.etContent.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                viewModel.setInputCount(charSequence.length());
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        initRV();
    }

    private void initRV() {
        binding.rv.setLayoutManager(new GridLayoutManager(this, 3, LinearLayoutManager.VERTICAL, false));
        binding.rv.setAdapter(adapter);
        binding.rv.addOnItemTouchListener(viewModel.itemClickListener());
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                onBackPressed();
                break;
            case R.id.btn_confirm:
                viewModel.uploadImage();
                break;
        }
    }

    @Override
    protected void viewModelDestroy() {
        viewModel.destroy();
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
}
