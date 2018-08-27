package com.qiantang.partybuilding.module.study.adapter;

import com.facebook.drawee.view.SimpleDraweeView;
import com.qiantang.partybuilding.R;
import com.qiantang.partybuilding.adapter.BindingViewHolder;
import com.qiantang.partybuilding.adapter.EasyBindQuickAdapter;
import com.qiantang.partybuilding.config.Config;
import com.qiantang.partybuilding.utils.DraweeViewUtils;
import com.qiantang.partybuilding.BR;
import cn.finalteam.galleryfinal.model.PhotoInfo;

public class PublishAdapter extends EasyBindQuickAdapter<PhotoInfo> {

    public PublishAdapter(int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void easyConvert(BindingViewHolder holder, PhotoInfo item) {
        holder.getBinding().setVariable(BR.item,item);
        holder.getBinding().executePendingBindings();
        ((SimpleDraweeView)holder.getView(R.id.sdv)).setImageURI(DraweeViewUtils.getUriPath(item.getPhotoPath()));
        holder.addOnClickListener(R.id.publish_item_lose)
                .addOnClickListener(R.id.sdv_item_pic);
    }
}
