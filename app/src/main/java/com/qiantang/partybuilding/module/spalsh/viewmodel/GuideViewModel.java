package com.qiantang.partybuilding.module.spalsh.viewmodel;

import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.databinding.BindingAdapter;
import android.databinding.ObservableInt;
import android.support.v4.view.ViewPager;
import android.widget.ImageView;

import com.qiantang.partybuilding.BaseBindActivity;
import com.qiantang.partybuilding.R;
import com.qiantang.partybuilding.base.ViewModel;
import com.qiantang.partybuilding.BR;
import com.qiantang.partybuilding.config.CacheKey;
import com.qiantang.partybuilding.module.spalsh.adapter.GuideAdapter;
import com.qiantang.partybuilding.utils.ActivityUtil;
import com.qiantang.partybuilding.utils.SharedPreferences;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhaoyong bai on 2018/6/22.
 */
public class GuideViewModel extends BaseObservable implements ViewModel {
    private BaseBindActivity activity;
    private ObservableInt postion = new ObservableInt(0);
    private GuideAdapter adapter;
    private int[] imgRes = new int[]{R.mipmap.guide_one, R.mipmap.guide_two, R.mipmap.guide_three};

    public GuideViewModel(BaseBindActivity activity, GuideAdapter adapter) {
        this.activity = activity;
        this.adapter = adapter;
    }

    public void setData() {
        ViewPager.LayoutParams mParams = new ViewPager.LayoutParams();
        List<ImageView> imageViews = new ArrayList<>();
        for (int i = 0; i < imgRes.length; i++) {
            ImageView imageView = new ImageView(activity);
            imageView.setLayoutParams(mParams);
            imageView.setImageResource(imgRes[i]);
//            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            imageViews.add(imageView);
        }
        adapter.setData(imageViews);
    }

    @BindingAdapter("guideOne")
    public static void guideOne(ImageView imageView, int position) {
        imageView.setEnabled(position == 0);
    }

    @BindingAdapter("guideTwo")
    public static void guideTwo(ImageView imageView, int position) {
        imageView.setEnabled(position == 0);
    }

    @BindingAdapter("guideThree")
    public static void guideThree(ImageView imageView, int position) {
        imageView.setEnabled(position == 0);
    }

    @Bindable
    public int getPostion() {
        return postion.get();
    }

    public void setPostion(int postion) {
        this.postion.set(postion);
        notifyPropertyChanged(BR.postion);
    }


    public void jumpNextPage() {
        ActivityUtil.startMainActivity(activity);
        SharedPreferences.getInstance().putBoolean(CacheKey.FIRST, false);
        activity.onBackPressed();
    }

    @Override
    public void destroy() {

    }
}
