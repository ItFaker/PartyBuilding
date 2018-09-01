package com.qiantang.partybuilding.module.index.view;

import android.databinding.DataBindingUtil;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.qiantang.partybuilding.BaseBindActivity;
import com.qiantang.partybuilding.R;
import com.qiantang.partybuilding.config.Event;
import com.qiantang.partybuilding.databinding.ActivityRecycleviewBinding;
import com.qiantang.partybuilding.module.index.adapter.BookRecommendAdapter;
import com.qiantang.partybuilding.module.index.viewmodel.BookRecommendViewModel;
import com.qiantang.partybuilding.utils.ActivityUtil;
import com.qiantang.partybuilding.utils.RecycleViewUtils;

/**
 * Created by zhaoyong bai on 2018/6/17.
 * 好书推荐
 */
public class BookRecommendActivity extends BaseBindActivity {
    private ActivityRecycleviewBinding binding;
    private BookRecommendAdapter adapter;
    private BookRecommendViewModel viewModel;

    @Override
    protected void initBind() {
        adapter=new BookRecommendAdapter(R.layout.item_book_recommend);
        viewModel=new BookRecommendViewModel(this,adapter);
        binding= DataBindingUtil.setContentView(this,R.layout.activity_recycleview);
        binding.setViewModel(viewModel);
    }

    @Override
    public void initView() {
        binding.toolbar.setTitle("好书推荐");
        binding.toolbar.setResId(R.mipmap.icon_search_black);
        initRv(binding.rv);
        viewModel.getData(1);
//        initRefresh(binding.cptr);
    }

    @Override
    public void refreshData() {
        super.refreshData();
        viewModel.getData(1);
    }

    private void initRv(RecyclerView rv) {
        rv.setLayoutManager(new LinearLayoutManager(this));
        rv.setAdapter(adapter);
        adapter.setEnableLoadMore(true);
        adapter.setLoadMoreView(RecycleViewUtils.getLoadMoreView());
        adapter.setOnLoadMoreListener(()->viewModel.loadMore(),rv);
        rv.addOnItemTouchListener(viewModel.onItemTouchListener());
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                onBackPressed();
                break;
            case R.id.iv_right://跳转到好书推荐搜索界面
                ActivityUtil.startSearchActivity(this, Event.SEARCH_GOOD_BOOK);
                break;
        }
    }
    @Override
    protected void viewModelDestroy() {
        viewModel.destroy();
    }
}
