package com.iia.myqcm.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.iia.myqcm.entity.Category;
import com.iia.myqcm.entity.Group;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Created by gemax on 01/02/2016.
 */
public class CategorySQLiteAdapter {
    public static final String TABLE_CATEGORY = "category";
    public static final String COL_ID = "_id";
    public static final String COL_NAME = "name";
    public static final String COL_CREATEDAT = "created_at";
    public static final String COL_UPDATEDAT = "updated_at";

    private SQLiteDatabase db;
    private MyqcmSQLiteOpenHelper helper;

    public CategorySQLiteAdapter(Context context) {
        this.helper = new MyqcmSQLiteOpenHelper(context,MyqcmSQLiteOpenHelper.DB_NAME, null, 1);
    }

    /**
     * @return schema of the database
     */
    public static String getSchema(){
        return "CREATE TABLE "+ TABLE_CATEGORY + " (" + COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COL_NAME + " TEXT NOT NULL,"
                + COL_CREATEDAT + " DATE NOT NULL,"
                + COL_UPDATEDAT + " DATE NOT NULL);";
    }

    public void open(){
        this.db = this.helper.getWritableDatabase();
    }

    public void close(){
        this.db.close();
    }

    //INSERT CATEGORY ON DATABASE
    public long insert(Category category){
        return db.insert(TABLE_CATEGORY, null, this.itemToContentValues(category));
    }

    //UPDATE CATEGORY ON DATABASE
    public long update(Category category){
        ContentValues values = this.itemToContentValues(category);

        String whereClause = COL_ID + "= ?";
        String[] whereArgs = {String.valueOf(category.getId())};

        return this.db.update(TABLE_CATEGORY, values, whereClause, whereArgs);
    }

    //DELETE CATEGORY ON DATABASE
    public long delete(Category category){
        //DELETE
        String whereClause = COL_ID + "= ?";
        String[] whereArgs = {String.valueOf(category.getId())};

        return this.db.delete(TABLE_CATEGORY, whereClause, whereArgs);
    }

    public Category getCategory(long id){
        //SELECT
        String[] cols = {COL_ID, COL_NAME, COL_CREATEDAT, COL_UPDATEDAT};

        String whereClauses = COL_ID + "= ?";
        String[] whereArgs = {String.valueOf(id)};

        Cursor c = this.db.query(TABLE_CATEGORY, cols, whereClauses, whereArgs, null, null, null);

        Category resultCategory = null;

        if(c.getCount() > 0){
            c.moveToFirst();

            resultCategory = this.cursorToItem(c);
        }

        c.close();

        return resultCategory;
    }

    public Cursor getAllCursor(){
        //SELECT
        String[] cols = {COL_ID, COL_NAME, COL_CREATEDAT, COL_UPDATEDAT};

        Cursor c = db.query(TABLE_CATEGORY, cols,null,null, null, null, null);

        return c;
    }

    public static Category cursorToItem(Cursor c){
        Category resultCategory = new Category();

        resultCategory.setId(c.getInt(c.getColumnIndex(COL_ID)));
        resultCategory.setName(c.getString(c.getColumnIndex(COL_NAME)));
        //DATE CREATED AT
        DateFormat df = new SimpleDateFormat("yyyy/MM/dd hh:mm:ss");
        String dateCreatedAt = c.getString(c.getColumnIndex(COL_CREATEDAT));
        try {
            resultCategory.setCreated_at(df.parse(dateCreatedAt));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        //DATE UPDATED AT
        String dateUpdatedAt = c.getString(c.getColumnIndex(COL_UPDATEDAT));
        try {
            resultCategory.setCreated_at(df.parse(dateUpdatedAt));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return resultCategory;
    }

    //CONVERT ITEM TO CONTENT VALUES
    private ContentValues itemToContentValues(Category category){
        ContentValues values = new ContentValues();
        values.put(COL_NAME, category.getName());
        //DateFormat df = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");
        values.put(COL_CREATEDAT, category.getCreated_at().toString());
        values.put(COL_UPDATEDAT, category.getUpdated_at().toString());

        return values;
    }
}
