package com.example.cot;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.util.List;

public class ProductAdapter extends BaseAdapter {
    private chondouongActivity context;
    private int layout;
    private List<Product> productList;

    public ProductAdapter(chondouongActivity context, int layout, List<Product> productList) {
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
        TextView txtName, txtPrice, txtProducttype, txtid;
        ImageView imgHinh;
        ImageView imgEdit, imgDelete;
    }
    @Override
    public View getView(int position, View view, ViewGroup parent) {
        ViewHolder holder;
        if (view == null) {
            holder = new ProductAdapter.ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(layout, null);

            holder.txtName = (TextView) view.findViewById(R.id.txtname);
            holder.txtPrice = (TextView) view.findViewById(R.id.txtprice);
            holder.txtProducttype = (TextView) view.findViewById(R.id.txtProducttype);
            holder.imgEdit = (ImageView) view.findViewById(R.id.imageviewEdit2);
            holder.imgDelete = (ImageView) view.findViewById(R.id.imageviewDelete2);
            holder.txtid = (TextView) view.findViewById(R.id.tv_idsp);
            holder.imgHinh=(ImageView) view.findViewById(R.id.imghinh);
            view.setTag(holder);
        }else{
            holder=(ProductAdapter.ViewHolder)view.getTag();
        }
        final Product product= productList.get(position);

        holder.txtName.setText(product.getName());
        holder.txtPrice.setText("Giá tiền: "+product.getPrice());
        holder.txtProducttype.setText("Loại sản phẩm: "+ product.getProducttype());
        holder.txtid.setText(product.getId()+"");


        Picasso.with(context).load(product.getImg()).into(holder.imgHinh);

        holder.imgEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, UpdateProActivity.class);
                intent.putExtra("dataProduct",product);
                context.startActivity(intent);
                context.overridePendingTransition(R.anim.anim_enter,R.anim.anim_exit);
            }
        });
        holder.imgDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                XacNhanXoa(product.getId());
            }
        });

        Animation animation= AnimationUtils.loadAnimation(context,R.anim.scale_list);
        view.startAnimation(animation);
        return view;
    }


    private void XacNhanXoa(final int id) {
        AlertDialog.Builder dialogXoa = new AlertDialog.Builder(context);
        dialogXoa.setMessage("Bạn có chắc muốn làm chuyện này không?");
        dialogXoa.setPositiveButton("Chắc như bắp!", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                context.DeletePro(id);
            }
        });
        dialogXoa.setNegativeButton("Thôi! Nghĩ lại.", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        dialogXoa.show();
    }
}
