package com.example.duan1_13.Model;

public class NuocHoa {
    private int maNH;
    private String tenNH;
    private int maH_NH;
    private int soLuong;
    private Double gia;
    private byte[] img;

    public NuocHoa() {
    }

    public NuocHoa(int maNH, String tenNH, int maH_NH, int soLuong, Double gia, byte[] img) {
        this.maNH = maNH;
        this.tenNH = tenNH;
        this.maH_NH = maH_NH;
        this.soLuong = soLuong;
        this.gia = gia;
        this.img = img;
    }

    public int getMaNH() {
        return maNH;
    }

    public void setMaNH(int maNH) {
        this.maNH = maNH;
    }

    public String getTenNH() {
        return tenNH;
    }

    public void setTenNH(String tenNH) {
        this.tenNH = tenNH;
    }

    public int getMaH_NH() {
        return maH_NH;
    }

    public void setMaH_NH(int maH_NH) {
        this.maH_NH = maH_NH;
    }

    public int getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(int soLuong) {
        this.soLuong = soLuong;
    }

    public Double getGia() {
        return gia;
    }

    public void setGia(Double gia) {
        this.gia = gia;
    }

    public byte[] getImg() {
        return img;
    }

    public void setImg(byte[] img) {
        this.img = img;
    }
}
