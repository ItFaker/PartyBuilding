package com.qiantang.partybuilding.module.index.viewmodel;

import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;
import android.databinding.ObservableInt;
import android.os.CountDownTimer;
import android.text.TextUtils;
import android.view.View;

import com.google.gson.Gson;
import com.qiantang.partybuilding.BR;
import com.qiantang.partybuilding.BaseBindActivity;
import com.qiantang.partybuilding.R;
import com.qiantang.partybuilding.base.ViewModel;
import com.qiantang.partybuilding.modle.HttpResult;
import com.qiantang.partybuilding.modle.RxQuestion;
import com.qiantang.partybuilding.modle.RxRecordDetial;
import com.qiantang.partybuilding.modle.RxTestDetial;
import com.qiantang.partybuilding.modle.RxTestRecord;
import com.qiantang.partybuilding.modle.RxTestSave;
import com.qiantang.partybuilding.module.index.adapter.TestDetialAdapter;
import com.qiantang.partybuilding.module.index.adapter.TestRecordAdapter;
import com.qiantang.partybuilding.network.NetworkSubscriber;
import com.qiantang.partybuilding.network.retrofit.ApiWrapper;
import com.qiantang.partybuilding.network.retrofit.RetrofitUtil;
import com.qiantang.partybuilding.utils.AppUtil;
import com.qiantang.partybuilding.utils.LoadingWindow;
import com.qiantang.partybuilding.utils.ToastUtil;
import com.qiantang.partybuilding.widget.NoScrollRecycleView;
import com.trello.rxlifecycle2.android.ActivityEvent;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhaoyong bai on 2018/6/14.
 */
public class TestRecordViewModel extends BaseObservable implements ViewModel {
    private BaseBindActivity activity;
    private TestRecordAdapter detialAdapter;
    private String id;
    private int currentPos = 0;
    private List<RxRecordDetial> questionList = new ArrayList<>();
    private ObservableField<String> buttonText = new ObservableField<>("下一题");
    private ObservableBoolean isCorrect = new ObservableBoolean(false);

    public TestRecordViewModel(BaseBindActivity activity, TestRecordAdapter detialAdapter) {
        this.activity = activity;
        this.detialAdapter = detialAdapter;
        initData();
    }

    private void initData() {
        id = activity.getIntent().getStringExtra("id");
        getData();
    }


    private void getData() {
        ApiWrapper.getInstance().questionnaireStatistics(id)
                .compose(activity.bindUntilEvent(ActivityEvent.DESTROY))
                .subscribe(new NetworkSubscriber<List<RxRecordDetial>>() {
                    @Override
                    public void onSuccess(List<RxRecordDetial> data) {
                        questionList.addAll(data);
                        detialAdapter.setNewData(questionList);
                        init();
                    }
                });
    }


    @Bindable
    public String getButtonText() {
        return buttonText.get();
    }

    public void setButtonText(String buttonText) {
        this.buttonText.set(buttonText);
        notifyPropertyChanged(BR.buttonText);
    }

    @Bindable
    public boolean getIsCorrect() {
        return isCorrect.get();
    }

    public void setIsCorrect(boolean isCorrect) {
        this.isCorrect.set(isCorrect);
        notifyPropertyChanged(BR.isCorrect);
    }

    @Override
    public void destroy() {

    }

    /**
     * 初始化第一题的数据
     */
    private void init() {
        List<RxTestRecord> records = questionList.get(0).getOption();
        boolean isCorrect = false;
        for (int i = 0; i < records.size(); i++) {
            if (records.get(i).isAnswer() && records.get(i).isGranswer()) {
                isCorrect = true;
            } else if (!records.get(i).isAnswer() && records.get(i).isGranswer()) {
                isCorrect = false;
                break;
            }
        }
        setIsCorrect(isCorrect);
        currentPos++;
    }

    /**
     * 从第二题开始
     * @param rv
     */
    public void next(NoScrollRecycleView rv) {
        if (currentPos == questionList.size()) {
            //最后一题
            activity.onBackPressed();
            return;
        }
        rv.scrollToPosition(currentPos);
        List<RxTestRecord> records = questionList.get(currentPos).getOption();
        boolean isCorrect = false;
        for (int i = 0; i < records.size(); i++) {
            if (records.get(i).isAnswer() && records.get(i).isGranswer()) {
                isCorrect = true;
            } else if (!records.get(i).isAnswer() && records.get(i).isGranswer()) {
                isCorrect = false;
                break;
            }
        }
        setIsCorrect(isCorrect);
        currentPos++;
        if (currentPos == questionList.size() - 1) {
            setButtonText("结束查看");
        }

    }

}
