package com.example.easytransfer;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class DBHandler extends SQLiteOpenHelper {

    public DBHandler(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String create = "CREATE TABLE user_info (name TEXT, email_id TEXT PRIMARY KEY, account_no TEXT, balance INTEGER);";
        db.execSQL(create);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS 'user_info'");
        onCreate(db);
    }

    public void addUser(User user) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("name", user.getName());
        values.put("email_id", user.getEmail_id());
        values.put("account_no", user.getAccount_no());
        values.put("balance", user.getBalance());
        long k = db.insert("user_info", null, values);
        db.close();
    }

    public ArrayList<User> getUser(String email_id) {
        ArrayList<User> a_u1 = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        String sql = "SELECT * FROM user_info WHERE email_id = '" + email_id + "';";
        Cursor cursor = db.rawQuery(sql, null);
        if(cursor != null) {
            if(cursor.moveToFirst()) {
                do {
                    User u1 = new User(cursor.getString(0), cursor.getString(1), cursor.getString(2), cursor.getInt(3));
                    a_u1.add(u1);
                }while(cursor.moveToNext());
            }
        }
        cursor.close();
        return a_u1;

    }

    public int getCount() {
        String query = "SELECT * FROM user_info";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        return cursor.getCount();

    }

    public int getCount(String email) {
        String query = "SELECT * FROM user_info WHERE email_id != '" + email + "';";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        return cursor.getCount();

    }

    public ArrayList<User> getAllUsers() {
        ArrayList<User> a_u1 = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        String sql = "SELECT * FROM user_info";
        Cursor cursor = db.rawQuery(sql, null);
        if(cursor != null) {
            if(cursor.moveToFirst()) {
                do {
                    User u1 = new User(cursor.getString(0), cursor.getString(1), cursor.getString(2), cursor.getInt(3));
                    a_u1.add(u1);
                }while(cursor.moveToNext());
            }
        }
        cursor.close();
        return a_u1;
    }

    public ArrayList<User> getReceiverDetails(String sender_email) {
        ArrayList<User> a_u2 = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        String sql = "SELECT * FROM user_info WHERE email_id != '" + sender_email + "';";
        Cursor cursor = db.rawQuery(sql, null);
        if(cursor != null) {
            if(cursor.moveToFirst()) {
                do {
                    User u1 = new User(cursor.getString(0), cursor.getString(1), cursor.getString(2), cursor.getInt(3));
                    a_u2.add(u1);
                } while(cursor.moveToNext());
            }
        }
        cursor.close();
        return a_u2;
    }

    public void Update(String sender_email, String receiver_email, int amount) {
        SQLiteDatabase db = this.getWritableDatabase();
        String sql1 = "Update user_info set balance = balance - " + amount + " where email_id = '" + sender_email + "';";
        String sql2 = "Update user_info set balance = balance + " + amount + " where email_id = '" + receiver_email + "';";
        db.execSQL(sql1);
        db.execSQL(sql2);
        db.close();
    }

}
