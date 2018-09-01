package com.qiantang.partybuilding.module.search.fragment;

import android.databinding.DataBindingUtil;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.qiantang.partybuilding.BaseBindFragment;
import com.qiantang.partybuilding.R;
import com.qiantang.partybuilding.adapter.EasyBindQuickAdapter;
import com.qiantang.partybuilding.config.Event;
import com.qiantang.partybuilding.databinding.FragmentRecycleviewBinding;
import com.qiantang.partybuilding.module.assistant.adapter.ActivityAdapter;
import com.qiantang.partybuilding.module.assistant.adapter.MsgAdapter;
import com.qiantang.partybuilding.module.index.adapter.BookRecommendAdapter;
import com.qiantang.partybuilding.module.index.adapter.IndexCommonAdapter;
import com.qiantang.partybuilding.module.index.adapter.LearnAdapter;
import com.qiantang.partybuilding.module.index.adapter.SpechAdapter;
import com.qiantang.partybuilding.module.search.viewmodel.FcNoticeViewModel;
import com.qiantang.partybuilding.utils.RecycleViewUtils;

/**
 * Created by zhaoyong bai on 2018/7/10.
 * 使用fcNotice接口的搜索页面
 */
public class FcNoticeFragment extends BaseBindFragment {
    private FragmentRecycleviewBinding binding;
    private FcNoticeViewModel viewModel;
    private SpechAdapter mSpechAdapter;
    private BookRecommendAdapter mRecommendAdapter;
    private IndexCommonAdapter mAdapter;
    private LearnAdapter mLearnAdapter;
    private ActivityAdapter mActivityAdapter;
    private MsgAdapter mMsgAdapter;
    private int type;
//    private int classifyId;

    @Override
    public View initBinding(LayoutInflater inflater, ViewGroup container) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_recycleview, container, false);
        type = getArguments().getInt("type");
//        classifyId = getArguments().getInt("classifyId");
        viewModel = new FcNoticeViewModel(this, type);
        return binding.getRoot();
    }

    @Override
    public void initView() {
        int resId = 0;
        switch (type) {
            case Event.SEARCH_CHARACTER:
                resId = R.layout.item_character;
                mAdapter = new IndexCommonAdapter(resId);
                viewModel.setAdapter(mAdapter);
                initRv(binding.rv,mAdapter);
                break;
//            case Event.SEARCH_MEETING:
//                resId = R.layout.item_meeting_record;
//                break;
            case Event.SEARCH_NEWS:
                resId = R.layout.item_news;
                mAdapter = new IndexCommonAdapter(resId);
                viewModel.setAdapter(mAdapter);
                initRv(binding.rv,mAdapter);
                break;
            case Event.SEARCH_MIEN:
                resId = R.layout.item_study_state;
                mAdapter = new IndexCommonAdapter(resId);
                viewModel.setAdapter(mAdapter);
                initRv(binding.rv,mAdapter);
                break;
            case Event.SEARCH_STUDY_STATE:
                resId = R.layout.item_news;
                mAdapter = new IndexCommonAdapter(resId);
                viewModel.setAdapter(mAdapter);
                initRv(binding.rv,mAdapter);
                break;
            case Event.SEARCH_MEETING:
                resId = R.layout.item_meeting_record;
                mAdapter = new IndexCommonAdapter(resId);
                viewModel.setAdapter(mAdapter);
                initRv(binding.rv,mAdapter);
                break;
            case Event.SEARCH_SPEECH_STUDY://系列讲话
                resId = R.layout.item_index_speech;
                mSpechAdapter = new SpechAdapter(resId);
                viewModel.setAdapter(mSpechAdapter);
                initRv(binding.rv,mSpechAdapter);
                break;
            case Event.SEARCH_GOOD_BOOK://好书推荐
                resId = R.layout.item_book_recommend;
                mRecommendAdapter = new BookRecommendAdapter(resId);
                viewModel.setAdapter(mRecommendAdapter);
                initRv(binding.rv,mRecommendAdapter);
                break;
            case Event.SEARCH_LEARNING://专题学习
                resId = R.layout.item_learn_list;
                mLearnAdapter = new LearnAdapter(resId);
                viewModel.setAdapter(mLearnAdapter);
                initRv(binding.rv,mLearnAdapter);
                break;
            case Event.SEARCH_ONLINE://理论在线
                resId = R.layout.item_online_list;
                mLearnAdapter = new LearnAdapter(resId);
                viewModel.setAdapter(mLearnAdapter);
                initRv(binding.rv,mLearnAdapter);
                break;
            case Event.SEARCH_PARAGON://先进典范
                resId = R.layout.item_paragon;
                mAdapter = new IndexCommonAdapter(resId);
                viewModel.setAdapter(mAdapter);
                initRv(binding.rv,mAdapter);
                break;
            case Event.SEARCH_PARTY://党建活动
                resId = R.layout.item_activity;
                mActivityAdapter = new ActivityAdapter(resId);
                viewModel.setAdapter(mActivityAdapter);
                initRv(binding.rv,mActivityAdapter);
                break;
            case Event.SEARCH_MSG://通知公告
                resId = R.layout.item_msg_list;
                mMsgAdapter = new MsgAdapter(resId);
                viewModel.setAdapter(mMsgAdapter);
                initRv(binding.rv,mMsgAdapter);
                break;
        }
//        adapter = new IndexCommonAdapter(resId);
//        viewModel.setAdapter(adapter);
//        initRv(binding.rv);
    }


    private void initRv(RecyclerView rv, EasyBindQuickAdapter adapter) {
        adapter.setEnableLoadMore(true);
        adapter.setLoadMoreView(RecycleViewUtils.getLoadMoreView());
        rv.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false));
        rv.setAdapter(adapter);
        rv.addOnItemTouchListener(viewModel.onItemTouchListener());
        adapter.setOnLoadMoreListener(() -> viewModel.onLoadMore(), rv);
    }

    @Override
    protected void viewModelDestroy() {
        viewModel.destroy();
    }
}
