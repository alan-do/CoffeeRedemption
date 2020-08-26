package com.example.cot;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;


public class ChonOrderActivity extends AppCompatActivity {
    String urlGetData = "http://luyenthihothanh.edu.vn/cot/ProductAll.php";
    ListView lvProduct;
    ArrayList<Product> arrayProduct;
    ChonOrderAdapter adapter;
    Button btn_go_to_cart, btn_canle_ordering;
    Spinner spinner_type_when_ordering;
    public static ArrayList<CheckoutBag> checkoutBagArrayList;

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(ChonOrderActivity.this, HomeActivity.class);
        startActivity(intent);
    }

    protected void onCreate(Bundle savedInstanceState) {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chon_order);
        Intent intent = getIntent();
        Table table = (Table) intent.getSerializableExtra("Table");

        Mapping();

        arrayProduct = new ArrayList<>();
        adapter = new ChonOrderAdapter(this, R.layout.chonorder, arrayProduct);
        GetData(urlGetData);
        lvProduct.setAdapter(adapter);


        Spinner_();


        String filter = spinner_type_when_ordering.getSelectedItem().toString();
        spinner_type_when_ordering.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                loadDATA();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        ActionButton();

    }

    private void Mapping() {
        lvProduct = (ListView) findViewById((R.id.lvProduct));
        btn_go_to_cart = findViewById(R.id.btn_go_to_cart);
        btn_canle_ordering = findViewById(R.id.btn_cancle_ordering);
        spinner_type_when_ordering = findViewById(R.id.spinner_filter);
    }

    private void ActionButton() {
        btn_go_to_cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ChonOrderActivity.checkoutBagArrayList != null) {
                    Intent intent = new Intent(ChonOrderActivity.this, CheckoutBagActivity.class);
                    startActivity(intent);
                } else {
                    AlertDialog.Builder dialogXoa = new AlertDialog.Builder(ChonOrderActivity.this);
                    dialogXoa.setMessage("Giỏ hàng trống");

                    dialogXoa.setNegativeButton("Quay lại", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    });
                    dialogXoa.show();
                }

            }
        });
        btn_canle_ordering.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ChonOrderActivity.checkoutBagArrayList = null;
                Intent intent = new Intent(ChonOrderActivity.this, HomeActivity.class);
                startActivity(intent);
            }
        });
    }

    private void Spinner_() {
        List<String> categories = new ArrayList<String>();
        categories.add("TẤT CẢ");
        categories.add("CÀ PHÊ");
        categories.add("TRÀ & MACCHIATO");
        categories.add("THỨC UỐNG ĐÁ XAY");
        categories.add("THỨC UỐNG TRÁI CÂY");
        categories.add("BÁNH & SNACK");
        categories.add("COMBO");
        categories.add("BIA");

        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categories);

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // attaching data adapter to spinner
        spinner_type_when_ordering.setAdapter(dataAdapter);
    }

    private void GetData(String url) {
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        for (int i = 0; i < response.length(); i++) {
                            try {
                                JSONObject object = response.getJSONObject(i);
                                arrayProduct.add(new Product(
                                        object.getInt("id"),
                                        object.getString("name"),
                                        object.getInt("price"),
//                                        object.getString("description"),
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

    private void loadDATA() {
        arrayProduct.clear();
        if (spinner_type_when_ordering.getSelectedItem().equals("TẤT CẢ")) {
            urlGetData = "http://luyenthihothanh.edu.vn/cot/ProductAll.php";
            GetData(urlGetData);
            lvProduct.setAdapter(adapter);
        } else {
            String filter = spinner_type_when_ordering.getSelectedItem().toString();
            String query = null;
            try {
                query = URLEncoder.encode(filter, "utf-8");
                urlGetData = "http://luyenthihothanh.edu.vn/cot/Product.php?name=" + query;
                GetData(urlGetData);
                lvProduct.setAdapter(adapter);
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }

        }
        adapter.notifyDataSetChanged();
    }
}