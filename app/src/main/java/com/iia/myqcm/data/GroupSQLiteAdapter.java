package com.iia.myqcm.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.iia.myqcm.entity.Group;
import com.iia.myqcm.entity.User;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

/**
 * Created by gemax on 30/01/2016.
 */
public class  GroupSQLiteAdapter {
    public static final String TABLE_GROUP = "groupUser";
    public static final String COL_ID = "_id";
    public static final String COL_NAME = "name";
    public static final String COL_CREATEDAT = "created_at";
    public static final String COL_UPDATEDAT = "updated_at";

    private SQLiteDatabase db;
    private MyqcmSQLiteOpenHelper helper;

    /**
     * Constructor of GroupSQLiteAdapter
     * @param context
     */
    public GroupSQLiteAdapter(Context context) {
        this.helper = new MyqcmSQLiteOpenHelper(context,MyqcmSQLiteOpenHelper.DB_NAME, null, 1);
    }

    /**
     * @return schema of the database
     */
    public static String getSchema(){
        return "CREATE TABLE "+ TABLE_GROUP + " (" + COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COL_NAME + " TEXT NOT NULL,"
                + COL_CREATEDAT + " DATE NOT NULL,"
                + COL_UPDATEDAT + " DATE NOT NULL);";
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
     * Insert user in database
     * @param group
     * @return group id
     */
    public long insert(Group group){
        return db.insert(TABLE_GROUP, null, this.itemToContentValues(group));
    }

    /**
     * Update user in database
     * @param group
     * @return group id
     */
    public long update(Group group){
        ContentValues values = this.itemToContentValues(group);

        String whereClause = COL_ID + "= ?";
        String[] whereArgs = {String.valueOf(group.getId())};

        return this.db.update(TABLE_GROUP, values, whereClause, whereArgs);
    }

    /**
     * Delete user in database
     * @param group
     * @return group id
     */
    //DELETE USER ON DATABASE
    public long delete(Group group){
        //DELETE
        String whereClause = COL_ID + "= ?";
        String[] whereArgs = {String.valueOf(group.getId())};

        return this.db.delete(TABLE_GROUP, whereClause, whereArgs);
    }

    /**
     * Get group in database
     * @param id
     * @return Group
     */
    public Group getGroup(long id){
        //SELECT
        String[] cols = {COL_ID, COL_NAME, COL_CREATEDAT, COL_UPDATEDAT};

        String whereClauses = COL_ID + "= ?";
        String[] whereArgs = {String.valueOf(id)};

        Cursor c = this.db.query(TABLE_GROUP, cols, whereClauses, whereArgs, null, null, null);

        Group resultGroup = null;

        if(c.getCount() > 0){
            c.moveToFirst();

            resultGroup = this.cursorToItem(c);
        }

        c.close();

        return resultGroup;
    }

    /**
     * Convert cursor to group
     * @param c
     * @return Group
     */
    public static Group cursorToItem(Cursor c){
        Group resultGroup = new Group();

        resultGroup.setId(c.getInt(c.getColumnIndex(COL_ID)));
        resultGroup.setName(c.getString(c.getColumnIndex(COL_NAME)));
        //DATE CREATED AT
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String dateCreatedAt = c.getString(c.getColumnIndex(COL_CREATEDAT));
        try {
            resultGroup.setCreated_at(dateFormat.parse(dateCreatedAt));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        //DATE UPDATED AT
        String dateUpdatedAt = c.getString(c.getColumnIndex(COL_UPDATEDAT));
        try {
            resultGroup.setCreated_at(dateFormat.parse(dateUpdatedAt));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return resultGroup;
    }

    /**
     * Convert group to Content Values
     * @param group
     * @return Content Values
     */
    //CONVERT ITEM TO CONTENT VALUES
    private ContentValues itemToContentValues(Group group){
        ContentValues values = new ContentValues();
        values.put(COL_NAME, group.getName());
        values.put(COL_CREATEDAT, group.getCreated_at().toString());
        values.put(COL_UPDATEDAT, group.getUpdated_at().toString());

        return values;
    }
}
