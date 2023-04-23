package com.example.maestro;

import static com.example.maestro.RegistrationModel.COLUMN_EMAIL;
import static com.example.maestro.RegistrationModel.COLUMN_ID;
import static com.example.maestro.RegistrationModel.COLUMN_LOGIN;
import static com.example.maestro.RegistrationModel.COLUMN_PASSWORD;
import static com.example.maestro.RegistrationModel.TABLE_NAME;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
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


    public boolean addUser(RegistrationModel registrationModel) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues Values = new ContentValues();
        Values.put(COLUMN_LOGIN, registrationModel.getLogin());
        Values.put(COLUMN_PASSWORD, registrationModel.getPassword());
        Values.put(COLUMN_EMAIL,registrationModel.getEmail());
        long insert = db.insert(TABLE_NAME, null, Values);
        if (insert == -1) {
            return false;

        } else {
            return true;
        }
    }

    public RegistrationModel getModelByID(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        String queryString = " SELECT * FROM " + TABLE_NAME + " WHERE " + COLUMN_ID + " = " + id;
        Cursor cursor = db.rawQuery(queryString, null);
        if (cursor.moveToFirst()) {
            int column_id = cursor.getInt(0);
            String login = cursor.getString(1);
            String password = cursor.getString(2);
            String email= cursor.getString(3);
            RegistrationModel Register = new RegistrationModel(column_id, login,password, email);
            return Register;
        }
        cursor.close();
        db.close();
        return null;
    }

    public boolean deleteUsersByID(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_NAME, COLUMN_ID + "=" + id, null) > 0;
    }


    public boolean updateUsers(RegistrationModel registrationModel) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues Values = new ContentValues();
        Values.put(COLUMN_LOGIN, registrationModel.getLogin());
        Values.put(COLUMN_PASSWORD, registrationModel.getPassword());
        Values.put(COLUMN_EMAIL,registrationModel.getEmail());
        return db.update(TABLE_NAME, Values, COLUMN_ID + "= ?", new String[]{String.valueOf(registrationModel.getId())}) > 0;
    }
}

