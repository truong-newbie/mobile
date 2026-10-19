package com.example.project1;

public class ThoiTiet {
    private String kieuThoiTiet;
    private int icon;

    public ThoiTiet(String kieuThoiTiet, int icon) {
        this.kieuThoiTiet = kieuThoiTiet;
        this.icon = icon;
    }

    public String getKieuThoiTiet() {
        return kieuThoiTiet;
    }

    public void setKieuThoiTiet(String kieuThoiTiet) {
        this.kieuThoiTiet = kieuThoiTiet;
    }

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }
}
