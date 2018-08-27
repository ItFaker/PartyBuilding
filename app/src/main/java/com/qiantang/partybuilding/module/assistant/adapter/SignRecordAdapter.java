package com.qiantang.partybuilding.module.assistant.adapter;

import com.facebook.drawee.view.SimpleDraweeView;
import com.qiantang.partybuilding.R;
import com.qiantang.partybuilding.adapter.BindingViewHolder;
import com.qiantang.partybuilding.adapter.EasyBindQuickAdapter;
import com.qiantang.partybuilding.config.Config;
import com.qiantang.partybuilding.modle.RxSignInfo;
import com.qiantang.partybuilding.BR;

import java.util.List;

/**
 * Created by zhaoyong bai on 2018/6/8.
 */
public class SignRecordAdapter extends EasyBindQuickAdapter<RxSignInfo> {
    public SignRecordAdapter(int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void easyConvert(BindingViewHolder holder, RxSignInfo item) {
        holder.getBinding().setVariable(BR.item, item);
        holder.getBinding().executePendingBindings();
        ((SimpleDraweeView) holder.getBinding().getRoot().findViewById(R.id.sdv)).setImageURI(Config.IMAGE_HOST+item.getAvatar());
    }
}
