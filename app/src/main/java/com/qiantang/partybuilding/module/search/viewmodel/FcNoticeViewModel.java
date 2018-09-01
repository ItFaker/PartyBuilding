package com.qiantang.partybuilding.module.search.viewmodel;

import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.qiantang.partybuilding.BaseBindFragment;
import com.qiantang.partybuilding.MyApplication;
import com.qiantang.partybuilding.R;
import com.qiantang.partybuilding.adapter.EasyBindQuickAdapter;
import com.qiantang.partybuilding.base.ViewModel;
import com.qiantang.partybuilding.config.CacheKey;
import com.qiantang.partybuilding.config.Event;
import com.qiantang.partybuilding.modle.RxActivity;
import com.qiantang.partybuilding.modle.RxBookRecommend;
import com.qiantang.partybuilding.modle.RxIndexCommon;
import com.qiantang.partybuilding.modle.RxIndexSpeak;
import com.qiantang.partybuilding.modle.RxLearningList;
import com.qiantang.partybuilding.modle.RxMsg;
import com.qiantang.partybuilding.modle.RxOnline;
import com.qiantang.partybuilding.module.assistant.adapter.ActivityAdapter;
import com.qiantang.partybuilding.module.assistant.adapter.MsgAdapter;
import com.qiantang.partybuilding.module.index.adapter.BookRecommendAdapter;
import com.qiantang.partybuilding.module.index.adapter.IndexCommonAdapter;
import com.qiantang.partybuilding.module.index.adapter.LearnAdapter;
import com.qiantang.partybuilding.module.index.adapter.NewsAdapter;
import com.qiantang.partybuilding.module.index.adapter.SpechAdapter;
import com.qiantang.partybuilding.network.NetworkSubscriber;
import com.qiantang.partybuilding.network.URLs;
import com.qiantang.partybuilding.network.retrofit.ApiWrapper;
import com.qiantang.partybuilding.network.retrofit.RetrofitUtil;
import com.qiantang.partybuilding.utils.ActivityUtil;
import com.qiantang.partybuilding.utils.ToastUtil;
import com.trello.rxlifecycle2.android.ActivityEvent;
import com.trello.rxlifecycle2.android.FragmentEvent;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

/**
 * Created by zhaoyong bai on 2018/7/10.
 */
public class FcNoticeViewModel implements ViewModel {
    private BaseBindFragment fragment;
    private int pageNo = 1;
    private int type;
    private String keyword;
    private IndexCommonAdapter adapter;
    private SpechAdapter speechAdapter;
    private BookRecommendAdapter mRecommendAdapter;
    private LearnAdapter mLearnAdapter;
    private ActivityAdapter mActivityAdapter;
    private MsgAdapter mMsgAdapter;

//    private int classifyId;

    public FcNoticeViewModel(BaseBindFragment fragment, int type) {
        this.fragment = fragment;
        this.type = type;
//        this.classifyId = classifyId;
        EventBus.getDefault().register(this);
    }

    public void onLoadMore() {
        pageNo++;
        getData(pageNo, keyword);
    }

    public void setAdapter(IndexCommonAdapter adapter){
        this.adapter=adapter;
    }
    public void setAdapter(SpechAdapter adapter){
        this.speechAdapter = adapter;
    }
    public void setAdapter(BookRecommendAdapter adapter){
        this.mRecommendAdapter = adapter;
    }
    public void setAdapter(LearnAdapter adapter){
        this.mLearnAdapter = adapter;
    }
    public void setAdapter(ActivityAdapter adapter){
        this.mActivityAdapter = adapter;
    }
    public void setAdapter(MsgAdapter adapter){
        this.mMsgAdapter = adapter;
    }
    public void getData(int pageNo, String keyword) {
        this.pageNo = pageNo;
        this.keyword = keyword;
        switch (type) {
            case Event.SEARCH_SPEECH_STUDY://系列讲话搜索
                ApiWrapper.getInstance().speechListSearch(pageNo,keyword)
                        .compose(fragment.bindUntilEvent(FragmentEvent.DESTROY))
                        .subscribe(new NetworkSubscriber<List<RxIndexSpeak>>() {
                            @Override
                            public void onSuccess(List<RxIndexSpeak> data) {
                                fragment.refreshOK();
                                speechAdapter.setPagingData(data, pageNo);
                            }
                            @Override
                            public void onFail(RetrofitUtil.APIException e) {
                                super.onFail(e);
                                speechAdapter.loadMoreEnd();
                                fragment.refreshFail();
                            }
                        });
                break;
            case Event.SEARCH_GOOD_BOOK://好书推荐搜索
                ApiWrapper.getInstance().userRecommendSearch(pageNo,keyword)
                        .compose(fragment.bindUntilEvent(FragmentEvent.DESTROY))
                        .subscribe(new NetworkSubscriber<List<RxBookRecommend>>() {
                            @Override
                            public void onSuccess(List<RxBookRecommend> data) {
                                fragment.refreshOK();
                                mRecommendAdapter.setPagingData(data, pageNo);
                                Log.e("onSuccess",data.size()+"");
                            }
                            @Override
                            public void onFail(RetrofitUtil.APIException e) {
                                super.onFail(e);
                                Log.e("onFail",e.getMessage()+" ");
                                mRecommendAdapter.loadMoreEnd();
                            }
                        });
                break;
                case Event.SEARCH_LEARNING://专题学习搜索
                    ApiWrapper.getInstance().special(pageNo, 0,keyword)
                            .compose(fragment.bindUntilEvent(FragmentEvent.DESTROY))
                            .subscribe(new NetworkSubscriber<List<RxLearningList>>() {
                                @Override
                                public void onFail(RetrofitUtil.APIException e) {
                                    super.onFail(e);
                                    mLearnAdapter.loadMoreEnd();
                                    fragment.refreshFail();
                                }

                                @Override
                                public void onSuccess(List<RxLearningList> data) {
                                    fragment.refreshOK();
                                    mLearnAdapter.setPagingData(data, pageNo);
                                }
                            });
                    break;
            case Event.SEARCH_ONLINE://理论在线搜索
                ApiWrapper.getInstance().theory(pageNo, 0,keyword)
                        .compose(fragment.bindUntilEvent(FragmentEvent.DESTROY))
                        .subscribe(new NetworkSubscriber<RxOnline>() {
                            @Override
                            public void onFail(RetrofitUtil.APIException e) {
                                super.onFail(e);
                                mLearnAdapter.loadMoreEnd();
                                fragment.refreshFail();
                            }

                            @Override
                            public void onSuccess(RxOnline data) {
                                fragment.refreshOK();
                                mLearnAdapter.setPagingData(data.getList(), pageNo);
                            }
                        });
                break;
            case Event.SEARCH_PARAGON:
                ApiWrapper.getInstance().paragonList(pageNo,keyword)
                        .compose(fragment.bindUntilEvent(FragmentEvent.DESTROY))
                        .subscribe(new NetworkSubscriber<List<RxIndexCommon>>() {
                            @Override
                            public void onFail(RetrofitUtil.APIException e) {
                                super.onFail(e);
                                adapter.loadMoreEnd();
                                fragment.refreshFail();
                            }

                            @Override
                            public void onSuccess(List<RxIndexCommon> data) {
                                fragment.refreshOK();
                                adapter.setPagingData(data, pageNo);
                            }
                        });
                break;
            case Event.SEARCH_PARTY:
                ApiWrapper.getInstance().djActivity(pageNo,keyword)
                        .compose(fragment.bindUntilEvent(FragmentEvent.DESTROY))
                        .subscribe(new NetworkSubscriber<List<RxActivity>>() {
                            @Override
                            public void onFail(RetrofitUtil.APIException e) {
                                super.onFail(e);
                                mActivityAdapter.loadMoreEnd();
                            }

                            @Override
                            public void onSuccess(List<RxActivity> data) {
                                mActivityAdapter.setPagingData(data, pageNo);
                                adapter.loadMoreEnd();
                            }
                        });
                break;
            case Event.SEARCH_MSG://通知公告
                ApiWrapper.getInstance().tzNotice(pageNo,keyword)
                        .compose(fragment.bindUntilEvent(FragmentEvent.DESTROY))
                        .subscribe(new NetworkSubscriber<List<RxMsg>>() {
                            @Override
                            public void onSuccess(List<RxMsg> data) {
                                mMsgAdapter.setPagingData(data, pageNo);
                            }
                            @Override
                            public void onFail(RetrofitUtil.APIException e) {
                                super.onFail(e);
                                mMsgAdapter.loadMoreEnd();
                            }
                        });
                break;
            default://会议纪要搜索
                ApiWrapper.getInstance().fcNoticeSearch(pageNo,type,keyword)
                        .compose(fragment.bindUntilEvent(FragmentEvent.DESTROY))
                        .subscribe(new NetworkSubscriber<List<RxIndexCommon>>() {
                            @Override
                            public void onSuccess(List<RxIndexCommon> data) {
                                adapter.setPagingData(data, pageNo);
                                fragment.refreshOK();
                            }
                            @Override
                            public void onFail(RetrofitUtil.APIException e) {
                                super.onFail(e);
                                Log.e("onFail",e.getMessage()+" ");
                                adapter.loadMoreEnd();
                                fragment.refreshFail();
                            }
                        });
                break;
        }
    }

    public RecyclerView.OnItemTouchListener onItemTouchListener() {
        return new OnItemClickListener() {
            @Override
            public void onSimpleItemClick(BaseQuickAdapter adaptera, View view, int position) {

                switch (type) {
                    case Event.SEARCH_CHARACTER:
                        ActivityUtil.startCharacterDetialActivity(fragment.getActivity(),
                                adapter.getData().get(position).getContentId(),
                                adapter.getData().get(position).getPrinturl());
                        break;
                    case Event.SEARCH_MEETING:
                        ActivityUtil.startHeadWebActivity(fragment.getActivity(),
                                adapter.getData().get(position).getContentId(),
                                "会议纪要", URLs.NOTICE_DETIAL,
                                0, adapter.getData().get(position).getImgSrc());
                        break;
                    case Event.SEARCH_NEWS:
                        ActivityUtil.startHeadWebActivity(fragment.getActivity(), adapter.getData().get(position).getContentId(),
                                "新闻快报", URLs.NOTICE_DETIAL, 0, adapter.getData().get(position).getImgSrc());
                        break;
                    case Event.SEARCH_MIEN:
                        ActivityUtil.startHeadWebActivity(fragment.getActivity(),
                                adapter.getData().get(position).getContentId(),
                                "党建风采", URLs.NOTICE_DETIAL, 0, adapter.getData().get(position).getImgSrc());
                        break;
                    case Event.SEARCH_STUDY_STATE:
                        ActivityUtil.startHeadWebActivity(fragment.getActivity(), adapter.getData().get(position).getContentId(),
                                "学习动态", URLs.NOTICE_DETIAL, 0, adapter.getData().get(position).getImgSrc());
                        break;
                    case Event.SEARCH_SPEECH_STUDY://系列讲话详情
                        RxIndexSpeak rxIndexSpeak = speechAdapter.getData().get(position);
                        if (rxIndexSpeak.getSpeaktype() == 1) {
                            //视频
                            ActivityUtil.startVideoSpeechDetialActivity(fragment.getActivity(), rxIndexSpeak.getSpeakurl(), rxIndexSpeak.getTitle(), rxIndexSpeak.getSpeak_id());
                        } else {//音频
                            ActivityUtil.startVoiceSpeechDetialActivity(fragment.getActivity(), rxIndexSpeak.getSpeakurl(), rxIndexSpeak.getTitle(), rxIndexSpeak.getSpeak_id());
                        }
                        break;
                    case Event.SEARCH_GOOD_BOOK://好书推荐详情
                        ActivityUtil.startBookDetialActivity(fragment.getActivity(), mRecommendAdapter.getData().get(position).getContentId());
                        break;
                    case Event.SEARCH_LEARNING://专题学习详情
                        ActivityUtil.startHeadWebActivity(fragment.getActivity(), mLearnAdapter.getData().get(position).getContentId(),
                                "专题学习", URLs.SPECIALORTHEORY,3,mLearnAdapter.getData().get(position).getPrinturl());
                        break;
                    case Event.SEARCH_ONLINE://理论在线
                        ActivityUtil.startHeadWebActivity(fragment.getActivity(), mLearnAdapter.getData().get(position).getContentId(),
                                "理论在线", URLs.SPECIALORTHEORY, 4, "");
                        break;
                    case Event.SEARCH_PARAGON://先进典范
                        ActivityUtil.startParagonDetialActivity(fragment.getActivity(),
                                adapter.getData().get(position).getContentId(), adapter.getData().get(position).getPrinturl());
                        break;
                    case Event.SEARCH_PARTY://党建活动
                        ActivityUtil.startActivityDetialActivity(fragment.getActivity(),
                                mActivityAdapter.getData().get(position).getActivityId(), mActivityAdapter.getData().get(position).getStatus());
                        break;
                    case Event.SEARCH_MSG://通知公告
                        if (!MyApplication.isLogin()) {
                            ActivityUtil.startLoginActivity(fragment.getActivity());
                            return;
                        }
                        if (TextUtils.equals(MyApplication.mCache.getAsString(CacheKey.DEPT_ID), "1")) {
                            ToastUtil.toast("仅内部人员可查看");
                            return;
                        }
                        if (((int) MyApplication.mCache.getAsObject(CacheKey.STATUS)) > 0) {
                            ToastUtil.toast("您尚未通过审核，请耐心等待");
                            return;
                        }
                        ActivityUtil.jumpWeb(fragment.getActivity(), URLs.MESSAGE_DETIAL + (mMsgAdapter.getData().get(position)).getNoticeId(), "公告详情");
                        break;
                }
            }
        };
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(String keyword) {
        if (TextUtils.isEmpty(keyword)) {
            return;
        }
        getData(1, keyword);
    }

    @Override
    public void destroy() {
        EventBus.getDefault().unregister(this);
    }
}
