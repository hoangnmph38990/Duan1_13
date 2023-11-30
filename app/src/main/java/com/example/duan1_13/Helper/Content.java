package com.example.duan1_13.Helper;

public class Content {

    public static final String DB_NAME = "UNG DUNG NUOC HOA";
    public static final int DB_VERSION = 2;


    public static final String TABLE_NV = "User";
    public static final String COLUMN_NV_MA = "MaNV";
    public static final String COLUMN_NV_PASS = "Password";
    public static final String COLUMN_NV_HOTEN = "HoTen";
    public static final String COLUMN_NV_EMAIL = "Email";
    public static final String COLUMN_NV_HA = "HinhAnh";

    public static final String TABLE_HANG ="HangNH";
    public static final String COLUMN_H_MA = "MaHang";
    public static final String COLUMN_H_TEN = "TenHang";
    public static final String COLUMN_H_HA = "HinhAnh";

    public static final String TABLE_NH = "NuocHoa";
    public static final String COLUMN_NH_MA = "MaNH";
    public static final String COLUMN_NH_HANG_MA = "MaHang";
    public static final String COLUMN_NH_TEN = "TenNuocHoa";
    public static final String COLUMN_NH_SL = "SoLuong";
    public static final String COLUMN_NH_GIA = "GiaTien";
    public static final String COLUMN_NH_HA = "HinhAnh";

    public static final String TABLE_HD = "HoaDon";
    public static final String COLUMN_HD_MA = "MaHoaDon";
    public static final String COLUMN_HD_NV_MA = "MaNV";
    public static final String COLUMN_HD_KH = "TenKhachHang";
    public static final String COLUMN_HD_DATE = "NgayMua";
    public static final String COLUMN_HD_TIEN = "TongTienHD";

    public static final String TABLE_HDCT = "HoaDonChiTiet";
    public static final String COLUMN_HDCT_MA = "MaChiTiet";
    public static final String COLUMN_HDCT_HD_MA = "MaHD";
    public static final String COLUMN_HDCT_NH_MA = "MaNH";
    public static final String COLUMN_HDCT_SL = "SoLuong";
    public static final String COLUMN_HDCT_TIEN = "TongTien";

}
