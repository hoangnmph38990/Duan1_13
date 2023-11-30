package com.example.duan1_13.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import static com.example.duan1_13.Helper.Content.*;

import com.example.duan1_13.Database.Mydatabase;
import com.example.duan1_13.Model.NuocHoa;

import java.util.ArrayList;
import java.util.List;

public class NuocHoa_DAO {
    private int _maNH, _maH, _tenNH, _gia, _soLuong, _img;

    private SQLiteDatabase sql;
    private Mydatabase db;

    public NuocHoa_DAO(Context context) {
        db = new Mydatabase(context);
        sql = db.getWritableDatabase();
    }

    public long insert(NuocHoa nh) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_NH_HANG_MA, nh.getMaNH());
        values.put(COLUMN_NH_TEN, nh.getTenNH());
        values.put(COLUMN_NH_GIA, nh.getGia());
        values.put(COLUMN_NH_SL, nh.getSoLuong());
        values.put(COLUMN_NH_HA, nh.getImg());
        return sql.insert(TABLE_NH, null, values);
    }

    public int update(NuocHoa nh) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_NH_MA, nh.getMaNH());
        values.put(COLUMN_NH_HANG_MA, nh.getMaH_NH());
        values.put(COLUMN_NH_TEN, nh.getTenNH());
        values.put(COLUMN_NH_GIA, nh.getGia());
        values.put(COLUMN_NH_SL, nh.getSoLuong());
        values.put(COLUMN_NH_HA, nh.getImg());
        return sql.update(TABLE_NH, values,
                COLUMN_NH_MA + " = ? ",
                new String[]{String.valueOf(nh.getMaNH())});
    }

    public int delete(String id) {
        return sql.delete(TABLE_NH,
                COLUMN_NH_MA + " = ? ",
                new String[]{String.valueOf(id)});
    }

    public List<NuocHoa> getAll() {
        String sql = "select * from " + TABLE_NH;
        return getData(sql);
    }

    public NuocHoa getID(String id) {
        String sql = "select * from " + TABLE_NH + " where " + COLUMN_NH_MA + " = ? ";
        List<NuocHoa> list = getData(sql, id);
        return list.get(0);
    }

    public List<NuocHoa> getData(String _sql, String... selectionArgs) {
        List<NuocHoa> list = new ArrayList<>();
        Cursor cursor = sql.rawQuery(_sql, selectionArgs);
        cursor.moveToFirst();
        try {
            while (cursor.isAfterLast() == false) {
                column(cursor);
                int id = cursor.getInt(_maNH);
                int idH = cursor.getInt(_maH);
                String ten = cursor.getString(_tenNH);
                int soLuong = cursor.getInt(_soLuong);
                Double gia = cursor.getDouble(_gia);
                byte[] img = cursor.getBlob(_img);
                NuocHoa nh = new NuocHoa(id, ten, idH, soLuong, gia, img);
                list.add(nh);
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

    public NuocHoa getByID(String _id) {
        NuocHoa nuocHoa = null;
        sql = db.getReadableDatabase();
        Cursor cursor = sql.rawQuery("select * from " + TABLE_NH + " WHERE " + COLUMN_NH_HANG_MA + " = ?", new String[]{String.valueOf(_id)});
        while (cursor.moveToNext()) {
            column(cursor);
            nuocHoa = new NuocHoa();
            nuocHoa.setMaNH(cursor.getInt(_maNH));
            nuocHoa.setMaH_NH(cursor.getInt(_maH));
            nuocHoa.setTenNH(cursor.getString(_tenNH));
            Log.d("NHDAO", "getByID: " + cursor.getString(_tenNH));
            nuocHoa.setSoLuong(cursor.getInt(_soLuong));
            nuocHoa.setGia(cursor.getDouble(_gia));
            nuocHoa.setImg(cursor.getBlob(_img));

        }
        cursor.close();
        return nuocHoa;
    }

    public String checkTen(String tenNH) {
        String tennh = "";
        sql = db.getReadableDatabase();
        Cursor cursor = sql.rawQuery(" select " + COLUMN_NH_TEN + " from " + TABLE_NH + " WHERE " + COLUMN_NH_TEN + " = ?", new String[]{String.valueOf(tenNH)});
        while (cursor.moveToNext()) {
            column(cursor);
            tennh = cursor.getString(_tenNH);
        }
        cursor.close();
        return tennh;

    }

    public void column(Cursor cursor) {

        _maNH = cursor.getColumnIndex(COLUMN_NH_MA);
        _maH = cursor.getColumnIndex(COLUMN_NH_HANG_MA);
        _tenNH = cursor.getColumnIndex(COLUMN_NH_TEN);
        _gia = cursor.getColumnIndex(COLUMN_NH_GIA);
        _soLuong = cursor.getColumnIndex(COLUMN_NH_SL);
        _img = cursor.getColumnIndex(COLUMN_NH_HA);
        Log.d("STT của Ảnh: ", "" + _img);

    }
}
