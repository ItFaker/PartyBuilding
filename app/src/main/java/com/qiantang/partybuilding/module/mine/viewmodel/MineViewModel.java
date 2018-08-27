package com.qiantang.partybuilding.module.mine.viewmodel;

import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.databinding.BindingAdapter;
import android.databinding.ObservableField;
import android.view.View;

import com.facebook.drawee.view.SimpleDraweeView;
import com.qiantang.partybuilding.BaseBindFragment;
import com.qiantang.partybuilding.MyApplication;
import com.qiantang.partybuilding.R;
import com.qiantang.partybuilding.base.ViewModel;
import com.qiantang.partybuilding.config.CacheKey;
import com.qiantang.partybuilding.config.Config;
import com.qiantang.partybuilding.config.Event;
import com.qiantang.partybuilding.modle.RxMyUserInfo;
import com.qiantang.partybuilding.utils.ACache;
import com.qiantang.partybuilding.utils.ActivityUtil;
import com.qiantang.partybuilding.BR;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * Created by zhaoyong bai on 2018/5/21.
 */
public class MineViewModel extends BaseObservable implements ViewModel {
    private BaseBindFragment fragment;
    private ObservableField<RxMyUserInfo> userBean = new ObservableField<>();

    public MineViewModel(BaseBindFragment fragment) {
        this.fragment = fragment;
        EventBus.getDefault().register(this);
        initData();
    }

    private void initData() {
        if (MyApplication.isLogin()) {
            MyApplication.mCache.getAsJSONBean(CacheKey.USER_INFO, RxMyUserInfo.class, new ACache.CacheResultListener<RxMyUserInfo>() {
                @Override
                public void onResult(RxMyUserInfo rxMyUserInfo) {
                    setUserBean(rxMyUserInfo);
                }
            });
        }
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rl_info:
                //个人档案
//                ActivityUtil.startCompeteActivity(fragment.getActivity(),"","");
                if (MyApplication.isLogin()) {
                    ActivityUtil.startInfoActivity(fragment.getActivity());
                } else {
                    ActivityUtil.startLoginActivity(fragment.getActivity());
                }
                break;
            case R.id.ll_month:
                //每月学习值
                if (MyApplication.isLogin()) {
                    ActivityUtil.startMonthScoreActivity(fragment.getActivity(),"");
                } else {
                    ActivityUtil.startLoginActivity(fragment.getActivity());
                }
                break;
            case R.id.ll_total:
                //累计学习值
                if (MyApplication.isLogin()) {
                    ActivityUtil.startTotalScoreActivity(fragment.getActivity());
                } else {
                    ActivityUtil.startLoginActivity(fragment.getActivity());
                }
                break;
            case R.id.fl_collection:
                if (MyApplication.isLogin()) {
                    ActivityUtil.startMyCollectionActivity(fragment.getActivity());
                } else {
                    ActivityUtil.startLoginActivity(fragment.getActivity());
                }

                break;
            case R.id.fl_activity:
                if (MyApplication.isLogin()) {
                    ActivityUtil.startMyActivity(fragment.getActivity());
                } else {
                    ActivityUtil.startLoginActivity(fragment.getActivity());
                }

                break;
            case R.id.fl_test:
                if (MyApplication.isLogin()) {
                    ActivityUtil.startMyTestActivity(fragment.getActivity());
                } else {
                    ActivityUtil.startLoginActivity(fragment.getActivity());
                }

                break;
            case R.id.fl_about_us:
                ActivityUtil.startAboutUsActivity(fragment.getActivity());
                break;
            case R.id.fl_setting:
                //设置
                ActivityUtil.startSettingActivity(fragment.getActivity());
                break;
        }
    }

    @BindingAdapter("avatar")
    public static void avatar(SimpleDraweeView sdv, String avatar) {
        sdv.setImageURI(Config.IMAGE_HOST + avatar);
    }

    //接收更新请求
    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void onEvent(RxMyUserInfo myUserInfo) {
        if (myUserInfo != null) {
            setUserBean(myUserInfo);
        }
    }

    //接收更新请求
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(Integer integer) {
        if (integer == Event.LOGOUT) {
            setUserBean(new RxMyUserInfo());
        }
    }


    @Bindable
    public RxMyUserInfo getUserBean() {
        return userBean.get();
    }

    public void setUserBean(RxMyUserInfo userBean) {
        this.userBean.set(userBean);
        notifyPropertyChanged(BR.userBean);
    }

    @Override
    public void destroy() {
        EventBus.getDefault().unregister(this);
    }
}
