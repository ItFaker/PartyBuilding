package com.qiantang.partybuilding.module.mine.adapter;

import android.app.Activity;
import android.content.res.Configuration;
import android.view.View;
import android.widget.RelativeLayout;

import com.facebook.drawee.view.SimpleDraweeView;
import com.qiantang.partybuilding.BR;
import com.qiantang.partybuilding.MyApplication;
import com.qiantang.partybuilding.R;
import com.qiantang.partybuilding.adapter.BindingViewHolder;
import com.qiantang.partybuilding.adapter.EasyBindQuickAdapter;
import com.qiantang.partybuilding.config.Config;
import com.qiantang.partybuilding.modle.RxVideoStudy;
import com.qiantang.partybuilding.widget.SampleCoverVideo;
import com.shuyu.gsyvideoplayer.GSYVideoManager;
import com.shuyu.gsyvideoplayer.listener.GSYSampleCallBack;
import com.shuyu.gsyvideoplayer.utils.Debuger;
import com.shuyu.gsyvideoplayer.utils.OrientationUtils;
import com.shuyu.gsyvideoplayer.video.StandardGSYVideoPlayer;

/**
 * Created by zhaoyong bai on 2018/5/24.
 */
public class VideoStudyFragmentAdapter extends EasyBindQuickAdapter<RxVideoStudy> {


    public VideoStudyFragmentAdapter(int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void easyConvert(BindingViewHolder holder, RxVideoStudy item) {
        ((SimpleDraweeView)holder.getBinding().getRoot().findViewById(R.id.sdv)).setImageURI(Config.IMAGE_HOST+item.getImg());
        holder.addOnClickListener(R.id.tv_name);
        holder.getBinding().setVariable(BR.item, item);
        holder.getBinding().executePendingBindings();
    }

}
