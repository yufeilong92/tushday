package com.xuechuan.xcedu.sqlitedb;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;

import com.xuechuan.xcedu.base.DataMessageVo;
import com.xuechuan.xcedu.db.DbHelp.DatabaseContext;
import com.xuechuan.xcedu.utils.DbQueryUtil;
import com.xuechuan.xcedu.vo.SqliteVo.DoBankSqliteVo;

import java.util.ArrayList;

/**
 * @version V 1.0 xxxxxxxx
 * @Title: xcedu
 * @Package com.xuechuan.xcedu.sqlitedb
 * @Description: 做题帮助类(用与上传)
 * @author: L-BackPacker
 * @date: 2018.12.14 上午 10:38
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2018
 */
public class DoBankSqliteUpHelp {
    private static volatile DoBankSqliteUpHelp _singleton;
    private Context mContext;
    private SQLiteDatabase mSqLiteDatabase;
    private final DbQueryUtil mDbQueryUtil;

    private DoBankSqliteUpHelp(Context context) {
        this.mContext = context;
        mSqLiteDatabase = createtable();
        mDbQueryUtil = DbQueryUtil.get_Instance();
    }

    public static DoBankSqliteUpHelp get_Instance(Context context) {
        if (_singleton == null) {
            synchronized (DoBankSqliteUpHelp.class) {
                if (_singleton == null) {
                    _singleton = new DoBankSqliteUpHelp(context);
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

    private boolean empty() {
        if (mSqLiteDatabase == null)
            return true;
        if (mSqLiteDatabase.isReadOnly())
            return true;
        return false;
    }

    public void addDoBankItem(DoBankSqliteVo vo) {
        if (empty()) return;
        try {
            DoBankSqliteVo qid = queryWQid(vo.getQuestion_id());
            if (qid == null || qid.getIsDo() == 0) {
                ContentValues values = getContentValues(vo);
                mSqLiteDatabase.insert(DataMessageVo.USER_QUESTION_UP_DO_TABLE, null, values);
            } else {
                ContentValues values = getContentValues(vo);
                mSqLiteDatabase.update(DataMessageVo.USER_QUESTION_UP_DO_TABLE, values, "id=?",
                        new String[]{String.valueOf(qid.getId())});
            }
        } catch (Exception e) {
            if (mSqLiteDatabase.isOpen()) {
                mSqLiteDatabase.close();
            }
            e.printStackTrace();
        }
    }

    @NonNull
    public ContentValues getContentValues(DoBankSqliteVo vo) {
        ContentValues values = new ContentValues();
        values.put("question_id", vo.getQuestion_id());
        values.put("isright", vo.getIsright());
        values.put("isdo", vo.getIsDo());
        values.put("selectA", vo.getSelectA());
        values.put("selectB", vo.getSelectB());
        values.put("selectC", vo.getSelectC());
        values.put("selectD", vo.getSelectD());
        values.put("selectE", vo.getSelectE());
        values.put("questiontype", vo.getQuestiontype());
        values.put("chapterid", vo.getChapterid());
        values.put("courseid", vo.getCourseid());
        return values;
    }

    public ArrayList<DoBankSqliteVo> finDAllUserDoText() {
        if (empty()) return null;
        Cursor query = mSqLiteDatabase.query(DataMessageVo.USER_QUESTION_UP_DO_TABLE, null, null, null, null, null, null);
        mDbQueryUtil.initCursor(query);
        ArrayList<DoBankSqliteVo> list = new ArrayList<>();
        while (query.moveToNext()) {
            DoBankSqliteVo doBankVo = getDoBankVo(mDbQueryUtil);
            if (doBankVo != null) {
                list.add(doBankVo);
            }
        }
        return list;
    }


    public DoBankSqliteVo queryWQid(int qusstion_id) {
        if (empty()) return null;
        Cursor query = mSqLiteDatabase.query(DataMessageVo.USER_QUESTION_UP_DO_TABLE, null, "question_id=?", new String[]{String.valueOf(qusstion_id)}
                , null, null, null);
        mDbQueryUtil.initCursor(query);
        while (query.moveToNext()) {
            DoBankSqliteVo vo = getDoBankVo(mDbQueryUtil);
            return vo;
        }
        return null;
    }

    public void deleteBankWithQuestid(int question_id) {
        if (empty()) return;
        mSqLiteDatabase.delete(DataMessageVo.USER_QUESTION_UP_DO_TABLE, "question_id=?", new String[]{String.valueOf(question_id)});

    }

    public void close() {
        if (empty()) return;
        mSqLiteDatabase.close();
    }

    public void delelteTable() {
        if (empty()) return;
        mSqLiteDatabase.delete(DataMessageVo.USER_QUESTION_UP_DO_TABLE, null, null);
    }

    public synchronized DoBankSqliteVo getDoBankVo(DbQueryUtil mDbQueryUtil) {
        DoBankSqliteVo vo = new DoBankSqliteVo();
        int id = mDbQueryUtil.queryInt("id");
        int question_id = mDbQueryUtil.queryInt("question_id");
        int isdo = mDbQueryUtil.queryInt("isdo");
        int selectA = mDbQueryUtil.queryInt("selectA");
        int selectB = mDbQueryUtil.queryInt("selectB");
        int selectC = mDbQueryUtil.queryInt("selectC");
        int selectD = mDbQueryUtil.queryInt("selectD");
        int selectE = mDbQueryUtil.queryInt("selectE");
        int isright = mDbQueryUtil.queryInt("isright");
        int courseid = mDbQueryUtil.queryInt("courseid");
        int chapterid = mDbQueryUtil.queryInt("chapterid");
        int questiontype = mDbQueryUtil.queryInt("questiontype");
        vo.setId(id);
        vo.setChapterid(chapterid);
        vo.setCourseid(courseid);
        vo.setQuestiontype(questiontype);
        vo.setIsDo(isdo);
        vo.setIsright(isright);
        vo.setQuestion_id(question_id);
        vo.setSelectA(selectA);
        vo.setSelectB(selectB);
        vo.setSelectC(selectC);
        vo.setSelectD(selectD);
        vo.setSelectE(selectE);
        return vo;
    }


}
