package com.example.cot;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import static com.example.cot.CheckoutBagActivity.Total;


public class CheckoutAdapter extends BaseAdapter {

    private CheckoutBagActivity context;
    private int layout;
    private ArrayList<CheckoutBag> checkoutBagArray;

    public CheckoutAdapter(CheckoutBagActivity context, int layout, ArrayList<CheckoutBag> checkoutBagArray) {
        this.context = context;
        this.layout = layout;
        this.checkoutBagArray = checkoutBagArray;
    }
    @Override
    public int getCount() {
        return checkoutBagArray.size();
    }

    @Override
    public Object getItem(int position) {
        return checkoutBagArray.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }
    private class ViewHolder{
        TextView txt_name,txt_price,txt_sl;
        Button btnCong,btnTru;
        ImageView imgHinh;
    }
    @Override
    public View getView(final int position, View view, ViewGroup parent) {
        final ViewHolder holder;
        if(view==null){
            holder= new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view =inflater.inflate(layout,null);
            holder.txt_name= view.findViewById(R.id.txt_name);
            holder.txt_price=view.findViewById(R.id.txt_price);
            holder.txt_sl=view.findViewById(R.id.txt_sl);
            holder.btnCong=view.findViewById(R.id.btn_bag_cong);
            holder.btnTru=view.findViewById(R.id.btn_bag_tru);
            holder.imgHinh=view.findViewById(R.id.img_hinh5);

            view.setTag(holder);
        }else {
            holder= (CheckoutAdapter.ViewHolder) view.getTag();
        }
        final DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        final  CheckoutBag checkoutBag= checkoutBagArray.get(position);

        if(checkoutBag!= null){
            Picasso.with(context).load(checkoutBag.getUrl()).into(holder.imgHinh);
            holder.txt_name.setText(checkoutBag.getName());

            holder.txt_price.setText(decimalFormat.format(checkoutBag.getPrice())+"");
            holder.txt_sl.setText(checkoutBag.getSl()+"");
            int sl = Integer.parseInt(holder.txt_sl.getText().toString());
            if (sl>=10){
                holder.btnTru.setVisibility(View.VISIBLE);
                holder.btnCong.setVisibility(View.INVISIBLE);

            }else if(sl<=1){
                holder.btnTru.setVisibility(View.INVISIBLE);

            }else if(sl>=1){
                holder.btnTru.setVisibility(View.VISIBLE);
                holder.btnCong.setVisibility(View.VISIBLE);
            }
        }
        else {
            Toast.makeText(context, "Giỏ hàng trống", Toast.LENGTH_SHORT).show();
        }

        holder.btnTru.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int slmoi= Integer.parseInt(holder.txt_sl.getText().toString())-1;
                int slht = Integer.parseInt(ChonOrderActivity.checkoutBagArrayList.get(position).getSl());
                int giaht = checkoutBagArray.get(position).getPrice();
                checkoutBagArray.get(position).setSl(slmoi+"");
                int giamoi = (giaht*slmoi)/slht;
                checkoutBagArray.get(position).setPrice(giamoi);
                holder.txt_sl.setText(slmoi+"");
                holder.txt_price.setText(decimalFormat.format(giamoi)+"");
                Total();
                if (slmoi>=10){
                    holder.btnTru.setVisibility(View.VISIBLE);
                    holder.btnCong.setVisibility(View.INVISIBLE);

                }else if(slmoi<=1){
                    holder.btnTru.setVisibility(View.INVISIBLE);

                }else if(slmoi>=1){
                    holder.btnTru.setVisibility(View.VISIBLE);
                    holder.btnCong.setVisibility(View.VISIBLE);
                }
            }
        });
        holder.btnCong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int slmoi= Integer.parseInt(holder.txt_sl.getText().toString())+1;
                int slht = Integer.parseInt(checkoutBagArray.get(position).getSl());
                int giaht = checkoutBagArray.get(position).getPrice();
                checkoutBagArray.get(position).setSl(slmoi+"");
                int giamoi = (giaht*slmoi)/slht;
                checkoutBagArray.get(position).setPrice(giamoi);
                holder.txt_sl.setText(slmoi+"");
                holder.txt_price.setText(decimalFormat.format(giamoi)+"");
                Total();
                if (slmoi>=10){
                    holder.btnTru.setVisibility(View.VISIBLE);
                    holder.btnCong.setVisibility(View.INVISIBLE);

                }else if(slmoi<=1){
                    holder.btnTru.setVisibility(View.INVISIBLE);

                }else if(slmoi>=1){
                    holder.btnTru.setVisibility(View.VISIBLE);
                    holder.btnCong.setVisibility(View.VISIBLE);
                }
            }
        });
        Animation animation= AnimationUtils.loadAnimation(context,R.anim.scale_list);
        view.startAnimation(animation);

        return view;
    }

}