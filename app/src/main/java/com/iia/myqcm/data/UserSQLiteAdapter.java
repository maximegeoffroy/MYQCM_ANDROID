package com.iia.myqcm.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.iia.myqcm.entity.User;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by gemax on 30/01/2016.
 */
public class UserSQLiteAdapter {
    public static final String TABLE_USER = "user";
    public static final String COL_ID = "_id";
    public static final String COL_USERNAME = "username";
    public static final String COL_PASSWORD = "password";
    public static final String COL_NAME = "name";
    public static final String COL_FIRSTNAME = "firstname";
    public static final String COL_EMAIL = "email";
    public static final String COL_CREATEDAT = "created_at";
    public static final String COL_UPDATEDAT = "updated_at";
    public static final String COL_IDSERVER = "idServer";
    public static final String COL_GROUPID = "group_id";

    private Context ctx;

    private SQLiteDatabase db;
    private MyqcmSQLiteOpenHelper helper;

    /**
     * Constructor of UserSQLiteAdapter
     * @param context
     */
    public UserSQLiteAdapter(Context context) {
        this.ctx = context;
        this.helper = new MyqcmSQLiteOpenHelper(context, MyqcmSQLiteOpenHelper.DB_NAME, null, 1);
    }

    /**
     * @return schema of the database
     */
    public static String getSchema(){
        return "CREATE TABLE "+ TABLE_USER + " (" + COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COL_USERNAME + " TEXT NOT NULL,"
                + COL_PASSWORD + " TEXT NOT NULL,"
                + COL_NAME + " TEXT NOT NULL,"
                + COL_FIRSTNAME + " TEXT NOT NULL,"
                + COL_EMAIL + " TEXT NOT NULL,"
                + COL_CREATEDAT  + " DATE NOT NULL,"
                + COL_UPDATEDAT + " DATE NOT NULL,"
                + COL_IDSERVER + " INTEGER NOT NULL,"
                + COL_GROUPID + " INTEGER);";
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
     * @param user
     * @return user id
     */
    public long insert(User user){
        long id = 0;
        if(this.getUser(user.getIdServer()) != null){
            id = this.getUser(user.getIdServer()).getId();
        }else{
            id = db.insert(TABLE_USER, null, this.itemToContentValues(user));
        }

        if(id > 0){
            id = user.getIdServer();
        }
        return id;
    }

    /**
     * Update user in database
     * @param user
     * @return user id
     */
    public long update(User user){
        ContentValues values = this.itemToContentValues(user);

        String whereClause = COL_ID + "= ?";
        String[] whereArgs = {String.valueOf(user.getId())};

        return this.db.update(TABLE_USER, values, whereClause, whereArgs);
    }

    /**
     * Delete user in database
     * @param user
     * @return user id
     */
    //DELETE USER ON DATABASE
    public long delete(User user){
        //DELETE
        String whereClause = COL_ID + "= ?";
        String[] whereArgs = {String.valueOf(user.getId())};

        return this.db.delete(TABLE_USER, whereClause, whereArgs);
    }

    /**
     * Get user in database
     * @param idServer
     * @return User
     */
    public User getUser(long idServer){
        //SELECT
        String[] cols = {COL_ID, COL_USERNAME,COL_PASSWORD, COL_NAME, COL_FIRSTNAME, COL_EMAIL, COL_CREATEDAT,
                COL_UPDATEDAT,COL_IDSERVER, COL_GROUPID};

        String whereClauses = COL_IDSERVER + "= ?";
        String[] whereArgs = {String.valueOf(idServer)};

        Cursor c = db.query(TABLE_USER, cols, whereClauses, whereArgs, null, null, null);

        User resultUser = null;

        if(c.getCount() > 0){
            c.moveToFirst();

            resultUser = this.cursorToItem(c);
        }

        c.close();

        return resultUser;
    }

    /**
     * Get user by id
     * @param id
     * @return User
     */
    public User getUserById(long id){
        //SELECT
        String[] cols = {COL_ID, COL_USERNAME, COL_PASSWORD, COL_NAME, COL_FIRSTNAME, COL_EMAIL, COL_CREATEDAT, COL_UPDATEDAT, COL_GROUPID};

        String whereClauses = COL_ID + "= ?";
        String[] whereArgs = {String.valueOf(id)};

        Cursor c = db.query(TABLE_USER, cols, whereClauses, whereArgs, null, null, null);

        User resultUser = null;

        if(c.getCount() > 0){
            c.moveToFirst();

            resultUser = this.cursorToItem(c);
        }

        c.close();

        return resultUser;
    }

    /**
     * Convert cursor to User
     * @param c
     * @return User
     */
    public User cursorToItem(Cursor c){
        User resultUser = new User();

        resultUser.setId(c.getInt(c.getColumnIndex(COL_ID)));
        resultUser.setUsername(c.getString(c.getColumnIndex(COL_USERNAME)));
        resultUser.setPassword(c.getString(c.getColumnIndex(COL_PASSWORD)));
        resultUser.setName(c.getString(c.getColumnIndex(COL_NAME)));
        resultUser.setFirstname(c.getString(c.getColumnIndex(COL_FIRSTNAME)));
        resultUser.setEmail(c.getString(c.getColumnIndex(COL_EMAIL)));

        //DATE CREATED AT
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String dateCreatedAt = c.getString(c.getColumnIndex(COL_CREATEDAT));
        /*try {
            resultUser.setCreated_at(dateFormat.parse(dateCreatedAt));
        } catch (ParseException e) {
            e.printStackTrace();
        }*/

        //DATE UPDATED AT
        /*String dateUpdatedAt = c.getString(c.getColumnIndex(COL_UPDATEDAT));
        try {
            resultUser.setCreated_at(dateFormat.parse(dateUpdatedAt));
        } catch (ParseException e) {
            e.printStackTrace();
        }*/

        resultUser.setIdServer(c.getLong(c.getColumnIndex(COL_IDSERVER)));

        /**
         * Group
         */
        GroupSQLiteAdapter groupSQLiteAdapter = new GroupSQLiteAdapter(ctx);
        groupSQLiteAdapter.open();
        resultUser.setGroup(groupSQLiteAdapter.getGroup(c.getInt(c.getColumnIndex(COL_GROUPID))));
        groupSQLiteAdapter.close();

        return resultUser;
    }

    /**
     * Convert user to content values
     * @param user
     * @return Content Values
     */
    //CONVERT ITEM TO CONTENT VALUES
    private ContentValues itemToContentValues(User user){
        ContentValues values = new ContentValues();
        values.put(COL_USERNAME, user.getUsername());
        values.put(COL_PASSWORD, user.getPassword());
        values.put(COL_NAME, user.getName());
        values.put(COL_FIRSTNAME, user.getFirstname());
        values.put(COL_EMAIL, user.getEmail());
        Date d = new Date();
        values.put(COL_CREATEDAT, d.toString());
        values.put(COL_UPDATEDAT, d.toString());
        values.put(COL_IDSERVER, user.getIdServer());
        values.put(COL_GROUPID, user.getGroup().getId());

        return values;
    }

}
