package com.example.duan1_13.Model;

public class NhanVien {
    public String MaNV;
    public String Password;
    public String HoTen;
    public String Email;
    public byte[] HinhAnh;

    public NhanVien() {
    }

    public NhanVien(String maNV, String password, String hoTen, String email, byte[] hinhAnh) {
        MaNV = maNV;
        Password = password;
        HoTen = hoTen;
        Email = email;
        HinhAnh = hinhAnh;
    }

    public String getMaNV() {
        return MaNV;
    }

    public void setMaNV(String maNV) {
        MaNV = maNV;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public String getHoTen() {
        return HoTen;
    }

    public void setHoTen(String hoTen) {
        HoTen = hoTen;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public byte[] getHinhAnh() {
        return HinhAnh;
    }

    public void setHinhAnh(byte[] hinhAnh) {
        HinhAnh = hinhAnh;
    }
}
