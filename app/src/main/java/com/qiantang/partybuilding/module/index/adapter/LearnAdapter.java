package com.qiantang.partybuilding.module.index.adapter;

import com.facebook.common.references.SharedReference;
import com.facebook.drawee.view.SimpleDraweeView;
import com.qiantang.partybuilding.R;
import com.qiantang.partybuilding.adapter.BindingViewHolder;
import com.qiantang.partybuilding.adapter.EasyBindQuickAdapter;
import com.qiantang.partybuilding.config.Config;
import com.qiantang.partybuilding.modle.RxLearningList;
import com.qiantang.partybuilding.BR;

/**
 * Created by zhaoyong bai on 2018/6/13.
 */
public class LearnAdapter extends EasyBindQuickAdapter<RxLearningList> {
    public LearnAdapter(int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void easyConvert(BindingViewHolder holder, RxLearningList item) {
        holder.getBinding().setVariable(BR.item, item);
        holder.getBinding().executePendingBindings();
        try {
            ((SimpleDraweeView) holder.getBinding().getRoot().findViewById(R.id.sdv)).setImageURI(Config.IMAGE_HOST+item.getPrinturl());
        } catch (NullPointerException e) {

        }
    }
}
