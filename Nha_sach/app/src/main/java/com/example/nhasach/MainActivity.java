package com.example.nhasach;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private EditText edtMaSach, edtNhaXB;
    private CheckBox cbThamKhao, cbSGK;
    private RadioGroup rgChuDe;
    private RadioButton rbToan, rbTiengViet;
    private TextView tvSoLanDangKy;

    // Danh sách lưu trữ các phiếu mượn
    private ArrayList<PhieuMuon> listPhieuMuon = new ArrayList<>();
    // Vị trí đang sửa (-1 nghĩa là thêm mới)
    private int editingPosition = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViews();
        handleIntentData(getIntent());
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        handleIntentData(intent);
    }

    private void initViews() {
        edtMaSach = findViewById(R.id.edtMaSach);
        edtNhaXB = findViewById(R.id.edtNhaXB);
        cbThamKhao = findViewById(R.id.cbThamKhao);
        cbSGK = findViewById(R.id.cbSGK);
        rgChuDe = findViewById(R.id.rgChuDe);
        rbToan = findViewById(R.id.rbToan);
        rbTiengViet = findViewById(R.id.rbTiengViet);
        tvSoLanDangKy = findViewById(R.id.tvSoLanDangKy);
    }

    // Nhận dữ liệu nếu được gửi ngược từ GD2 về để sửa
    private void handleIntentData(Intent intent) {
        if (intent != null && intent.getExtras() != null) {
            Bundle bundle = intent.getExtras();

            // Cập nhật danh sách từ GD2 nếu có
            if (bundle.containsKey("danhSach")) {
                ArrayList<PhieuMuon> list = (ArrayList<PhieuMuon>) bundle.getSerializable("danhSach");
                if (list != null) {
                    listPhieuMuon = list;
                    tvSoLanDangKy.setText("Số lần đăng ký: " + listPhieuMuon.size());
                }
            }

            // Đổ lại dữ liệu lên form nếu có yêu cầu sửa từ GD2
            if (bundle.containsKey("phieuMuonSua")) {
                PhieuMuon pm = (PhieuMuon) bundle.getSerializable("phieuMuonSua");
                editingPosition = bundle.getInt("viTriSua", -1);

                if (pm != null) {
                    edtMaSach.setText(pm.getMaSach());
                    edtNhaXB.setText(pm.getNhaXB());

                    cbThamKhao.setChecked(pm.getLoaiSach().contains("Tham khảo"));
                    cbSGK.setChecked(pm.getLoaiSach().contains("SGK"));

                    if ("Toán".equalsIgnoreCase(pm.getChuDe())) {
                        rbToan.setChecked(true);
                    } else if ("Tiếng Việt".equalsIgnoreCase(pm.getChuDe())) {
                        rbTiengViet.setChecked(true);
                    }

                    Toast.makeText(this, "Đã tải dữ liệu sách lên form để sửa", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    // Khởi tạo Option Menu GD1 (Câu 1)
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_gd1, menu);
        return true;
    }

    // Xử lý các mục trong Option Menu GD1 (Câu 2a, 2b, Câu 3)
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.menu_xac_nhan) {
            xuLyXacNhan();
            return true;
        } else if (id == R.id.menu_xem_ds) {
            xuLyXemDanhSach();
            return true;
        } else if (id == R.id.menu_thoat_gd1) {
            hienThiDialogThoat();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * Câu 2a: Xác nhận tại GD1
     * - Kiểm tra Mã sách, Nhà XB không rỗng
     * - Chọn ít nhất 1 loại sách & 1 chủ đề
     * - Mã sách không trùng lặp -> lưu và tăng Số lần đăng ký lên 1
     */
    private void xuLyXacNhan() {
        String maSach = edtMaSach.getText().toString().trim();
        String nhaXB = edtNhaXB.getText().toString().trim();

        // 1. Kiểm tra Mã sách không rỗng
        if (maSach.isEmpty()) {
            edtMaSach.setError("Vui lòng nhập mã sách!");
            edtMaSach.requestFocus();
            return;
        }

        // 2. Kiểm tra Nhà XB không rỗng
        if (nhaXB.isEmpty()) {
            edtNhaXB.setError("Vui lòng nhập nhà xuất bản!");
            edtNhaXB.requestFocus();
            return;
        }

        // 3. Kiểm tra chọn ít nhất 1 loại sách
        boolean isThamKhao = cbThamKhao.isChecked();
        boolean isSGK = cbSGK.isChecked();
        if (!isThamKhao && !isSGK) {
            Toast.makeText(this, "Vui lòng chọn ít nhất 1 loại sách!", Toast.LENGTH_SHORT).show();
            return;
        }

        // 4. Kiểm tra chọn 1 chủ đề
        int checkedChuDeId = rgChuDe.getCheckedRadioButtonId();
        if (checkedChuDeId == -1) {
            Toast.makeText(this, "Vui lòng chọn 1 chủ đề!", Toast.LENGTH_SHORT).show();
            return;
        }
        String chuDe = (checkedChuDeId == R.id.rbToan) ? "Toán" : "Tiếng Việt";

        // 5. Kiểm tra mã sách không trùng lặp
        for (int i = 0; i < listPhieuMuon.size(); i++) {
            if (i != editingPosition && listPhieuMuon.get(i).getMaSach().equalsIgnoreCase(maSach)) {
                Toast.makeText(this, "Mã sách đã tồn tại! Vui lòng nhập mã khác.", Toast.LENGTH_SHORT).show();
                edtMaSach.requestFocus();
                return;
            }
        }

        // Ghép chuỗi loại sách
        StringBuilder sbLoaiSach = new StringBuilder();
        if (isThamKhao) sbLoaiSach.append("Tham khảo");
        if (isSGK) {
            if (sbLoaiSach.length() > 0) sbLoaiSach.append(", SGK");
            else sbLoaiSach.append("SGK");
        }
        String loaiSach = sbLoaiSach.toString();

        // 6. Lưu dữ liệu
        if (editingPosition != -1) {
            // Cập nhật lại sách đang sửa
            PhieuMuon pm = listPhieuMuon.get(editingPosition);
            pm.setMaSach(maSach);
            pm.setNhaXB(nhaXB);
            pm.setLoaiSach(loaiSach);
            pm.setChuDe(chuDe);
            editingPosition = -1;
            Toast.makeText(this, "Cập nhật thông tin sách thành công!", Toast.LENGTH_SHORT).show();
        } else {
            // Thêm mới
            PhieuMuon pm = new PhieuMuon(maSach, nhaXB, loaiSach, chuDe);
            listPhieuMuon.add(pm);
            Toast.makeText(this, "Đăng ký mượn sách thành công!", Toast.LENGTH_SHORT).show();
        }

        // Tăng số lần đăng ký (hiển thị tổng số lượng đăng ký)
        tvSoLanDangKy.setText("Số lần đăng ký: " + listPhieuMuon.size());

        // Xóa trắng form để nhập tiếp
        xoaTrangForm();
    }

    private void xoaTrangForm() {
        edtMaSach.setText("");
        edtNhaXB.setText("");
        cbThamKhao.setChecked(false);
        cbSGK.setChecked(false);
        rgChuDe.clearCheck();
        edtMaSach.requestFocus();
    }

    /**
     * Câu 2b: Chuyển màn hình Xem danh sách
     * Sử dụng Explicit Intent (Bundle) chuyển toàn bộ dữ liệu sang GD2 hiển thị trên ListView
     */
    private void xuLyXemDanhSach() {
        Intent intent = new Intent(MainActivity.this, DanhSachActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("danhSach", listPhieuMuon);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    /**
     * Câu 3: Thoát ứng dụng
     * Menu Thoát hiển thị AlertDialog xác nhận (Đồng ý / Hủy). Chọn Đồng ý thì đóng Activity (finish()).
     */
    private void hienThiDialogThoat() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Xác nhận thoát");
        builder.setMessage("Bạn có chắc chắn muốn thoát ứng dụng?");
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
