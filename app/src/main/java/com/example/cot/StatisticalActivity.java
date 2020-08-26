package com.example.cot;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.WindowManager;
import android.widget.TextView;
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

public class StatisticalActivity extends AppCompatActivity {
    String urlGetData ="http://luyenthihothanh.edu.vn/cot/DayStatistic.php";
    TextView tv_today_order, tv_today_total;
    ArrayList<Statistic> statisticArrayList;
    StatisticAdapter statisticAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistical);

    }
    private void GetData(String url) {
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        for (int i = 0; i < response.length(); i++) {
                            try {
                                Toast.makeText(StatisticalActivity.this, response.toString(), Toast.LENGTH_SHORT).show();
                                JSONObject object = response.getJSONObject(i);
                                statisticArrayList.add(new Statistic(
                                        object.getInt("order"),
                                        object.getInt("total"),
                                        object.getInt("order_month"),
                                        object.getInt("total+month")
                                ));
//                                Toast.makeText(OrderListActivity.this, object.getInt("id")+"", Toast.LENGTH_SHORT).show();
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        statisticAdapter.notifyDataSetChanged();
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
