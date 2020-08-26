package com.example.cot;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ListView;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class staffActivity extends AppCompatActivity {

    String urlGetData = "http://luyenthihothanh.edu.vn/cot/GetStaff.php";
    String urlDelete = "http://luyenthihothanh.edu.vn/cot/DeleteTK.php";
    ListView lvStaff;
    ArrayList<Staff> arrayStaff;
    StaffAdapter adapter;
    Button btnAddStaff;

    @Override
    public void onBackPressed() {
        Intent intent= new Intent(staffActivity.this,HomeActivity.class);
        startActivity(intent);
    }
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_staff);
        lvStaff = (ListView) findViewById((R.id.lvStaff));
        arrayStaff = new ArrayList<>();
        adapter = new StaffAdapter(this, R.layout.one_staff, arrayStaff);
        lvStaff.setAdapter(adapter);
        GetData(urlGetData);
        btnAddStaff = (Button) findViewById(R.id.btn_themnhanvien);
        final Context context = this;
        btnAddStaff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context,AddStaffActivity.class);
                startActivity(intent);
            }
        });
    }

    private void GetData(String url) {
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        arrayStaff.clear();
                        for (int i = 0; i < response.length(); i++) {
                            try {
                                JSONObject object = response.getJSONObject(i);
                                arrayStaff.add(new Staff(

                                        object.getString("name"),
                                        object.getString("taikhoan"),
                                        object.getString("matkhau"),
                                        object.getInt("id"),
                                        object.getInt("salary"),
                                        object.getInt("position")
                                ));
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        adapter.notifyDataSetChanged();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }
        );
        requestQueue.add(jsonArrayRequest);
    }
    public  void DeleteStaff(final int idnv){
        RequestQueue requestQueue= Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, urlDelete,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if(response.trim().equals("success")){
                            Toast.makeText(staffActivity.this,"Xóa thành công!",Toast.LENGTH_SHORT).show();
                            GetData(urlGetData);
                        }else{
                            Toast.makeText(staffActivity.this,"Lỗi! Xóa thất bại!",Toast.LENGTH_SHORT).show();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(staffActivity.this,"Xảy ra lỗi!",Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params= new HashMap<>();
                params.put("idnv",String.valueOf(idnv));

                return params;
            }
        };
        requestQueue.add(stringRequest);
    }
}
