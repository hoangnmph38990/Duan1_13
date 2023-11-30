package com.example.duan1_13.Model;

public class Top {

    private int MaNH;
    private int soLuong;

    public Top() {
    }

    public Top(int maNH, int soLuong) {
        MaNH = maNH;
        this.soLuong = soLuong;
    }

    public int getMaNH() {
        return MaNH;
    }

    public void setMaNH(int maNH) {
        MaNH = maNH;
    }

    public int getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(int soLuong) {
        this.soLuong = soLuong;
    }
}
