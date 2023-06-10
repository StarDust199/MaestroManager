package com.example.maestro;


import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DbHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "myDatabase.db";
    private static final int DATABASE_VERSION = 11;
    public static final String TABLE_NAME = "registration";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_LOGIN = "login";
    public static final String COLUMN_PASSWORD = "password";
    public static final String COLUMN_EMAIL = "email";
    public static final String COLUMN_ROLE="role";


    public DbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Tworzenie tabeli rejestracji
        String CREATE_REGISTRATION_TABLE =
                "CREATE TABLE " + TABLE_NAME + "("
                        + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                        + COLUMN_LOGIN + " TEXT,"
                        + COLUMN_PASSWORD + " TEXT,"
                        + COLUMN_EMAIL + " TEXT,"
                        + COLUMN_ROLE + " TEXT"
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
        Values.put(COLUMN_EMAIL, registrationModel.getEmail());
        Values.put(COLUMN_ROLE, registrationModel.getRole());
        long insert = db.insert(TABLE_NAME, null, Values);
        return insert != -1;
    }

    public RegistrationModel getModelByID(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        String queryString = " SELECT * FROM " + TABLE_NAME + " WHERE " + COLUMN_ID + " = " + id;
        Cursor cursor = db.rawQuery(queryString, null);
        if (cursor.moveToFirst()) {
            int column_id = cursor.getInt(0);
            String login = cursor.getString(1);
            String password = cursor.getString(2);
            String email = cursor.getString(3);
            String role = cursor.getString(4);
            return new RegistrationModel(column_id, login, password, email, role);
        }
        cursor.close();
        db.close();
        return null;
    }



    public boolean updateUsers(RegistrationModel registrationModel) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues Values = new ContentValues();
        Values.put(COLUMN_LOGIN, registrationModel.getLogin());
        Values.put(COLUMN_PASSWORD, registrationModel.getPassword());
        Values.put(COLUMN_EMAIL, registrationModel.getEmail());
        Values.put(COLUMN_ROLE,registrationModel.getRole());
        return db.update(TABLE_NAME, Values, COLUMN_ID + "= ?", new String[]{String.valueOf(registrationModel.getId())}) > 0;
    }

    @SuppressLint("Range")
    public boolean checkUser(String login, String password) {
        if (login.isEmpty() || password.isEmpty()) {
            return false; // Nieprawidłowe dane logowania
        }

        SQLiteDatabase db = this.getReadableDatabase();
        String[] columns = {COLUMN_ID, COLUMN_ROLE};
        String selection = COLUMN_LOGIN + " = ?" + " AND " + COLUMN_PASSWORD + " = ?";
        String[] selectionArgs = {login, password};
        Cursor cursor = db.query(TABLE_NAME, columns, selection, selectionArgs, null, null, null);
        int count = cursor.getCount();
        cursor.close();
        db.close();

        return count > 0;
    }

    @SuppressLint("Range")
    public boolean isAdmin(String login, String password) {
        if (login.isEmpty() || password.isEmpty()) {
            return false; // Nieprawidłowe dane logowania
        }

        SQLiteDatabase db = this.getReadableDatabase();
        String[] columns = {COLUMN_ID, COLUMN_ROLE};
        String selection = COLUMN_LOGIN + " = ?" + " AND " + COLUMN_PASSWORD + " = ?";
        String[] selectionArgs = {login, password};
        Cursor cursor = db.query(TABLE_NAME, columns, selection, selectionArgs, null, null, null);
        int count = cursor.getCount();

        String userRole = null;
        if (count > 0) {
            cursor.moveToFirst();
            userRole = cursor.getString(cursor.getColumnIndex(COLUMN_ROLE));
        }

        cursor.close();
        db.close();

        return userRole != null && userRole.equals("Kapelmistrz");
    }


}


