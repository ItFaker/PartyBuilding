package com.qiantang.partybuilding.module.mine.adapter;

import com.facebook.drawee.view.SimpleDraweeView;
import com.qiantang.partybuilding.BR;
import com.qiantang.partybuilding.R;
import com.qiantang.partybuilding.adapter.BindingViewHolder;
import com.qiantang.partybuilding.adapter.EasyBindQuickAdapter;
import com.qiantang.partybuilding.config.Config;
import com.qiantang.partybuilding.modle.RxActivity;

/**
 * Created by zhaoyong bai on 2018/5/28.
 */
public class MyActivityAdapter extends EasyBindQuickAdapter<RxActivity> {
    public MyActivityAdapter(int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void easyConvert(BindingViewHolder holder, RxActivity item) {
        holder.getBinding().setVariable(BR.item,item);
        holder.getBinding().executePendingBindings();
        holder.addOnClickListener(R.id.tv_check)
                .addOnClickListener(R.id.tv_del);
        ((SimpleDraweeView) holder.getBinding().getRoot().findViewById(R.id.sdv)).setImageURI(Config.IMAGE_HOST + item.getImgSrc());
    }
}
