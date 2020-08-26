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

public class UpdateProActivity extends AppCompatActivity {
    EditText edtTenSP, edtGiaTien, edtLoaiSP,edtidsp;
    Button btnDongy, btnHuy;
    String url="http://luyenthihothanh.edu.vn/cot/UpdatePro.php";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_pro);
        AnhXa();
        Intent intent = getIntent();
        Product product = (Product) intent.getSerializableExtra("dataProduct");
        edtidsp.setText(product.getId()+"");
        edtTenSP.setText(product.getName());
        edtGiaTien.setText(product.getPrice()+"");
        edtLoaiSP.setText(product.getProducttype());
        btnDongy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                String name= edtTenSP.getText().toString();
                String price=edtGiaTien.getText().toString();
                String typeid =edtLoaiSP.getText().toString();

                if(name.isEmpty()||price.isEmpty()||typeid.isEmpty()){
                    Toast.makeText(UpdateProActivity.this,"Vui lòng nhập đủ thông tin!!!",Toast.LENGTH_SHORT).show();
                }else if(typeid.equals("2")||typeid.equals("3")){
                    UpdatePro(url);
                }
//
                else{
                    Toast.makeText(UpdateProActivity.this,"Vui lòng nhập đúng loại sản phẩm!!!",Toast.LENGTH_SHORT).show();
                }


            }
        });
        btnHuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                overridePendingTransition(R.anim.anim_back_en,R.anim.anim_back_exi);
            }
        });
    }
    private void AnhXa() {
        edtidsp = findViewById(R.id.tv_idsp);
        edtTenSP = findViewById(R.id.et_tensanpham);
        edtGiaTien = findViewById(R.id.et_giatien);
        edtLoaiSP = findViewById(R.id.et_loaisp);
        btnDongy = findViewById(R.id.btn_dongythem2);
        btnHuy = findViewById(R.id.btn_huythem2);
    }
    private void UpdatePro(String url) {
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if(response.trim().equals("success")){
                            Toast.makeText(UpdateProActivity.this,"Update thành công",Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(UpdateProActivity.this,chondouongActivity.class));
                            overridePendingTransition(R.anim.anim_back_en,R.anim.anim_back_exi);
                        }else{
                            Toast.makeText(UpdateProActivity.this,"Không thành công",Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(UpdateProActivity.this,"Xảy ra lỗi!",Toast.LENGTH_SHORT).show();
                        Log.d("AAA","Lỗi!\n"+error.toString());
                    }
                }
        ){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params= new HashMap<>();
                params.put("idsp",edtidsp.getText().toString());
                params.put("tensp",edtTenSP.getText().toString());
                params.put("giatien",edtGiaTien.getText().toString());
                params.put("loaisp",edtLoaiSP.getText().toString());
                return params;
            }
        };
        requestQueue.add(stringRequest);
    }
}
