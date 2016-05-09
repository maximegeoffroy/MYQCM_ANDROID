package com.iia.myqcm.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.iia.myqcm.entity.Answer;
import com.iia.myqcm.entity.AnswerUser;

/**
 * Created by gemax on 17/02/2016.
 */
public class AnswerUserSQLiteAdapter {
    public static final String TABLE_ANSWERUSER = "answer_user";
    public static final String COL_ID = "_id";
    public static final String COL_ANSWERID = "answer_id";
    public static final String COL_QUESTIONID = "question_id";

    private SQLiteDatabase db;
    private MyqcmSQLiteOpenHelper helper;

    public AnswerUserSQLiteAdapter(Context context) {
        this.helper = new MyqcmSQLiteOpenHelper(context,MyqcmSQLiteOpenHelper.DB_NAME, null, 1);
    }

    /**
     * @return schema of the database
     */
    public static String getSchema(){
        return "CREATE TABLE "+ TABLE_ANSWERUSER + " (" + COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COL_ANSWERID + " INTEGER NOT NULL,"
                + COL_QUESTIONID + " INTEGER NOT NULL);";
    }

    public void open(){
        this.db = this.helper.getWritableDatabase();
    }

    public void close(){
        this.db.close();
    }

    //INSERT ANSWER ON DATABASE
    public long insert(AnswerUser answerUser){
        return db.insert(TABLE_ANSWERUSER, null, this.itemToContentValues(answerUser));
    }

    //CONVERT ITEM TO CONTENT VALUES
    private ContentValues itemToContentValues(AnswerUser answerUser){
        ContentValues values = new ContentValues();
        values.put(COL_ANSWERID, answerUser.getQuestion_id());
        values.put(COL_QUESTIONID, answerUser.getAnswer_id());

        return values;
    }
}
