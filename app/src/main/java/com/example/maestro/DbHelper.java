package com.example.maestro;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DbHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "myDatabase.db";
    private static final int DATABASE_VERSION = 4;

    public static final String TABLE_NAME = "registration";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_LOGIN = "login";
    public static final String COLUMN_PASSWORD = "password";
    public static final String COLUMN_EMAIL = "email";

    public static final String TABLE_NOTES = "Notes";
    public static final String NOTES_ID = "id";
    public static final String NOTES_AUTH = "author";
    public static final String NOTES_TITLE = "title";


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
                        + COLUMN_EMAIL + " TEXT"
                        + ")";

        String CREATE_NOTES_TABLE =
                "CREATE TABLE " + TABLE_NOTES + "("
                        + NOTES_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                        + NOTES_AUTH + " TEXT,"
                        + NOTES_TITLE + " TEXT" + ")";
        db.execSQL(CREATE_REGISTRATION_TABLE);
        db.execSQL(CREATE_NOTES_TABLE);
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Aktualizacja bazy danych
        db.execSQL("DROP TABLE IF EXISTS registration");
        db.execSQL("DROP TABLE IF EXISTS Notes");
        onCreate(db);
    }

    public boolean addUser(RegistrationModel registrationModel) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues Values = new ContentValues();
        Values.put(COLUMN_LOGIN, registrationModel.getLogin());
        Values.put(COLUMN_PASSWORD, registrationModel.getPassword());
        Values.put(COLUMN_EMAIL, registrationModel.getEmail());
        long insert = db.insert(TABLE_NAME, null, Values);
        return insert != -1;
    }
    public boolean addNotes(NotesModel notesModel){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues Values = new ContentValues();
        Values.put(NOTES_AUTH, notesModel.getAuthor());
        Values.put(NOTES_TITLE, notesModel.getTitle());
        long insert = db.insert(TABLE_NOTES, null, Values);
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
            return new RegistrationModel(column_id, login, password, email);
        }
        cursor.close();
        db.close();
        return null;
    }

    public NotesModel getNModelByID(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        String queryString = " SELECT * FROM " + TABLE_NOTES+ " WHERE " + NOTES_ID + " = " + id;
        Cursor cursor = db.rawQuery(queryString, null);
        if (cursor.moveToFirst()) {
            int column_id = cursor.getInt(0);
            String author = cursor.getString(1);
            String title = cursor.getString(2);

            return new NotesModel(column_id, author, title);
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
        Values.put(COLUMN_EMAIL, registrationModel.getEmail());
        return db.update(TABLE_NAME, Values, COLUMN_ID + "= ?", new String[]{String.valueOf(registrationModel.getId())}) > 0;
    }

    public boolean updateNotes(NotesModel notesModel) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues Values = new ContentValues();
        Values.put(NOTES_AUTH, notesModel.getAuthor());
        Values.put(NOTES_TITLE, notesModel.getTitle());
        return db.update(TABLE_NOTES, Values, COLUMN_ID + "= ?", new String[]{String.valueOf(notesModel.getId())}) > 0;
    }
    public boolean checkUser(String login, String password) {
        if ( login.isEmpty() || password.isEmpty()) {
            return false; // niepoprawne dane logowania
        }

        SQLiteDatabase db = this.getReadableDatabase();
        String[] columns = {COLUMN_ID};
        String selection = COLUMN_LOGIN + " = ?" + " AND " + COLUMN_PASSWORD + " = ?";
        String[] selectionArgs = {login, password};
        Cursor cursor = db.query(TABLE_NAME, columns, selection, selectionArgs, null, null, null);
        int count = cursor.getCount();
        cursor.close();
        db.close();


        return count > 0; // znaleziono użytkownika z podanymi loginem i hasłem
    }

}


