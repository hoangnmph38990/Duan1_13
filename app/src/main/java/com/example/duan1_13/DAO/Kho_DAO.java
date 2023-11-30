package com.example.duan1_13.DAO;

import static com.example.duan1_13.Helper.Content.*;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.duan1_13.Database.Mydatabase;

public class Kho_DAO {
    private SQLiteDatabase sql;
    private Mydatabase db;

    public Kho_DAO(Context context) {
        db = new Mydatabase(context);
        sql = db.getWritableDatabase();
    }

    public int getSLConLai(int ID) {
        int soL = 0;
        sql = db.getReadableDatabase();
        Cursor cursor = sql.rawQuery(" select " + COLUMN_NH_SL + " from " + TABLE_NH + " WHERE " + COLUMN_NH_MA + " = ?", new String[]{String.valueOf(ID)});
        while (cursor.moveToNext()) {
            int a = cursor.getColumnIndex(COLUMN_NH_SL);
            soL = cursor.getInt(a);
        }
        cursor.close();
        return soL;
    }

    public int updateSL(int sl, int maNH) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_NH_SL, sl);
        return sql.update(TABLE_NH, values,
                COLUMN_NH_MA + " = ? ",
                new String[]{String.valueOf(maNH)});
    }
}
