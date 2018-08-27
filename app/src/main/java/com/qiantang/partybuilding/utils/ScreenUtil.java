package com.qiantang.partybuilding.utils;

import android.content.Context;
import android.os.Build;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.util.DisplayMetrics;
import android.view.WindowManager;


import com.qiantang.partybuilding.MyApplication;

import java.util.UUID;

public class ScreenUtil {
    //获取屏幕分辨率
    public static DisplayMetrics getWidthAndHeight() {
        DisplayMetrics dm = new DisplayMetrics();
        WindowManager windowManager = (WindowManager) (MyApplication.getContext()).getSystemService(Context.WINDOW_SERVICE);
        windowManager.getDefaultDisplay().getMetrics(dm);
        return dm;
    }

    public static int dip2px(float dpValue) {
        final float scale = MyApplication.getContext().getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }


    public static String getDeviceId() {
        try {
            TelephonyManager tm = (TelephonyManager) MyApplication.getContext()
                    .getSystemService(Context.TELEPHONY_SERVICE);
            String deviceId = tm.getDeviceId();
            if (StringUtil.isEmpty(deviceId)) {
                deviceId = Settings.Secure.getString(MyApplication.getContext().getContentResolver(), Settings.Secure.ANDROID_ID);
            }
            return deviceId;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    //获得独一无二的Psuedo ID
    public static String getUniquePsuedoID() {
        String serial = null;
        String m_szDevIDShort = "35" +
                Build.BOARD.length() % 10 + Build.BRAND.length() % 10 +
                //手机主板                  系统定制商
                Build.CPU_ABI.length() % 10 + Build.DEVICE.length() % 10 +
                //CPU指令集                    设备参数
                Build.DISPLAY.length() % 10 + Build.HOST.length() % 10 +
                //显示屏参数
                Build.ID.length() % 10 + Build.MANUFACTURER.length() % 10 +
                //修订版本列表                硬件制造商
                Build.MODEL.length() % 10 + Build.PRODUCT.length() % 10 +
                //版本即最终用户可见的名称      整个产品的名称
                Build.TAGS.length() % 10 + Build.TYPE.length() % 10 +
                //描述build的标签，如未签名，debug等    build的类型
                Build.USER.length() % 10; //13 位

        try {
            serial = android.os.Build.class.getField("SERIAL").get(null).toString();
            //API>=9 使用serial号
            return new UUID(m_szDevIDShort.hashCode(), serial.hashCode()).toString();
        } catch (Exception exception) {
            //serial需要一个初始化
            serial = "serial"; // 随便一个初始化
        }
        //使用硬件信息拼凑出来的15位号码
        return new UUID(m_szDevIDShort.hashCode(), serial.hashCode()).toString();
    }

    public static float getDimension(int id) {
        return MyApplication.getContext().getResources().getDimension(id);
    }
}
