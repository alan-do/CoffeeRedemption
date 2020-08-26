package com.example.cot;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.view.WindowManager;
import android.view.textclassifier.TextLinks;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
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

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class chondouongActivity extends AppCompatActivity {
    String urlGetData="http://luyenthihothanh.edu.vn/cot/ProductAll.php";
    String urlDelete="http://luyenthihothanh.edu.vn/cot/DeletePro.php";
    ListView lvProduct;
    ArrayList<Product> arrayProduct;
    ProductAdapter adapter;
    Button btnAddPro;
    @Override
    public void onBackPressed() {
        Intent intent= new Intent(chondouongActivity.this,HomeActivity.class);
        startActivity(intent);
    }
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chondouong);
        lvProduct= (ListView) findViewById((R.id.lvProduct));
        arrayProduct= new ArrayList<>();
        adapter = new ProductAdapter(this,R.layout.chondouong,arrayProduct);
        lvProduct.setAdapter(adapter);
        GetData(urlGetData);
        btnAddPro = (Button) findViewById(R.id.btn_themsanpham);
        final Context context = this;
        btnAddPro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context,AddProductActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.anim_enter,R.anim.anim_exit);
            }
        });
    }
    private void GetData(String url){
        RequestQueue requestQueue= Volley.newRequestQueue(this);
        JsonArrayRequest jsonArrayRequest= new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {

                        arrayProduct.clear();
                        for(int i=0;i<response.length();i++) {
                            try {
                                JSONObject object= response.getJSONObject(i);
                                arrayProduct.add(new Product(
                                        object.getInt("id"),
                                        object.getString("name"),
                                        object.getInt("price"),
                                        object.getString("namep"),
                                        object.getString("img")

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
    public  void DeletePro(final int idpro){
        RequestQueue requestQueue= Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, urlDelete,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if(response.trim().equals("success")){
                            Toast.makeText(chondouongActivity.this,"Xóa thành công!",Toast.LENGTH_SHORT).show();
                            GetData(urlGetData);
                        }else{
                            Toast.makeText(chondouongActivity.this,"Lỗi! Xóa thất bại!",Toast.LENGTH_SHORT).show();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(chondouongActivity.this,"Xảy ra lỗi!",Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params= new HashMap<>();
                params.put("idpro",String.valueOf(idpro));

                return params;
            }
        };
        requestQueue.add(stringRequest);
    }
}
