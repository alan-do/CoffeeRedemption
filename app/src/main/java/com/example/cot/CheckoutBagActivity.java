package com.example.cot;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static com.example.cot.MainActivity.checkoutBagArrayList;
import static com.example.cot.MainActivity.staff_id;

public class CheckoutBagActivity extends AppCompatActivity {

    ListView lv_checkout;
    CheckoutAdapter checkoutAdapter;
    public static TextView tv_total;
    Button btn_continue, btn_submit_checkout, btn_delete_checkout;
    String urlInsertOrder = "http://luyenthihothanh.edu.vn/cot/InsertOrder.php";
    String urlInsertOrderDetail = "http://luyenthihothanh.edu.vn/cot/InsertOrderDetail.php";

    @Override
    public void onBackPressed() {
        Intent intent= new Intent(CheckoutBagActivity.this,ChonOrderActivity.class);
        startActivity(intent);
    }
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        Intent intent = getIntent();
        final CheckoutBag checkoutBag = (CheckoutBag) intent.getSerializableExtra("checkout");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout_bag);
        AnhXa();

        //Nếu mảng tồn tại thì thêm, ko tồn tại thì thêm mới => tránh mảng bị clear khi chuyển activity

        if (ChonOrderActivity.checkoutBagArrayList != null) {
            if (checkoutBag != null) {
                ChonOrderActivity.checkoutBagArrayList.add(checkoutBag);
            }
        } else {
            ChonOrderActivity.checkoutBagArrayList = new ArrayList<>();
            if (checkoutBag != null) {
                ChonOrderActivity.checkoutBagArrayList.add(checkoutBag);
            }
        }
        checkoutAdapter = new CheckoutAdapter(this, R.layout.bag_item, ChonOrderActivity.checkoutBagArrayList);
        lv_checkout.setAdapter(checkoutAdapter);
        CheckData();
        Total();


        btn_continue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CheckoutBagActivity.this, ChonOrderActivity.class);
                startActivity(intent);
            }
        });
        btn_submit_checkout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InsertOrder(urlInsertOrder);
            }
        });
        btn_delete_checkout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ChonOrderActivity.checkoutBagArrayList=null;
                Intent intent = new Intent(CheckoutBagActivity.this, ChonOrderActivity.class);
                startActivity(intent);
            }
        });
        DeleteItemOnListView();
    }

    private void AnhXa() {
        tv_total = findViewById(R.id.tv_total_checkout);
        btn_continue = findViewById(R.id.btn_continue);
        btn_submit_checkout = findViewById(R.id.btn_submit_checkout);
        btn_delete_checkout = findViewById(R.id.btn_xoa);

        lv_checkout = findViewById(R.id.lv_checkout);
    }

    private void CheckData() {
        if (ChonOrderActivity.checkoutBagArrayList.size() <= 0) {
            checkoutAdapter.notifyDataSetChanged();
            lv_checkout.setVisibility(View.INVISIBLE);
        } else {
            checkoutAdapter.notifyDataSetChanged();
            lv_checkout.setVisibility(View.VISIBLE);
        }
    }

    private void DeleteItemOnListView() {
        lv_checkout.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                AlertDialog.Builder builder = new AlertDialog.Builder(CheckoutBagActivity.this);
                builder.setTitle("Xác nhận xóa sản phẩm");
                builder.setMessage("Xóa sản phẩm nhé?");
                builder.setPositiveButton("Chắc như bắp!", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (ChonOrderActivity.checkoutBagArrayList.size() <= 0) {

                        } else {
                            ChonOrderActivity.checkoutBagArrayList.remove(position);
                            checkoutAdapter.notifyDataSetChanged();
                            Total();
                        }
                    }
                });
                builder.setNegativeButton("Thôi", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        checkoutAdapter.notifyDataSetChanged();
                        Total();
                    }
                });
                builder.show();
                return;
            }
        });
    }

    public static void Total() {
        int sum = 0;
        for (int i = 0; i < ChonOrderActivity.checkoutBagArrayList.size(); i++) {
            sum = sum + ChonOrderActivity.checkoutBagArrayList.get(i).getPrice();
        }
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        tv_total.setText("Tổng tiền: " + decimalFormat.format(sum) + " Đ");
    }

    private void InsertOrder(String url) {
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(final String order_id) {
                        Log.d("id", order_id);
                        if (Integer.parseInt(order_id) > 0) {
                            RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
                            StringRequest request = new StringRequest(Request.Method.POST, urlInsertOrderDetail, new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    if (response.equals("1")) {
                                        ChonOrderActivity.checkoutBagArrayList=null;

                                        Toast.makeText(CheckoutBagActivity.this, "ĐẶT HÀNG THÀNH CÔNG", Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                                        startActivity(intent);
                                        finish();
                                    } else {
                                        Toast.makeText(CheckoutBagActivity.this, "Lỗi", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            }, new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {

                                }
                            }) {
                                @Override
                                protected Map<String, String> getParams() throws AuthFailureError {
                                    JSONArray jsonArray = new JSONArray();
                                    for (int i = 0; i < ChonOrderActivity.checkoutBagArrayList.size(); i++) {
                                        JSONObject jsonObject = new JSONObject();
                                        try {
                                            jsonObject.put("order_id", order_id + "");
                                            jsonObject.put("product_id", ChonOrderActivity.checkoutBagArrayList.get(i).getId() + "");
                                            jsonObject.put("quantity", ChonOrderActivity.checkoutBagArrayList.get(i).getSl() + "");
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }
                                        jsonArray.put(jsonObject);
                                    }

                                    HashMap<String, String> hashMap = new HashMap<String, String>();
                                    hashMap.put("json", jsonArray.toString());
                                    return hashMap;
                                }
                            };
                            queue.add(request);
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(CheckoutBagActivity.this, "Xảy ra lỗi!", Toast.LENGTH_SHORT).show();
                        Log.d("AAA", "Lỗi!\n" + error.toString());
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                String status = "wating";
                params.put("staff_id", staff_id);
                params.put("status", status);
                return params;
            }
        };
        requestQueue.add(stringRequest);

    }

}
