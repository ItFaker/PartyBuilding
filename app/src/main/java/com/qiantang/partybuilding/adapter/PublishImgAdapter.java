package com.qiantang.partybuilding.adapter;

import com.facebook.drawee.view.SimpleDraweeView;
import com.qiantang.partybuilding.BR;
import com.qiantang.partybuilding.R;
import com.qiantang.partybuilding.utils.DraweeViewUtils;

import cn.finalteam.galleryfinal.model.PhotoInfo;

public class PublishImgAdapter extends EasyBindQuickAdapter<PhotoInfo> {

    public PublishImgAdapter(int layoutResId) {
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
