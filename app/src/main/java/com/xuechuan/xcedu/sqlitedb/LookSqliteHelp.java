package com.xuechuan.xcedu.sqlitedb;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.xuechuan.xcedu.base.DataMessageVo;
import com.xuechuan.xcedu.db.DbHelp.DatabaseContext;
import com.xuechuan.xcedu.utils.DbQueryUtil;
import com.xuechuan.xcedu.utils.StringUtil;
import com.xuechuan.xcedu.vo.SqliteVo.LookSqliteVo;

import java.util.ArrayList;

/**
 * @version V 1.0 xxxxxxxx
 * @Title: xcedu
 * @Package com.xuechuan.xcedu.sqlitedb
 * @Description: 用户观看记录
 * @author: L-BackPacker
 * @date: 2018.12.21 上午 9:09
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2018
 */
public class LookSqliteHelp {
    private static volatile LookSqliteHelp _singleton;
    private Context mContext;
    private final SQLiteDatabase mSqLiteDatabase;
    private final DbQueryUtil mDbQueryUtil;

    private LookSqliteHelp(Context context) {
        this.mContext = context;
        mSqLiteDatabase = createtable();
        mDbQueryUtil = DbQueryUtil.get_Instance();
    }

    public static LookSqliteHelp get_Instance(Context context) {
        if (_singleton == null) {
            synchronized (LookSqliteHelp.class) {
                if (_singleton == null) {
                    _singleton = new LookSqliteHelp(context);
                }
            }
        }
        return _singleton;
    }

    private SQLiteDatabase createtable() {
        DatabaseContext context = new DatabaseContext(mContext);
//        UserInfomOpenHelp userInfomOpenHelp = new UserInfomOpenHelp(context);
        UserInfomOpenHelp userInfomOpenHelp = UserInfomOpenHelp.get_Instance(context);
        return userInfomOpenHelp.getWritableDatabase();
    }

    public void addDoLookItem(LookSqliteVo vo) {
        if (empty()) return;
        LookSqliteVo look = findLookWithTidChapterId(vo.getChapterid(), vo.getKid());
        if (look == null || StringUtil.isEmpty(look.getRightnumber())) {
            ContentValues values = setContentValues(vo);
            mSqLiteDatabase.insert(DataMessageVo.USER_INFOM_TABLE_LOOK, null, values);
        } else {
            ContentValues values = setContentValues(vo);
            mSqLiteDatabase.update(DataMessageVo.USER_INFOM_TABLE_LOOK, values, "id=?",
                    new String[]{String.valueOf(look.getId())});
        }

    }

    public void deleteLookItem(int chapterid, int kid) {
        if (empty()) return;
        mSqLiteDatabase.delete(DataMessageVo.USER_INFOM_TABLE_LOOK, "chapterid=? and kid=? ",
                new String[]{String.valueOf(chapterid), String.valueOf(kid)});
    }

    public void deleteLookItem(int id) {
        if (empty()) return;
        mSqLiteDatabase.delete(DataMessageVo.USER_INFOM_TABLE_LOOK, "id=?", new String[]{String.valueOf(id)});

    }

    /**
     * @param chapterid 章节id
     * @param kid       科id
     */
    public LookSqliteVo findLookWithTidChapterId(int chapterid, int kid) {
        if (empty()) return null;
        Cursor query = mSqLiteDatabase.query(DataMessageVo.USER_INFOM_TABLE_LOOK, null, "chapterid=? and kid=? ",
                new String[]{String.valueOf(chapterid), String.valueOf(kid)}, null, null, null);
        mDbQueryUtil.initCursor(query);
        while (query.moveToNext()) {
            LookSqliteVo vo = getContentValues(mDbQueryUtil);
            return vo;
        }
        return null;
    }

    public ArrayList<LookSqliteVo> findALLLooK() {
        if (empty()) return null;
        Cursor query = mSqLiteDatabase.query(DataMessageVo.USER_INFOM_TABLE_LOOK, null, null, null, null, null, null);
        mDbQueryUtil.initCursor(query);
        ArrayList<LookSqliteVo> list = new ArrayList<>();
        while (query.moveToNext()) {
            LookSqliteVo contentValues = getContentValues(mDbQueryUtil);
            if (contentValues != null) {
                list.add(contentValues);
            }
        }
        return list;
    }
    public void close() {
        if (empty()) return;
        mSqLiteDatabase.close();
    }
    private LookSqliteVo getContentValues(DbQueryUtil mDbQueryUtil) {
        LookSqliteVo vo = new LookSqliteVo();
        int id = mDbQueryUtil.queryInt("id");
        int chapterid = mDbQueryUtil.queryInt("chapterid");
        int textid = mDbQueryUtil.queryInt("textid");
        int kid = mDbQueryUtil.queryInt("kid");
        String userid = mDbQueryUtil.queryString("userid");
        String rightnumber = mDbQueryUtil.queryString("rightnumber");
        String rightallnumber = mDbQueryUtil.queryString("rightallnumber");
        vo.setChapterid(chapterid);
        vo.setId(id);
        vo.setKid(kid);
        vo.setRightAllNumber(rightallnumber);
        vo.setRightnumber(rightnumber);
        vo.setTextid(textid);
        vo.setUserid(userid);
        return vo;
    }

    private ContentValues setContentValues(LookSqliteVo vo) {
        ContentValues values = new ContentValues();
        values.put("chapterid", vo.getChapterid());
        values.put("textid", vo.getTextid());
        values.put("kid", vo.getKid());
        values.put("userid", vo.getUserid());
        values.put("rightnumber", vo.getRightnumber());
        values.put("rightallnumber", vo.getRightAllNumber());
        return values;
    }

    private boolean empty() {
        if (mSqLiteDatabase == null)
            return true;
        if (mSqLiteDatabase.isReadOnly())
            return true;
        return false;
    }
}
