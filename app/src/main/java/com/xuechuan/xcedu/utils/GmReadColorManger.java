package com.xuechuan.xcedu.utils;

import android.content.Context;

import com.xuechuan.xcedu.R;

/**
 * @version V 1.0 xxxxxxxx
 * @Title: xcedu
 * @Package com.xuechuan.xcedu.utils
 * @Description: 做题颜色管理器
 * @author: L-BackPacker
 * @date: 2018.12.07 上午 8:58
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2018
 */
public class GmReadColorManger {
    private static volatile GmReadColorManger _singleton;
    private Context mContext;
    /**
     * 颜色为浅灰色
     */
    private int mTextFuColor;
    /**
     * 颜色为黑色
     */
    private int mTextTitleColor;
    /**
     * 颜色为红色
     */
    private int mTextRedColor;
    /**
     * 正确颜色
     */
    private int mTextRightColor;
    /**
     * 分割线的颜色
     */
    private int mCutLineColor;
    /**
     * 整体背景颜色
     */
    private int mLayoutBgColor;
    /**
     * 评价的颜色
     */
    private int mLayoutEvalueBgColor;

    /**
     * 白天模式
     */
    public static final int DAYTIME = 1;
    /**
     * 夜间模式
     */
    public static final int NIGHT = 2;
    /**
     * 护眼模式
     */
    public static final int EYE = 3;

    public static GmReadColorManger get_Instance(Context context) {
        if (_singleton == null) {
            synchronized (GmReadColorManger.class) {
                if (_singleton == null) {
                    _singleton = new GmReadColorManger(context);
                }
            }
        }
        return _singleton;
    }


    private GmReadColorManger(Context context) {
        this.mContext = context;
        mTextFuColor = getColor(R.color.text_fu_color);
        mTextTitleColor = getColor(R.color.text_title_color);
        mTextRedColor = getColor(R.color.red_text);
        mCutLineColor = getColor(R.color.gray_line);
        mLayoutBgColor = getColor(R.color.white);
        mTextRightColor = getColor(R.color.text_color_right);
        mLayoutEvalueBgColor = getColor(R.color.gray_line);
    }

    private int getColor(int text_fu_color) {
        return mContext.getResources().getColor(text_fu_color);
    }

    public void setGmBgColor(int number) {
        switch (number) {
            case DAYTIME://白天模式
                mTextFuColor = getColor(R.color.text_fu_color);
                mTextTitleColor = getColor(R.color.text_title_color);
                mTextRedColor = getColor(R.color.red_text);
                mCutLineColor = getColor(R.color.gray_line);
                mLayoutBgColor = getColor(R.color.white);
                mTextRightColor = getColor(R.color.text_color_right);
                break;
            case NIGHT://夜间夜间
                mTextFuColor = getColor(R.color.night_text_color);
                mTextTitleColor = getColor(R.color.night_text_color);
                mTextRedColor = getColor(R.color.red_text);
                mCutLineColor = getColor(R.color.night_line_bg);
                mLayoutBgColor = getColor(R.color.night_layout_bg);
                mTextRightColor = getColor(R.color.text_color_right);
                break;
            case EYE://护眼模式
                mTextFuColor = getColor(R.color.text_fu_color);
                mTextTitleColor = getColor(R.color.text_title_color);
                mTextRedColor = getColor(R.color.red_text);
                mCutLineColor = getColor(R.color.eye_line_bg);
                mLayoutBgColor = getColor(R.color.eye_layout_bg);
                mTextRightColor = getColor(R.color.text_color_right);
                break;
            default:

        }

    }


    public int getmTextFuColor() {
        return mTextFuColor;
    }

    public int getmTextTitleColor() {
        return mTextTitleColor;
    }

    public int getmTextRedColor() {
        return mTextRedColor;
    }

    public int getmTextRightColor() {
        return mTextRightColor;
    }

    public int getmCutLineColor() {
        return mCutLineColor;
    }

    public int getmLayoutBgColor() {
        return mLayoutBgColor;
    }

}
