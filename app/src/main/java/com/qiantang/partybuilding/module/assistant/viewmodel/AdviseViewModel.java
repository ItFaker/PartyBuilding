package com.qiantang.partybuilding.module.assistant.viewmodel;

import android.databinding.ObservableField;
import android.text.TextUtils;

import com.qiantang.partybuilding.BaseBindActivity;
import com.qiantang.partybuilding.base.ViewModel;
import com.qiantang.partybuilding.modle.HttpResult;
import com.qiantang.partybuilding.network.NetworkSubscriber;
import com.qiantang.partybuilding.network.retrofit.ApiWrapper;
import com.qiantang.partybuilding.network.retrofit.RetrofitUtil;
import com.qiantang.partybuilding.utils.ToastUtil;
import com.trello.rxlifecycle2.android.ActivityEvent;

/**
 * Created by zhaoyong bai on 2018/5/31.
 */
public class AdviseViewModel implements ViewModel {
    private BaseBindActivity activity;
    public ObservableField<String> content = new ObservableField<>();

    public AdviseViewModel(BaseBindActivity activity) {
        this.activity = activity;
    }

    public void publish() {
        if (TextUtils.isEmpty(content.get())) {
            ToastUtil.toast("请输入要反馈的内容");
            return;
        }
        ApiWrapper.getInstance().insertIdea(content.get())
                .compose(activity.bindUntilEvent(ActivityEvent.DESTROY))
                .subscribe(new NetworkSubscriber<HttpResult>() {
                    @Override
                    public void onFail(RetrofitUtil.APIException e) {
                        super.onFail(e);

                    }

                    @Override
                    public void onSuccess(HttpResult data) {
                        ToastUtil.toast("您反馈的问题已提交");
                        activity.onBackPressed();
                    }
                });
    }

    @Override
    public void destroy() {

    }
}
