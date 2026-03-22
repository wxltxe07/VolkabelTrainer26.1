package com.example.vokabeltrainer261;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;


public class DbHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "databaseVocab.db";
    private static final int DB_VERSION = 1;

    public DbHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String createTable =
                "CREATE TABLE IF NOT EXISTS Vocabs (" +
                        "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                        "german TEXT NOT NULL," +
                        "other TEXT NOT NULL," +
                        "lektion INTEGER DEFAULT 0," +
                        "score INTEGER DEFAULT 0)";

        db.execSQL(createTable);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // später für Updates
    }

    public void addVocab(String german, String other) {

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("german", german);
        values.put("other", other);

        db.insert("Vocabs", null, values);

        db.close();
    }

    public void updateVocab(String german, String other, int id){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("german", german);
        values.put("other", other);
        db.update("Vocabs", values, "id = ?", new String[]{String.valueOf(id)});
        db.close();
    }

    public String readGerman(int id){
        String germanWord = null;
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery(
                "SELECT german FROM Vocabs WHERE id = ?",
                new String[]{String.valueOf(id)}
        );

        if (cursor.moveToFirst()) {
            germanWord = cursor.getString(cursor.getColumnIndexOrThrow("german"));
        }

        cursor.close();
        db.close();

        return germanWord;
    }

    public int readAmount(){

        SQLiteDatabase db = this.getReadableDatabase();

        int amount = 0;

        Cursor cursor = db.rawQuery("SELECT COUNT(*) FROM Vocabs", null);

        if (cursor.moveToFirst()) {
            amount = cursor.getInt(0);
        }

        cursor.close();


        db.close();

        return amount;
    }


    public String readOther(int id){
        String otherWord = null;
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery(
                "SELECT other FROM Vocabs WHERE id = ?",
                new String[]{String.valueOf(id)}
        );

        if (cursor.moveToFirst()) {
            otherWord = cursor.getString(cursor.getColumnIndexOrThrow("other"));
        }

        cursor.close();
        db.close();

        return otherWord;
    }

    public void deleteVocab(int id){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete("Vocabs", "id = ?", new String[]{String.valueOf(id)});
        db.close();
    }


    }
