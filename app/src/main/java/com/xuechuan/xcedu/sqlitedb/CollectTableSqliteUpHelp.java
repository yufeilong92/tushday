package com.xuechuan.xcedu.sqlitedb;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.xuechuan.xcedu.base.DataMessageVo;
import com.xuechuan.xcedu.db.DbHelp.DatabaseContext;
import com.xuechuan.xcedu.utils.DbQueryUtil;
import com.xuechuan.xcedu.utils.StringUtil;
import com.xuechuan.xcedu.vo.SqliteVo.CollectTableSqliteVo;

/**
 * @version V 1.0 xxxxxxxx
 * @Title: xcedu
 * @Package com.xuechuan.xcedu.sqlitedb
 * @Description: 收藏表(副操作表)
 * @author: L-BackPacker
 * @date: 2018.12.25 下午 4:30
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2018
 */
public class CollectTableSqliteUpHelp {
    private static volatile CollectTableSqliteUpHelp _singleton;
    private Context mContext;
    private final DbQueryUtil mDbQueryUtil;
    private final SQLiteDatabase mSqLiteDatabase;

    private CollectTableSqliteUpHelp(Context context) {
        this.mContext = context;
        mSqLiteDatabase = createtable();
        mDbQueryUtil = DbQueryUtil.get_Instance();
    }

    public static CollectTableSqliteUpHelp get_Instance(Context context) {
        if (_singleton == null) {
            synchronized (CollectTableSqliteUpHelp.class) {
                if (_singleton == null) {
                    _singleton = new CollectTableSqliteUpHelp(context);
                }
            }
        }
        return _singleton;
    }

    private SQLiteDatabase createtable() {
        DatabaseContext context = new DatabaseContext(mContext);
        UserInfomOpenHelp userInfomOpenHelp = UserInfomOpenHelp.get_Instance(context);
        return userInfomOpenHelp.getWritableDatabase();
    }

    private boolean empty() {
        if (mSqLiteDatabase == null)
            return true;
        if (mSqLiteDatabase.isReadOnly())
            return true;
        return false;
    }

    public void addCoolectItem(CollectTableSqliteVo vo) {
        if (empty()) return;
        CollectTableSqliteVo add = queryIsAdd(vo.getCourseid(), vo.getChapterid(), vo.getQuestion_id());
        if (add == null|| StringUtil.isEmpty(add.getTime())) {
            ContentValues value = getContentValue(vo);
            mSqLiteDatabase.insert(DataMessageVo.USER_QUESTION_COLLECT_TABLE, null, value);
        } else {
            ContentValues value = getContentValue(vo);
            mSqLiteDatabase.update(DataMessageVo.USER_QUESTION_COLLECT_TABLE, value, "id=?", new String[]{String.valueOf(add.getId())});
        }
    }

    public void deleteItem(int id) {
        mSqLiteDatabase.delete(DataMessageVo.USER_QUESTION_COLLECT_TABLE, "id=?", new String[]{String.valueOf(id)});
    }

    private CollectTableSqliteVo queryIsAdd(int couresid, int chapterid, int questionid) {
        if (empty()) return null;
        Cursor query = mSqLiteDatabase.query(DataMessageVo.USER_QUESTION_COLLECT_TABLE, null, "courseid=? and chapterid=? and question_id=?",
                new String[]{String.valueOf(couresid), String.valueOf(chapterid), String.valueOf(questionid)},
                null, null, null);
        mDbQueryUtil.initCursor(query);
        while (query.moveToNext()) {
            CollectTableSqliteVo vo = getCollectTableVo(mDbQueryUtil);
            return vo;
        }
        return null;
    }


    private ContentValues getContentValue(CollectTableSqliteVo vo) {
        ContentValues values = new ContentValues();
        values.put("question_id", vo.getQuestion_id());
        values.put("collectable", vo.getCollectable());
        values.put("questiontype", vo.getQuestiontype());
        values.put("chapterid", vo.getChapterid());
        values.put("time", vo.getTime());
        values.put("courseid", vo.getCourseid());
        return values;
    }

    private CollectTableSqliteVo getCollectTableVo(DbQueryUtil mDbQueryUtil) {
        CollectTableSqliteVo vo = new CollectTableSqliteVo();
        int id = mDbQueryUtil.queryInt("id");
        int question_id = mDbQueryUtil.queryInt("question_id");
        int collectable = mDbQueryUtil.queryInt("collectable");
        int questiontype = mDbQueryUtil.queryInt("questiontype");
        int chapterid = mDbQueryUtil.queryInt("chapterid");
        String time = mDbQueryUtil.queryString("time");
        int courseid = mDbQueryUtil.queryInt("courseid");
        vo.setId(id);
        vo.setQuestion_id(question_id);
        vo.setChapterid(chapterid);
        vo.setCollectable(collectable);
        vo.setCourseid(courseid);
        vo.setTime(time);
        vo.setQuestiontype(questiontype);
        return vo;

    }

}
