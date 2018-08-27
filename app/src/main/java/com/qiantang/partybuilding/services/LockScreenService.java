package com.qiantang.partybuilding.services;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;
import android.util.Log;

import com.qiantang.partybuilding.module.index.view.LockScreenActivity;
import com.qiantang.partybuilding.utils.SharedPreferences;

/**
 * Author:    Jintf
 * Date:      2018/8/22 0022 下午 1:41
 */
public class LockScreenService extends Service{
    private BroadcastReceiver receiver = new BroadcastReceiver() {
        @SuppressWarnings("deprecation")
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals(Intent.ACTION_SCREEN_ON)) {

                if(SharedPreferences.getInstance().getBoolean("isPlaying",false)){
                    Intent mLockIntent = new Intent(context, LockScreenActivity.class);
                    mLockIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK
                            | Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
                    startActivity(mLockIntent);
                }
            }
        }
    };


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.e("TAG","开启锁屏服务");
        return START_STICKY;
    }

    @Override
    public void onCreate() {
        IntentFilter filter = new IntentFilter();
        filter.addAction(Intent.ACTION_SCREEN_OFF);
        filter.addAction(Intent.ACTION_SCREEN_ON);
        registerReceiver(receiver, filter);
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unregisterReceiver(receiver);

//        Intent localIntent = new Intent();
//        localIntent.setClass(this, LockScreenService.class); //销毁时重新启动Service
//        this.startService(localIntent);
    }
}
