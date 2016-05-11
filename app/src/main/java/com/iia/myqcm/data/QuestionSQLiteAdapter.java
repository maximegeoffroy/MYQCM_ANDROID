package com.iia.myqcm.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.iia.myqcm.entity.Category;
import com.iia.myqcm.entity.Question;
import com.iia.myqcm.view.CategoryListActivity;
import com.iia.myqcm.view.QcmCursorAdapter;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

/**
 * Created by gemax on 06/02/2016.
 */
public class QuestionSQLiteAdapter {

    public static final String TABLE_QUESTION = "question";
    public static final String COL_ID = "_id";
    public static final String COL_CONTENT = "content";
    public static final String COL_CREATEDAT = "created_at";
    public static final String COL_UPDATEDAT = "updated_at";
    public static final String COL_QCMID = "qcm_id";
    public static final String COL_IDSERVER = "idServer";

    private SQLiteDatabase db;
    private MyqcmSQLiteOpenHelper helper;
    private Context ctx;

    /**
     * Constructor of QuestionSQLiteAdapter
     * @param context
     */
    public QuestionSQLiteAdapter(Context context) {
        this.ctx = context;
        this.helper = new MyqcmSQLiteOpenHelper(context,MyqcmSQLiteOpenHelper.DB_NAME, null, 1);
    }

    /**
     * @return schema of the database
     */
    public static String getSchema(){
        return "CREATE TABLE "+ TABLE_QUESTION + " (" + COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COL_CONTENT + " TEXT NOT NULL,"
                + COL_CREATEDAT + " DATE NOT NULL,"
                + COL_UPDATEDAT + " DATE NOT NULL,"
                + COL_QCMID + " INTEGER NOT NULL,"
                + COL_IDSERVER + " INTEGER NOT NULL)";
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
     * Insert question in database
     * @param question
     * @return question id
     */
    public long insert(Question question){
        long id = 0;
        if(this.getQuestion(question.getIdServer()) != null){
            id = this.getQuestion(question.getIdServer()).getId();
        }else{
            return db.insert(TABLE_QUESTION, null, this.itemToContentValues(question));
        }
        return id;
    }

    /**
     * Update question in database
     * @param question
     * @return question id
     */
    public long update(Question question){
        ContentValues values = this.itemToContentValues(question);

        String whereClause = COL_ID + "= ?";
        String[] whereArgs = {String.valueOf(question.getId())};

        return this.db.update(TABLE_QUESTION, values, whereClause, whereArgs);
    }

    /**
     * Delete question in database
     * @param question
     * @return question id
     */
    public long delete(Question question){
        //DELETE
        String whereClause = COL_ID + "= ?";
        String[] whereArgs = {String.valueOf(question.getId())};

        return this.db.delete(TABLE_QUESTION, whereClause, whereArgs);
    }

    /**
     * Get question in database
     * @param idServer
     * @return Question
     */
    public Question getQuestion(long idServer){
        //SELECT
        String[] cols = {COL_ID, COL_CONTENT, COL_CREATEDAT, COL_UPDATEDAT, COL_QCMID, COL_IDSERVER};

        String whereClauses = COL_IDSERVER + "= ?";
        String[] whereArgs = {String.valueOf(idServer)};

        Cursor c = this.db.query(TABLE_QUESTION, cols, whereClauses, whereArgs, null, null, null);

        Question resultQuestion = null;

        if(c.getCount() > 0){
            c.moveToFirst();

            resultQuestion = this.cursorToItem(c);
        }

        c.close();

        return resultQuestion;
    }

    /**
     * Get all cursor question by qcm
     * @param qcmId
     * @return Cursor
     */
    public Cursor getAllCursorByQcm(long qcmId){
        //SELECT
        String[] cols = {COL_ID, COL_CONTENT, COL_CREATEDAT, COL_UPDATEDAT, COL_QCMID};

        String whereClauses = COL_QCMID + "= ?";
        String[] whereArgs = {String.valueOf(qcmId)};

        Cursor c = db.query(TABLE_QUESTION, cols,whereClauses,whereArgs, null, null, null);

        return c;
    }

    /**
     * Get all question by qcm
     * @param qcmId
     * @return Array List questions
     */
    public ArrayList<Question> getAllByQcm(long qcmId){
        Cursor c = this.getAllCursorByQcm(qcmId);

        ArrayList<Question> resultQuestions = null;

        if(c.moveToFirst()){

            resultQuestions = new ArrayList<Question>();

            do {
                resultQuestions.add(this.cursorToItem(c));
            }while(c.moveToNext());
        }

        c.close();

        return resultQuestions;
    }

    /**
     * Get all cursor question
     * @return Cursor
     */
    public Cursor getAllCursor(){
        //SELECT
        String[] cols = {COL_ID, COL_CONTENT, COL_CREATEDAT, COL_UPDATEDAT, COL_QCMID};

        Cursor c = db.query(TABLE_QUESTION, cols,null,null, null, null, null);

        return c;
    }

    /**
     * Convert cursor to question
     * @param c
     * @return Question
     */
    public Question cursorToItem(Cursor c){
        Question resultQuestion = new Question();

        resultQuestion.setId(c.getInt(c.getColumnIndex(COL_ID)));
        resultQuestion.setContent(c.getString(c.getColumnIndex(COL_CONTENT)));
        //DATE CREATED AT
        DateFormat df = new SimpleDateFormat("yyyy/MM/dd hh:mm:ss");
        String dateCreatedAt = c.getString(c.getColumnIndex(COL_CREATEDAT));
        try {
            resultQuestion.setCreated_at(df.parse(dateCreatedAt));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        //DATE UPDATED AT
        String dateUpdatedAt = c.getString(c.getColumnIndex(COL_UPDATEDAT));
        try {
            resultQuestion.setCreated_at(df.parse(dateUpdatedAt));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        //QCM
        long qcmId = c.getInt(c.getColumnIndex(COL_QCMID));

        QcmSQLiteAdapter qcmSQLiteAdapter = new QcmSQLiteAdapter(this.ctx);
        qcmSQLiteAdapter.open();

        resultQuestion.setQcm(qcmSQLiteAdapter.getQcm(qcmId));

        qcmSQLiteAdapter.close();

        return resultQuestion;
    }

    /**
     * Convert question to content values
     * @param question
     * @return Content Values
     */
    //CONVERT ITEM TO CONTENT VALUES
    private ContentValues itemToContentValues(Question question){
        ContentValues values = new ContentValues();
        values.put(COL_CONTENT, question.getContent());
        values.put(COL_CREATEDAT, question.getCreated_at().toString());
        values.put(COL_UPDATEDAT, question.getUpdated_at().toString());
        values.put(COL_QCMID, question.getQcm().getId());
        values.put(COL_IDSERVER, question.getIdServer());

        return values;
    }
}
