package com.example.cot;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;

import java.util.ArrayList;

public class StatisticActivity extends AppCompatActivity {
//    LineChart mpLineChart;
    @Override
    public void onBackPressed() {
        Intent intent= new Intent(StatisticActivity.this,HomeActivity.class);
        startActivity(intent);
    }
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistic);
//        mpLineChart=(LineChart) findViewById(R.id.line_chart);
//        LineDataSet lineDataSet1 = new LineDataSet(dataValues1(),"Data Set 1");
//        ArrayList<ILineDataSet> dataSets = new ArrayList<>();
//
////        Toast.makeText(this, lineDataSet1.toString(), Toast.LENGTH_LONG).show();
//
//        dataSets.add(lineDataSet1);
//        LineData data = new LineData(dataSets);
//        Toast.makeText(this, data.toString(), Toast.LENGTH_LONG).show();
//        mpLineChart.setData(data);
//        mpLineChart.invalidate();
//    }
//    private ArrayList<Entry> dataValues1(){
//        ArrayList<Entry> dataVals = new ArrayList<Entry>();
//        dataVals.add(new Entry(0,20));
//        dataVals.add(new Entry(2,22));
//        dataVals.add(new Entry(3,2));
//        dataVals.add(new Entry(4,10));
//        dataVals.add(new Entry(5,30));
//        return dataVals;

    }
}
