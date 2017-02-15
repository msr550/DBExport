package com.rbt.sandeep.gdrivedemo.activities;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.rbt.sandeep.gdrivedemo.R;
import com.rbt.sandeep.gdrivedemo.db.DBHandler;
import com.rbt.sandeep.gdrivedemo.pojo.User;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.util.ArrayList;

import static com.rbt.sandeep.gdrivedemo.db.DataBaseContext.getDBObject;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private TextView countTV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        countTV = (TextView) findViewById(R.id.countTV);
        DBHandler.getInstance(this);
        //  Log.i("==p1","===path::"+getE());
        // SQLiteDatabase dbObject = getDBObject(1);
        // insertData(dbObject);
        displayCount();


    }

    private void displayCount() {
        SQLiteDatabase dbObject = getDBObject(1);
        ArrayList<User> users = getUsers(dbObject);
        countTV.setText("Records Count:" + users.size());
    }

    private void insertData(SQLiteDatabase dbObject) {
        ArrayList<User> users = new ArrayList<>();
        users.add(new User("Sandeep", "RBT"));
        users.add(new User("Sachin", "CTS"));
        users.add(new User("DRavid", "Google"));

        try {

            dbObject.beginTransaction();
            SQLiteStatement stmt = dbObject.compileStatement("INSERT INTO " + DBHandler.USERS_TABLE + " (name, company) VALUES (?, ?)");
            for (int i = 0; i < users.size(); i++) {
                User user = users.get(i);
                stmt.bindString(1, user.getName());
                stmt.bindString(2, user.getCompany());
                stmt.execute();
            }
            dbObject.setTransactionSuccessful();
        } catch (Exception e) {
            e.printStackTrace();

        } finally {
            dbObject.endTransaction();
        }
    }

    public ArrayList<User> getUsers(SQLiteDatabase dbObject) {
        Cursor cursor = null;
        User user = null;
        ArrayList<User> usersList = new ArrayList<User>();
        try {
            cursor = dbObject.rawQuery("SELECT * from " + DBHandler.USERS_TABLE, null);
            if (cursor.moveToFirst()) {
                do {
                    user = new User();
                    user.setId(cursor.getInt(0));
                    user.setName(cursor.getString(1));
                    user.setCompany(cursor.getString(2));
                    usersList.add(user);
                    user = null;
                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
        }
        Log.e("===Size==", "==Uses::" + usersList.size());
        return usersList;
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.exportBtn:
                exportDB();
                break;
            case R.id.importBtn:
                try {
                    importDatabase("/storage/emulated/0/" + DBHandler.DATABASE_NAME);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                displayCount();
                break;
        }
    }

    private void exportDB() {
        File sd = Environment.getExternalStorageDirectory();
        File data = Environment.getDataDirectory();
        Log.i("==P12==", "===PPP" + data.getAbsolutePath());
        FileChannel source = null;
        FileChannel destination = null;
        String currentDBPath = "/data/" + getPackageName() + "/databases/" + DBHandler.DATABASE_NAME;
        String backupDBPath = DBHandler.DATABASE_NAME;
        File currentDB = new File(data, currentDBPath);
        File backupDB = new File(sd, backupDBPath);
        if (currentDB.exists()) {
            try {
                source = new FileInputStream(currentDB).getChannel();
                destination = new FileOutputStream(backupDB).getChannel();
                destination.transferFrom(source, 0, source.size());
                source.close();
                Log.i("===Path==", "===" + backupDB.getAbsolutePath());
                destination.close();
                Toast.makeText(this, "DB Exported!", Toast.LENGTH_LONG).show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public boolean importDatabase(String dbPath) throws IOException {

        // Close the SQLiteOpenHelper so it will commit the created empty
        // database to internal storage.
        File oldDB = new File(dbPath);
        File data = Environment.getDataDirectory();
        String targetPath = "/data/" + getPackageName() + "/databases/" + DBHandler.DATABASE_NAME;
        File targetFile = new File(data, targetPath);
        if (oldDB.exists()) {
            copyFile(new FileInputStream(oldDB), new FileOutputStream(targetFile));
            // Access the copied database so SQLiteHelper will cache it and mark
            // it as created.
            return true;
        }
        return false;
    }

    public void copyFile(FileInputStream fromFile, FileOutputStream toFile) throws IOException {
        FileChannel fromChannel = null;
        FileChannel toChannel = null;
        try {
            fromChannel = fromFile.getChannel();
            toChannel = toFile.getChannel();
            fromChannel.transferTo(0, fromChannel.size(), toChannel);
        } finally {
            try {
                if (fromChannel != null) {
                    fromChannel.close();
                }
            } finally {
                if (toChannel != null) {
                    toChannel.close();
                }
            }
        }
    }
}
