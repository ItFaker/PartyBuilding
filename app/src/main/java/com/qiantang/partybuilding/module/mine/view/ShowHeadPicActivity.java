package com.qiantang.partybuilding.module.mine.view;

import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.view.View;


import com.qiantang.partybuilding.BaseBindActivity;
import com.qiantang.partybuilding.R;
import com.qiantang.partybuilding.databinding.ActivityShowBinding;
import com.qiantang.partybuilding.module.mine.viewmodel.ChangeAvatarViewModel;
import com.qiantang.partybuilding.utils.ToastUtil;
import com.qiantang.partybuilding.utils.permissions.EasyPermission;
import com.qiantang.partybuilding.utils.permissions.PermissionCode;

import java.util.List;

import cn.finalteam.galleryfinal.GalleryFinal;

public class ShowHeadPicActivity extends BaseBindActivity implements EasyPermission.PermissionCallback {
    private static final int REQUEST_CODE_CAMERA = 1000;
    private ActivityShowBinding binding;
    private ChangeAvatarViewModel viewModel;

    @Override
    protected void initBind() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_show);
        viewModel = new ChangeAvatarViewModel(this);
        binding.setViewModel(viewModel);
    }

    @Override
    public void initView() {
        binding.toolbar.setTitle("修改头像");
        binding.sdvShow.setOnClickListener(view -> {
            viewModel.saveImg();
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                onBackPressed();
                break;
        }
    }


    @Override
    public void onPermissionGranted(int requestCode, List<String> perms) {
        switch (requestCode) {
            case PermissionCode.RG_CAMERA_PERM:
                GalleryFinal.openCamera(REQUEST_CODE_CAMERA, GalleryFinal.getCoreConfig().getFunctionConfig(),
                        viewModel.resultCallback);
                break;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[]
            grantResults) {
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
    protected void viewModelDestroy() {
        if (viewModel != null) viewModel.destroy();
    }
}
