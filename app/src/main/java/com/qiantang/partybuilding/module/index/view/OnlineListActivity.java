package com.qiantang.partybuilding.module.index.view;

import android.databinding.DataBindingUtil;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;

import com.qiantang.partybuilding.BaseBindActivity;
import com.qiantang.partybuilding.R;
import com.qiantang.partybuilding.config.Event;
import com.qiantang.partybuilding.databinding.ActivityLearningListBinding;
import com.qiantang.partybuilding.module.index.adapter.LearningViewPagerAdapter;
import com.qiantang.partybuilding.module.index.viewmodel.LearningListViewModel;
import com.qiantang.partybuilding.module.index.viewmodel.OnlineListViewModel;
import com.qiantang.partybuilding.utils.ActivityUtil;

import java.util.List;

/**
 * Created by zhaoyong bai on 2018/6/13.
 */
public class OnlineListActivity extends BaseBindActivity {
    private ActivityLearningListBinding binding;
    private LearningViewPagerAdapter viewPagerAdapter;
    private OnlineListViewModel viewModel;

//    private List<Integer> classifyIdList;
    private int currentIndex;
//    private int classifyId;

    @Override
    protected void initBind() {
        viewPagerAdapter = new LearningViewPagerAdapter(getSupportFragmentManager());
        viewModel = new OnlineListViewModel(this, viewPagerAdapter);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_learning_list);
    }

    @Override
    public void initView() {
        binding.toolbar.setTitle("理论在线");
        binding.toolbar.setResId(R.mipmap.icon_search_black);
        binding.tablayout.setupWithViewPager(binding.viewpager);
        binding.viewpager.setAdapter(viewPagerAdapter);
        binding.viewpager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                currentIndex = position;
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        viewModel.getData();
    }

    public void setPagerPos(int pos) {
        binding.viewpager.setCurrentItem(pos);
    }
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_right:
//                classifyIdList = viewModel.getCurrentClassifyIdList();
//                classifyId = classifyIdList.get(currentIndex);
//                Log.e("classifyId",""+classifyId);
                ActivityUtil.startSearchActivityLearn(this, Event.SEARCH_ONLINE);
                break;
            case R.id.iv_back:
                onBackPressed();
                break;
        }
    }

    @Override
    protected void viewModelDestroy() {
        viewModel.destroy();
    }
}
