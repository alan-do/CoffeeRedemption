package com.example.cot;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;


import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class ProductDetailActivity extends AppCompatActivity {
    ImageView img_pro_detail;
    TextView tv_des, tv_name;
    Spinner spinner;
    Button btn_ordering;
    EditText edt_note;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);
        tv_name = findViewById(R.id.tv_name_pro);
//        tv_des = findViewById(R.id.tv_product_description);
        spinner = findViewById(R.id.spinner_sl);
        btn_ordering = findViewById(R.id.btn_datmua);
        img_pro_detail= findViewById(R.id.img_product_detail);
        List<Integer> categories = new ArrayList<Integer>();
        categories.add(1);
        categories.add(2);
        categories.add(3);
        categories.add(4);
        categories.add(5);
        categories.add(6);

        // Creating adapter for spinner
        ArrayAdapter<Integer> dataAdapter = new ArrayAdapter<Integer>(this, android.R.layout.simple_spinner_item, categories);

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        spinner.setAdapter(dataAdapter);
        Intent intent = getIntent();
        final Product product = (Product) intent.getSerializableExtra("data");
        tv_name.setText(product.getName());
        Picasso.with(ProductDetailActivity.this).load(product.getURL()).into(img_pro_detail);
        btn_ordering.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String a = spinner.getSelectedItem().toString();
                CheckoutBag checkout =new CheckoutBag(product.getId(), product.getName(),product.getPrice(), product.getPrice(), product.getURL(), a);
                Intent intent = new Intent(ProductDetailActivity.this,CheckoutBagActivity.class);
                intent.putExtra("checkout",checkout);
                startActivity(intent);
            }
        });
    }
}
