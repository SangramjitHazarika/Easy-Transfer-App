package com.example.easytransfer;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class DBHandler_2 extends SQLiteOpenHelper {

    public DBHandler_2(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String create = "CREATE TABLE transaction_history(sl_no INTEGER PRIMARY KEY, sender_name TEXT, sender_email TEXT, receiver_name TEXT, receiver_email TEXT, amount INTEGER, date_and_time TEXT, result TEXT);";
        db.execSQL(create);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS 'transaction_history'");
        onCreate(db);
    }

    public void addTransactionRecord(Transaction_Record tr) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("sender_name", tr.getSender_name());
        values.put("sender_email", tr.getSender_email());
        values.put("receiver_name", tr.getReceiver_name());
        values.put("receiver_email", tr.getReceiver_email());
        values.put("amount", tr.getAmount());
        values.put("date_and_time", tr.getDateTime());
        values.put("result", tr.getResult());
        long k = db.insert("transaction_history", null, values);
        db.close();
    }

    public int getCount() {
        String query = "SELECT * FROM transaction_history";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        return cursor.getCount();

    }

    public ArrayList<Transaction_Record> getAllTransactions() {
        ArrayList<Transaction_Record> a_u1 = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        String sql = "SELECT * FROM transaction_history";
        Cursor cursor = db.rawQuery(sql, null);
        if(cursor != null) {
            if(cursor.moveToFirst()) {
                do {
                    Transaction_Record u1 = new Transaction_Record(cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4), cursor.getInt(5), cursor.getString(6), cursor.getString(7));
                    a_u1.add(u1);
                }while(cursor.moveToNext());
            }
        }
        cursor.close();
        return a_u1;
    }

}