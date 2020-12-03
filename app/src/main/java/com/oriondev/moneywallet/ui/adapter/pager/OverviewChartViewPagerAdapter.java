/*
 * Copyright (c) 2018.
 *
 * This file is part of MoneyWallet.
 *
 * MoneyWallet is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * MoneyWallet is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with MoneyWallet.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.oriondev.moneywallet.ui.adapter.pager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.charts.RadarChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.RadarData;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.oriondev.moneywallet.R;
import com.oriondev.moneywallet.model.OverviewData;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by andrea on 17/08/18.
 */
public class OverviewChartViewPagerAdapter extends PagerAdapter {

    private static final int CHART_COUNT = 3;

    private static final int POSITION_BAR_CHART = 0;
    private static final int POSITION_LINE_CHART = 1;
    private static final int POSITION_RADAR_CHART = 2;

    private OverviewData mOverviewData;

    @NonNull
    public Object instantiateItem(@NonNull final ViewGroup container, int position) {
        LayoutInflater inflater = LayoutInflater.from(container.getContext());
        View view = null;
        final Map<Float, String> month = new HashMap<Float, String>() {{
            put((float) 0.0, container.getContext().getResources().getString(R.string.january));
            put((float) 1.0, container.getContext().getResources().getString(R.string.february));
            put((float) 2.0, container.getContext().getResources().getString(R.string.march));
            put((float) 3.0, container.getContext().getResources().getString(R.string.april));
            put((float) 4.0, container.getContext().getResources().getString(R.string.may));
            put((float) 5.0, container.getContext().getResources().getString(R.string.june));
            put((float) 6.0, container.getContext().getResources().getString(R.string.july));
            put((float) 7.0, container.getContext().getResources().getString(R.string.august));
            put((float) 8.0, container.getContext().getResources().getString(R.string.september));
            put((float) 9.0, container.getContext().getResources().getString(R.string.october));
            put((float) 10.0, container.getContext().getResources().getString(R.string.november));
            put((float) 11.0, container.getContext().getResources().getString(R.string.december));
        }};
        switch (position) {
            case POSITION_BAR_CHART:
                view = inflater.inflate(R.layout.adapter_bar_chart_item, container, false);
                BarChart barChart = view.findViewById(R.id.bar_chart_view);
                barChart.getDescription().setEnabled(false);
                barChart.setDrawBarShadow(false);
                barChart.setDrawGridBackground(false);
                barChart.setDrawMarkers(true);
                barChart.setDragEnabled(false);
                barChart.setScaleEnabled(false);
                if (mOverviewData != null) {
                    BarData barData = mOverviewData.getBarData();
                    barChart.setData(barData);
                    barChart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
                        @Override
                        public void onValueSelected(Entry e, Highlight h) {
                            String monthStat = month.get(e.getX()) + " : " + e.getY();
                            Toast.makeText(container.getContext(), monthStat, Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onNothingSelected() {

                        }
                    });
                    if (barData != null) {
                        barData.setHighlightEnabled(true);
                        XAxis xAxis = barChart.getXAxis();
                        xAxis.setGranularity(1f);
                        if (barData.getDataSetCount() > 1) {
                            xAxis.setCenterAxisLabels(true);
                            xAxis.setAxisMinimum(0f);
                        } else {
                            xAxis.setAxisMinimum(-1f);
                        }
                        xAxis.setAxisMaximum(mOverviewData.getPeriodCount());
                        xAxis.setValueFormatter(new IAxisValueFormatter() {

                            @Override
                            public String getFormattedValue(float value, AxisBase axis) {
                                return String.valueOf((int) value + 1);
                            }

                        });
                        YAxis leftAxis = barChart.getAxisLeft();
                        leftAxis.setSpaceTop(35f);
                        barChart.getAxisRight().setEnabled(false);
                        barData.setValueTextColor(xAxis.getTextColor());
                    }
                }
                break;
            case POSITION_LINE_CHART:
                view = inflater.inflate(R.layout.adapter_line_chart_item, container, false);
                LineChart lineChart = view.findViewById(R.id.line_chart_view);
                lineChart.getDescription().setEnabled(false);
                lineChart.setDragEnabled(false);
                lineChart.setScaleEnabled(false);
                lineChart.setHighlightPerDragEnabled(true);
                lineChart.setHighlightPerTapEnabled(true);
                lineChart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
                    @Override
                    public void onValueSelected(Entry e, Highlight h) {
                        String monthStat = month.get(e.getX()) + " : " + e.getY();
                        Toast.makeText(container.getContext(), monthStat, Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onNothingSelected() {

                    }
                });
                if (mOverviewData != null) {
                    LineData lineData = mOverviewData.getLineData();
                    lineChart.setData(lineData);
                    if (lineData != null) {
                        lineData.setHighlightEnabled(true);
                        XAxis xAxis = lineChart.getXAxis();
                        xAxis.setGranularity(1f);
                        xAxis.setAxisMinimum(-1f);
                        xAxis.setAxisMaximum(mOverviewData.getPeriodCount());
                        xAxis.setValueFormatter(new IAxisValueFormatter() {

                            @Override
                            public String getFormattedValue(float value, AxisBase axis) {
                                return String.valueOf((int) value + 1);
                            }

                        });
                        YAxis leftAxis = lineChart.getAxisLeft();
                        leftAxis.setSpaceTop(35f);
                        lineChart.getAxisRight().setEnabled(false);
                        lineData.setValueTextColor(xAxis.getTextColor());
                    }
                }
                break;
            case POSITION_RADAR_CHART:
                view = inflater.inflate(R.layout.adapter_radar_chart_item, container, false);
                RadarChart radarChart = view.findViewById(R.id.radar_chart_view);
                radarChart.getDescription().setEnabled(false);
                radarChart.setRotationEnabled(false);
                if (mOverviewData != null) {
                    RadarData radarData = mOverviewData.getRadarData();
                    radarChart.setData(radarData);
                    if (radarData != null) {
                        XAxis xAxis = radarChart.getXAxis();
                        radarData.setValueTextColor(xAxis.getTextColor());
                    }
                }
                break;
        }
        if (view == null) {
            throw new IllegalStateException("OverviewChartViewPagerAdapter has not correctly initialized the view at position: " + position);
        }
        container.addView(view);
        return view;
    }

    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }

    @Override
    public int getCount() {
        return CHART_COUNT;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @Override
    public int getItemPosition(@NonNull Object object) {
        return POSITION_NONE;
    }

    public void setData(OverviewData data) {
        mOverviewData = data;
        notifyDataSetChanged();
    }
}