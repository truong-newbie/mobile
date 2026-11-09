package com.example.nhasach;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class DanhSachActivity extends AppCompatActivity {

    private ListView lvPhieuMuon;
    private ArrayList<PhieuMuon> listPhieuMuon;
    private PhieuMuonAdapter adapter;
    private int selectedPosition = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_danh_sach);

        lvPhieuMuon = findViewById(R.id.lvPhieuMuon);

        // Nhận dữ liệu truyền từ GD1 qua Explicit Intent (Bundle)
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        if (bundle != null && bundle.containsKey("danhSach")) {
            listPhieuMuon = (ArrayList<PhieuMuon>) bundle.getSerializable("danhSach");
        }

        if (listPhieuMuon == null) {
            listPhieuMuon = new ArrayList<>();
        }

        // Khởi tạo Custom Adapter cho ListView
        adapter = new PhieuMuonAdapter(this, R.layout.item_phieu_muon, listPhieuMuon);
        lvPhieuMuon.setAdapter(adapter);

        // Bắt sự kiện chọn 1 dòng trong ListView
        lvPhieuMuon.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selectedPosition = position;
                adapter.setSelectedPosition(position);
                Toast.makeText(DanhSachActivity.this, "Đã chọn mã sách: " + listPhieuMuon.get(position).getMaSach(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    // Khởi tạo Option Menu GD2 (Câu 1)
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_gd2, menu);
        return true;
    }

    // Xử lý các mục trong Option Menu GD2 (Câu 2c, 2d, Câu 3)
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.menu_xoa) {
            xuLyXoa();
            return true;
        } else if (id == R.id.menu_sua) {
            xuLySua();
            return true;
        } else if (id == R.id.menu_thong_ke) {
            xuLyThongKeVaSMS();
            return true;
        } else if (id == R.id.menu_thoat_gd2) {
            hienThiDialogThoat();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * Câu 2c: Xóa tại GD2
     * Chọn 1 dòng -> Xóa khỏi danh sách
     */
    private void xuLyXoa() {
        if (selectedPosition == -1 || selectedPosition >= listPhieuMuon.size()) {
            Toast.makeText(this, "Vui lòng chọn 1 dòng trên danh sách để xóa!", Toast.LENGTH_SHORT).show();
            return;
        }

        String maXoa = listPhieuMuon.get(selectedPosition).getMaSach();
        listPhieuMuon.remove(selectedPosition);
        selectedPosition = -1;
        adapter.setSelectedPosition(-1);
        adapter.notifyDataSetChanged();

        Toast.makeText(this, "Đã xóa phiếu mượn mã: " + maXoa, Toast.LENGTH_SHORT).show();
    }

    /**
     * Câu 2c: Sửa tại GD2
     * Chọn 1 dòng -> Sửa -> gửi Intent ngược về GD1 đổ lại dữ liệu lên form để cập nhật.
     */
    private void xuLySua() {
        if (selectedPosition == -1 || selectedPosition >= listPhieuMuon.size()) {
            Toast.makeText(this, "Vui lòng chọn 1 dòng trên danh sách để sửa!", Toast.LENGTH_SHORT).show();
            return;
        }

        PhieuMuon phieuMuonCanSua = listPhieuMuon.get(selectedPosition);

        // Tạo Intent gửi ngược về GD1 (MainActivity)
        Intent intent = new Intent(DanhSachActivity.this, MainActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("phieuMuonSua", phieuMuonCanSua);
        bundle.putInt("viTriSua", selectedPosition);
        bundle.putSerializable("danhSach", listPhieuMuon);
        intent.putExtras(bundle);

        startActivity(intent);
        finish(); // Đóng GD2 để quay về form cập nhật ở GD1
    }

    /**
     * Câu 2d: Thống kê & SMS
     * Thống kê số lượng theo từng Loại sách -> Dùng Implicit Intent mở giao diện SMS gửi báo cáo tới đầu số 222
     */
    private void xuLyThongKeVaSMS() {
        int countThamKhao = 0;
        int countSGK = 0;

        for (PhieuMuon pm : listPhieuMuon) {
            if (pm.getLoaiSach() != null) {
                if (pm.getLoaiSach().contains("Tham khảo")) {
                    countThamKhao++;
                }
                if (pm.getLoaiSach().contains("SGK")) {
                    countSGK++;
                }
            }
        }

        String noiDungSMS = "Thong ke so luong theo tung Loai sach: Tham khao = "
                + countThamKhao + ", SGK = " + countSGK;

        // Dùng Implicit Intent mở ứng dụng SMS
        Intent smsIntent = new Intent(Intent.ACTION_SENDTO);
        smsIntent.setData(Uri.parse("smsto:222")); // Đầu số 222
        smsIntent.putExtra("sms_body", noiDungSMS);

        try {
            startActivity(smsIntent);
        } catch (Exception e) {
            Toast.makeText(this, "Không tìm thấy ứng dụng nhắn tin SMS: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Câu 3: Thoát ứng dụng
     * Menu Thoát hiển thị AlertDialog xác nhận (Đồng ý / Hủy). Chọn Đồng ý thì đóng Activity (finish()).
     */
    private void hienThiDialogThoat() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Xác nhận thoát");
        builder.setMessage("Bạn có chắc chắn muốn thoát khỏi màn hình danh sách?");
        builder.setPositiveButton("Đồng ý", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });
        builder.setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.show();
    }
}
