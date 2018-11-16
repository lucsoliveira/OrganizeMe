package com.lucas.study.organizeme.Page;

import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.lucas.study.organizeme.R;
import com.lucas.study.organizeme.Widget.BestChoiceWithHumor;
import com.lucas.study.organizeme.Widget.ChartProductivity;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;

import xyz.rimon.adateswitcher.DateSwitcher;

public class Stats extends Fragment{

    public CoordinatorLayout chartView, layoutWidgetBestChoice, layoutWidgetBestInterval;
    public BestChoiceWithHumor widgetBestChoiceForHumor;
    public ChartProductivity widgetChartProductivity;
    public SimpleDateFormat df;
    public DateSwitcher mDateSwitcher;
    public DateTimeFormatter formatter;
    public Calendar c;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.tab_stats, container, false);

        layoutWidgetBestChoice = (CoordinatorLayout) rootView.findViewById(R.id.widgetBestChoice);
        layoutWidgetBestInterval = (CoordinatorLayout) rootView.findViewById(R.id.widgetBestInterval);
        mDateSwitcher = rootView.findViewById(R.id.mDateSwitcher);
        widgetBestChoiceForHumor = new BestChoiceWithHumor(layoutWidgetBestChoice);
        widgetChartProductivity = new ChartProductivity(layoutWidgetBestInterval);


        c = Calendar.getInstance();

        df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        formatter = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss");

        this.mDateSwitcher.setOnDateChangeListener(new DateSwitcher.OnDateChangeListener() {
            @Override
            public void onChange(Map<DateSwitcher.DateRange, Date> dateRange) {

                widgetBestChoiceForHumor.widgetHide();
                widgetChartProductivity.widgetHide();

                Date topDate = dateRange.get(DateSwitcher.DateRange.TOP_DATE);
                Date bottomDate = dateRange.get(DateSwitcher.DateRange.BOTTOM_DATE);

                String[] intervalDate = new String[2];
                String formattedDateMin = df.format(bottomDate);
                String formattedDateMax = df.format(topDate);

                intervalDate[0] = formattedDateMin;
                intervalDate[1] = formattedDateMax;
                Log.i("FormattedDateMin", formattedDateMin);

                widgetBestChoiceForHumor.widgetShow();
                widgetChartProductivity.widgetShow(intervalDate);
            }
        });

        Date now = c.getTime();

        String dateNow = df.format(now);
        String[] dNow = new String[1];
        dNow[0] = dateNow;

        Log.i("DateNow", dateNow);

        widgetBestChoiceForHumor.widgetShow();
        widgetChartProductivity.widgetShow(dNow);


        return rootView;
    }

}
