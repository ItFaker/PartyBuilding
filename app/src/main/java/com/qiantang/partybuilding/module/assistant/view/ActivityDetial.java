package com.qiantang.partybuilding.module.assistant.view;

import android.databinding.DataBindingUtil;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;

import com.qiantang.partybuilding.BaseBindActivity;
import com.qiantang.partybuilding.MyApplication;
import com.qiantang.partybuilding.R;
import com.qiantang.partybuilding.adapter.CommentAdapter;
import com.qiantang.partybuilding.config.Config;
import com.qiantang.partybuilding.databinding.ActivityDetialBinding;
import com.qiantang.partybuilding.databinding.ViewActivityDetialHeadBinding;
import com.qiantang.partybuilding.module.assistant.adapter.SignRecordAdapter;
import com.qiantang.partybuilding.module.assistant.viewmodel.ActivityDetialViewModel;
import com.qiantang.partybuilding.module.input.viewmodel.InputViewModel;
import com.qiantang.partybuilding.utils.ActivityUtil;
import com.qiantang.partybuilding.utils.AutoUtils;
import com.qiantang.partybuilding.utils.RecycleViewUtils;
import com.qiantang.partybuilding.widget.commentwidget.CircleViewHelper;
import com.qiantang.partybuilding.widget.commentwidget.CommentBox;
import com.qiantang.partybuilding.widget.commentwidget.IComment;
import com.qiantang.partybuilding.widget.commentwidget.KeyboardControlMnanager;

/**
 * Created by zhaoyong bai on 2018/5/28.
 * 活动详情
 */
public class ActivityDetial extends BaseBindActivity implements CommentBox.OnCommentSendClickListener {
    private ActivityDetialViewModel viewModel;
    private ActivityDetialBinding binding;
    private ViewActivityDetialHeadBinding headBinding;
    private CommentAdapter adapter;
    private SignRecordAdapter signRecordAdapter;
    private CircleViewHelper circleViewHelper;

    @Override
    protected void initBind() {
        signRecordAdapter = new SignRecordAdapter(R.layout.item_sign_record);
        adapter = new CommentAdapter(R.layout.item_comment);
        viewModel = new ActivityDetialViewModel(this, adapter, signRecordAdapter);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_detial);
        binding.setViewModel(viewModel);
        headBinding = DataBindingUtil.inflate(getLayoutInflater(), R.layout.view_activity_detial_head, null, false);
        headBinding.setViewModel(viewModel);
    }

    @Override
    public void initView() {
        binding.toolbar.setTitle("活动详情");
        binding.toolbar.setIsHide(false);
        initRv(binding.rv);
        initRecordRv(headBinding.rvRecord);
        if (circleViewHelper == null) {
            circleViewHelper = new CircleViewHelper(this);
        }
        binding.input.setOnCommentSendClickListener(this);
        if (viewModel.getStatus() >= 3) {
            binding.input.showCommentBox();
        }
    }

    private void initRecordRv(RecyclerView rvRecord) {
        rvRecord.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        rvRecord.setAdapter(signRecordAdapter);
        rvRecord.addOnItemTouchListener(viewModel.onSignItemTouchListener());
        initKeyboardHeightObserver();
    }

    private void initKeyboardHeightObserver() {
        //观察键盘弹出与消退
        KeyboardControlMnanager.observerKeyboardVisibleChange(this, (keyboardHeight, isVisible) -> {
            if (isVisible) {
                //定位评论框到view
//                circleViewHelper.alignCommentBoxToView(binding.rv, binding.input, binding.input.getCommentType());
            } else {
                //定位到底部
                binding.input.dismissCommentBox(true);
            }
        });
    }

    public void scorllTop() {
        binding.rv.scrollBy(0, Integer.MAX_VALUE);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                onBackPressed();
                break;
            case R.id.ll_comment:
                showCommentBox();
                break;
            case R.id.tv_join:
                viewModel.enroll();
                break;
        }
    }

    private void showCommentBox() {
        viewModel.isInput.set(true);
        binding.input.toggleCommentBox(viewModel.getDetials().getActivityId(), "发表评论", false);
    }

    @Override
    public void onBackPressed() {
        if (viewModel.isInput.get() && viewModel.getStatus() == 3&&!viewModel.isApply.get()) {
            viewModel.isInput.set(false);
            binding.input.dismissCommentBoxWithoutShowing(false);
            return;
        }
        super.onBackPressed();
    }

    private void initRv(RecyclerView rv) {
        adapter.setEnableLoadMore(Config.isLoadMore);
        AutoUtils.auto(headBinding.getRoot());
        adapter.addHeaderView(headBinding.getRoot());
        adapter.setLoadMoreView(RecycleViewUtils.getLoadMoreView());
        rv.setLayoutManager(new LinearLayoutManager(this));
        rv.setAdapter(adapter);
        rv.setFocusable(false);
        rv.addOnItemTouchListener(viewModel.onItemTouchListener());
        if (Config.isLoadMore) {
            adapter.setOnLoadMoreListener(() -> viewModel.loadMore(), rv);
        }
        initRefresh(binding.cptr);
        viewModel.getData(1);
    }


    @Override
    public void refreshData() {
        super.refreshData();
        viewModel.getData(1);
    }

    /**
     * 提交成功之后隐藏键盘
     */
    public void dissmissCommentBox() {
        binding.input.hideInput();
    }

    @Override
    protected void viewModelDestroy() {
        viewModel.destroy();
    }

    @Override
    public void onCommentSendClick(View v, IComment comment, String commentContent) {
        if (!MyApplication.isLogin()) {
            ActivityUtil.startLoginActivity(this);
            return;
        }
        viewModel.comment(commentContent);
    }
}
