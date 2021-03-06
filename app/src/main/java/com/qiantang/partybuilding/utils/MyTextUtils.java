package com.qiantang.partybuilding.utils;

import android.graphics.Color;
import android.graphics.Paint;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.widget.EditText;
import android.widget.TextView;


import com.qiantang.partybuilding.R;

import java.text.DecimalFormat;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MyTextUtils {

    public static int TYPE_IMAGE = 1000;
    public static int TYPE_TEXT = 1001;

    public static double get2Double(double a) {
        DecimalFormat df = new DecimalFormat("0.00");
        return new Double(df.format(a).toString());
    }

    public static String get2String(double f) {
        return "￥" + String.format("%.2f", f);
    }

    public static String getInt2String(int i) {
        return i + "";
    }

    public static void setSalePrice(TextView textView, double d) {
        textView.setText(getSalePrice(d), TextView.BufferType.SPANNABLE);
        textView.setTextColor(Color.parseColor("#c8142d"));
    }

    public static SpannableString getSalePrice(double d) {
        String s = get2String(d);
        SpannableString word = new SpannableString(s);
        word.setSpan(new RelativeSizeSpan(0.7f), 0, 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        word.setSpan(new RelativeSizeSpan(0.7f), s.length() - 2, s.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        return word;
    }

    public static SpannableString getLowestPerHour(double d) {
        int i = ((int) d);
        String str = i + "元/H 起";
        SpannableString word = new SpannableString(str);
        word.setSpan(new ForegroundColorSpan(Color.parseColor("#ffb400")), 0, str.length() - 5, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        word.setSpan(new RelativeSizeSpan(1.5f), 0, str.length() - 5, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        return word;
    }

    public static void setPrice(TextView textView, double f) {
        String s = get2String(f);
        textView.setText(s);
        TextPaint paint = textView.getPaint();
        paint.setFlags(Paint.STRIKE_THRU_TEXT_FLAG | Paint.ANTI_ALIAS_FLAG);
    }

    public static String getPrice(TextView textView, double f) {
        TextPaint paint = textView.getPaint();
        paint.setFlags(Paint.STRIKE_THRU_TEXT_FLAG | Paint.ANTI_ALIAS_FLAG);
        String s = "￥" + String.format("%.2f", f);
        return s;
    }

    public static SpannableString getScore(int f) {
        String s = "+" + f;

        SpannableString word = new SpannableString(s);
        word.setSpan(new RelativeSizeSpan(0.7f), 0, 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        word.setSpan(new RelativeSizeSpan(1f), 2, s.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        return word;
    }


    public static String getPrice(double f) {
        String s = "￥" + String.format("%.2f", f);
        return s;
    }

    public static String getSellCount(String count) {
        return "销量：" + count;
    }

    public static String getSellCount(int count) {
        return "销量：" + count;
    }

    public static void setCommentText(int start, int last, TextView textView, String s) {
        SpannableString word = new SpannableString(s);
        word.setSpan(new ForegroundColorSpan(Color.parseColor("#9B30FF")), 0, start, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        word.setSpan(new RelativeSizeSpan(0.7f), s.length() - last, s.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        textView.setText(word, TextView.BufferType.SPANNABLE);
    }

    public static SpannableString getRankText(String name, String comment) {
        String s = name + " " + comment;
        SpannableString word = new SpannableString(s);
        word.setSpan(new ForegroundColorSpan(Color.parseColor("#DC3F3F")), name.length(), s.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        word.setSpan(new RelativeSizeSpan(1.5f), name.length(), s.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        return word;
    }


    public static SpannableString getCommentText(String name, String comment) {
        String s = name + ":" + comment;
        SpannableString word = new SpannableString(s);
        word.setSpan(new ForegroundColorSpan(Color.parseColor("#DC3F3F")), 0, name.length() + 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        word.setSpan(new RelativeSizeSpan(1.0f), s.length() - name.length() - 1, s.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        return word;
    }

    public static SpannableString getCommentText(String name, String comment, String time) {
        String s = name + ": " + comment + "  " + time;
        SpannableString word = new SpannableString(s);
        word.setSpan(new ForegroundColorSpan(Color.parseColor("#9B30FF")), 0, name.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        word.setSpan(new RelativeSizeSpan(1.0f), name.length(), s.length() - time.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        word.setSpan(new RelativeSizeSpan(0.7f), s.length() - time.length(), s.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        return word;
    }

    public static void setSelectPlateText(int start, TextView textView, String s) {
        SpannableString word = new SpannableString(s);
        word.setSpan(new ForegroundColorSpan(AppUtil.getColor(R.color.indicator_color)), start, s.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        word.setSpan(new RelativeSizeSpan(1.2f), start, s.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        textView.setText(word, TextView.BufferType.SPANNABLE);
    }

    public static SpannableString setSelectPlateText(String s) {
        SpannableString word = new SpannableString(s);
        word.setSpan(new ForegroundColorSpan(AppUtil.getColor(R.color.tipsText)), 4, s.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        word.setSpan(new RelativeSizeSpan(1.2f), 4, s.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        return word;
    }

    public static void setNickText(int start, TextView textView, String s) {
        SpannableString word = new SpannableString(s);
        word.setSpan(new ForegroundColorSpan(Color.parseColor("#77384258")), start, s.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        textView.setText(word, TextView.BufferType.SPANNABLE);
    }

    /**
     * 暂时不用 2017.09.01
     *
     * @param et
     * @return
     */
//    public static void setReplyText(TextView contentTv, String userName, String reply) {
//        SpannableString word =
//                FaceUtils.convertNormalStringToSpannableString(userName + "：" + reply, contentTv);
//        word.setSpan(new ForegroundColorSpan(Color.parseColor("#0d81ca")), 0, userName.length() + 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
//        word.setSpan(new RelativeSizeSpan(0.7f), word.length(), word.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
//        contentTv.setText(word, TextView.BufferType.SPANNABLE);
//    }
    public static boolean isEmpty(EditText et) {
        if (et != null && et.getText().toString().trim().length() == 0) {
            return true;
        } else {
            return false;
        }
    }

    public static String getEditTextString(EditText et) {
        if (et != null) {
            return et.getText().toString().trim();
        }
        return null;
    }

    public static String toDBC(String input) {
        if (TextUtils.isEmpty(input)) {
            return "";
        }
        char[] c = input.toCharArray();
        for (int i = 0; i < c.length; i++) {
            if (c[i] == 12288) {
                c[i] = (char) 32;
                continue;
            }
            if (c[i] > 65280 && c[i] < 65375)
                c[i] = (char) (c[i] - 65248);
        }
        return new String(c);
    }

    /**
     * 下划线
     *
     * @param tv
     */
    public static void setUnderline(TextView tv) {
        tv.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);
        tv.getPaint().setAntiAlias(true);//抗锯齿
    }

    public static List<PostContent> getParsePostContent(String content) {
        List<PostContent> list = null;

        return list;
    }

    /**
     * 判断邮箱是否合法
     *
     * @param email
     * @return
     */
    public static boolean isEmail(String email) {
        if (null == email || "".equals(email)) return false;
        //Pattern p = Pattern.compile("\\w+@(\\w+.)+[a-z]{2,3}"); //简单匹配
        Pattern p = Pattern.compile("\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*");//复杂匹配
        Matcher m = p.matcher(email);
        return m.matches();
    }

    /**
     * @param content
     * @param type    状态 1 视频学习 2 音频学习 3 文章阅读 4 在线测评 5 学习感悟 6 评论 7 后台添加
     * @return
     */
    public static String getScoreTitle(String content, int type) {
        String title = "";
        switch (type) {
            case 1:
                title = "【视频学习】";
                break;
            case 2:
                title = "【音频学习】";
                break;
            case 3:
                title = "【文章阅读】";
                break;
            case 4:
                title = "【在线测评】";
                break;
            case 5:
                title = "【学习感悟】";
                break;
            case 6:
                title = "【评论】";
                break;
            case 7:
                title = "【后台添加】";
                break;
        }
        if (content == null) {
            content = "";
        }
        return title + content;
    }


    public static String getScoreData(String date) {
        return date.replace(",", "  ");
    }

    class PostContent {

        private String str;
        private int type;

        public String getStr() {
            return str;
        }

        public void setStr(String str) {
            this.str = str;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }
    }
}
