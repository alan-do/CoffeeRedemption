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
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class AddStaffActivity extends AppCompatActivity {
    EditText edtHoten, edtLuong, edtVitri, edtTaikhoan, edtMatkhau,edtid;
    Button btnDongy, btnHuy;
String url="http://luyenthihothanh.edu.vn/cot/InsertTK.php";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_staff);
        AnhXa();
        btnDongy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String hoten= edtHoten.getText().toString();
                String vitri=edtVitri.getText().toString();
                String luong=edtLuong.getText().toString();
                String taikhoan=edtTaikhoan.getText().toString();
                String matkhau=edtMatkhau.getText().toString();
                if(hoten.isEmpty()||vitri.isEmpty()||luong.isEmpty()||taikhoan.isEmpty()||matkhau.isEmpty()){
                    Toast.makeText(AddStaffActivity.this,"Vui lòng nhập đủ thông tin!!!",Toast.LENGTH_SHORT).show();
                }else{
                    AddStaff(url);
                }
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
        edtHoten = findViewById(R.id.et_hotendangnhap);
        edtLuong = findViewById(R.id.et_luong);
        edtVitri = findViewById(R.id.et_vitridangnhap);
        edtTaikhoan = findViewById(R.id.et_taikhoandangnhap);
        edtMatkhau = findViewById(R.id.et_matkhaudangnhap);
        btnDongy = findViewById(R.id.btn_dongythem);
        btnHuy = findViewById(R.id.btn_huythem);
    }

    private void AddStaff(String url) {
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if(response.trim().equals("success")){
                            Toast.makeText(AddStaffActivity.this,"Thêm thành công",Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(AddStaffActivity.this,staffActivity.class));
                        }else{
                            Toast.makeText(AddStaffActivity.this,"Không thành công",Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(AddStaffActivity.this,"Xảy ra lỗi!",Toast.LENGTH_SHORT).show();
                        Log.d("AAA","Lỗi!\n"+error.toString());
                    }
                }
        ){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params= new HashMap<>();
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
