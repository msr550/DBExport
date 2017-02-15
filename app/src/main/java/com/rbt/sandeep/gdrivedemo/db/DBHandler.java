package com.rbt.sandeep.gdrivedemo.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Sandeep on 2/15/2017.
 */

public class DBHandler extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "demo.db"; //data base name.
    public static final String USERS_TABLE = "users";
    private static final int DATABASE_VERSION = 3; //data base version.
    private static DBHandler dbHandler;
    private String CREATE_USERS_TABLE_QUERY = "CREATE TABLE " + USERS_TABLE + " (" +
            "id                         INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL UNIQUE," +
            "name                       TEXT," +
            "company                    TEXT" +
            ");";

    public DBHandler(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    /**
     * This method is used for Creating the object for DBHandler class, and creating object only once.
     * Meaning here we have applied singleton design pattern
     *
     * @param ctx Activity context
     * @return rerurns the DBHandler object
     */
    public static synchronized DBHandler getInstance(Context ctx) {

        if (dbHandler == null) {
            dbHandler = new DBHandler(ctx, DATABASE_NAME, null, DATABASE_VERSION);
            DataBaseContext.setDBHandler(dbHandler); // this classes's reference is stored in DataBaseContext class.
        }
        return dbHandler;
    }

    /**
     * This method is used to get the database object in readable or writable format.
     *
     * @param isWrtitable returns Database mode. 1 represents writable and 0 represents readable.
     * @return returns SQLiteDatabase object either readable or writable depends on the input
     */
    public SQLiteDatabase getDBObject(int isWrtitable) {
        return (isWrtitable == 1) ? this.getWritableDatabase() : this.getReadableDatabase();

    }


    @Override
    public void onCreate(SQLiteDatabase db) {


        db.execSQL(CREATE_USERS_TABLE_QUERY);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }
}
