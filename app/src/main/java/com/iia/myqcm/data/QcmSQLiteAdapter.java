package com.iia.myqcm.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.iia.myqcm.entity.Qcm;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

/**
 * Created by gemax on 31/01/2016.
 */
public class QcmSQLiteAdapter {

    public static final String TABLE_QCM = "qcm";
    public static final String COL_ID = "_id";
    public static final String COL_NAME = "name";
    public static final String COL_STARTAT = "start_at";
    public static final String COL_ENDAT = "end_at";
    public static final String COL_DURATION = "duration";
    public static final String COL_CREATEDAT = "created_at";
    public static final String COL_UPDATEDAT = "updated_at";
    public static final String COL_CATEGORYID = "category_id";
    public static final String COL_IDSERVER = "idServer";

    private SQLiteDatabase db;
    private MyqcmSQLiteOpenHelper helper;
    private Context ctx;

    /**
     * Constructor of QcmSQLiteAdapter
     * @param context
     */
    public QcmSQLiteAdapter(Context context){
        this.ctx = context;
        helper = new MyqcmSQLiteOpenHelper(context, MyqcmSQLiteOpenHelper.DB_NAME, null,1);
    }

    /**
     * @return schema of the database
     */
    public static String getSchema(){
        return "CREATE TABLE "+ TABLE_QCM + " (" + COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COL_NAME + " TEXT NOT NULL,"
                + COL_STARTAT + " DATE NOT NULL,"
                + COL_ENDAT + " DATE NOT NULL,"
                + COL_DURATION + " INTEGER NOT NULL,"
                + COL_CREATEDAT + " DATE NOT NULL,"
                + COL_UPDATEDAT + " DATE NOT NULL,"
                + COL_CATEGORYID + " INTEGER NOT NULL,"
                + COL_IDSERVER + " INTEGER NOT NULL);";
    }

    /**
     * Open database
     */
    public void open() {
        this.db = this.helper.getWritableDatabase();
    }

    /**
     * Close database
     */
    public void close(){
        this.db.close();
    }

    /**
     * Insert Qcm in database
     * @param qcm
     * @return qcm id
     */
    public long insert(Qcm qcm){
        long id = 0;
        if(this.getQcm(qcm.getIdServer()) != null){
            id = this.getQcm(qcm.getIdServer()).getId();
        }else{
            id = db.insert(TABLE_QCM, null, this.itemToContentValues(qcm));
        }

        return id;
    }

    /**
     * Get qcm in database
     * @param idServer
     * @return Qcm
     */
    public Qcm getQcm(long idServer){
        //SELECT
        String[] cols = {COL_ID, COL_NAME, COL_STARTAT, COL_ENDAT, COL_CREATEDAT, COL_UPDATEDAT, COL_CATEGORYID};

        String whereClauses = COL_IDSERVER + "= ?";
        String[] whereArgs = {String.valueOf(idServer)};

        Cursor c = db.query(TABLE_QCM, cols, whereClauses, whereArgs, null, null, null);

        Qcm resultQcm = null;

        if(c.getCount() > 0){
            c.moveToFirst();

            resultQcm = this.cursorToItem(c);
        }

        c.close();

        return resultQcm;
    }

    /**
     * Get qcm by category in database
     * @param idCategory
     * @return Qcm
     */
    public Qcm getQcmByCategory(long idCategory){
        //SELECT
        String[] cols = {COL_ID, COL_NAME, COL_STARTAT, COL_ENDAT, COL_CREATEDAT, COL_UPDATEDAT, COL_CATEGORYID};

        String whereClauses = COL_CATEGORYID + "= ?";
        String[] whereArgs = {String.valueOf(idCategory)};

        Cursor c = this.db.query(TABLE_QCM, cols, whereClauses, whereArgs, null, null, null);

        Qcm resultQcm = null;

        if(c.getCount() > 0){
            c.moveToFirst();

            resultQcm = this.cursorToItem(c);
        }

        c.close();

        return resultQcm;
    }

    /**
     * Get all cursor qcm
     * @return Cursor
     */
    public Cursor getAllCursor(){
        //SELECT
        String[] cols = {COL_ID, COL_NAME, COL_STARTAT, COL_ENDAT, COL_CREATEDAT, COL_UPDATEDAT, COL_CATEGORYID};

        Cursor c = db.query(TABLE_QCM, cols,null,null, null, null, null);

        return c;
    }

    /**
     * Get all cursor qcm by category
     * @param categoryId
     * @return Cursor
     */
    public Cursor getAllCursorByCategory(long categoryId){
        //SELECT
        String[] cols = {COL_ID, COL_NAME, COL_STARTAT, COL_ENDAT, COL_CREATEDAT, COL_UPDATEDAT, COL_CATEGORYID};

        String whereClauses = COL_CATEGORYID + "= ?";
        String[] whereArgs = {String.valueOf(categoryId)};

        Cursor c = db.query(TABLE_QCM, cols,whereClauses,whereArgs, null, null, null);

        return c;
    }

    /**
     * Convert cursor to qcm
     * @param c
     * @return Qcm
     */
    public Qcm cursorToItem(Cursor c){
        Qcm resultQcm = new Qcm();

        resultQcm.setId(c.getInt(c.getColumnIndex(COL_ID)));
        resultQcm.setName(c.getString(c.getColumnIndex(COL_NAME)));

        //DATE START AT
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        String dateStartAt = c.getString(c.getColumnIndex(COL_STARTAT));
        try {
            resultQcm.setStart_date(dateFormat.parse(dateStartAt));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        //DATE END AT
        String dateEndAt = c.getString(c.getColumnIndex(COL_UPDATEDAT));
        try {
            resultQcm.setEnd_date(dateFormat.parse(dateEndAt));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        //DATE CREATED AT
        String dateCreatedAt = c.getString(c.getColumnIndex(COL_CREATEDAT));
        try {
            resultQcm.setCreated_at(dateFormat.parse(dateCreatedAt));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        //DATE UPDATED AT
        String dateUpdatedAt = c.getString(c.getColumnIndex(COL_UPDATEDAT));
        try {
            resultQcm.setUpdated_at(dateFormat.parse(dateUpdatedAt));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        //CATEGORIE
        long categoryId = c.getInt(c.getColumnIndex(COL_CATEGORYID));

        CategorySQLiteAdapter categorySQLiteAdapter = new CategorySQLiteAdapter(this.ctx);
        categorySQLiteAdapter.open();

        resultQcm.setCategory(categorySQLiteAdapter.getCategory(categoryId));

        categorySQLiteAdapter.close();

        return resultQcm;
    }

    /**
     * Convert item to content values
     * @param qcm
     * @return Content Values
     */
    private ContentValues itemToContentValues(Qcm qcm){
        ContentValues values = new ContentValues();
        values.put(COL_NAME, qcm.getName());
        values.put(COL_STARTAT, qcm.getStart_date().toString());
        values.put(COL_ENDAT, qcm.getEnd_date().toString());
        values.put(COL_DURATION, qcm.getDuration());
        values.put(COL_CREATEDAT, qcm.getCreated_at().toString());
        values.put(COL_UPDATEDAT, qcm.getUpdated_at().toString());
        values.put(COL_CATEGORYID, qcm.getCategory().getId());
        values.put(COL_IDSERVER, qcm.getIdServer());

        return values;
    }
}
