package com.qiantang.partybuilding.module.index.adapter;

import com.facebook.drawee.view.SimpleDraweeView;
import com.qiantang.partybuilding.BR;
import com.qiantang.partybuilding.R;
import com.qiantang.partybuilding.adapter.BindingViewHolder;
import com.qiantang.partybuilding.adapter.EasyBindQuickAdapter;
import com.qiantang.partybuilding.config.Config;
import com.qiantang.partybuilding.modle.RxVideoStudy;

/**
 * Created by zhaoyong bai on 2018/5/24.
 */
public class IndexVideoAdapter extends EasyBindQuickAdapter<RxVideoStudy> {

    public IndexVideoAdapter(int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void easyConvert(BindingViewHolder holder, RxVideoStudy item) {
        holder.getBinding().setVariable(BR.item, item);
        holder.getBinding().executePendingBindings();
        ((SimpleDraweeView)holder.getBinding().getRoot().findViewById(R.id.sdv)).setImageURI(Config.IMAGE_HOST+item.getImg());
    }

}
