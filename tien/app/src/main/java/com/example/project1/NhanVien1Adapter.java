package com.example.project1;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import java.util.ArrayList;

public class NhanVien1Adapter extends ArrayAdapter<NhanVien> {
    private Context context;
    private ArrayList<NhanVien> dsNhanVien;


    public NhanVien1Adapter(Context context, ArrayList<NhanVien> dsNhanVien) {
        super(context,R.layout.item_nhan_vien, dsNhanVien);
        this.context = context;
        this.dsNhanVien = dsNhanVien;
    }
    @Override

    public View getView(int position,View convertView,ViewGroup parent)
    {
        if(convertView == null)
        {
           convertView = LayoutInflater.from(context).inflate(R.layout.item_nhan_vien,parent,false);
        }
        // ánh xạ đến giao diện
        TextView txtMa = convertView.findViewById(R.id.txtMa);
        TextView txtHoTen = convertView.findViewById(R.id.txtTen);
        TextView txtLuong = convertView.findViewById(R.id.txtLCB);
        TextView txtPhongBan = convertView.findViewById(R.id.txtPhongBan);
        ImageView ingIcon = convertView.findViewById(R.id.ImgIcon);
        // lấy ra phần tử hiện tại
        NhanVien nv = dsNhanVien.get(position);
        // hiển thị dữ liệu lên giao diện
        txtMa.setText(nv.getMaNV());
        txtHoTen.setText(nv.getHoTen());
        txtLuong.setText(Double.toString(nv.getLuongCoBan()));
        txtPhongBan.setText(nv.getPhongBan());
        ingIcon.setImageResource(nv.getTt().getIcon());
        return convertView;
    }
}
