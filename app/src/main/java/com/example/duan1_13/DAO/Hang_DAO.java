package com.example.duan1_13.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import static com.example.duan1_13.Helper.Content.*;

import com.example.duan1_13.Database.Mydatabase;
import com.example.duan1_13.Model.Hang;

import java.util.ArrayList;
import java.util.List;

public class Hang_DAO {
    private SQLiteDatabase sql;
    private Mydatabase db;

    public Hang_DAO(Context context) {
        db = new Mydatabase(context);
        sql = db.getWritableDatabase();
    }

    public long insert(Hang h) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_H_TEN, h.getTenH());
        values.put(COLUMN_H_HA, h.getImg());
        return sql.insert(TABLE_HANG, null, values);
    }

    public int update(Hang h) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_H_TEN, h.getTenH());
        values.put(COLUMN_H_HA, h.getImg());
        return sql.update(TABLE_HANG, values,
                COLUMN_H_MA + " = ? ",
                new String[]{String.valueOf(h.getMaH())});
    }

    public int delete(String id) {
        return sql.delete(TABLE_HANG,
                COLUMN_H_MA + " = ? ",
                new String[]{String.valueOf(id)});
    }

    public List<Hang> getAll() {
        String sql = "select * from " + TABLE_HANG;
        return getData(sql);
    }

//    public Hang getID(String id) {
//        String sql = "select * from " + TABLE_HANG + " where " + COLUMN_H_MA + " = ? ";
//        List<Hang> list = getData(sql, id);
//        return list.get(0);
//    }

    //    public Hang getID(String id){
//        String sql = "select * from " + TABLE_HANG + " where " + COLUMN_H_MA + " = ? ";
//        List<Hang> list = getData(sql,id);
//        return list.get(0);
//    }
    public Hang getID(String id) {
        String sql = "select * from " + TABLE_HANG + " where " + COLUMN_H_MA + " = ? ";
        List<Hang> list = getData(sql, id);
        // Kiểm tra xem list có phần tử không trước khi truy cập
        if (!list.isEmpty()) {
            return list.get(0);
        } else {
            // Xử lý trường hợp không tìm thấy phần tử
            return null; // Hoặc có thể ném một ngoại lệ hoặc thực hiện hành động phù hợp với ứng dụng của bạn.
        }
    }


    public List<Hang> getData(String _sql, String... selectionArgs) {
        List<Hang> list = new ArrayList<>();
        Cursor cursor = sql.rawQuery(_sql, selectionArgs);
        cursor.moveToFirst();
        try {
            while (cursor.isAfterLast() == false) {
                int id = cursor.getInt(0);
                String ten = cursor.getString(1);
                byte[] hinhAnh = cursor.getBlob(2);
                Hang h = new Hang(id, ten, hinhAnh);
                list.add(h);
                cursor.moveToNext();
            }
        } catch (Exception e) {
            Log.e("Get Category error: ", e.getMessage());
        } finally {
            if (cursor != null && !cursor.isClosed()) {
                cursor.close();
            }
        }
        return list;
    }

    public Hang getByID(String _id) {
        Hang h = new Hang();
        sql = db.getReadableDatabase();
        Cursor cursor = sql.rawQuery("select * from " + TABLE_HANG + " where " + COLUMN_H_MA + " = ? ", new String[]{String.valueOf(_id)});
        try {
            while (cursor.moveToNext()) {
                h.setMaH(cursor.getInt(0));
                h.setTenH(cursor.getString(1));
            }
        } catch (Exception e) {
            Log.e("Get Category error: ", e.getMessage());
        } finally {
            if (cursor != null && !cursor.isClosed()) {
                cursor.close();
            }
        }
        cursor.close();
        return h;
    }

    public String checkTen(String tenH) {
        String tenHnh = "";
        sql = db.getReadableDatabase();
        Cursor cursor = sql.rawQuery(" select " + COLUMN_H_TEN + " from " + TABLE_HANG + " WHERE " + COLUMN_H_TEN + " = ?", new String[]{String.valueOf(tenH)});
        while (cursor.moveToNext()) {
            int a = cursor.getColumnIndex(COLUMN_H_TEN);
            tenHnh = cursor.getString(a);
        }
        cursor.close();
        return tenHnh;

    }

}
