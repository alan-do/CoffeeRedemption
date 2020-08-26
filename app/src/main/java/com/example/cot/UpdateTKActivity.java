package com.example.cot;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class UpdateTKActivity extends AppCompatActivity {
    EditText edtHoten, edtLuong, edtVitri, edtTaikhoan, edtMatkhau,edtid;
    Button btnDongy, btnHuy;
    String url="http://luyenthihothanh.edu.vn/cot/UpdateTK.php";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_tk);
        AnhXa();
        Intent intent = getIntent();
        Staff staff = (Staff) intent.getSerializableExtra("dataStaff");
        // gán thông tin tk đó vào để hiển thị trên activity
        edtid.setText(staff.getId()+"");
        edtHoten.setText(staff.getName());
        edtLuong.setText(staff.getLuong()+"");
        edtVitri.setText(staff.getViTri()+"");
        edtTaikhoan.setText(staff.getTaiKhoan());
        edtMatkhau.setText(staff.getMatKhau());


        btnDongy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                    UpdateStaff(url);

            }
        });
        btnHuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
    private void AnhXa() {
        edtid = findViewById(R.id.tv_idupdate);
        edtHoten = findViewById(R.id.et_hotendangnhap1);
        edtLuong = findViewById(R.id.et_luong1);
        edtVitri = findViewById(R.id.et_vitridangnhap1);
        edtTaikhoan = findViewById(R.id.et_taikhoandangnhap1);
        edtMatkhau = findViewById(R.id.et_matkhaudangnhap1);
        btnDongy = findViewById(R.id.btn_dongythem1);
        btnHuy = findViewById(R.id.btn_huythem1);
    }
    private void UpdateStaff(String url) {
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if(response.trim().equals("success")){
                            Toast.makeText(UpdateTKActivity.this,"Update thành công",Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(UpdateTKActivity.this,staffActivity.class));
                        }else{
                            Toast.makeText(UpdateTKActivity.this,"Không thành công",Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(UpdateTKActivity.this,"Xảy ra lỗi!",Toast.LENGTH_SHORT).show();
                        Log.d("AAA","Lỗi!\n"+error.toString());
                    }
                }
        ){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params= new HashMap<>();
                params.put("idnv",edtid.getText().toString());
                params.put("tennv",edtHoten.getText().toString());
                params.put("vitrinv",edtVitri.getText().toString());
                params.put("luongnv",edtLuong.getText().toString());
                params.put("taikhoannv",edtTaikhoan.getText().toString().trim());
                params.put("matkhaunv",edtMatkhau.getText().toString().trim());
                return params;
            }
        };
        requestQueue.add(stringRequest);
    }
}
