package com.example.duan1_13.Model;

public class Top {

    private int Madt;
    private int soLuong;

    public Top() {
    }

    public Top(int madt, int soLuong) {
        Madt = madt;
        this.soLuong = soLuong;
    }

    public int getMadt() {
        return Madt;
    }

    public void setMadt(int madt) {
        Madt = madt;
    }

    public int getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(int soLuong) {
        this.soLuong = soLuong;
    }
}
