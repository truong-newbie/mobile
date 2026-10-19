package com.example.project1;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class fakeActivity extends AppCompatActivity {
    private Button btnThem,btnXoa,btnThongKe;
    private ListView listViewThongTin;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnThem = findViewById(R.id.btnThem);
        listViewThongTin = findViewById(R.id.listViewThongTin);
        btnThem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // lấy dữ liệu từ form rồi cho và danh sách rồi reload form
            }
        });
        // sự kiện list View
        listViewThongTin.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // gán vị trí hiện tại cho biến selectposition
                // lấy phần tử hiện tại đang trỏ đến
                // gán giá trị đấy lên form  thông qua các biến đã được ánh xạ
                Toast.makeText(fakeActivity.this,"Thông Báo",Toast.LENGTH_SHORT).show();
            }
        });
        btnThongKe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // lấy các dữ liệu cần thông kê
                // sử dụng
                String notify = "Tôi là tiến tôi đang test thử tính năng thông báo";
                new AlertDialog.Builder(fakeActivity.this)
                        .setTitle("Thông Báo")
                        .setMessage(notify)
                        .setPositiveButton("Đóng",(d,w)->d.dismiss())
                        .show();
            }
        });
    }
}
