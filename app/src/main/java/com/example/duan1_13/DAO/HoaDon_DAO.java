package com.example.duan1_13.DAO;

import static com.example.duan1_13.Helper.Content.COLUMN_HD_NV_MA;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import static com.example.duan1_13.Helper.Content.*;

import com.example.duan1_13.Database.Mydatabase;
import com.example.duan1_13.Model.HoaDon;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class HoaDon_DAO {
    private SQLiteDatabase sql;
    private Mydatabase db;
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    int _maHD, _maNV, _tenKH, _ngay, _tien;

    public HoaDon_DAO(Context context) {
        db = new Mydatabase(context);
        sql = db.getWritableDatabase();
    }

    public long insert(HoaDon hd) {
        ContentValues values = new ContentValues();

        values.put(COLUMN_HD_NV_MA, hd.getMaNV());
        values.put(COLUMN_HD_KH, hd.getTenKH());
        values.put(COLUMN_HD_DATE, sdf.format(hd.getNgay()));
        values.put(COLUMN_HD_TIEN, hd.getTienTong());

        return sql.insert(TABLE_HD, null, values);
    }

    public int delete(String id) {
        return sql.delete(TABLE_HD,
                COLUMN_HD_MA + " = ? ",
                new String[]{String.valueOf(id)});
    }

    public int update(HoaDon hd) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_HD_MA, hd.getMaHD());
        values.put(COLUMN_HD_NV_MA, hd.getMaNV());
        values.put(COLUMN_HD_KH, hd.getTenKH());
        values.put(COLUMN_HD_DATE, sdf.format(hd.getNgay()));
        values.put(COLUMN_HD_TIEN, hd.getTienTong());

        return sql.update(TABLE_HD, values,
                COLUMN_HD_MA + " = ? ",
                new String[]{String.valueOf(hd.getMaHD())});
    }

    public int updateH(Double gia, int MaHD) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_HD_MA, MaHD);
        values.put(COLUMN_HD_TIEN, gia);

        return sql.update(TABLE_HD, values,
                COLUMN_HD_MA + " = ? ",
                new String[]{String.valueOf(MaHD)});
    }

    public List<HoaDon> getAll() {
        String sql = "select * from " + TABLE_HD;
        return getData(sql);
    }

    public HoaDon getID(String id) {
        String sql = "select * from " + TABLE_HD + " where " + COLUMN_HD_MA + " = ? ";
        List<HoaDon> list = getData(sql, id);
        return list.get(0);
    }

    public List<HoaDon> getData(String _sql, String... selectionArgs) {

        List<HoaDon> list = new ArrayList<>();
        Cursor cursor = sql.rawQuery(_sql, selectionArgs);
        cursor.moveToFirst();
        try {
            while (cursor.isAfterLast() == false) {
                column(cursor);
                int MaHD = cursor.getInt(_maHD);
                String MaNV = cursor.getString(_maNV);
                String tenKH = cursor.getString(_tenKH);
                Date ngay = sdf.parse(cursor.getString(_ngay));
                Double tienTong = cursor.getDouble(_tien);

                HoaDon hd = new HoaDon(MaHD, MaNV, tenKH, ngay, tienTong);
                list.add(hd);
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

    public void column(Cursor cursor) {
        _maHD = cursor.getColumnIndex(COLUMN_HD_MA);
        _maNV = cursor.getColumnIndex(COLUMN_HD_NV_MA);
        _tenKH = cursor.getColumnIndex(COLUMN_HD_KH);
        _ngay = cursor.getColumnIndex(COLUMN_HD_DATE);
        _tien = cursor.getColumnIndex(COLUMN_HD_TIEN);
    }

}
