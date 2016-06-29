package com.iia.myqcm.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.iia.myqcm.entity.Answer;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by gemax on 06/02/2016.
 */
public class AnswerSQLiteAdapter {
    public static final String TABLE_ANSWER = "answer";
    public static final String COL_ID = "_id";
    public static final String COL_CONTENT = "content";
    public static final String COL_POINT = "point";
    public static final String COL_ISVALID = "is_valid";
    public static final String COL_CREATEDAT = "created_at";
    public static final String COL_UPDATEDAT = "updated_at";
    public static final String COL_QUESTIONID = "question_id";
    public static final String COL_IDSERVER = "idServer";
    public static final String COL_ISSELECTED = "is_selected";

    private SQLiteDatabase db;
    private MyqcmSQLiteOpenHelper helper;
    private Context ctx;

    /**
     * Constructor of AnswerSQLiteAdapter
     * @param context
     */
    public AnswerSQLiteAdapter(Context context) {
        this.ctx = context;
        this.helper = new MyqcmSQLiteOpenHelper(context,MyqcmSQLiteOpenHelper.DB_NAME, null, 1);
    }

    /**
     * @return schema of the database
     */
    public static String getSchema(){
        return "CREATE TABLE "+ TABLE_ANSWER + " (" + COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COL_CONTENT + " TEXT NOT NULL,"
                + COL_POINT + " INTEGER NOT NULL,"
                + COL_ISVALID + " INTEGER NOT NULL,"
                + COL_CREATEDAT + " DATE NOT NULL,"
                + COL_UPDATEDAT + " DATE NOT NULL,"
                + COL_QUESTIONID + " INTEGER NOT NULL,"
                + COL_IDSERVER + " INTEGER NOT NULL,"
                + COL_ISSELECTED + " INTEGER NOT NULL);";
    }

    /**
     * Open database
     */
    public void open(){
        this.db = this.helper.getWritableDatabase();
    }

    /**
     * Close database
     */
    public void close(){
        this.db.close();
    }

    /**
     * Insert Answer in database
     * @param answer
     * @return id
     */
    public long insert(Answer answer){
        long id = 0;
        if(this.getAnswer(answer.getIdServer()) != null){
            id = this.getAnswer(answer.getIdServer()).getId();
        }else{
            id = db.insert(TABLE_ANSWER, null, this.itemToContentValues(answer));
        }
        return id;
    }

    /**
     * Update Answer in database
     * @param answer
     * @return id
     */
    public long update(Answer answer){
        ContentValues values = this.itemToContentValues(answer);

        String whereClause = COL_ID + "= ?";
        String[] whereArgs = {String.valueOf(answer.getId())};

        return this.db.update(TABLE_ANSWER, values, whereClause, whereArgs);
    }

    /**
     * Delete answer in database
     * @param answer
     * @return id
     */
    //DELETE ANSWER ON DATABASE
    public long delete(Answer answer){
        String whereClause = COL_ID + "= ?";
        String[] whereArgs = {String.valueOf(answer.getId())};

        return this.db.delete(TABLE_ANSWER, whereClause, whereArgs);
    }

    /**
     * Get answer in database
     * @param idServer
     * @return Answer
     */
    public Answer getAnswer(long idServer){
        //SELECT
        String[] cols = {COL_ID, COL_CONTENT, COL_POINT, COL_ISVALID, COL_CREATEDAT, COL_UPDATEDAT, COL_QUESTIONID};

        String whereClauses = COL_IDSERVER + "= ?";
        String[] whereArgs = {String.valueOf(idServer)};

        Cursor c = this.db.query(TABLE_ANSWER, cols, whereClauses, whereArgs, null, null, null);

        Answer resultAnswer = null;

        if(c.getCount() > 0){
            c.moveToFirst();

            resultAnswer = this.cursorToItem(c);
        }

        c.close();

        return resultAnswer;
    }

    /**
     * Get all cursor by question
     * @param questionId
     * @return Cursor
     */
    public Cursor getAllCursorByQuestion(long questionId){
        //SELECT
        String[] cols = {COL_ID, COL_CONTENT, COL_POINT, COL_ISVALID, COL_CREATEDAT, COL_UPDATEDAT, COL_QUESTIONID, COL_IDSERVER,COL_ISSELECTED};

        String whereClauses = COL_QUESTIONID + "= ?";
        String[] whereArgs = {String.valueOf(questionId)};

        Cursor c = db.query(TABLE_ANSWER, cols,whereClauses,whereArgs, null, null, null);

        return c;
    }

    /**
     * Get all answer by question
     * @param questionId
     * @return ArrayList of answers
     */
    public ArrayList<Answer> getAllByQuestion(long questionId){
        Cursor c = this.getAllCursorByQuestion(questionId);

        ArrayList<Answer> resultAnswers = null;

        if(c.moveToFirst()){

            resultAnswers = new ArrayList<Answer>();

            do {
                resultAnswers.add(this.cursorToItem(c));
            }while(c.moveToNext());
        }

        c.close();

        return resultAnswers;
    }

    /**
     * Convert cursor to answer
     * @param c
     * @return Answer
     */
    public Answer cursorToItem(Cursor c){
        Answer resultAnswer = new Answer();

        resultAnswer.setId(c.getInt(c.getColumnIndex(COL_ID)));
        resultAnswer.setContent(c.getString(c.getColumnIndex(COL_CONTENT)));
        resultAnswer.setPoint(c.getInt(c.getColumnIndex(COL_POINT)));

        // IS VALID
        int isValid = c.getInt(c.getColumnIndex(COL_ISVALID));
        if(isValid == 0){
            resultAnswer.setIs_valid(false);
        }else{
            resultAnswer.setIs_valid(true);
        }

        //DATE CREATED AT
        Date d = new Date();
        resultAnswer.setCreated_at(d);
        //DATE UPDATED AT
        resultAnswer.setUpdated_at(d);

        //QUESTION
        long questionId = c.getLong(c.getColumnIndex(COL_QUESTIONID));
        QuestionSQLiteAdapter questionSQLiteAdapter = new QuestionSQLiteAdapter(this.ctx);
        questionSQLiteAdapter.open();

        resultAnswer.setQuestion(questionSQLiteAdapter.getQuestionById(questionId));

        questionSQLiteAdapter.close();

        return resultAnswer;
    }

    /**
     * Convert answer to content values
     * @param answer
     * @return ContentValues
     */
    //CONVERT ITEM TO CONTENT VALUES
    private ContentValues itemToContentValues(Answer answer){
        ContentValues values = new ContentValues();
        values.put(COL_CONTENT, answer.getContent());
        values.put(COL_POINT, answer.getPoint());
        values.put(COL_ISVALID, answer.getIs_valid());
        values.put(COL_CREATEDAT, answer.getCreated_at().toString());
        values.put(COL_UPDATEDAT, answer.getUpdated_at().toString());
        values.put(COL_QUESTIONID, answer.getQuestion().getId());
        values.put(COL_IDSERVER, answer.getIdServer());
        values.put(COL_ISSELECTED, answer.getIs_selected());

        return values;
    }
}
