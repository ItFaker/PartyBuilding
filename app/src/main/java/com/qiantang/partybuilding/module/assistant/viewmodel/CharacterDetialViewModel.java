package com.qiantang.partybuilding.module.assistant.viewmodel;

import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.databinding.BindingAdapter;
import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;
import android.databinding.ObservableInt;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.facebook.drawee.view.SimpleDraweeView;
import com.qiantang.partybuilding.BR;
import com.qiantang.partybuilding.BaseBindActivity;
import com.qiantang.partybuilding.MyApplication;
import com.qiantang.partybuilding.R;
import com.qiantang.partybuilding.adapter.CommentAdapter;
import com.qiantang.partybuilding.base.ViewModel;
import com.qiantang.partybuilding.config.CacheKey;
import com.qiantang.partybuilding.config.Config;
import com.qiantang.partybuilding.modle.HttpResult;
import com.qiantang.partybuilding.modle.RxActivityDetial;
import com.qiantang.partybuilding.modle.RxAds;
import com.qiantang.partybuilding.modle.RxCharacterDetial;
import com.qiantang.partybuilding.modle.RxPartyActivityDetial;
import com.qiantang.partybuilding.modle.RxPicUrl;
import com.qiantang.partybuilding.modle.RxSignInfo;
import com.qiantang.partybuilding.module.assistant.adapter.SignRecordAdapter;
import com.qiantang.partybuilding.module.assistant.view.ActivityDetial;
import com.qiantang.partybuilding.module.assistant.view.CharacterDetialActivity;
import com.qiantang.partybuilding.network.NetworkSubscriber;
import com.qiantang.partybuilding.network.retrofit.ApiWrapper;
import com.qiantang.partybuilding.network.retrofit.RetrofitUtil;
import com.qiantang.partybuilding.utils.ActivityUtil;
import com.qiantang.partybuilding.utils.ToastUtil;
import com.qiantang.partybuilding.utils.fullhtml.TextViewForFullHtml;
import com.qiantang.partybuilding.widget.MyBanner;
import com.trello.rxlifecycle2.android.ActivityEvent;

import java.util.ArrayList;
import java.util.List;

import cn.bingoogolapple.bgabanner.BGABanner;

/**
 * Created by zhaoyong bai on 2018/5/28.
 */
public class CharacterDetialViewModel extends BaseObservable implements ViewModel, BGABanner.Delegate<View, RxPicUrl>, BGABanner.Adapter {
    private BaseBindActivity activity;
    private CommentAdapter adapter;
    public ObservableBoolean isInput = new ObservableBoolean(false);
    private int pageNo = 1;
    private String id;
    private ObservableField<RxCharacterDetial> detials = new ObservableField<>();
    private ObservableInt commentCount = new ObservableInt(0);
    private boolean isDealing = false;
    private int commentPos = 0;
    private String printurl;
    private ObservableField<String> picCount = new ObservableField<>();
    public int picListSize = 0;


    public CharacterDetialViewModel(BaseBindActivity activity, CommentAdapter adapter) {
        this.activity = activity;
        this.adapter = adapter;
        initData();
    }

    private void initData() {
        id = activity.getIntent().getStringExtra("id");
        printurl = activity.getIntent().getStringExtra("printurl");
    }

    public void loadMore() {
        pageNo++;
        getData(pageNo);
    }

    public void getData(int pageNo) {
        this.pageNo = pageNo;
        ApiWrapper.getInstance().rwNoticeDetails(pageNo, id, printurl)
                .compose(activity.bindUntilEvent(ActivityEvent.DESTROY))
                .subscribe(new NetworkSubscriber<RxCharacterDetial>() {
                    @Override
                    public void onFail(RetrofitUtil.APIException e) {
                        super.onFail(e);
                        adapter.loadMoreEnd();
                        activity.refreshFail();
                    }

                    @Override
                    public void onSuccess(RxCharacterDetial data) {
                        activity.refreshOK();
                        if (Config.isLoadMore) {
                            adapter.setPagingData(data.getPl(), pageNo);
                        } else {
                            adapter.setNewData(data.getPl());
                        }
                        setCommentCount(data.getCount());
                        if (pageNo == 1 && picListSize == 0) {
                            picListSize = data.getImgSrc().size();
                            setPicCount("1/" + picListSize);
                            setDetials(data);
                        }
                    }
                });
    }


    public void comment(String content) {
        if (TextUtils.isEmpty(content)) {
            ToastUtil.toast("请输入评论内容");
            return;
        }
        isDealing = true;
        ApiWrapper.getInstance().comment(content, id)
                .compose(activity.bindUntilEvent(ActivityEvent.DESTROY))
                .doOnTerminate(() -> isDealing = false)
                .subscribe(new NetworkSubscriber<HttpResult>() {
                    @Override
                    public void onFail(RetrofitUtil.APIException e) {
                        super.onFail(e);
                        adapter.loadMoreFail();
                    }

                    @Override
                    public void onSuccess(HttpResult data) {
//                        RxCharacterDetial detial = getDetials();
//                        setCommentCount(getCommentCount() + 1);
//                        setDetials(detial);
                        getData(pageNo+1);
                        ((CharacterDetialActivity) activity).dissmissCommentBox();
                    }
                });
    }

    public void commentLike(String id) {
        isDealing = true;
        ApiWrapper.getInstance().videoLike(id)
                .compose(activity.bindUntilEvent(ActivityEvent.DESTROY))
                .doOnTerminate(() -> isDealing = false)
                .subscribe(new NetworkSubscriber<HttpResult>() {
                    @Override
                    public void onFail(RetrofitUtil.APIException e) {
                        super.onFail(e);
                    }

                    @Override
                    public void onSuccess(HttpResult data) {
                        adapter.getData().get(commentPos).setIsDz(1);
                        adapter.getData().get(commentPos).setDz(adapter.getData().get(commentPos).getDz() + 1);
                        adapter.notifyItemChanged(commentPos + 1);
                    }
                });
    }

    private void cancelLike(String id) {
        isDealing = true;
        ApiWrapper.getInstance().removeVideoLike(id)
                .compose(activity.bindUntilEvent(ActivityEvent.DESTROY))
                .doOnTerminate(() -> isDealing = false)
                .subscribe(new NetworkSubscriber<HttpResult>() {
                    @Override
                    public void onFail(RetrofitUtil.APIException e) {
                        super.onFail(e);
                    }

                    @Override
                    public void onSuccess(HttpResult data) {
                        //取消点赞成功
                        adapter.getData().get(commentPos).setIsDz(0);
                        adapter.getData().get(commentPos).setDz(adapter.getData().get(commentPos).getDz() - 1);
                        adapter.notifyItemChanged(commentPos + 1);
                    }
                });
    }

    @Bindable
    public String getPicCount() {
        return picCount.get();
    }

    public void setPicCount(String picCount) {
        this.picCount.set(picCount);
        notifyPropertyChanged(BR.picCount);
    }

    @Bindable
    public RxCharacterDetial getDetials() {
        return detials.get();
    }

    public void setDetials(RxCharacterDetial detials) {
        this.detials.set(detials);
        notifyPropertyChanged(BR.detials);
    }

    @BindingAdapter("topPic")
    public static void topPic(SimpleDraweeView sdv, String url) {
        sdv.setImageURI(url);
    }

    public RecyclerView.OnItemTouchListener onItemTouchListener() {
        return new OnItemClickListener() {
            @Override
            public void onSimpleItemClick(BaseQuickAdapter adapter, View view, int position) {

            }

            @Override
            public void onItemChildClick(BaseQuickAdapter adaptert, View view, int position) {
                super.onItemChildClick(adaptert, view, position);
                if (isDealing) {
                    return;
                }
                commentPos = position;

                switch (view.getId()) {
                    case R.id.iv_praise:
                        cancelLike(adapter.getData().get(position).getContentId());
                        break;
                    case R.id.iv_unpraise:
                        commentLike(adapter.getData().get(position).getContentId());
                        break;
                }
            }
        };
    }

    @BindingAdapter("loadContent")
    public static void loadContent(TextViewForFullHtml textViewForFullHtml, String content) {
        textViewForFullHtml.loadContent(content);
    }


    @Bindable
    public int getCommentCount() {
        return commentCount.get();
    }

    public void setCommentCount(int commentCount) {
        this.commentCount.set(commentCount);
        notifyPropertyChanged(BR.commentCount);
    }


    @Override
    public void destroy() {

    }

    @BindingAdapter("headBanner")
    public static void setBanner(MyBanner banner, List<RxPicUrl> list) {
        List<String> tips = new ArrayList<>();
        if (list == null || list.size() < 1) {
            return;
        }

        banner.setData(R.layout.viewpager_img, list, null);
    }

    @Override
    public void fillBannerItem(BGABanner banner, View itemView, Object model, int position) {
        ((SimpleDraweeView) itemView).setImageURI(Config.IMAGE_HOST + ((RxPicUrl) model).getImg_src());
    }

    @Override
    public void onBannerItemClick(BGABanner banner, View itemView, RxPicUrl model, int position) {

    }
}
