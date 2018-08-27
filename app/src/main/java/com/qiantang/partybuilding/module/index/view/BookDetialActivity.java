package com.qiantang.partybuilding.module.index.view;

import android.databinding.DataBindingUtil;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.RelativeLayout;

import com.qiantang.partybuilding.BaseBindActivity;
import com.qiantang.partybuilding.MyApplication;
import com.qiantang.partybuilding.R;
import com.qiantang.partybuilding.adapter.CommentAdapter;
import com.qiantang.partybuilding.config.Config;
import com.qiantang.partybuilding.databinding.ActivityCharacterDetialBinding;
import com.qiantang.partybuilding.databinding.ViewBookDetialHeadBinding;
import com.qiantang.partybuilding.databinding.ViewParagonHeadBinding;
import com.qiantang.partybuilding.module.index.viewmodel.BookDetialViewModel;
import com.qiantang.partybuilding.module.index.viewmodel.ParagonDetialViewModel;
import com.qiantang.partybuilding.module.input.viewmodel.InputViewModel;
import com.qiantang.partybuilding.utils.ActivityUtil;
import com.qiantang.partybuilding.utils.AutoUtils;
import com.qiantang.partybuilding.utils.RecycleViewUtils;
import com.qiantang.partybuilding.widget.MyBanner;
import com.qiantang.partybuilding.widget.commentwidget.CommentBox;
import com.qiantang.partybuilding.widget.commentwidget.IComment;
import com.qiantang.partybuilding.widget.commentwidget.KeyboardControlMnanager;

/**
 * Created by zhaoyong bai on 2018/5/28.
 * 人物表彰详情
 */
public class BookDetialActivity extends BaseBindActivity {
    private BookDetialViewModel viewModel;
    private ActivityCharacterDetialBinding binding;
    private ViewBookDetialHeadBinding headBinding;
    private CommentAdapter adapter;
    private InputViewModel inputViewModel;

    @Override
    protected void initBind() {
        adapter = new CommentAdapter(R.layout.item_comment);
        viewModel = new BookDetialViewModel(this, adapter);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_character_detial);
        headBinding = DataBindingUtil.inflate(getLayoutInflater(), R.layout.view_book_detial_head, null, false);
        headBinding.setViewModel(viewModel);
        headBinding.setViewModel(viewModel);
        inputViewModel = new InputViewModel(this);
        binding.input.setViewModel(inputViewModel);
    }

    @Override
    public void initView() {
        binding.toolbar.setTitle("好书推荐");
        initRv(binding.rv);
        inputViewModel.setHint("发表学习感悟...");
        initKeyboardHeightObserver();
        initRefresh(binding.cptr);
        inputViewModel.setShareVis(false);
    }

    @Override
    public void refreshData() {
        super.refreshData();
        viewModel.getData(1);
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

    public void updateCollect(boolean isCollect) {
        inputViewModel.setIsCollect(isCollect);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                onBackPressed();
                break;
            case R.id.iv_collect:
                viewModel.cancelPrase();
                break;
            case R.id.iv_uncollect:
                viewModel.prase();
                break;
            case R.id.tv_send:
                if (!MyApplication.isLogin()){
                    ActivityUtil.startLoginActivity(this);
                    return;
                }
                viewModel.comment(inputViewModel.getTextString());
                break;
        }
    }

    private void initRv(RecyclerView rv) {
        adapter.setEnableLoadMore(Config.isLoadMore);
        AutoUtils.auto(headBinding.getRoot());
        adapter.addHeaderView(headBinding.getRoot());
        adapter.setLoadMoreView(RecycleViewUtils.getLoadMoreView());
        rv.setLayoutManager(new LinearLayoutManager(this));
        rv.setAdapter(adapter);
        rv.addOnItemTouchListener(viewModel.onItemTouchListener());
        if (Config.isLoadMore) {
            adapter.setOnLoadMoreListener(() -> viewModel.loadMore(), rv);
        }
        viewModel.getData(1);
    }

    /**
     * 提交成功之后隐藏键盘
     */
    public void dissmissCommentBox() {
        closeInput();
        inputViewModel.setTextString("");
    }

    @Override
    protected void viewModelDestroy() {
        viewModel.destroy();
    }


}
