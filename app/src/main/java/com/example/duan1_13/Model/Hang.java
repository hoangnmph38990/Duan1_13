package com.example.duan1_13.Model;

public class Hang {

    private int maH;
    private String tenH;
    private byte[] img;

    public Hang() {
    }

    public Hang(int maH, String tenH, byte[] img) {
        this.maH = maH;
        this.tenH = tenH;
        this.img = img;
    }

    public int getMaH() {
        return maH;
    }

    public void setMaH(int maH) {
        this.maH = maH;
    }

    public String getTenH() {
        return tenH;
    }

    public void setTenH(String tenH) {
        this.tenH = tenH;
    }

    public byte[] getImg() {
        return img;
    }

    public void setImg(byte[] img) {
        this.img = img;
    }
}
