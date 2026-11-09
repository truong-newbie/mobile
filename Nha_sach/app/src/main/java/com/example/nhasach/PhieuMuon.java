package com.example.nhasach;

import java.io.Serializable;

public class PhieuMuon implements Serializable {
    private String maSach;
    private String nhaXB;
    private String loaiSach; // Ví dụ: "Tham khảo", "SGK", hoặc "Tham khảo, SGK"
    private String chuDe;    // "Toán" hoặc "Tiếng Việt"

    public PhieuMuon() {
    }

    public PhieuMuon(String maSach, String nhaXB, String loaiSach, String chuDe) {
        this.maSach = maSach;
        this.nhaXB = nhaXB;
        this.loaiSach = loaiSach;
        this.chuDe = chuDe;
    }

    public String getMaSach() {
        return maSach;
    }

    public void setMaSach(String maSach) {
        this.maSach = maSach;
    }

    public String getNhaXB() {
        return nhaXB;
    }

    public void setNhaXB(String nhaXB) {
        this.nhaXB = nhaXB;
    }

    public String getLoaiSach() {
        return loaiSach;
    }

    public void setLoaiSach(String loaiSach) {
        this.loaiSach = loaiSach;
    }

    public String getChuDe() {
        return chuDe;
    }

    public void setChuDe(String chuDe) {
        this.chuDe = chuDe;
    }
}
