package com.xuechuan.xcedu.utils;

import android.app.Activity;
import android.content.Context;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.xuechuan.xcedu.R;
import com.xuechuan.xcedu.vo.SqliteVo.DoBankSqliteVo;
import com.xuechuan.xcedu.vo.UseSelectItemInfomVo;

import java.util.ArrayList;
import java.util.List;

/**
 * @version V 1.0 xxxxxxxx
 * @Title: xcedu
 * @Package com.xuechuan.xcedu.utils
 * @Description: 题库工具
 * @author: L-BackPacker
 * @date: 2018.12.14 下午 4:31
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2018
 */
public class GmTextUtil {
    private static volatile GmTextUtil _singleton;
    private Context mContext;
    private final GmSelectImgManageUtil mGmSelectUtils;

    private GmTextUtil(Context context) {
        this.mContext = context;
        mGmSelectUtils = GmSelectImgManageUtil.get_Instance(context);
    }

    public static GmTextUtil get_Instance(Context context) {
        if (_singleton == null) {
            synchronized (GmTextUtil.class) {
                if (_singleton == null) {
                    _singleton = new GmTextUtil(context);
                }
            }
        }
        return _singleton;
    }

    /**
     * 获取答案
     *
     * @param key
     * @return
     */
    public List<String> getAnswerKeyList(String key) {
        if (StringUtil.isEmpty(key)) return null;
        ArrayList list = new ArrayList<>();
        getAnswer(key, list);
        return list;
    }

    /**
     * 获取截取答案
     *
     * @param key
     * @param list
     */
    private void getAnswer(String key, ArrayList<String> list) {
        int length = key.length();
        if (length > 1) {
            String substring = key.substring(0, 1);
            list.add(substring.toUpperCase());
            key = key.substring(1, length);
            getAnswer(key, list);
        } else {
            list.add(key.toUpperCase());
        }
    }

    /**
     * 单选判断是否正确
     *
     * @param listkey 正确集合集
     * @param key     正确数
     * @return
     */
    public boolean keyIsRight(List<String> listkey, String key) {
        if (listkey == null || listkey.isEmpty()) return false;
        if (StringUtil.isEmpty(key)) return false;
        for (String s : listkey) {
            if (s.equalsIgnoreCase(key)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 设置图片miss
     *
     * @param imgMiss
     */
    public void setImgMiss(ImageView imgMiss) {
        if (imgMiss == null) return;
        imgMiss.setImageDrawable(mGmSelectUtils.getDrawable(R.mipmap.ic_b_miss));
    }

    /**
     * 设置字体miss
     *
     * @param tv
     */
    public void setTvMiss(TextView tv) {
        if (tv == null) return;
        tv.setTextColor(mContext.getResources().getColor(R.color.text_color_woring));
    }

    /**
     * 设置正确图片
     *
     * @param imgRight
     */
    public void setImgRight(ImageView imgRight, boolean isRight) {
        imgRight.setImageDrawable(isRight ? mGmSelectUtils.getDrawable(R.mipmap.ic_b_text_right) : mGmSelectUtils.getDrawable(R.mipmap.ic_b_erro));
    }

    /**
     * 设置正确文字图片
     *
     * @param tv
     */
    public void setTvRight(TextView tv, boolean isRight) {
        if (tv == null) return;
        tv.setTextColor(isRight ? mContext.getResources().getColor(R.color.text_color_right) : mContext.getResources().getColor(R.color.text_color_error));
    }

    public int getColorDrawable(int id) {
        return mContext.getResources().getColor(id);
    }

    /**
     * 设置背景颜色
     *
     * @param bgAlpha
     */
    public void setBackgroundAlpha(float bgAlpha, Context mContext) {
        WindowManager.LayoutParams lp = ((Activity) mContext).getWindow()
                .getAttributes();
        lp.alpha = bgAlpha;
        ((Activity) mContext).getWindow().setAttributes(lp);
    }

    /**
     * @param img
     */
    public void setImgParams20(ImageView img) {
        ViewGroup.LayoutParams params = img.getLayoutParams();
        params.width = 60;
        params.height = 60;
        img.setLayoutParams(params);
    }

    /**
     * @param img
     */
    public void setImgParams10(ImageView img) {
        ViewGroup.LayoutParams params = img.getLayoutParams();
        params.width = 30;
        params.height = 30;
        img.setLayoutParams(params);
    }

    /**
     * 剪切获取数据 考点
     *
     * @param word
     * @return
     */
    public ArrayList<String> getKeyWords(String word) {
        if (StringUtil.isEmpty(word)) return null;
        String[] split = word.split(",");
        ArrayList<String> list = new ArrayList<>();
        for (int i = 0; i < split.length; i++) {
            list.add(split[i]);
        }
        return list;
    }

    /**
     * 获取用户正确率
     *
     * @param list
     * @param allNumber
     * @return
     */
    public String getRihgtAccuracy(ArrayList<DoBankSqliteVo> list, int allNumber) {
        int right = 0;
        if (list == null || list.isEmpty()) return "0";
        for (int i = 0; i < list.size(); i++) {
            DoBankSqliteVo vo = list.get(i);
            if (vo.getIsright() == 1) {
                right += 1;
            }
        }
        String s = ArithUtil.divNumber(right, allNumber, 2);
        return s;
    }

    /**
     * 获取分说
     *
     * @param list
     * @return
     */
    public String getUserGrade(ArrayList<DoBankSqliteVo> list) {
        if (list == null || list.isEmpty()) return "0";
        double score = 0;
        for (int i = 0; i < list.size(); i++) {
            DoBankSqliteVo vo = list.get(i);
            if (vo.getIsright() == 1) {
                if (vo.getQuestiontype() == 2) {
                    score += 1;
                } else if (vo.getQuestiontype() == 3) {
                    score += 2;
                }
            }
            if (vo.getIsright() == 2) {
                double more = 0;
                String a = null, b = null, c = null, d = null, e = null;
                String select = "select";
                if (vo.getSelectA() == 1) {
                    a = select;
                }
                if (vo.getSelectB() == 1) {
                    b = select;
                }
                if (vo.getSelectC() == 1) {
                    c = select;
                }
                if (vo.getSelectD() == 1) {
                    d = select;
                }
                if (vo.getSelectE() == 1) {
                    e = select;
                }
                if (!StringUtil.isEmpty(a)) {
                    more += 0.5;
                }
                if (!StringUtil.isEmpty(b)) {
                    more += 0.5;
                }
                if (!StringUtil.isEmpty(c)) {
                    more += 0.5;
                }
                if (!StringUtil.isEmpty(d)) {
                    more += 0.5;
                }
                if (!StringUtil.isEmpty(e)) {
                    more += 0.5;
                }
                if (more > 2) {
                    more = 2;
                }
                score += more;
            }
        }
        return String.valueOf(score);
    }

}
