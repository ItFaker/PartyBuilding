package com.qiantang.partybuilding.module.index.view;

import android.content.res.Configuration;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;

import com.qiantang.partybuilding.BaseBindActivity;
import com.qiantang.partybuilding.MyApplication;
import com.qiantang.partybuilding.R;
import com.qiantang.partybuilding.adapter.CommentAdapter;
import com.qiantang.partybuilding.config.Config;
import com.qiantang.partybuilding.databinding.ActivityStudyVideoDetialBinding;
import com.qiantang.partybuilding.databinding.ViewVideoSpeechHeadBinding;
import com.qiantang.partybuilding.databinding.ViewVideoStudyHeadBinding;
import com.qiantang.partybuilding.module.index.adapter.VideoDetialAdapter;
import com.qiantang.partybuilding.module.index.viewmodel.VideoDetialViewMdoel;
import com.qiantang.partybuilding.module.index.viewmodel.VideoSpeechDetialViewMdoel;
import com.qiantang.partybuilding.module.input.viewmodel.InputViewModel;
import com.qiantang.partybuilding.utils.AutoUtils;
import com.qiantang.partybuilding.utils.RecycleViewUtils;
import com.qiantang.partybuilding.widget.commentwidget.KeyboardControlMnanager;
import com.shuyu.gsyvideoplayer.GSYVideoManager;
import com.shuyu.gsyvideoplayer.builder.GSYVideoOptionBuilder;
import com.shuyu.gsyvideoplayer.listener.GSYSampleCallBack;
import com.shuyu.gsyvideoplayer.utils.OrientationUtils;
import com.shuyu.gsyvideoplayer.video.base.GSYVideoPlayer;

/**
 * Created by zhaoyong bai on 2018/5/25.
 */
public class VideoSpeechDetialActivity extends BaseBindActivity {
    private ActivityStudyVideoDetialBinding binding;
    private ViewVideoSpeechHeadBinding headBinding;
    private VideoSpeechDetialViewMdoel viewMdoel;
    private CommentAdapter adapter;
    private InputViewModel inputViewModel;
    private boolean isPlay;
    private boolean isPause;

    private OrientationUtils orientationUtils;

    @Override
    protected void initBind() {
        adapter = new CommentAdapter(R.layout.item_comment);
        viewMdoel = new VideoSpeechDetialViewMdoel(this, adapter);
        inputViewModel = new InputViewModel(this);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_study_video_detial);
        binding.input.setViewModel(inputViewModel);

        headBinding = DataBindingUtil.inflate(getLayoutInflater(), R.layout.view_video_speech_head, null, false);
        headBinding.setViewModel(viewMdoel);
    }

    @Override
    public void initView() {
        inputViewModel.setHint("发表学习感悟...");
        resolveNormalVideoUI();
        String title = getIntent().getStringExtra("title");
        //外部辅助的旋转，帮助全屏
        orientationUtils = new OrientationUtils(this, binding.scv);
        //初始化不打开外部的旋转
        orientationUtils.setEnable(false);

        GSYVideoOptionBuilder gsyVideoOption = new GSYVideoOptionBuilder();
        gsyVideoOption
                .setIsTouchWiget(true)
                .setRotateViewAuto(false)
                .setLockLand(false)
                .setAutoFullWithSize(true)
                .setShowFullAnimation(false)
                .setNeedLockFull(true)
                .setUrl("")
                .setVideoTitle(title)
                .setCacheWithPlay(false)
                .setVideoAllCallBack(new GSYSampleCallBack() {
                    @Override
                    public void onPrepared(String url, Object... objects) {
                        super.onPrepared(url, objects);
                        //这里禁用随屏旋转
//                        orientationUtils.setEnable(true);
                        isPlay = true;
                    }

                    @Override
                    public void onEnterFullscreen(String url, Object... objects) {
                        super.onEnterFullscreen(url, objects);
                    }

                    @Override
                    public void onAutoComplete(String url, Object... objects) {
                        super.onAutoComplete(url, objects);
                    }

                    @Override
                    public void onClickStartError(String url, Object... objects) {
                        super.onClickStartError(url, objects);
                    }

                    @Override
                    public void onQuitFullscreen(String url, Object... objects) {
                        super.onQuitFullscreen(url, objects);
                        if (orientationUtils != null) {
                            orientationUtils.backToProtVideo();
                        }
                    }
                })
                .setLockClickListener((view, lock) -> {
                    if (orientationUtils != null) {
                        //配合下方的onConfigurationChanged
                        orientationUtils.setEnable(!lock);
                    }
                })
                .setGSYVideoProgressListener((progress, secProgress, currentPosition, duration) -> {
//                        Debuger.printfLog(" progress " + progress + " secProgress " + secProgress + " currentPosition " + currentPosition + " duration " + duration);
                })
                .build(binding.scv);

        binding.scv.getFullscreenButton().setOnClickListener(v -> {
            //直接横屏
            orientationUtils.resolveByClick();

            //第一个true是否需要隐藏actionbar，第二个true是否需要隐藏statusbar
            binding.scv.startWindowFullscreen(VideoSpeechDetialActivity.this, true, true);
        });
        binding.scv.getBackButton().setOnClickListener(this::onClick);
        viewMdoel.initData();
        initRv(binding.rv);
        viewMdoel.testData(1, false);
        initRefresh(binding.cptr);
        inputViewModel.setHint("发表学习感悟...");
        initKeyboardHeightObserver();
        inputViewModel.setShareVis(false);
    }

    private void initKeyboardHeightObserver() {
        //观察键盘弹出与消退
        KeyboardControlMnanager.observerKeyboardVisibleChange(this, new KeyboardControlMnanager.OnKeyboardStateChangeListener() {
            View anchorView;

            @Override
            public void onKeyboardChange(int keyboardHeight, boolean isVisible) {
                if (isVisible) { //键盘弹出
                    inputViewModel.setIsPop(true);
                } else { //键盘收起的时候判断是否有文字输入,如果有则继续展示发送按钮
                    if (TextUtils.isEmpty(inputViewModel.getTextString())) {
                        inputViewModel.setIsPop(false);
                    } else {
                        inputViewModel.setIsPop(true);
                    }
                }
            }
        });
    }


    @Override
    public void update() {
        super.update();
    }

    @Override
    public void refreshData() {
        super.refreshData();
        viewMdoel.testData(1, true);
    }

    private void initRv(RecyclerView rv) {
        AutoUtils.auto(headBinding.getRoot());
        adapter.addHeaderView(headBinding.getRoot());
        adapter.setEnableLoadMore(Config.isLoadMore);
        adapter.setLoadMoreView(RecycleViewUtils.getLoadMoreView());
        if (Config.isLoadMore) {
            adapter.setOnLoadMoreListener(() -> viewMdoel.loadMore(), rv);
        }
        rv.setLayoutManager(new LinearLayoutManager(this));
        rv.setAdapter(adapter);
        rv.getViewTreeObserver().addOnGlobalLayoutListener(() -> {
            int heightDiff = MyApplication.heightPixels - rv.getHeight() - MyApplication.widthPixels * 9 / 16; //计算键盘占用高度
            if (heightDiff > MyApplication.heightPixels / 3) { //键盘弹出
                inputViewModel.setIsPop(true);
            } else { //键盘收起的时候判断是否有文字输入,如果有则继续展示发送按钮
                if (TextUtils.isEmpty(inputViewModel.getTextString())) {
                    inputViewModel.setIsPop(false);
                } else {
                    inputViewModel.setIsPop(true);
                }
            }
        });
    }

    public void startVideo(String url, String title) {
        getCurPlay().setUpLazy(url, false, null, null, title);
        getCurPlay().startPlayLogic();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back:
                onBackPressed();
                break;
            case R.id.tv_send:
                viewMdoel.comment(inputViewModel.getTextString());
                inputViewModel.setTextString("");
                closeInput();
                break;
            case R.id.iv_share:

                break;
            case R.id.iv_collect:
                viewMdoel.cancelPrase();
                break;
            case R.id.iv_uncollect:
                viewMdoel.prase();
                break;
        }
    }

    public void updateCollect(boolean isCollect) {
        inputViewModel.setIsCollect(isCollect);
    }


    @Override
    public void onBackPressed() {
        if (orientationUtils != null) {
            orientationUtils.backToProtVideo();
        }

        if (GSYVideoManager.backFromWindowFull(this)) {
            return;
        }
        super.onBackPressed();
    }


    @Override
    protected void onPause() {
        getCurPlay().onVideoPause();
        super.onPause();
        isPause = true;
    }

    @Override
    protected void onResume() {
        getCurPlay().onVideoResume(false);
        super.onResume();
        isPause = false;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        long pos = getCurPlay().getGSYVideoManager().getMediaPlayer().getCurrentPosition();
        viewMdoel.addScore((int) pos);
        viewMdoel.saveTime((int) pos);
        viewMdoel.destroy();
        if (isPlay) {
            getCurPlay().release();
        }
        //GSYPreViewManager.instance().releaseMediaPlayer();
        if (orientationUtils != null)
            orientationUtils.releaseListener();
    }


    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        //如果旋转了就全屏
        if (isPlay && !isPause) {
            binding.scv.onConfigurationChanged(this, newConfig, orientationUtils, true, true);
        }
    }


    private void resolveNormalVideoUI() {
        //增加title
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) binding.scv.getLayoutParams();
        params.height = MyApplication.widthPixels * 9 / 16;
        binding.scv.setLayoutParams(params);
        binding.scv.getTitleTextView().setVisibility(View.VISIBLE);
        binding.scv.getBackButton().setVisibility(View.VISIBLE);
    }

    private GSYVideoPlayer getCurPlay() {
        if (binding.scv.getFullWindowPlayer() != null) {
            return binding.scv.getFullWindowPlayer();
        }
        return binding.scv;
    }

    @Override
    protected void viewModelDestroy() {
        viewMdoel.destroy();
        inputViewModel.destroy();
    }
}
