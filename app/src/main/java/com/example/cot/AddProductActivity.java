package com.example.cot;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
public class AddProductActivity extends AppCompatActivity {
    EditText edtName, edtPrice, edtTypeId;
    String path = "";
    TextView tvPath;
    ImageView imgAddPro;
    Button btnDongy, btnHuy,btnCam;
    int Request_Code_Image = 123;
    int Request_Code_Camera = 456;
    Spinner spinner_type;
    Bitmap bitmap;
    String url = "http://luyenthihothanh.edu.vn/cot/InsertProduct.php";
    String urlGetImg = "http://luyenthihothanh.edu.vn/cot/UploadHinh.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product);
        AnhXa();
        final Context context = this;
        SpinnerAddPRO();
        btnDongy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = edtName.getText().toString();
                String price = edtPrice.getText().toString();
//                String typeid = edtTypeId.getText().toString();
                String type = spinner_type.getSelectedItem().toString();


                spinner_type.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        String type = spinner_type.getSelectedItem().toString();
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });
                if (name.isEmpty() || price.isEmpty()||type.isEmpty() ) {
                    Toast.makeText(AddProductActivity.this, "Vui lòng nhập đủ thông tin!!!", Toast.LENGTH_SHORT).show();
                }
                else AddPro(url);
            }
        });
        btnHuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(context,chondouongActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.anim_back_en,R.anim.anim_back_exi);

            }
        });
        imgAddPro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityCompat.requestPermissions(
                        AddProductActivity.this,
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                        Request_Code_Image
                );
            }
        });
        btnCam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent,Request_Code_Camera);
            }
        });
    }
    private void SpinnerAddPRO() {
        List<String> categories = new ArrayList<String>();
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
        spinner_type.setAdapter(dataAdapter);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == Request_Code_Image) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(Intent.createChooser(intent, "Select Image"), Request_Code_Image);
            } else {
                Toast.makeText(this, "Ban ko co quyen truy cap!", Toast.LENGTH_SHORT).show();
            }
            return;
        }

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == Request_Code_Image && resultCode == RESULT_OK && data != null) {
            Uri filePath = data.getData();

            try {
                InputStream inputStream = getContentResolver().openInputStream(filePath);
                bitmap = BitmapFactory.decodeStream(inputStream);
                imgAddPro.setImageBitmap(bitmap);
                AddImgPro(urlGetImg);

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
        if (requestCode == Request_Code_Camera && resultCode == RESULT_OK && data != null) {

                bitmap = (Bitmap) data.getExtras().get("data");
                imgAddPro.setImageBitmap(bitmap);
                AddImgPro(urlGetImg);
        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    private void AnhXa() {
        edtName = findViewById(R.id.et_add_tensanpham);
        edtPrice = findViewById(R.id.et_add_giatien);
        btnDongy = findViewById(R.id.btn_dongythem3);
        btnHuy = findViewById(R.id.btn_huythem3);
        imgAddPro = findViewById(R.id.img_add_product);
        tvPath = findViewById(R.id.tv_imgPath);
        btnCam= findViewById(R.id.btn_camera_pro);
        spinner_type =findViewById(R.id.spinner_type);
    }

    private String AddImgPro(String url) {
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        tvPath.setText(response);

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                String imageData = imageToString(bitmap);
                params.put("img", imageData);
                return params;
            }
        };
        requestQueue.add(stringRequest);
        return path;
    }

    private void AddPro(String url) {
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (response.trim().equals("success")) {
                            Toast.makeText(AddProductActivity.this, "Thêm thành công", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(AddProductActivity.this, chondouongActivity.class));
                            overridePendingTransition(R.anim.anim_enter,R.anim.anim_exit);
                        } else {
                            Toast.makeText(AddProductActivity.this, "Không thành công", Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(AddProductActivity.this, "Xảy ra lỗi!", Toast.LENGTH_SHORT).show();
                        Log.d("AAA", "Lỗi!\n" + error.toString());
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("tensp", edtName.getText().toString());
                params.put("giasp", edtPrice.getText().toString());
                params.put("loaisp", spinner_type.getSelectedItem().toString());
                params.put("img", tvPath.getText().toString());
                return params;
            }
        };
        requestQueue.add(stringRequest);
    }

    private String imageToString(Bitmap bitmap) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);
        byte[] imageBytes = outputStream.toByteArray();
        String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        return encodedImage;
    }
}
