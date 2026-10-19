package com.hiennd325.ontap1;

public class SanPham {
    public String ma, ten, loai;
    public long gia;

    public SanPham(String ma, String ten, long gia, String loai) {
        this.ma = ma;
        this.ten = ten;
        this.gia = gia;
        this.loai = loai;
    }

    @Override
    public String toString() {
        return "Mã: " + ma + "\nTên: " + ten + "\nGiá: " + gia + "\nLoại: " + loai;
    }
}
