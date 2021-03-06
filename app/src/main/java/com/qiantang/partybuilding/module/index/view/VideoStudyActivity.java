package com.qiantang.partybuilding.module.index.view;

import android.content.res.Configuration;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.qiantang.partybuilding.BaseBindActivity;
import com.qiantang.partybuilding.R;
import com.qiantang.partybuilding.config.Event;
import com.qiantang.partybuilding.databinding.ActivityVideoStudyBinding;
import com.qiantang.partybuilding.module.index.adapter.IndexCommonAdapter;
import com.qiantang.partybuilding.module.index.adapter.VideoStudyAdapter;
import com.qiantang.partybuilding.module.index.viewmodel.VideoStudyViewModel;
import com.qiantang.partybuilding.utils.ActivityUtil;
import com.qiantang.partybuilding.utils.RecycleViewUtils;
import com.shuyu.gsyvideoplayer.GSYVideoManager;

/**
 * Created by zhaoyong bai on 2018/5/22.
 * 视频学习
 */
public class VideoStudyActivity extends BaseBindActivity {
    private VideoStudyViewModel viewModel;
    private VideoStudyAdapter adapter;
    private ActivityVideoStudyBinding binding;
    private boolean isPause;

    @Override
    protected void initBind() {
        adapter = new VideoStudyAdapter(R.layout.item_video_study);
        viewModel = new VideoStudyViewModel(this, adapter);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_video_study);
        binding.setViewModel(viewModel);
    }

    @Override
    public void initView() {
        binding.toolbar.setTitle("视频学习");
        binding.toolbar.setIsHide(false);
        binding.toolbar.setResId(R.mipmap.icon_search_black);
        initRv(binding.rv);
        initRefresh(binding.cptr);
    }

    @Override
    public void update() {
        super.update();
        binding.cptr.autoRefresh();
    }

    @Override
    public void refreshData() {
        super.refreshData();
        viewModel.getData(1);
    }


    private void initRv(RecyclerView rv) {
        adapter.setLoadMoreView(RecycleViewUtils.getLoadMoreView());
        adapter.setEnableLoadMore(true);
        adapter.setOnLoadMoreListener(() -> viewModel.onLoadMore(), rv);
        rv.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        rv.setAdapter(adapter);
        rv.addOnItemTouchListener(viewModel.onItemTouchListener());
        rv.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        rv.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                LinearLayoutManager manager = (LinearLayoutManager) recyclerView.getLayoutManager();
                int firstVisibleItem = manager.findFirstVisibleItemPosition();
                int lastVisibleItem = manager.findLastVisibleItemPosition();
                //大于0说明有播放
                if (GSYVideoManager.instance().getPlayPosition() >= 0) {
                    //当前播放的位置
                    int position = GSYVideoManager.instance().getPlayPosition();
                    //对应的播放列表TAG
                    if (GSYVideoManager.instance().getPlayTag().equals(VideoStudyAdapter.TAG)
                            && (position < firstVisibleItem || position > lastVisibleItem)) {
                        if (adapter.isFull()) {
                            return;
                        }
                        //如果滑出去了上面和下面就是否，和今日头条一样
                        GSYVideoManager.releaseAllVideos();
                        adapter.notifyDataSetChanged();
                    }
                }
            }
        });
    }
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                onBackPressed();
                break;
            case R.id.iv_right:
                ActivityUtil.startSearchActivityVideo(this, Event.SEARCH_VIDEO_STUDY);
                break;
        }
    }

    @Override
    public void onBackPressed() {
        onBackPressAdapter();

        if (GSYVideoManager.backFromWindowFull(this)) {
            return;
        }
        super.onBackPressed();
    }

    @Override
    protected void onPause() {
        super.onPause();
        GSYVideoManager.onPause();
        isPause = true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        GSYVideoManager.onResume();
        isPause = false;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        GSYVideoManager.releaseAllVideos();
        if (adapter != null) {
            adapter.onDestroy();
        }
    }

    /********************************为了支持重力旋转********************************/
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (adapter != null && adapter.getListNeedAutoLand() && !isPause) {
            adapter.onConfigurationChanged(this, newConfig);
        }
    }

    private void onBackPressAdapter() {
        //为了支持重力旋转
        if (adapter != null && adapter.getListNeedAutoLand()) {
            adapter.onBackPressed();
        }
    }

    @Override
    protected void viewModelDestroy() {
        viewModel.destroy();
    }
}
