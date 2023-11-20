package com.example.duan1_13.Model;

public class HoaDonCT {

    private int maCT;
    private int maHD;
    private int maNH;
    private int soLuong;
    private Double thanhTien;

    public HoaDonCT() {
    }

    public HoaDonCT(int maCT, int maHD, int maNH, int soLuong, Double thanhTien) {
        this.maCT = maCT;
        this.maHD = maHD;
        this.maNH = maNH;
        this.soLuong = soLuong;
        this.thanhTien = thanhTien;
    }

    public int getMaCT() {
        return maCT;
    }

    public void setMaCT(int maCT) {
        this.maCT = maCT;
    }

    public int getMaHD() {
        return maHD;
    }

    public void setMaHD(int maHD) {
        this.maHD = maHD;
    }

    public int getMaNH() {
        return maNH;
    }

    public void setMaNH(int maNH) {
        this.maNH = maNH;
    }

    public int getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(int soLuong) {
        this.soLuong = soLuong;
    }

    public Double getThanhTien() {
        return thanhTien;
    }

    public void setThanhTien(Double thanhTien) {
        this.thanhTien = thanhTien;
    }
}
