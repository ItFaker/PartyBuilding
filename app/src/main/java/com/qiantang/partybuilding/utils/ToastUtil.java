package com.qiantang.partybuilding.utils;

import android.widget.Toast;

import com.qiantang.partybuilding.MyApplication;

public class ToastUtil {
    private static Toast toast;

    public static void toast(final String msg) {
        if (!StringUtil.isEmpty(msg)) {
            makeText(msg, Toast.LENGTH_SHORT).show();
        }
    }

    public static void longToast(final String msg) {
//        String values = String.format(msg, params);
        if (!StringUtil.isEmpty(msg)) {
            makeText(msg, Toast.LENGTH_LONG).show();
        }
    }


    public static void toast(int messageId, final Object... params) {
        String values = StringUtil.getString(messageId, params);
        if (!StringUtil.isEmpty(values)) {
            makeText(values, Toast.LENGTH_SHORT).show();
        }
    }

   /* public static void imgToast(int imgId, int messageId, final Object... params) {
        String message = MyApplication.getContext().getResources().getString(messageId);
        imgToast(imgId, message, params);
    }*/

   /* public static void imgToast(int imgId, String message, final Object... params) {
        View layout = LayoutInflater.from(MyApplication.getContext()).inflate(R.layout
        .layout_image_toast, null);
        TextView text = (TextView) layout.findViewById(R.id.tv_toast);
        ImageView image = (ImageView) layout.findViewById(R.id.iv_toast);
        image.setImageResource(imgId);
        String values = String.format(message, params);
        text.setText(values);
        if (toast != null) {
            toast.cancel();
        }
        toast = new Toast(MyApplication.getContext());
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setView(layout);
        toast.show();
    }*/

    private static Toast makeText(String message, int duration) {
        if (StringUtil.isEmpty(message)) {
            message = "";
        }
        if (toast != null) {
            toast.cancel();
        }
        toast = Toast.makeText(MyApplication.getContext(), message, duration);
        return toast;
    }
}
