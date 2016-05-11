package com.iia.myqcm.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by gemax on 30/01/2016.
 */
public class MyqcmSQLiteOpenHelper extends SQLiteOpenHelper {

    public static final String DB_NAME = "myqcmdatabase.sqlite";

    /**
     * Constructor of MyqcmSQLiteOpenHelper
     * @param context
     * @param name
     * @param factory
     * @param version
     */
    public MyqcmSQLiteOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(UserSQLiteAdapter.getSchema());
        db.execSQL(GroupSQLiteAdapter.getSchema());
        db.execSQL(QcmSQLiteAdapter.getSchema());
        db.execSQL(CategorySQLiteAdapter.getSchema());
        db.execSQL(QuestionSQLiteAdapter.getSchema());
        db.execSQL(AnswerSQLiteAdapter.getSchema());
        db.execSQL(AnswerUserSQLiteAdapter.getSchema());
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
