package com.example.cot;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class TableAdapter extends BaseAdapter {
    private LoadTableActivity context;
    private int layout;
    private List<Table> TableList;

    public TableAdapter(LoadTableActivity context, int layout, List<Table> tableList) {
        this.context = context;
        this.layout = layout;
        TableList = tableList;
    }

    @Override
    public int getCount() {
        return TableList.size();
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
        TextView tv_table;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        ViewHolder holder;
        if (view == null) {
            holder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(layout, null);
            holder.tv_table = view.findViewById(R.id.tv_table);

            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        final Table table = TableList.get(position);
        holder.tv_table.setText("SỐ: "+table.getId());
        holder.tv_table.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ChonOrderActivity.class);
                intent.putExtra("table", table);
                context.startActivity(intent);
            }
        });

        return view;
    }
}