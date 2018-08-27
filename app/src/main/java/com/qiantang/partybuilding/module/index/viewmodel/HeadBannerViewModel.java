package com.qiantang.partybuilding.module.index.viewmodel;

import android.app.Activity;
import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.databinding.BindingAdapter;
import android.databinding.ObservableField;
import android.text.TextUtils;
import android.view.View;

import com.facebook.drawee.view.SimpleDraweeView;
import com.qiantang.partybuilding.BR;
import com.qiantang.partybuilding.BaseBindActivity;
import com.qiantang.partybuilding.BaseBindFragment;
import com.qiantang.partybuilding.R;
import com.qiantang.partybuilding.base.ViewModel;
import com.qiantang.partybuilding.config.Config;
import com.qiantang.partybuilding.modle.RxAds;
import com.qiantang.partybuilding.network.NetworkSubscriber;
import com.qiantang.partybuilding.network.retrofit.ApiWrapper;
import com.qiantang.partybuilding.utils.ActivityUtil;
import com.qiantang.partybuilding.utils.AppUtil;
import com.qiantang.partybuilding.widget.MyBanner;
import com.trello.rxlifecycle2.android.ActivityEvent;
import com.trello.rxlifecycle2.android.FragmentEvent;

import java.util.ArrayList;
import java.util.List;

import cn.bingoogolapple.bgabanner.BGABanner;

/**
 * Created by 123 on 2017/9/5.
 */

public class HeadBannerViewModel extends BaseObservable implements ViewModel, BGABanner.Delegate<View, RxAds>, BGABanner.Adapter {
    private Activity activity;
    private BaseBindActivity bindActivity;
    private BaseBindFragment fragment;
    public ObservableField<List<RxAds>> bannerList = new ObservableField<>();

    public HeadBannerViewModel(BaseBindFragment fragment) {
        this.fragment = fragment;
        this.activity = fragment.getActivity();
        getBannerData();
    }

    public HeadBannerViewModel(BaseBindActivity activity) {
        this.bindActivity = activity;
        this.activity = activity;
    }

    @Override
    public void destroy() {

    }

    @Override
    public void fillBannerItem(BGABanner banner, View itemView, Object model, int position) {
            ((SimpleDraweeView) itemView).setImageURI(Config.IMAGE_HOST+((RxAds)model).getImage());
    }

    @BindingAdapter("headBanner")
    public static void setBanner(MyBanner banner, List<RxAds> list) {
        List<String> tips = new ArrayList<>();
            if (list == null || list.size() < 1) {
                return;
            }


            for (RxAds ads : list) {
                tips.add(ads.getImage_text());
            }


        banner.setData(R.layout.viewpager_img, list, tips);
    }

    /**
     * 获取轮播图数据
     */
    public void getBannerData() {
       ApiWrapper.getInstance().advertising()
               .compose(fragment.bindUntilEvent(FragmentEvent.DESTROY))
               .subscribe(new NetworkSubscriber<List<RxAds>>() {
                   @Override
                   public void onSuccess(List<RxAds> data) {
                       setBannerList(data);
                   }
               });
    }

    @Bindable
    public List<RxAds> getBannerList() {
        return bannerList.get();
    }

    public void setBannerList(List<RxAds> bannerList) {
        this.bannerList.set(bannerList);
        notifyPropertyChanged(BR.bannerList);
    }

    @Override
    public void onBannerItemClick(BGABanner banner, View itemView, RxAds model, int position) {

    }
}
