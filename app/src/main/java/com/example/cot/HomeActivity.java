package com.example.cot;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class HomeActivity extends AppCompatActivity {
    ImageButton img_btn_order;
    ImageButton img_btn_order_list;
    ImageButton img_btn_customer;
    ImageButton img_btn_recipe;
    ImageButton img_btn_staff;
    ImageButton img_btn_statistic;
    Button btn_logout;
    @Override
    public void onBackPressed() {
        AlertDialog.Builder dialogXoa= new AlertDialog.Builder(HomeActivity.this);
        dialogXoa.setMessage("Bạn muốn đăng xuất");
        dialogXoa.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                MainActivity.staff_id=null;
                Intent intent = new Intent(HomeActivity.this,MainActivity.class);
                startActivity(intent);
            }
        });
        dialogXoa.setNegativeButton("Quay lại", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        dialogXoa.show();
    }

    protected void onCreate(Bundle savedInstanceState) {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);
        img_btn_order = findViewById(R.id.img_btn_order);
        img_btn_order_list = findViewById(R.id.img_btn_orderlist);
        img_btn_customer = findViewById(R.id.img_btn_customer);
        img_btn_recipe = findViewById(R.id.img_btn_recipe);
        img_btn_staff = findViewById(R.id.img_btn_staff);
        img_btn_statistic=findViewById(R.id.img_btn_statistic);
        btn_logout=findViewById(R.id.btn_logout);
        ImageButtonAction();
    }

    private void ImageButtonAction() {
        final Context context = this;
        img_btn_order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context,ChonOrderActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.anim_enter,R.anim.anim_exit);
            }
        });
        img_btn_order_list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context,OrderListActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.anim_enter,R.anim.anim_exit);
            }
        });

        img_btn_staff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context,staffActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.anim_enter,R.anim.anim_exit);
            }
        });
        img_btn_recipe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context,chondouongActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.anim_enter,R.anim.anim_exit);
            }
        });
        img_btn_statistic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context,StatisticActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.anim_enter,R.anim.anim_exit);
            }
        });
        btn_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder dialogXoa= new AlertDialog.Builder(context);
                dialogXoa.setMessage("Đăng xuất");
                dialogXoa.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        MainActivity.staff_id=null;
                        Intent intent = new Intent(HomeActivity.this,MainActivity.class);
                        startActivity(intent);
                    }
                });
                dialogXoa.setNegativeButton("Quay lại", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                dialogXoa.show();
            }
        });

    }

}
