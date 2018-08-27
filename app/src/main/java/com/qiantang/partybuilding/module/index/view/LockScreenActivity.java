package com.qiantang.partybuilding.module.index.view;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.qiantang.partybuilding.R;
import com.qiantang.partybuilding.config.Config;
import com.qiantang.partybuilding.utils.SharedPreferences;
import com.qiantang.partybuilding.widget.SildingFinishLayout;

/**
 * Author:    Jintf
 * Date:      2018/8/22 0022 上午 11:57
 */
public class LockScreenActivity extends AppCompatActivity implements View.OnClickListener{
    SildingFinishLayout mFinishLayout;
    ImageView ivLockMusicPlay;
    public boolean isPlaying ;
    public String voiceTitle;
    public String voiceUrl;
    public SimpleDraweeView mSimpleDraweeView;
    public TextView tvVoiceTitle;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //注意需要做一下判断
        if (getWindow() != null) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN |
                    WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN |
                    WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
            // 锁屏的activity内部也要做相应的配置，让activity在锁屏时也能够显示，同时去掉系统锁屏。
            // 当然如果设置了系统锁屏密码，系统锁屏是没有办法去掉的
            // FLAG_DISMISS_KEYGUARD用于去掉系统锁屏页
            // FLAG_SHOW_WHEN_LOCKED使Activity在锁屏时仍然能够显示
            window.addFlags(WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD |
                    WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED);
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                window.getDecorView().setSystemUiVisibility(
                        View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                                | View.SYSTEM_UI_FLAG_FULLSCREEN | View.SYSTEM_UI_FLAG_IMMERSIVE);
            }
        }
        setContentView(R.layout.activity_lockscreen);
        initView();
    }
    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if(hasFocus && getWindow()!=null){
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                getWindow().getDecorView().setSystemUiVisibility(
                        // SYSTEM_UI_FLAG_LAYOUT_STABLE保持整个View稳定，使View不会因为SystemUI的变化而做layout
                        View.SYSTEM_UI_FLAG_LAYOUT_STABLE |
                                // SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION，开发者容易被其中的HIDE_NAVIGATION所迷惑，
                                // 其实这个Flag没有隐藏导航栏的功能，只是控制导航栏浮在屏幕上层，不占据屏幕布局空间；
                                View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION |
                                View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN |
                                // SYSTEM_UI_FLAG_HIDE_NAVIGATION，才是能够隐藏导航栏的Flag；
                                View.SYSTEM_UI_FLAG_HIDE_NAVIGATION |
                                // SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN，由上面可知，也不能隐藏状态栏，只是使状态栏浮在屏幕上层。
                                View.SYSTEM_UI_FLAG_FULLSCREEN |
                                View.SYSTEM_UI_FLAG_IMMERSIVE);
            }
        }
    }
    public void initView(){
        mFinishLayout = findViewById(R.id.slf);
        ivLockMusicPlay = findViewById(R.id.lock_music_play);
        mSimpleDraweeView = findViewById(R.id.sdv);
        tvVoiceTitle = findViewById(R.id.tv_voice_title);

        mFinishLayout.setOnSildingFinishListener(new SildingFinishLayout.OnSildingFinishListener() {

            @Override
            public void onSildingFinish() {
                finish();
            }
        });
        mFinishLayout.setTouchView(getWindow().getDecorView());
        ivLockMusicPlay.setOnClickListener(this);
        voiceTitle = SharedPreferences.getInstance().getString("VoiceTitle","");
        Log.e("VoiceTitle",voiceTitle);
        tvVoiceTitle.setText(voiceTitle);
        voiceUrl = SharedPreferences.getInstance().getString("VoiceImg","");
        Log.e("VoiceImg",voiceUrl);
        mSimpleDraweeView.setImageURI(Config.IMAGE_HOST + voiceUrl);
        isPlaying = SharedPreferences.getInstance().getBoolean("isPlaying",false);
    }
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.lock_music_play:
                Log.e("onClick","点击");

                if (isPlaying){
                    ivLockMusicPlay.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(),R.drawable.lock_btn_play));
                }else if(!isPlaying){
                    ivLockMusicPlay.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(),R.drawable.lock_btn_pause));
                }
                isPlaying = !isPlaying;
                SharedPreferences.getInstance().putBoolean("isPlaying",isPlaying);
                break;
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        int key = event.getKeyCode();
        switch (key) {
            case KeyEvent.KEYCODE_BACK: {
                return true;
            }
            case KeyEvent.KEYCODE_MENU:{
                return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }
    @Override
    public void onBackPressed() {
        // 不做任何事，为了屏蔽back键
    }
}
