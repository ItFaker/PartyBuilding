package com.qiantang.partybuilding.module.mine.viewmodel;

import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;
import android.text.TextUtils;
import android.view.View;

import com.qiantang.partybuilding.BR;
import com.qiantang.partybuilding.BaseBindFragment;
import com.qiantang.partybuilding.MyApplication;
import com.qiantang.partybuilding.R;
import com.qiantang.partybuilding.base.ViewModel;
import com.qiantang.partybuilding.config.CacheKey;
import com.qiantang.partybuilding.config.Event;
import com.qiantang.partybuilding.modle.HttpResult;
import com.qiantang.partybuilding.modle.RxMyUserInfo;
import com.qiantang.partybuilding.module.mine.view.ModifyPhoneActivity;
import com.qiantang.partybuilding.module.mine.view.ModifyPwdActivity;
import com.qiantang.partybuilding.network.NetworkSubscriber;
import com.qiantang.partybuilding.network.retrofit.ApiWrapper;
import com.qiantang.partybuilding.network.retrofit.RetrofitUtil;
import com.qiantang.partybuilding.utils.ACache;
import com.qiantang.partybuilding.utils.ToastUtil;
import com.qiantang.partybuilding.utils.WebUtil;
import com.trello.rxlifecycle2.android.FragmentEvent;
import com.umeng.message.PushAgent;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * Created by zhaoyong bai on 2018/5/31.
 */
public class FragmentNewPwdViewModel extends BaseObservable implements ViewModel {
    private BaseBindFragment fragment;
    public ObservableField<String> phone = new ObservableField<>();
    public ObservableField<String> newPwd = new ObservableField<>();

    public FragmentNewPwdViewModel(BaseBindFragment fragment) {
        this.fragment = fragment;

    }


    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_confirm:
                bindPhone();
                break;
        }
    }


    private void bindPhone() {
        if (TextUtils.isEmpty(getPhone())) {
            ToastUtil.toast("请输入新密码");
            return;
        }

        if (TextUtils.isEmpty(getNewPwd())) {
            ToastUtil.toast("请再次输入新密码");
            return;
        }
        if (!TextUtils.equals(getPhone(), getNewPwd())) {
            ToastUtil.toast("两次输入的密码不一致");
            return;
        }
        String oldPHone = MyApplication.mCache.getAsString(CacheKey.PHONE);
        ApiWrapper.getInstance().revise(getNewPwd())
                .compose(fragment.bindUntilEvent(FragmentEvent.DESTROY))
                .subscribe(new NetworkSubscriber<HttpResult>() {
                    @Override
                    public void onFail(RetrofitUtil.APIException e) {
                        super.onFail(e);
                        ToastUtil.toast("修改失败请重试");
                    }

                    @Override
                    public void onSuccess(HttpResult data) {
                       logOut();
                        fragment.getActivity().onBackPressed();
                    }
                });
    }
    /**
     * 退出登录
     */
    public void logOut() {
        PushAgent.getInstance(fragment.getContext()).deleteAlias(MyApplication.USER_ID, "party", (b, s) -> {

        });
        MyApplication.mCache.remove(CacheKey.USER_ID);
        MyApplication.mCache.remove(CacheKey.INFO);
        MyApplication.mCache.remove(CacheKey.USER_INFO);
        MyApplication.mCache.remove(CacheKey.PHONE);
        MyApplication.USER_ID = "";
        System.gc();
        WebUtil.removeCookie();
        EventBus.getDefault().post(Event.RELOAD);
        EventBus.getDefault().post(Event.LOGOUT);
        MyApplication.isLoginOB.set(false);

    }

    @Bindable
    public String getPhone() {
        return phone.get();
    }

    public void setPhone(String msg) {
        this.phone.set(msg);
        notifyPropertyChanged(BR.phone);
    }

    @Bindable
    public String getNewPwd() {
        return newPwd.get();
    }

    public void setNewPwd(String newPwd) {
        this.newPwd.set(newPwd);
        notifyPropertyChanged(BR.newPwd);
    }

    @Override
    public void destroy() {

    }
}
