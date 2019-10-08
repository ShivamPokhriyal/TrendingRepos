package com.example.trendingrepo.dbUtils;

import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.example.trendingrepo.utils.Utils;

/**
 * $ |-| ! V @ M
 * Created by Shivam Pokhriyal on 2019-10-08.
 */
public class DBHelper extends SQLiteOpenHelper {

    public static final String _ID = "_id";
    public static final String AUTHOR = "author";
    public static final String REPO_NAME = "repo_name";
    public static final String AVATAR = "avatar";
    public static final String DESCRIPTION = "description";
    public static final String STARS = "stars";
    public static final String FORKS = "forks";
    public static final String LANGUAGE = "language";
    public static final String LANGUAGE_COLOR = "language_color";
    public static final String REPO_URL = "repo_url";
    public static final String REPO_TABLE_NAME = "repository";

    private static final String DATABASE_NAME = "TrendingRepos";
    private static final int DB_VERSION = 1;

    private static final String CREATE_REPOSITORY_TABLE = " CREATE TABLE " + REPO_TABLE_NAME + " ( " +
            _ID + " integer primary key autoincrement, "
            + AUTHOR + " varchar(2500) , "
            + REPO_NAME + " varchar(250) , "
            + AVATAR + " varchar(250) ,"
            + DESCRIPTION + " text ,"
            + STARS + " integer,"
            + FORKS + " integer,"
            + LANGUAGE + " varchar(250) ,"
            + LANGUAGE_COLOR + " varchar(250),"
            + REPO_URL + " varchar(250) ,"
            + "UNIQUE (" + REPO_URL + "))";

    private static DBHelper dbHelper;

    private DBHelper(Context context) {
        this(context, DATABASE_NAME, null, DB_VERSION);
    }

    public DBHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    public static DBHelper getInstance(Context context) {
        if (dbHelper == null) {
            dbHelper = new DBHelper(context.getApplicationContext());
        }
        return dbHelper;
    }

    @Override
    public SQLiteDatabase getReadableDatabase() {
        SQLiteDatabase database = super.getReadableDatabase();
        database.enableWriteAheadLogging();
        return database;
    }

    @Override
    public SQLiteDatabase getWritableDatabase() {
        SQLiteDatabase database = super.getWritableDatabase();
        database.enableWriteAheadLogging();
        return database;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        if (!isTableExists(sqLiteDatabase, REPO_TABLE_NAME)) {
            sqLiteDatabase.execSQL(CREATE_REPOSITORY_TABLE);
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
//        if (oldVersion < newVersion) {
            if (!isTableExists(sqLiteDatabase, REPO_TABLE_NAME)) {
                sqLiteDatabase.execSQL(CREATE_REPOSITORY_TABLE);
            }
//        } else {
//            onCreate(sqLiteDatabase);
//        }
    }

    private boolean isTableExists(SQLiteDatabase database, String tableName) {
        Cursor cursor = database.rawQuery("select DISTINCT tbl_name from sqlite_master where tbl_name = '" + tableName + "'", null);
        if (cursor != null) {
            if (cursor.getCount() > 0) {
                cursor.close();
                return true;
            }
            cursor.close();
        }
        return false;
    }
}
