package com.xuechuan.xcedu.vo.SqliteVo;

import com.xuechuan.xcedu.utils.StringUtil;

/**
 * @version V 1.0 xxxxxxxx
 * @Title: xcedu
 * @Package com.xuechuan.xcedu.vo.SqliteVo
 * @Description: 做题帮助类
 * @author: L-BackPacker
 * @date: 2018.12.14 上午 10:40
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2018
 */
public class DoBankSqliteVo {
    private int id;
    private int question_id;
    private int isright;//0为未选，1为正确 ，2 为漏选 ，3为错误
    private int selectA;
    private int selectB;
    private int selectC;
    private int selectD;
    private int selectE;
    private int isDo;
    private int questiontype;//题干类型
    private int chapterid;//章节
    private int courseid;//科目id

    public int getChapterid() {
        return chapterid;
    }

    public void setChapterid(int chapterid) {
        this.chapterid = chapterid;
    }

    public int getCourseid() {
        return courseid;
    }

    public void setCourseid(int courseid) {
        this.courseid = courseid;
    }

    public int getQuestiontype() {
        return questiontype;
    }

    public void setQuestiontype(int questiontype) {
        this.questiontype = questiontype;
    }

    public int getIsDo() {
        return isDo;
    }

    public void setIsDo(int isDo) {
        this.isDo = isDo;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getQuestion_id() {
        return question_id;
    }

    public void setQuestion_id(int question_id) {
        this.question_id = question_id;
    }

    public int getIsright() {
        return isright;
    }

    public void setIsright(int isright) {
        this.isright = isright;
    }

    public int getSelectA() {
        return selectA;
    }

    public void setSelectA(int selectA) {
        this.selectA = selectA;
    }

    public int getSelectB() {
        return selectB;
    }

    public void setSelectB(int selectB) {
        this.selectB = selectB;
    }

    public int getSelectC() {
        return selectC;
    }

    public void setSelectC(int selectC) {
        this.selectC = selectC;
    }

    public int getSelectD() {
        return selectD;
    }

    public void setSelectD(int selectD) {
        this.selectD = selectD;
    }

    public int getSelectE() {
        return selectE;
    }

    public void setSelectE(int selectE) {
        this.selectE = selectE;
    }
}
