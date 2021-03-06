package com.qiantang.partybuilding.module.input.viewmodel;

import android.app.Activity;
import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.databinding.Observable;
import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;

import com.qiantang.partybuilding.BR;
import com.qiantang.partybuilding.BaseBindActivity;
import com.qiantang.partybuilding.base.ViewModel;

/**
 * Created by zhaoyong bai on 2018/5/27.
 */
public class InputViewModel extends BaseObservable implements ViewModel {
    private ObservableBoolean isPop = new ObservableBoolean(false);
    public ObservableField<String> hint = new ObservableField<>("");
    private Activity activity;
    private ObservableField<String> textString = new ObservableField<>("");
    private ObservableBoolean isCollect=new ObservableBoolean(false);
    private ObservableBoolean shareVis=new ObservableBoolean(true);

    public InputViewModel(Activity activity) {
        this.activity = activity;
    }

    @Bindable
    public boolean getIsPop() {
        return isPop.get();
    }

    public void setIsPop(boolean isPop) {
        this.isPop.set(isPop);
        notifyPropertyChanged(BR.isPop);
    }

    @Bindable
    public boolean getIsCollect() {
        return isCollect.get();
    }

    public void setIsCollect(boolean isCollect) {
        this.isCollect.set(isCollect);
        notifyPropertyChanged(BR.isCollect);
    }

    @Bindable
    public String getHint() {
        return hint.get();
    }

    public void setHint(String hint) {
        this.hint.set(hint);
        notifyPropertyChanged(BR.hint);
    }

    @Bindable
    public String getTextString() {
        return textString.get();
    }

    public void setTextString(String hint) {
        this.textString.set(hint);
        notifyPropertyChanged(BR.textString);
    }

    @Bindable
    public boolean getShareVis() {
        return shareVis.get();
    }

    public void setShareVis(boolean shareVis) {
        this.shareVis.set(shareVis);
        notifyPropertyChanged(BR.shareVis);
    }

    @Override
    public void destroy() {

    }
}
