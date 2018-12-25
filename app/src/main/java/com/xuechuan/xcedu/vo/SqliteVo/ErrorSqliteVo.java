package com.xuechuan.xcedu.vo.SqliteVo;

/**
 * @version V 1.0 xxxxxxxx
 * @Title: xcedu
 * @Package com.xuechuan.xcedu.vo.SqliteVo
 * @Description: 用户错题记录表
 * @author: L-BackPacker
 * @date: 2018.12.10 上午 11:42
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2018
 */
public class ErrorSqliteVo {
    private int id;
    private int chapterid;
    private String kanme;
    private String rightnumber;
    private String rightAllNumber;
    private String userid;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getChapterid() {
        return chapterid;
    }

    public void setChapterid(int chapterid) {
        this.chapterid = chapterid;
    }

    public String getKanme() {
        return kanme;
    }

    public void setKanme(String kanme) {
        this.kanme = kanme;
    }

    public String getRightnumber() {
        return rightnumber;
    }

    public void setRightnumber(String rightnumber) {
        this.rightnumber = rightnumber;
    }

    public String getRightAllNumber() {
        return rightAllNumber;
    }

    public void setRightAllNumber(String rightAllNumber) {
        this.rightAllNumber = rightAllNumber;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }
}
