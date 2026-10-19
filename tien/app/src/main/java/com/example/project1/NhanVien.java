package com.example.project1;

public class NhanVien {
    private String maNV,hoTen,phongBan;
    private double luongCoBan;
    private ThoiTiet tt;

    public NhanVien(String maNV, String hoTen, String phongBan, double luongCoBan,ThoiTiet tt) {
        this.maNV = maNV;
        this.hoTen = hoTen;
        this.phongBan = phongBan;
        this.luongCoBan = luongCoBan;
        this.tt = tt;
    }

    public String getHoTen() {
        return hoTen;
    }

    public void setHoTen(String hoTen) {
        this.hoTen = hoTen;
    }

    public String getPhongBan() {
        return phongBan;
    }

    public void setPhongBan(String phongBan) {
        this.phongBan = phongBan;
    }

    public String getMaNV() {
        return maNV;
    }

    public void setMaNV(String maNV) {
        this.maNV = maNV;
    }

    public ThoiTiet getTt() {
        return tt;
    }

    public void setTt(ThoiTiet tt) {
        this.tt = tt;
    }

    public double getLuongCoBan() {
        return luongCoBan;
    }

    public void setLuongCoBan(double luongCoBan) {
        this.luongCoBan = luongCoBan;
    }
}
