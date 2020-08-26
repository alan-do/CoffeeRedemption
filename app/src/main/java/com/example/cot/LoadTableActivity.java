package com.example.cot;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.WindowManager;
import android.widget.GridView;

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

public class LoadTableActivity extends AppCompatActivity {
    String urlGetTable ="http://luyenthihothanh.edu.vn/cot/GetTables.php";
    GridView gv_table;
    ArrayList<Table> tableArrayList;
    TableAdapter tableAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_load_table);
        gv_table = findViewById(R.id.gv_table);
        tableArrayList = new ArrayList<>();
        tableAdapter = new TableAdapter(this, R.layout.table,tableArrayList);
        gv_table.setAdapter(tableAdapter);
        GetData(urlGetTable);
    }

    private void GetData(String url) {
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        tableArrayList.clear();
                        for (int i = 0; i < response.length(); i++) {
                            try {
//                                Toast.makeText(LoadTableActivity.this, response.toString(), Toast.LENGTH_SHORT).show();
                                JSONObject object = response.getJSONObject(i);
                                tableArrayList.add(new Table(
                                        object.getInt("id"),
                                        object.getInt("number_of_seat"),
                                        object.getInt("floor")
                                ));
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        tableAdapter.notifyDataSetChanged();
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
