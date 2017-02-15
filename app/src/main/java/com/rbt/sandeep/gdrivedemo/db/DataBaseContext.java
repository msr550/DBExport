package com.rbt.sandeep.gdrivedemo.db;

import android.database.sqlite.SQLiteDatabase;

/**
 * Created by sandeep on 6/5/15.
 */
public class DataBaseContext {

    private static DBHandler dbHandler;

    /**
     * This method is used to set DBHandler object, it mainly used for singleton pattern
     *
     * @param dbHandler DBHandler object
     */
    public static void setDBHandler(DBHandler dbHandler) {
        DataBaseContext.dbHandler = dbHandler;
    }

    /**
     * This method is used to get the database object in readable or writable format.
     *
     * @param isWrtitable returns Database mode. 1 represents writable and 0 represents readable.
     * @return returns SQLiteDatabase object either readable or writable depends on the input
     */
    public static SQLiteDatabase getDBObject(int isWrtitable) {
        return dbHandler.getDBObject(isWrtitable);
    }

}
