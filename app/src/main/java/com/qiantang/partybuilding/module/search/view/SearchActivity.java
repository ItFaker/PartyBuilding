package com.qiantang.partybuilding.module.search.view;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.TextView;

import com.qiantang.partybuilding.BaseBindActivity;
import com.qiantang.partybuilding.R;
import com.qiantang.partybuilding.adapter.SearchFlowAdapter;
import com.qiantang.partybuilding.config.Event;
import com.qiantang.partybuilding.databinding.ActivitySearchBinding;
import com.qiantang.partybuilding.module.index.view.VideoStudySearchFragment;
import com.qiantang.partybuilding.module.search.fragment.FcNoticeFragment;
import com.qiantang.partybuilding.utils.SharedPreferences;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhaoyong bai on 2018/6/8.
 */
public class SearchActivity extends BaseBindActivity {
    private ActivitySearchBinding binding;
    private int type;
    private Fragment fragment;
    private FragmentTransaction transaction;
    private List<String> searchList;
    private SearchFlowAdapter mSearchFlowAdapter;
//    private int classifyId = 0;//如果页面有tab ，表示tab的index
    private int isVideo = 0;
    @Override
    protected void initBind() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_search);
    }

    @Override
    public void initView() {
        type = getIntent().getIntExtra("type", 0);
//        classifyId = getIntent().getIntExtra("classifyId",0);
        isVideo = getIntent().getIntExtra("isVideo",0);
        binding.toolbar.ivClear.setVisibility(View.GONE);
        searchList = new ArrayList<>();
        if(null != SharedPreferences.getInstance().getDataList("searchList","")){
            searchList = SharedPreferences.getInstance().getDataList("searchList","");
            setRecyclerView();
        }
        if(null!=searchList &&searchList.size()!=0){
            binding.linearRecord.setVisibility(View.VISIBLE);
            binding.flContent.setVisibility(View.GONE);

        }else{
            binding.linearRecord.setVisibility(View.GONE);
            binding.flContent.setVisibility(View.VISIBLE);
        }
        transaction = getSupportFragmentManager().beginTransaction();
        if (type > 0) {
            if(isVideo !=1){
                fragment = new FcNoticeFragment();
            }else{
                fragment = new VideoStudySearchFragment();
            }
            Bundle bundle = new Bundle();
            bundle.putInt("type", type);
//            if(classifyId!=0){
//                bundle.putInt("classifyId", classifyId);
//            }
            fragment.setArguments(bundle);
            transaction.add(R.id.fl_content, fragment);
            transaction.commit();
        }
        binding.toolbar.et.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                if (i == EditorInfo.IME_ACTION_SEARCH) {
                    String keyword = "";
                    keyword = binding.toolbar.et.getText().toString();
                    searchList.add(0,keyword);
                    SharedPreferences.getInstance().setDataList("searchList",searchList);
                    EventBus.getDefault().post(keyword);
                    closeInput();
                    return true;
                }
                return false;
            }
        });
        binding.toolbar.et.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence sequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence sequence, int i, int i1, int i2) {
                if(!TextUtils.isEmpty(sequence)){
                    binding.toolbar.ivClear.setVisibility(View.VISIBLE);
                }else {
                    binding.toolbar.ivClear.setVisibility(View.GONE);
                    binding.linearRecord.setVisibility(View.VISIBLE);
                    binding.flContent.setVisibility(View.GONE);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    @Override
    public void onClick(View view) {
        super.onClick(view);
        switch (view.getId()) {
            case R.id.iv_back:
                onBackPressed();
                break;
            case R.id.tv_right:
                binding.linearRecord.setVisibility(View.GONE);
                binding.flContent.setVisibility(View.VISIBLE);
                String keyword = "";
                keyword = binding.toolbar.et.getText().toString();
                if(null!=SharedPreferences.getInstance().getDataList("searchList","")&&
                        !TextUtils.isEmpty(keyword)){

                    if(!SharedPreferences.getInstance().getDataList("searchList","").contains(keyword) &&
                            !TextUtils.isEmpty(keyword)){
                        searchList.add(0,keyword);
                    }
                }else {
                    searchList.add(0,keyword);
                }
                SharedPreferences.getInstance().setDataList("searchList",searchList);
                EventBus.getDefault().post(keyword);
                closeInput();
                break;
            case R.id.iv_clear:
                binding.toolbar.et.setText("");
                binding.flContent.setVisibility(View.GONE);
                binding.linearRecord.setVisibility(View.VISIBLE);
                if(null == mSearchFlowAdapter){
                    setRecyclerView();
                }else{
                    mSearchFlowAdapter.notifyDataSetChanged();
                }
                binding.toolbar.ivClear.setVisibility(View.GONE);
                break;
            case R.id.tv_delete_all:
                searchList.clear();
                SharedPreferences.getInstance().setDataList("searchList",searchList);
                mSearchFlowAdapter.notifyDataSetChanged();
                break;
        }
    }

    public void setRecyclerView(){
//        mSearchFlowAdapter = new SearchFlowAdapter(searchList, new SearchFlowAdapter.onItemClickListener() {
//            @Override
//            public void onItemClick(int pos) {
//                String keyword = searchList.get(pos);
//                binding.toolbar.et.setText(keyword);
//                EventBus.getDefault().post(keyword);
//                closeInput();
//            }
//        });
        mSearchFlowAdapter = new SearchFlowAdapter(searchList);
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        binding.recyclerView.setAdapter(mSearchFlowAdapter);
        mSearchFlowAdapter.setOnItemClickListener(new SearchFlowAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onClick(View view, SearchFlowAdapter.ViewName viewName, int position) {
                switch (viewName){
                    case ITEM:
                        String keyword = searchList.get(position);
                        binding.toolbar.et.setText(keyword);
                        break;
                    case PRACTISE:
                        searchList.remove(position);
                        SharedPreferences.getInstance().setDataList("searchList",searchList);
                        mSearchFlowAdapter.notifyDataSetChanged();
                        break;
                }
            }
        });
    }
    @Override
    protected void viewModelDestroy() {

    }
}
