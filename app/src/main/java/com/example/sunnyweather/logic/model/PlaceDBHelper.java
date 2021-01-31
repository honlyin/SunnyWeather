package com.example.sunnyweather.logic.model;

import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class PlaceDBHelper extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "place.db";
    private static final String TEXT_TYPE = " TEXT";
    private static final String COMMA_SEP = ",";
    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + PlaceReaderContract.PlaceEntry.TABLE_NAME + " (" +
                    PlaceReaderContract.PlaceEntry._ID + " INTEGER PRIMARY KEY autoincrement," +
                    PlaceReaderContract.PlaceEntry.COLUMN_NAME_ENTRY_ID + TEXT_TYPE + COMMA_SEP +
                    PlaceReaderContract.PlaceEntry.COLUMN_NAME_LNG + TEXT_TYPE + COMMA_SEP +
                    PlaceReaderContract.PlaceEntry.COLUMN_NAME_LAT + TEXT_TYPE + COMMA_SEP +
                    PlaceReaderContract.PlaceEntry.COLUMN_NAME_PROVINCE + TEXT_TYPE + COMMA_SEP +
                    PlaceReaderContract.PlaceEntry.COLUMN_NAME_CITY + TEXT_TYPE + COMMA_SEP +
                    PlaceReaderContract.PlaceEntry.COLUMN_NAME_DISTRICT + TEXT_TYPE + COMMA_SEP +
                    PlaceReaderContract.PlaceEntry.COLUMN_NAME_FORMATTED_ADDRESS + TEXT_TYPE + " UNIQUE ON CONFLICT REPLACE "+ " )";


    public PlaceDBHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS" + PlaceReaderContract.PlaceEntry.TABLE_NAME);
        onCreate(db);
    }
}
