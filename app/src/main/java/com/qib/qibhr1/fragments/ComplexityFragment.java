package com.qib.qibhr1.fragments;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.qib.qibhr1.R;
import com.qib.qibhr1.charting.charts.LineChart;
import com.qib.qibhr1.charting.components.Legend;
import com.qib.qibhr1.charting.components.XAxis;
import com.qib.qibhr1.charting.components.YAxis;


public class ComplexityFragment extends SimpleFragment {

    public static Fragment newInstance() {
        return new ComplexityFragment();
    }

    private LineChart mChart;
    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.frag_simple_line, container, false);
        
        mChart = (LineChart) v.findViewById(R.id.lineChart1);
        
        mChart.setDescription("");

        mChart.setDrawGridBackground(false);
        
        mChart.setData(getComplexity());
        mChart.animateX(3000);
        
        Typeface tf = Typeface.createFromAsset(getActivity().getAssets(), "OpenSans-Light.ttf");
        
        Legend l = mChart.getLegend();
        l.setTypeface(tf);
        
        YAxis leftAxis = mChart.getAxisLeft();
        leftAxis.setTypeface(tf);
        
        mChart.getAxisRight().setEnabled(false);
        
        XAxis xAxis = mChart.getXAxis();
        xAxis.setEnabled(false);
        
        return v;
    }
}
