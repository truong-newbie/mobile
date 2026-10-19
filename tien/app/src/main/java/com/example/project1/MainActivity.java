package com.example.project1;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.project1.R;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private EditText edtMa,edtHoTen,edtLuongCoBan;
    private Spinner spinnerPhongBan,spinnerThoiTiet1;
    private Button btnThem,btnXoa,btnThongKe;
    private ListView listViewThongTin;
    private ArrayList<NhanVien> dsNhanVien;
    private ArrayAdapter<NhanVien> adapter;

    private int SelectedPosition = -1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // ánh xạ các biến hiển thị
        edtMa = findViewById(R.id.edtMa);
        edtHoTen = findViewById(R.id.edtHoTen);
        edtLuongCoBan = findViewById(R.id.edtLuongCoBan);
        spinnerPhongBan = findViewById(R.id.spinnerPhongBan);
        spinnerThoiTiet1 = findViewById(R.id.spinnerThoiTiet1);
        btnThem = findViewById(R.id.btnThem);
        btnXoa  = findViewById(R.id.btnXoa);
        btnThongKe = findViewById(R.id.btnThongKe);
        listViewThongTin = findViewById(R.id.listViewThongTin);

        // khởi tạo danh danh sách và adapter
        dsNhanVien = new ArrayList<>();
        adapter = new NhanVienAdapter(this,dsNhanVien);
        listViewThongTin.setAdapter(adapter);
        // xử lý sự kiện Thêm
        btnThem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String ma = edtMa.getText().toString().trim();
                String hoTen = edtHoTen.getText().toString().trim();
                Double luongCoBan = Double.parseDouble(edtLuongCoBan.getText().toString().trim());
                String phongBan = spinnerPhongBan.getSelectedItem().toString().trim();
                String kieuThoiTiet = spinnerThoiTiet1.getSelectedItem().toString().trim();
                int icon = getIconThoiTiet(kieuThoiTiet);
                ThoiTiet tt = new ThoiTiet(kieuThoiTiet,icon);
                NhanVien nv = new NhanVien(ma,hoTen, phongBan,luongCoBan,tt);
                dsNhanVien.add(nv);
                clearInfo();
                adapter.notifyDataSetChanged();
                Toast.makeText(MainActivity.this,"Thêm thành công",Toast.LENGTH_SHORT).show();
            }
        });
        // xử lý sự kiện nhấn item trong list view hiển thị lại dữ liệu vào trường dữ liệu nhập
        listViewThongTin.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                SelectedPosition = position;
                NhanVien nv = dsNhanVien.get(position);

                edtMa.setText(nv.getMaNV().trim());
                edtHoTen.setText(nv.getHoTen().trim());
                edtLuongCoBan.setText(Double.toString(nv.getLuongCoBan()).trim());
                for(int i = 0;i < spinnerPhongBan.getCount();i++)
                {
                    if (nv.getPhongBan().equals(spinnerPhongBan.getItemAtPosition(i).toString().trim()))
                    {
                        spinnerPhongBan.setSelection(i);
                        break;
                    }
                }


            }
        });
        // xử lý sự kiện xóa
        btnXoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(SelectedPosition != -1 && SelectedPosition < dsNhanVien.size())
                {
                    dsNhanVien.remove(SelectedPosition);
                    clearInfo();
                    adapter.notifyDataSetChanged();
                    Toast.makeText(MainActivity.this,"Xóa thành công",Toast.LENGTH_SHORT).show();

                }else
                {
                    Toast.makeText(MainActivity.this,"Vui lòng chọn nhân viên cần xóa",Toast.LENGTH_SHORT).show();
                }
            }
        });

        // xứ lý sự kiện thống kê
        btnThongKe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               if (dsNhanVien.isEmpty())
               {
                   Toast.makeText(MainActivity.this,"Danh sách trống",Toast.LENGTH_SHORT).show();
               }else
               {
                   int tongSo = dsNhanVien.size();
                   double minLuong = Double.MAX_VALUE;
                   for (NhanVien item : dsNhanVien)
                   {
                       if (minLuong > item.getLuongCoBan())
                       {
                           minLuong  = item.getLuongCoBan();
                       }
                   }
                   String msg = "Tổng số nhân viên" + tongSo +"\nLương thấp nhất là:" + minLuong;
                   new AlertDialog.Builder(MainActivity.this)
                           .setTitle("Thống kê")
                           .setMessage(msg)
                           .setPositiveButton("Đóng",(d,w)->d.dismiss())
                           .show();
               }
          }


        });
    }
    public void clearInfo()
    {
        edtMa.setText("");
        edtHoTen.setText("");
        edtLuongCoBan.setText("");
        spinnerPhongBan.setSelection(0);
        SelectedPosition = -1;
    }
    public int getIconThoiTiet(String kieuThoiTiet)
    {
        if (kieuThoiTiet.equals("Nắng nhẹ"))
        {
            return R.drawable.w1;
        }else if (kieuThoiTiet.equals("Mưa"))
        {
            return R.drawable.w2;
        }else
        {
            return R.drawable.w3;
        }
    }
}