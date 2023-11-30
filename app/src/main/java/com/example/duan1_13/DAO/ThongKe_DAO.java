package com.example.duan1_13.DAO;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.duan1_13.Database.Mydatabase;
import com.example.duan1_13.Model.PieChart;
import com.example.duan1_13.Model.Top;
import com.example.duan1_13.Model.TopNV;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class ThongKe_DAO {

    private SQLiteDatabase sql;
    private Mydatabase db;
    private Context context;
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM--dd");

    public ThongKe_DAO(Context context){
        this.context = context;
        db = new Mydatabase(context);
        sql = db.getWritableDatabase();
    }

    //top ban chay
    public List<Top> getTop(){
        List<Top> list = new ArrayList<>();
        String q = "select MaNH, sum(SoLuong) " +
                "as soLuong from HoaDonChiTiet group by MaNH " +
                "order by soLuong desc limit 10";
        SQLiteDatabase sdb = db.getReadableDatabase();
        Cursor cursor = sdb.rawQuery(q, null);
        cursor.moveToFirst();
        try {
            while (cursor.isAfterLast() == false){
                int a = cursor.getColumnIndex("MaNH");
                int b = cursor.getColumnIndex("soLuong");
                Log.d("TAG", "getTop: " + a + b);
                Top t = new Top();
                t.setMaNH(cursor.getInt(a));
                t.setSoLuong(Integer.parseInt(cursor.getString(b)));
                Log.d("TAG", "getTop: "+cursor.getString(b));
                list.add(t);
                cursor.moveToNext();
            }
        }catch (Exception e){
            Log.e("Get Category error: ", e.getMessage());
        }finally {
            if (cursor != null && !cursor.isClosed()){
                cursor.close();
            }
        }
        return list;
    }

    // Top nhân viên
    public List<TopNV> getTopNV(){
        List<TopNV> list = new ArrayList<>();
        String q = "select MaNV, sum(TongTienHD) " +
                "as tongTien from HoaDon group by MaNV " +
                "order by tongTien desc limit 10";
        SQLiteDatabase sdb = db.getReadableDatabase();
        Cursor cursor = sdb.rawQuery(q, null);
        cursor.moveToFirst();
        try {
            while (cursor.isAfterLast() == false){
                int a = cursor.getColumnIndex("MaNV");
                int b = cursor.getColumnIndex("tongTien");
                Log.d("TAG", "getTopNV: "+a+b);
                TopNV topNv = new TopNV();
                topNv.setManv(cursor.getString(a));
                topNv.setTien(cursor.getDouble(b));
                Log.d("TAG", "getTopTien: "+cursor.getString(b));
                list.add(topNv);
                cursor.moveToNext();
            }
        }catch (Exception e){
            Log.e("Get Category error: ", e.getMessage());
        }finally {
            if (cursor != null && !cursor.isClosed()){
                cursor.close();
            }
        }

        return list;
    }

    // Thống kê doanh thu
    public int getDoanhThu(String to, String from){
        String sqlNH = "select SUM(TongTienHD) " +
                "as doanhThu from HoaDon WHERE NgayMua between ? and ?";
        List<Integer> list = new ArrayList<Integer>();
        SQLiteDatabase sdb = db.getReadableDatabase();
        Cursor cursor = sdb.rawQuery(sqlNH, new String[]{to, from});
        cursor.moveToFirst();
        try {
            while (cursor.isAfterLast() == false){
                int b = cursor.getColumnIndex("doanhThu");
                int doanhThu = Integer.parseInt(cursor.getString(b));
                list.add(doanhThu);
                cursor.moveToNext();
            }
        }catch (Exception e){
            Log.e("Get Category error: ", e.getMessage());
            list.add(0);
        }finally {
            if (cursor != null && !cursor.isClosed()){
                cursor.close();
            }
        }

        return list.get(0);
    }

    public List<PieChart> getChart(){
        List<PieChart> list = new ArrayList<>();
        String sqlNH = "select MaNH, SUM(TongTien) " +
                "as doanhThu from HoaDonChiTiet  group by MaNH " +
                "order by doanhThu";
        Cursor cursor = sql.rawQuery(sqlNH, null);
        cursor.moveToFirst();
        try {
            while (cursor.isAfterLast() == false){
                PieChart pie = new PieChart();
                int a = cursor.getColumnIndex("doanhThu");
                Log.d("Doanh thu", "getChart: ");
                pie.PieTien = (int) cursor.getDouble(a);
                Log.d("Doanh thu", "getChart: "+cursor.getDouble(a));
                int b = cursor.getColumnIndex("MaNH");
                pie.PieMaNH = cursor.getInt(b);
                Log.d("MaNH", "getChart: "+cursor.getInt(b));
                list.add(pie);
                cursor.moveToNext();
            }
        }catch (Exception e){
            Log.e("Get Category error: ", e.getMessage());
        }finally {
            if (cursor != null && !cursor.isClosed()){
                cursor.close();
            }
        }
        return list;
    }
    public List<PieChart> getChart2(String to, String from){
        List<PieChart> list = new ArrayList<>();
        String sqlNH = "select MaNH, SUM(TongTien) " +
                "as doanhThu from HoaDonChiTiet join HoaDon on HoaDonChiTiet.MaHD = HoaDon.MaHoaDon" +
                " where NgayMua " +
                "between ? and ? group by MaNH " +
                "order by doanhThu";
        Cursor cursor = sql.rawQuery(sqlNH, new String[]{to, from});
        cursor.moveToFirst();
        try {
            while (cursor.isAfterLast() == false){
                PieChart pie = new PieChart();
                int a = cursor.getColumnIndex("doanhThu");
                pie.PieTien = (int) cursor.getDouble(a);
                Log.d("Doanh thu2", "getChart: "+cursor.getDouble(a));
                int b = cursor.getColumnIndex("MaNH");
                pie.PieMaNH = cursor.getInt(b);
                Log.d("MaNH2", "getChart: "+cursor.getInt(b));
                list.add(pie);
                cursor.moveToNext();
            }
        }catch (Exception e){
            Log.e("Get Category error: ", e.getMessage());
        }finally {
            if (cursor != null && !cursor.isClosed()){
                cursor.close();
            }
        }
        return list;
    }
}
