package com.example.cot;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.util.List;

public class OrderDetailAdapter extends BaseAdapter {
    Context context;
    private int layout;
    private List<OrderDetail> OrderDetailList;

    public OrderDetailAdapter(Context context, int layout, List<OrderDetail> orderList) {
        this.context = context;
        this.layout = layout;
        OrderDetailList = orderList;
    }

    @Override
    public int getCount() {
        return OrderDetailList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }
    private class ViewHolder{
        TextView tv_product_name, tv_quantity, tv_product_price;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder;
        if(view ==null){
            viewHolder = new OrderDetailAdapter.ViewHolder();
            LayoutInflater inflater= (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(layout,null);
            viewHolder.tv_product_name = view.findViewById(R.id.tv_product_name);
            viewHolder.tv_quantity = view.findViewById(R.id.tv_quantity);
            viewHolder.tv_product_price = view.findViewById(R.id.tv_product_price);
            view.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) view.getTag();
        }
        OrderDetail orderDetail = OrderDetailList.get(position);
        viewHolder.tv_product_name.setText("Tên món: "+ orderDetail.getProduct_name());
        viewHolder.tv_quantity.setText("Số lượng: " + orderDetail.getQuantity());
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        viewHolder.tv_product_price.setText("Giá: "+decimalFormat.format(orderDetail.getProduct_price())+ " Đ");

        Animation animation= AnimationUtils.loadAnimation(context,R.anim.scale_list);
        view.startAnimation(animation);
        return view;
    }
}
