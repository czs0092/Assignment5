package com.example.assignment5;

import android.database.Cursor;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase;
import android.content.Context;
import android.content.ContentValues;

public class DBManagement extends SQLiteOpenHelper{

        public static final String DATABASE_NAME = "spending.db";
        public static final String TABLE_NAME = "SpendingTable";
        public static final String COL_NAME_ENUMERATION = "Count";
        public static final String COL_NAME_ITEM = "Item";
        public static final String COL_NAME_DATE = "Date";
        public static final String COL_NAME_PRICE = "Price";

        public DBManagement(Context context){
            super(context, DATABASE_NAME, null, 5);
        }

        public void onCreate(SQLiteDatabase sqlDB){
            sqlDB.execSQL("CREATE TABLE " + TABLE_NAME + " (Count INTEGER PRIMARY KEY AUTOINCREMENT, Item varchar(100), Date varchar(100), Price DOUBLE)");
        }

        @Override
        public void onUpgrade(SQLiteDatabase sqlDB, int older, int newer){
            sqlDB.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
            onCreate(sqlDB);
        }


        public boolean createTransaction(Model model){
            SQLiteDatabase db = this.getReadableDatabase();
            ContentValues contentValues = new ContentValues();
            contentValues.put(COL_NAME_ITEM, model.mItem);
            contentValues.put(COL_NAME_DATE, model.mDate);
            contentValues.put(COL_NAME_PRICE, model.mPrice);
            long result = db.insert(TABLE_NAME, null, contentValues);
            if (result == -1) {
                return false;
            }
            return true;
        }

        public Cursor pullData() {
            SQLiteDatabase sqlDB = this.getReadableDatabase();
            Cursor res = sqlDB.rawQuery("SELECT * FROM " + TABLE_NAME, null);
            return res;
        }
    }

