package com.example.maestro;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DbHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "myDatabase.db";
    private static final int DATABASE_VERSION = 1;

    public DbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Tworzenie tabeli rejestracji
        String CREATE_REGISTRATION_TABLE = "CREATE TABLE registration ("
                + "id INTEGER PRIMARY KEY AUTOINCREMENT,"
                + "login TEXT,"
                + "password TEXT,"
                + "email TEXT"
                + ")";
        db.execSQL(CREATE_REGISTRATION_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Aktualizacja bazy danych
        db.execSQL("DROP TABLE IF EXISTS registration");
        onCreate(db);
    }
}

