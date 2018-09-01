package com.qiantang.partybuilding.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import com.qiantang.partybuilding.R;

import java.util.List;

/**
 * Author:    Jintf
 * Date:      2018/8/28 0028 下午 5:48
 */
public class SearchFlowAdapter extends RecyclerView.Adapter<SearchFlowAdapter.SearchViewHolder> implements View.OnClickListener {
    private List<String> mStringList;

    public SearchFlowAdapter(List<String> list){
        this.mStringList = list;
    }

    @NonNull
    @Override
    public SearchViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_search_record,parent,false);

        return new SearchViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SearchViewHolder holder, int position) {

        holder.tvSearch.setText(mStringList.get(position));
        SearchViewHolder viewHolder = holder;
        viewHolder.itemView.setTag(position);
        viewHolder.mImageView.setTag(position);


    }

    @Override
    public int getItemCount() {
        if(null ==mStringList)
            return 0;
        return mStringList.size();
    }

    @Override
    public void onClick(View view) {
        int position = (int) view.getTag();
        if (mOnItemClickListener != null) {
            switch (view.getId()){
                case R.id.iv_delete:
                    mOnItemClickListener.onClick(view, ViewName.PRACTISE, position);
                    break;
                default:
                    mOnItemClickListener.onClick(view, ViewName.ITEM, position);
                    break;
            }
        }

    }


    public  class SearchViewHolder extends RecyclerView.ViewHolder {
        public TextView tvSearch;
        public ImageView mImageView;
        public SearchViewHolder(View view){
            super(view);
            tvSearch = view.findViewById(R.id.tv_search_record);
            mImageView = view.findViewById(R.id.iv_delete);
            view.setOnClickListener(SearchFlowAdapter.this);
            mImageView.setOnClickListener(SearchFlowAdapter.this);
        }
    }
    ////////////////////////////以下为item点击处理///////////////////////////////

    private OnRecyclerViewItemClickListener mOnItemClickListener = null;

    public void setOnItemClickListener(OnRecyclerViewItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }

    /** item里面有多个控件可以点击 */
    public enum ViewName {
        ITEM,
        PRACTISE
    }

    public interface OnRecyclerViewItemClickListener {
        void onClick(View view, ViewName viewName, int position);
    }


}
