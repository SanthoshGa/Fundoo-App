package com.bridgelabz.fundoo.LoginSignup.Model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.bridgelabz.fundoo.DatabaseHelpers.SQLiteDatabaseHelper;

import static com.bridgelabz.fundoo.DatabaseHelpers.SQLiteDatabaseHelper.USER_TABLE_COL_FIRST_NAME;
import static com.bridgelabz.fundoo.DatabaseHelpers.SQLiteDatabaseHelper.USER_TABLE_COL_ID;
import static com.bridgelabz.fundoo.DatabaseHelpers.SQLiteDatabaseHelper.USER_TABLE_COL_LAST_NAME;
import static com.bridgelabz.fundoo.DatabaseHelpers.SQLiteDatabaseHelper.USER_TABLE_COL_USERNAME;
import static com.bridgelabz.fundoo.DatabaseHelpers.SQLiteDatabaseHelper.USER_TABLE_COL_PASSWORD;
import static com.bridgelabz.fundoo.DatabaseHelpers.SQLiteDatabaseHelper.USER_TABLE_COL_EMAIL;
import static com.bridgelabz.fundoo.DatabaseHelpers.SQLiteDatabaseHelper.USER_TABLE_NAME;

public class UserDatabaseManager {
    private static final String TAG = "DatabaseHelper.class";
    private SQLiteDatabaseHelper databaseHelper;
    public UserDatabaseManager(Context context){
        databaseHelper = SQLiteDatabaseHelper.getDatabaseHelper(context);
    }

    public boolean addUser(User user) {
        SQLiteDatabase db = databaseHelper.openDb();
        ContentValues contentValues = new ContentValues();
        contentValues.put(USER_TABLE_COL_FIRST_NAME, user.getFirstName());
        contentValues.put(USER_TABLE_COL_LAST_NAME, user.getLastName());
        contentValues.put(USER_TABLE_COL_USERNAME, user.getUsername());
        contentValues.put(USER_TABLE_COL_EMAIL, user.getEmailId());
        contentValues.put(USER_TABLE_COL_PASSWORD, user.getPassword());


        long res = db.insert(USER_TABLE_NAME, null, contentValues);
        db.close();

        Log.e(TAG, " res is " + res);
        return res > 0;
    }

    public boolean checkUser(String email, String password) {
        String[] columns = {USER_TABLE_COL_ID};
        SQLiteDatabase db = databaseHelper.openDb();
        String selection = USER_TABLE_COL_EMAIL + "=?" + " and " + USER_TABLE_COL_PASSWORD + "=?";
        String[] selectionArgs = {email, password};
        Cursor cursor = db.query("USER_TABLE", columns, selection, selectionArgs, null, null, null);
        int count = cursor.getCount();
        cursor.close();
        db.close();

        Log.e(TAG, "count is " + count);

        return count > 0;
    }

}