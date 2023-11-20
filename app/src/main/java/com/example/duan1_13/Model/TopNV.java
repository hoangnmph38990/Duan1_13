package com.example.duan1_13.Model;

public class TopNV {

    private String manv;
    private Double tien;

    public TopNV() {
    }

    public TopNV(String manv, Double tien) {
        this.manv = manv;
        this.tien = tien;
    }

    public String getManv() {
        return manv;
    }

    public void setManv(String manv) {
        this.manv = manv;
    }

    public Double getTien() {
        return tien;
    }

    public void setTien(Double tien) {
        this.tien = tien;
    }
}
