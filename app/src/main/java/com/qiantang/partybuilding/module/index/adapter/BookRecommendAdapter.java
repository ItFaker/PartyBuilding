package com.qiantang.partybuilding.module.index.adapter;

import com.facebook.drawee.view.SimpleDraweeView;
import com.qiantang.partybuilding.BR;
import com.qiantang.partybuilding.R;
import com.qiantang.partybuilding.adapter.BindingViewHolder;
import com.qiantang.partybuilding.adapter.EasyBindQuickAdapter;
import com.qiantang.partybuilding.config.Config;
import com.qiantang.partybuilding.modle.RxBookRecommend;
import com.qiantang.partybuilding.modle.RxIndexCommon;

/**
 * Created by zhaoyong bai on 2018/5/22.
 */
public class BookRecommendAdapter extends EasyBindQuickAdapter<RxBookRecommend> {
    public BookRecommendAdapter(int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void easyConvert(BindingViewHolder holder, RxBookRecommend item) {
        holder.getBinding().setVariable(BR.item, item);
        try {
            ((SimpleDraweeView) holder.getBinding().getRoot().findViewById(R.id.sdv)).setImageURI(Config.IMAGE_HOST+item.getPrinturl());
        } catch (NullPointerException e) {

        }

        holder.getBinding().executePendingBindings();
    }
}
