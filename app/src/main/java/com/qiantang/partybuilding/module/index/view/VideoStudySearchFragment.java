package com.qiantang.partybuilding.module.index.view;

import android.databinding.DataBindingUtil;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.qiantang.partybuilding.BaseBindFragment;
import com.qiantang.partybuilding.R;
import com.qiantang.partybuilding.databinding.FragmentRecycleviewBinding;
import com.qiantang.partybuilding.module.index.adapter.VideoStudyAdapter;
import com.qiantang.partybuilding.module.index.viewmodel.VideoStudySearchViewModel;
import com.qiantang.partybuilding.utils.RecycleViewUtils;
import com.shuyu.gsyvideoplayer.GSYVideoManager;

/**
 * Author:    Jintf
 * Date:      2018/8/31 0031 下午 5:04
 */
public class VideoStudySearchFragment extends BaseBindFragment {
    private FragmentRecycleviewBinding binding;
    private VideoStudySearchViewModel viewModel;
    private VideoStudyAdapter adapter;
    private boolean isPause;
    private int type;

    /**
     * 初始化 bind
     *
     * @param inflater
     * @param container
     * @return
     */
    @Override
    public View initBinding(LayoutInflater inflater, ViewGroup container) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_recycleview, container, false);
        type = getArguments().getInt("type");
        viewModel = new VideoStudySearchViewModel(this);
        return binding.getRoot();
    }

    @Override
    public void initView() {
        adapter = new VideoStudyAdapter(R.layout.item_video_study);
        viewModel.setAdapter(adapter);
        initRv(binding.rv);
    }

    @Override
    public void update() {
        super.update();
        binding.cptr.autoRefresh();
    }

    private void initRv(RecyclerView rv) {
        adapter.setLoadMoreView(RecycleViewUtils.getLoadMoreView());
        adapter.setEnableLoadMore(true);
        adapter.setOnLoadMoreListener(() -> viewModel.onLoadMore(), rv);
        rv.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        rv.setAdapter(adapter);
        rv.addOnItemTouchListener(viewModel.onItemTouchListener());
        rv.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL));
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

    /**
     * 实现该方法 并在调用viewModel 的 destroy函数
     */
    @Override
    protected void viewModelDestroy() {
        viewModel.destroy();
    }
}
