package com.example.cot;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
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

public class OrderListActivity extends AppCompatActivity {
    String urlGetOrder = "http://luyenthihothanh.edu.vn/cot/Order.php";
    String urlDeleteOrder = "http://luyenthihothanh.edu.vn/cot/DeleteOrder.php";
    String urlMakeItDone ="http://luyenthihothanh.edu.vn/cot/MakeItDone.php";
    ListView lvOrder;
    ArrayList<Order> orderArrayList;
    OrderAdaptor orderAdaptor;

    @Override
    public void onBackPressed() {
        Intent intent= new Intent(OrderListActivity.this,HomeActivity.class);
        startActivity(intent);
    }
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_list);
        lvOrder = findViewById(R.id.lv_Order);
        orderArrayList = new ArrayList<>();
        orderAdaptor = new OrderAdaptor(this, R.layout.order, orderArrayList);
        lvOrder.setAdapter(orderAdaptor);
        GetData(urlGetOrder);

    }


    private void GetData(String url) {
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        orderArrayList.clear();
                        for (int i = 0; i < response.length(); i++) {
                            try {
                                JSONObject object = response.getJSONObject(i);
                                orderArrayList.add(new Order(
                                        object.getInt("id"),
                                        object.getInt("table_id"),
                                        object.getString("status")
                                ));
//                                Toast.makeText(OrderListActivity.this, object.getInt("id")+"", Toast.LENGTH_SHORT).show();
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        orderAdaptor.notifyDataSetChanged();
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

    public void DeleteOrder(final int ido) {
        RequestQueue requestQueue = Volley.newRequestQueue(OrderListActivity.this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, urlDeleteOrder,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(OrderListActivity.this,response, Toast.LENGTH_SHORT).show();

                        if (response.trim().equals("success")) {
                            Toast.makeText(OrderListActivity.this, "Xóa thành công!", Toast.LENGTH_SHORT).show();
                            GetData(urlGetOrder);


                        } else {
                            Toast.makeText(OrderListActivity.this, "Lỗi! Xóa thất bại!", Toast.LENGTH_SHORT).show();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(OrderListActivity.this, "Xảy ra lỗi!", Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("ido", String.valueOf(ido));
                return params;
            }
        };
        requestQueue.add(stringRequest);
    }
    public void MakeItDoneclass(final int ido) {
        RequestQueue requestQueue = Volley.newRequestQueue(OrderListActivity.this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, urlMakeItDone,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        if (response.trim().equals("success")) {
                            Toast.makeText(OrderListActivity.this, "Order đã hoàn thành", Toast.LENGTH_SHORT).show();
                            GetData(urlGetOrder);
                        } else {
                            Toast.makeText(OrderListActivity.this, "Lỗi, Không thể hoàn thành!", Toast.LENGTH_SHORT).show();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(OrderListActivity.this, "Xảy ra lỗi!", Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("ido", String.valueOf(ido));
                return params;
            }
        };
        requestQueue.add(stringRequest);
    }
}
