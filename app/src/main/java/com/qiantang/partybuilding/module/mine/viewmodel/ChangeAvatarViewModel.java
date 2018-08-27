package com.qiantang.partybuilding.module.mine.viewmodel;

import android.Manifest;
import android.content.Intent;
import android.databinding.BaseObservable;
import android.databinding.BindingAdapter;
import android.databinding.ObservableField;
import android.net.Uri;
import android.text.TextUtils;

import com.baoyz.actionsheet.ActionSheet;
import com.facebook.drawee.view.SimpleDraweeView;
import com.qiantang.partybuilding.MyApplication;
import com.qiantang.partybuilding.R;
import com.qiantang.partybuilding.base.ViewModel;
import com.qiantang.partybuilding.config.CacheKey;
import com.qiantang.partybuilding.config.Config;
import com.qiantang.partybuilding.modle.HttpResult;
import com.qiantang.partybuilding.modle.RxMyUserInfo;
import com.qiantang.partybuilding.module.mine.view.ShowHeadPicActivity;
import com.qiantang.partybuilding.network.NetworkSubscriber;
import com.qiantang.partybuilding.network.retrofit.ApiWrapper;
import com.qiantang.partybuilding.utils.ACache;
import com.qiantang.partybuilding.utils.DraweeViewUtils;
import com.qiantang.partybuilding.utils.LoadingWindow;
import com.qiantang.partybuilding.utils.StringUtil;
import com.qiantang.partybuilding.utils.ToastUtil;
import com.qiantang.partybuilding.utils.permissions.EasyPermission;
import com.qiantang.partybuilding.utils.permissions.PermissionCode;
import com.trello.rxlifecycle2.android.ActivityEvent;

import org.greenrobot.eventbus.EventBus;

import java.io.File;
import java.lang.ref.WeakReference;
import java.util.List;

import cn.finalteam.galleryfinal.GalleryFinal;
import cn.finalteam.galleryfinal.model.PhotoInfo;

public class ChangeAvatarViewModel extends BaseObservable implements ViewModel {


    private static final int REQUEST_CODE_GALLERY = 1001;
    private final LoadingWindow loadingWindow;
    private final ShowHeadPicActivity activity;
    private ActionSheet.Builder picSheet;
    private ActionSheet.Builder builder;
    public ObservableField<String> url = new ObservableField<>();
    public GalleryFinal.OnHanlderResultCallback resultCallback;

    public ChangeAvatarViewModel(ShowHeadPicActivity activity) {
        this.activity = activity;
        loadingWindow = new LoadingWindow(activity);
        initFunctionConfig();
        getCache();
    }

    @BindingAdapter("avatar")
    public static void avatar(SimpleDraweeView sdv, String url) {
        sdv.setImageURI(Config.IMAGE_HOST + url);
    }

    public void getCache() {
        url.set(activity.getIntent().getStringExtra("url"));
    }

    private void initFunctionConfig() {
        resultCallback = new GalleryFinal.OnHanlderResultCallback() {
            @Override
            public void onHanlderSuccess(int reqeustCode, List<PhotoInfo> resultList) {
                if (resultList != null && resultList.size() > 0) {
                    String path = resultList.get(0).getPhotoPath();
                    upload(path);
                }
            }

            @Override
            public void onHanlderFailure(int requestCode, String errorMsg) {
                ToastUtil.toast(errorMsg);
            }
        };
    }

    public void saveImg() {
        getPic();
    }

    /**
     * 获取手机相片
     */
    public void getPic() {
        if (picSheet == null) {
            picSheet = ActionSheet.createBuilder(activity, activity.getSupportFragmentManager())
                    .setCancelButtonTitle("取消")
                    .setOtherButtonTitles("打开相册", "拍照")
                    .setCancelableOnTouchOutside(true)
                    .setListener(new ActionSheet.ActionSheetListener() {
                        @Override
                        public void onDismiss(ActionSheet actionSheet, boolean isCancel) {
                        }

                        @Override
                        public void onOtherButtonClick(ActionSheet actionSheet, int index) {
                            switch (index) {
                                case 0:
                                    GalleryFinal.openGallerySingle(REQUEST_CODE_GALLERY,
                                            GalleryFinal.getCoreConfig().getFunctionConfig(),
                                            resultCallback);
                                    break;
                                case 1:
                                    EasyPermission.with(activity)
                                            .rationale(StringUtil.getString(R.string
                                                    .rationale_camera))
                                            .addRequestCode(PermissionCode.RG_CAMERA_PERM)
                                            .permissions(Manifest.permission.CAMERA)
                                            .request();
                                    break;
                                default:
                                    break;
                            }
                        }
                    });
        }
        picSheet.show();
    }

    /**
     * 上传头像
     */
    private void upload(final String path) {
        loadingWindow.showWindow();
        ApiWrapper.getInstance()
                .setUploadUrl(path)
                .subscribe(new NetworkSubscriber<HttpResult>() {
                    @Override
                    public void onComplete() {
                        super.onComplete();
                        GalleryFinal.cleanCacheFile();
                    }

                    @Override
                    public void onSuccess(HttpResult uploadUrl) {
                        String urlu = uploadUrl.getAvatar();
                        url.set(urlu);
                        update(urlu);
                        setUserInfo(urlu);
                    }
                });
    }

    /**
     * 更改用户头像信息
     *
     * @param relativeUrl
     */
    private void update(final String relativeUrl) {
        ApiWrapper.getInstance()
                .avatar(relativeUrl)
                .doOnTerminate(loadingWindow::hidWindow)
                .compose(activity.bindUntilEvent(ActivityEvent.DESTROY))
                .subscribe(new NetworkSubscriber<HttpResult>() {
                    @Override
                    public void onSuccess(HttpResult bean) {
                        ToastUtil.longToast("头像修改成功");
                    }
                });
    }


    /**
     * 更新用户信息
     */
    private void setUserInfo(String avatar) {
        MyApplication.mCache.getAsJSONBean(CacheKey.USER_INFO, RxMyUserInfo.class, rxMyUserInfo -> {
            rxMyUserInfo.setAvatar(avatar);
            MyApplication.mCache.put(CacheKey.USER_INFO, rxMyUserInfo);
            EventBus.getDefault().post(rxMyUserInfo);
            activity.onBackPressed();
        });


    }

    @Override
    public void destroy() {
        WeakReference<GalleryFinal.OnHanlderResultCallback> wr1 = new WeakReference<>(
                resultCallback);
        WeakReference<GalleryFinal.OnHanlderResultCallback> wr2 = new WeakReference<>(
                GalleryFinal.getCallback());
        GalleryFinal.clearCallback();
        resultCallback = null;
        if (loadingWindow != null) loadingWindow.dismiss();
    }
}
