package com.qiantang.partybuilding.module.spalsh.view;

import android.databinding.DataBindingUtil;
import android.os.Handler;

import com.qiantang.partybuilding.BaseBindActivity;
import com.qiantang.partybuilding.R;
import com.qiantang.partybuilding.config.CacheKey;
import com.qiantang.partybuilding.config.Config;
import com.qiantang.partybuilding.databinding.ActivitySplashBinding;
import com.qiantang.partybuilding.module.spalsh.viewmodel.SplashViewModel;
import com.qiantang.partybuilding.utils.ActivityUtil;
import com.qiantang.partybuilding.utils.SharedPreferences;

/**
 * Created by zhaoyong bai on 2018/5/21.
 */
public class SplahsActivity extends BaseBindActivity {
    private SplashViewModel viewModel;
    private ActivitySplashBinding binding;

    @Override
    protected void initBind() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_splash);
        viewModel = new SplashViewModel(this);
        binding.setViewModel(viewModel);
    }

    @Override
    public void initView() {
        if (SharedPreferences.getInstance().getBoolean(CacheKey.FIRST, true)) {
            startGuide();
        } else {
            intent();
        }
    }

    private void startGuide() {
        ActivityUtil.startGuideActivity(this);
    }

    private void intent() {
        new Handler().postDelayed(() -> viewModel.jumpNextPage(), Config.SPLASH_TIME);
    }


    @Override
    protected void viewModelDestroy() {
        viewModel.destroy();
    }
}
