package com.example.duan1_13.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import static com.example.duan1_13.Helper.Content.*;

import com.example.duan1_13.Database.Mydatabase;
import com.example.duan1_13.Model.HoaDonCT;

import java.util.ArrayList;
import java.util.List;

public class HoaDonCt_DAO {
    private SQLiteDatabase sql;
    private Mydatabase db;
    int _maCT, _maHD, _maNH, _soLuong, _tien;

    public HoaDonCt_DAO(Context context) {
        db = new Mydatabase(context);
        sql = db.getWritableDatabase();
    }

    public long insert(HoaDonCT ct) {
        ContentValues values = new ContentValues();

        values.put(COLUMN_HDCT_HD_MA, ct.getMaHD());
        values.put(COLUMN_HDCT_NH_MA, ct.getMaNH());
        values.put(COLUMN_HDCT_SL, ct.getSoLuong());
        values.put(COLUMN_HDCT_TIEN, ct.getThanhTien());

        return sql.insert(TABLE_HDCT, null, values);
    }

    public int delete(String id) {
        return sql.delete(TABLE_HDCT,
                COLUMN_HDCT_MA + " = ? ",
                new String[]{String.valueOf(id)});
    }

    public int update(HoaDonCT ct) {
        ContentValues values = new ContentValues();

        values.put(COLUMN_HDCT_MA, ct.getMaCT());
        values.put(COLUMN_HDCT_HD_MA, ct.getMaHD());
        values.put(COLUMN_HDCT_NH_MA, ct.getMaNH());
        values.put(COLUMN_HDCT_SL, ct.getSoLuong());
        values.put(COLUMN_HDCT_TIEN, ct.getThanhTien());

        return sql.update(TABLE_HDCT, values,
                COLUMN_HDCT_MA + " = ? ",
                new String[]{String.valueOf(ct.getMaCT())});
    }

    public int updateSL(int MaHdct, int sl, Double gia, int MaHD) {
        ContentValues values = new ContentValues();

        values.put(COLUMN_HDCT_MA, MaHdct);
        values.put(COLUMN_HDCT_SL, sl);
        values.put(COLUMN_HDCT_TIEN, gia);

        return sql.update(TABLE_HDCT, values,
                COLUMN_HDCT_MA + " = ? " + " and " + COLUMN_HDCT_HD_MA + " = ? ",
                new String[]{String.valueOf(MaHdct), String.valueOf(MaHD)});
    }

    public List<HoaDonCT> getAllHD(String maHD) {
        String sql = "select * from " + TABLE_HDCT + " where " + COLUMN_HDCT_HD_MA + " = ? ";
        return getData(sql, maHD);
    }

    public HoaDonCT getID(String id) {
        String sql = "select * from " + TABLE_HDCT + " where " + COLUMN_HDCT_MA + " = ? ";
        List<HoaDonCT> list = getData(sql, id);
        return list.get(0);
    }

    public Double getIdTien(String id) {
        String sql = "select * from " + TABLE_HDCT + " where " + COLUMN_HDCT_MA + " = ? ";
        List<HoaDonCT> list = getData(sql, id);
        return list.get(0).getThanhTien();
    }

    public int checkHdct(String _id) {
        int a = -1;
        sql = db.getReadableDatabase();
        Cursor cursor = sql.rawQuery("select * from " + TABLE_HDCT + " where " + COLUMN_HDCT_NH_MA + " = ? ", new String[]{String.valueOf(_id)});
        try {
            while (cursor.moveToNext()) {
                a = cursor.getInt(_maNH);
            }
        } catch (Exception e) {
            Log.e("Get Category error: ", e.getMessage());
        } finally {
            if (cursor != null && !cursor.isClosed()) {
                cursor.close();
            }
        }
        cursor.close();
        return a;
    }

    public HoaDonCT getIdAll(String _id) {
        HoaDonCT hdct = new HoaDonCT();
        sql = db.getReadableDatabase();
        Cursor cursor = sql.rawQuery("select * from " + TABLE_HDCT + " where " + COLUMN_HDCT_MA + " = ? ", new String[]{String.valueOf(_id)});
        try {
            while (cursor.moveToNext()) {
                hdct.setMaCT(cursor.getInt(_maCT));
                hdct.setMaHD(cursor.getInt(_maHD));
                hdct.setMaNH(cursor.getInt(_maNH));
                hdct.setThanhTien(cursor.getDouble(_tien));
                hdct.setSoLuong(cursor.getInt(_soLuong));
            }
        } catch (Exception e) {
            Log.e("Get Category error: ", e.getMessage());
        } finally {
            if (cursor != null && !cursor.isClosed()) {
                cursor.close();
            }
        }
        cursor.close();
        return hdct;
    }

    public int getByIdNH(String _id, String _idHD) {
        int MaNHHDCT = -1;
        sql = db.getReadableDatabase();
        Cursor cursor = sql.rawQuery("select " + COLUMN_HDCT_MA +
                " from " + TABLE_HDCT + " where " + COLUMN_HDCT_NH_MA + " = ? " + " and " +
                COLUMN_HDCT_HD_MA + " = ? ", new String[]{String.valueOf(_id), String.valueOf(_idHD)});
        try {
            while (cursor.moveToNext()) {
                MaNHHDCT = cursor.getInt(0);
            }
        } catch (Exception e) {
            Log.e("Get Category error: ", e.getMessage());
        } finally {
            if (cursor != null && !cursor.isClosed()) {
                cursor.close();
            }
        }
        cursor.close();
        return MaNHHDCT;
    }

    public List<HoaDonCT> getData(String _sql, String... selectionArgs) {

        List<HoaDonCT> list = new ArrayList<>();
        Cursor cursor = sql.rawQuery(_sql, selectionArgs);
        cursor.moveToFirst();
        try {
            while (cursor.isAfterLast() == false) {
                column(cursor);
                int MaCT = cursor.getInt(_maCT);
                int MaHD = cursor.getInt(_maHD);
                int MaNH = cursor.getInt(_maNH);
                int SoLuong = cursor.getInt(_soLuong);
                Double tienTong = cursor.getDouble(_tien);

                HoaDonCT hdct = new HoaDonCT(MaCT, MaHD, MaNH, SoLuong, tienTong);
                list.add(hdct);
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

    public int getTongTien(int id) {
        String sqlNH = "select SUM(TongTien) " +
                "as doanhThu from HoaDonChiTiet WHERE MaHD = ?";
        List<Integer> list = new ArrayList<Integer>();
        SQLiteDatabase sdb = db.getReadableDatabase();
        Cursor cursor = sdb.rawQuery(sqlNH, new String[]{String.valueOf(id)});
        cursor.moveToFirst();
        try {
            while (cursor.isAfterLast() == false) {
                int b = cursor.getColumnIndex("doanhThu");
                int doanhThu = Integer.parseInt(cursor.getString(b));
                list.add(doanhThu);
                cursor.moveToNext();
            }
        } catch (Exception e) {
            Log.e("Get Category error: ", e.getMessage());
            list.add(0);
        } finally {
            if (cursor != null && !cursor.isClosed()) {
                cursor.close();
            }
        }

        return list.get(0);
    }

    public int deleteHD(String id) {
        return sql.delete(TABLE_HDCT,
                COLUMN_HDCT_HD_MA + " = ? ",
                new String[]{String.valueOf(id)});
    }

    public void column(Cursor cursor) {
        _maCT = cursor.getColumnIndex(COLUMN_HDCT_MA);
        _maHD = cursor.getColumnIndex(COLUMN_HDCT_HD_MA);
        _maNH = cursor.getColumnIndex(COLUMN_HDCT_NH_MA);
        _soLuong = cursor.getColumnIndex(COLUMN_HDCT_SL);
        _tien = cursor.getColumnIndex(COLUMN_HDCT_TIEN);
    }


}
