package com.example.cot;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class ChonOrderAdapter extends BaseAdapter {
    private Context context;
    private int layout;
    private List<Product> productList;

    public ChonOrderAdapter(Context context, int layout, List<Product> productList) {
        this.context = context;
        this.layout = layout;
        this.productList = productList;
    }

    @Override
    public int getCount() {
        return productList.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    private class ViewHolder {
        TextView txtName, txtPrice,tv_sl;
        Spinner spinner_sl;
        Button btn_detail;
        ImageView imgHinh;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        final ViewHolder holder;
        if (view == null) {
            holder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view= inflater.inflate(R.layout.chonorder,null,false);
            holder.txtName = (TextView) view.findViewById(R.id.txtname);
            holder.txtPrice = (TextView) view.findViewById(R.id.txtprice);
            holder.imgHinh = (ImageView) view.findViewById(R.id.imghinh1);
            holder.btn_detail =view.findViewById(R.id.btn_product_detail);
            view.setTag(holder);
        }else{
            holder=(ViewHolder)view.getTag();
        }

        final Product product=productList.get(i);

        holder.txtName.setText(product.getName());
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        holder.txtPrice.setText("Giá tiền: " + decimalFormat.format(product.getPrice())+" Đ");
        holder.btn_detail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ProductDetailActivity.class);
                intent.putExtra("data",  product);
                context.startActivity(intent);
            }
        });
        Picasso.with(context).load(product.getURL()).into(holder.imgHinh);
        Animation animation= AnimationUtils.loadAnimation(context,R.anim.scale_list);
        view.startAnimation(animation);
        return view;
    }
}