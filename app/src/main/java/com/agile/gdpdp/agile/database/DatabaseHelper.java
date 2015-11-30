package com.agile.gdpdp.agile.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.agile.gdpdp.agile.database.model.Sprint;

/**
 * Created by gdpdp on 11/30/2015.
 */
public class DatabaseHelper extends SQLiteOpenHelper implements IDatabaseHelper{

    private static final int DTABASE_VERSION = 1;
    private static final String DATABASE_NAME = "AgileApplication";

    private static final String TABLE_SPRINT = "sprint";

    // Sprint table column
    private static final String KEY_ID_SPRINT = "idSprint";
    private static final String KEY_NAME_SPRINT = "nameSprint";
    private static final String KEY_START_SPRINT_DATETIME = "startDate";
    private static final String KEY_END_SPRINT_DATETIME = "endDate";

    private static final String CREATE_TABLE_SPRINT = "CREATE TABLE "
            + TABLE_SPRINT + "(" + KEY_ID_SPRINT + " INTEGER PRIMARY KEY," + KEY_NAME_SPRINT + " TEXT,"
            + KEY_START_SPRINT_DATETIME + " DATETIME," + KEY_END_SPRINT_DATETIME + " DATETIME" + ")";

    public DatabaseHelper (Context context) {
        super(context, DATABASE_NAME, null, DTABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_SPRINT);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SPRINT);

        onCreate(db);
    }

    @Override
    public void addSprint(Sprint sprint) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_NAME_SPRINT, sprint.getName());
        values.put(KEY_START_SPRINT_DATETIME, sprint.getStartDateTime());
        values.put(KEY_END_SPRINT_DATETIME, sprint.getEndDateTime());

        db.insert(TABLE_SPRINT, null, values);

        System.out.println("Sprint saved!!!!!!");
    }
}
