package com.example.cot;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
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

import java.security.Timestamp;
import java.sql.Time;
import java.util.Date;
import java.util.List;
import java.util.Timer;

public class OrderAdaptor extends BaseAdapter {
    private OrderListActivity context;
    private int layout;
    private List<Order> OrderList;

    public OrderAdaptor(OrderListActivity context, int layout, List<Order> orderList) {
        this.context = context;
        this.layout = layout;
        OrderList = orderList;
    }

    @Override
    public int getCount() {
        return OrderList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    private class ViewHolder {
        TextView tv_id, tv_table_id, tv_status;
        ImageView img_detail, img_done, img_cancle;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        ViewHolder holder;
        if (view == null) {
            holder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(layout, null);
            holder.tv_id = view.findViewById(R.id.tv_order_id);
            holder.tv_table_id = view.findViewById(R.id.tv_table_id);
            holder.tv_status = view.findViewById(R.id.tv_status);
            holder.img_detail = view.findViewById(R.id.img_detail);
            holder.img_done = view.findViewById(R.id.img_done);
            holder.img_cancle = view.findViewById(R.id.img_cancle);

            view.setTag(holder);
        } else {
            holder = (OrderAdaptor.ViewHolder) view.getTag();
        }
        final Order order = OrderList.get(position);
        holder.tv_id.setText("ID: "+order.getId());
        holder.tv_table_id.setText("Bàn số: " + order.getTable_id());

        if ((order.getStatus()+"").equals("done")) {
            holder.tv_status.setText("Trạng thái: "+ order.getStatus());
            holder.tv_status.setBackgroundColor(Color.parseColor("#4CAF50"));
        }
        else if((order.getStatus()+"").equals("cancle")){
            holder.tv_status.setText("Trạng thái: "+ order.getStatus());
            holder.tv_status.setBackgroundColor(Color.parseColor("#D51717"));
        }
        else
        {
            holder.tv_status.setText("Trạng thái: "+ order.getStatus());
            holder.tv_status.setBackgroundColor(Color.parseColor("#F4D03F"));
        }
        holder.img_detail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, OrderDetailActivity.class);
                intent.putExtra("order_id", order);
                context.startActivity(intent);
            }
        });
        holder.img_done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (order.getStatus().equals("done")){
                    Toast.makeText(context, "Đơn hàng đã thanh toán trước đó", Toast.LENGTH_SHORT).show();
                }else if(order.getStatus().equals("cancle")){
                    Toast.makeText(context, "Không thể hoàn thành đơn hàng đã hủy", Toast.LENGTH_SHORT).show();
                }else{
                    MakeItDone(order.getId());}
            }
        });
        holder.img_cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(order.getStatus().equals("done")){
                    Toast.makeText(context, "Không thể cancle vì đơn hàng đã thanh toán", Toast.LENGTH_SHORT).show();
                }else if(order.getStatus().equals("cancle")){
                    Toast.makeText(context, "Đơn hàng đã cancle trước đó", Toast.LENGTH_SHORT).show();
                }else {
                    XacNhanXoa(order.getId());
                }
            }
        });
        Animation animation= AnimationUtils.loadAnimation(context,R.anim.scale_list);
        view.startAnimation(animation);
        return view;
    }
    private  void MakeItDone(final int ido){
        AlertDialog.Builder dialog= new AlertDialog.Builder(context);
        dialog.setMessage("Xác nhận hoàn thành?");
        dialog.setPositiveButton("Xác nhận!", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                context.MakeItDoneclass(ido);
            }
        });
        dialog.setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        dialog.show();
    }
    private  void XacNhanXoa(final int ido){
        AlertDialog.Builder dialogXoa= new AlertDialog.Builder(context);
        dialogXoa.setMessage("Xác nhận cancle Order?");
        dialogXoa.setPositiveButton("Xác nhận!", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                context.DeleteOrder(ido);
            }
        });
        dialogXoa.setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        dialogXoa.show();
    }

}
