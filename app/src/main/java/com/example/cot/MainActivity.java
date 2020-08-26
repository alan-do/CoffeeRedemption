package com.example.cot;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.view.Menu;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    String url = "http://luyenthihothanh.edu.vn/cot/SignIn.php";
    EditText edtuser, edtpassword;
    Button btndangnhap, btnquenmatkhau;
    RadioButton remember;
    public static String staff_id;
    public static Editable user;
    public static ArrayList<CheckoutBag> checkoutBagArrayList;
    @Override

    protected void onCreate(Bundle savedInstanceState) {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Anhxa();
        ControlButton();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu1, menu);
        return super.onCreateOptionsMenu(menu);
    }

    private void ControlButton() {
        btndangnhap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String tk = edtuser.getText().toString();
                String mk = edtpassword.getText().toString();
                if (tk.isEmpty() || mk.isEmpty()) {
                    Toast.makeText(MainActivity.this, "Vui lòng nhập đủ thông tin!!!", Toast.LENGTH_SHORT).show();
                } else {
                    SignIn(url);
                }
            }
        });
        remember.setOnCheckedChangeListener(new RadioButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (remember.isChecked()) {
                    MainActivity.user = edtuser.getText();
                }
                else
                    MainActivity.user=null;
            }

        });
    }

    private void Anhxa() {
        edtuser = (EditText) findViewById(R.id.et_tendangnhap);
        if(user!=null) {
            edtuser.setText(user);
        }
        else edtuser.setHint("Tài khoản");
        edtpassword = (EditText) findViewById(R.id.et_matkhau);
        btndangnhap = (Button) findViewById(R.id.btn_dangnhap);
        remember = findViewById(R.id.rb_ghinho);
    }

    public void SignIn(String url) {
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (response.trim().equals("error")) {
                            Toast.makeText(MainActivity.this, "Lỗi! Thất bại!", Toast.LENGTH_SHORT).show();

                        } else {
                            staff_id = response;
                            if (response.trim().equals("")) {
                                Toast.makeText(MainActivity.this, "Sai tài khoản và mật khẩu", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(MainActivity.this, "Đăng nhập thành công!", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(MainActivity.this, HomeActivity.class);
                                startActivity(intent);
                            }
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity.this, "Xảy ra lỗi!", Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("tk", edtuser.getText().toString());
                params.put("mk", edtpassword.getText().toString());
                return params;
            }
        };
        requestQueue.add(stringRequest);
    }
}
