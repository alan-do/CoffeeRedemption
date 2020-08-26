package com.example.cot;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class StatisticAdapter extends BaseAdapter {
    private StatisticalActivity context;
    private int layout;
    private List<Statistic> statisticList;

    public StatisticAdapter(StatisticalActivity context, int layout, List<Statistic> statisticList) {
        this.context = context;
        this.layout = layout;
        this.statisticList = statisticList;
    }

    @Override
    public int getCount() {
        return statisticList.size();
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
        TextView tv_today_order, tv_today_revenue, tv_thismonth_order, tv_thismonth_revenue, tv_bestseller;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        ViewHolder viewHolder;
        if(view ==null){
            viewHolder = new StatisticAdapter.ViewHolder();
            viewHolder.tv_today_order = view.findViewById(R.id.tv_number_of_order);
            viewHolder.tv_today_revenue = view.findViewById(R.id.tv_day_revenue);
            viewHolder.tv_bestseller = view.findViewById(R.id.tv_best_seller);
            viewHolder.tv_thismonth_order= view.findViewById(R.id.tv_month_number_of_order);
            viewHolder.tv_thismonth_revenue= view.findViewById(R.id.tv_month_revenue);
            view.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) view.getTag();
        }
        Statistic statistic= statisticList.get(position);

        return view;
    }
}
