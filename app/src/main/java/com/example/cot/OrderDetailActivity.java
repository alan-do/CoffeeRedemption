package com.example.cot;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.ListView;
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

import java.util.ArrayList;

public class OrderDetailActivity extends AppCompatActivity {
    String urlGetOrderDetail;
    ListView lv_order_detail;
    ArrayList<OrderDetail> orderDetailArrayList;
    OrderDetailAdapter orderDetailAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_detail);
        lv_order_detail = findViewById(R.id.lv_Order_detail);
        orderDetailArrayList = new ArrayList<>();
        orderDetailAdapter = new OrderDetailAdapter(this, R.layout.order_detail, orderDetailArrayList);
        Intent intent = getIntent();
        Order order = (Order) intent.getSerializableExtra("order_id");
        urlGetOrderDetail = "http://luyenthihothanh.edu.vn/cot/Order_detail.php?id="+order.getId();
        lv_order_detail.setAdapter(orderDetailAdapter);
        GetData(urlGetOrderDetail);
    }

    private void GetData(String url) {
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        for (int i = 0; i < response.length(); i++) {
                            try {
//                                Toast.makeText(OrderDetailActivity.this, response.toString(), Toast.LENGTH_SHORT).show();
                                JSONObject object = response.getJSONObject(i);
                                orderDetailArrayList.add(new OrderDetail(
                                        object.getInt("id"),
                                        object.getInt("product_id"),
                                        object.getInt("quantity"),
                                        object.getInt("order_id"),
                                        object.getString("product_name"),
                                        object.getInt("product_price")
                                ));

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        orderDetailAdapter.notifyDataSetChanged();
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
}
