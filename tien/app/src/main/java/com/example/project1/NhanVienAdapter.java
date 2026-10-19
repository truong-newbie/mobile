package com.example.project1;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class NhanVienAdapter extends ArrayAdapter<NhanVien> {
    private Context context;
    private ArrayList<NhanVien> dsNhanVien;

    public NhanVienAdapter(Context context,ArrayList<NhanVien> dsNhanVien)
    {
        super(context,R.layout.item_nhan_vien,dsNhanVien);
        this.context = context;
        this.dsNhanVien = dsNhanVien;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // 1. Tái sử dụng view nếu có thể
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_nhan_vien, parent, false);
        }

        // 2. Ánh xạ các TextView trong file item_nhan_vien.xml
        TextView txtMa = convertView.findViewById(R.id.txtMa);
        TextView txtTen = convertView.findViewById(R.id.txtTen);
        TextView txtLCB = convertView.findViewById(R.id.txtLCB);
        TextView txtPhongBan = convertView.findViewById(R.id.txtPhongBan);
        ImageView imgIcon = convertView.findViewById(R.id.ImgIcon);

        // 3. Lấy đối tượng NhanVien tại vị trí hiện tại
        NhanVien nv = dsNhanVien.get(position);

        // 4. QUAN TRỌNG: Gán dữ liệu cho TẤT CẢ các TextView (phải nằm ngoài khối if ở trên)
        imgIcon.setImageResource(nv.getTt().getIcon());
        txtMa.setText("Mã: " + nv.getMaNV());
        txtTen.setText("Họ tên: " + nv.getHoTen());
        txtLCB.setText("Lương: " + nv.getLuongCoBan());
        txtPhongBan.setText("Phòng ban: " + nv.getPhongBan());

        return convertView;
    }
}
