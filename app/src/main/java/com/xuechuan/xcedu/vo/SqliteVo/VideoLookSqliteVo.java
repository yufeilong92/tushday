package com.xuechuan.xcedu.vo.SqliteVo;

/**
 * @version V 1.0 xxxxxxxx
 * @Title: xcedu
 * @Package com.xuechuan.xcedu.vo.SqliteVo
 * @Description: 用户观看视频表
 * @author: L-BackPacker
 * @date: 2018.12.10 上午 11:40
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2018
 */
public class VideoLookSqliteVo {
    private int id;
    private int Charpterid;
    private String kname;
    private String userid;
    private String rightnumber;
    private String rightAllnumber;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCharpterid() {
        return Charpterid;
    }

    public void setCharpterid(int charpterid) {
        Charpterid = charpterid;
    }

    public String getKname() {
        return kname;
    }

    public void setKname(String kname) {
        this.kname = kname;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getRightnumber() {
        return rightnumber;
    }

    public void setRightnumber(String rightnumber) {
        this.rightnumber = rightnumber;
    }

    public String getRightAllnumber() {
        return rightAllnumber;
    }

    public void setRightAllnumber(String rightAllnumber) {
        this.rightAllnumber = rightAllnumber;
    }
}
