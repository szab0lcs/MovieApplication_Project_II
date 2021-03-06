package com.example.movieapp.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.movieapp.User.User;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "register.db";
    private static final String TABLE_NAME = "registeruser";
    public static final String id = "ID";
    public static final String name = "name";
    public static final String age = "age";
    public static final String email = "email";
    private static final String password = "password";

    public DatabaseHelper(@Nullable Context context) {

        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE registeruser (ID INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT, age INTEGER, email TEXT, password TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
    }

    //insert data into database

    public boolean addUser(String name, String age, String email, String password){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("name", name);
        contentValues.put("age", age);
        contentValues.put("email", email);
        contentValues.put("password", password);
        long res = db.insert("registeruser", null, contentValues);
        if(res==-1)
            return false;
        else
            return true;

    }

    //check if email exists

    public Boolean checkEmail(String email){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM registeruser where email=? ", new String[] {email});

        if (cursor.getCount()>0)
            return false;
        else
            return true;

    }

    //check the email and password in login

    public Boolean checkLogin(String email, String password){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM registeruser where email=? and password=?", new String[] {email, password});
        if(cursor.getCount()>0)
            return true;
        else
            return false;
    }

    public User profileLogin(String email){

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM registeruser where email=?", new String[] {email});
        String name ;
        String email1;
        String age;
        User user;

        if(cursor != null){
            cursor.moveToFirst();
        }
        age = cursor.getString(2);
        name = cursor.getString(1);
        email1 = cursor.getString(3);


        user = new User(name, email1, age);

        return user ;
    }

    public boolean updatePassword(String email1, String password1){

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(email, email1);
        contentValues.put(password, password1);

        db.update(TABLE_NAME, contentValues, email+" = ?", new String[]{email1});
        return true;
    }
}
