package com.lucas.study.organizeme.Page;

import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.lucas.study.organizeme.R;
import com.lucas.study.organizeme.Widget.BestChoiceWithHumor;
import com.lucas.study.organizeme.Widget.BestInterval;

public class Stats extends Fragment{

    public CoordinatorLayout messageChart, messageBestChoice, chartView, layoutWidgetBestChoice, layoutWidgetBestInterval;
    public BestChoiceWithHumor widgetBestChoiceForHumor;
    public BestInterval widgetBestInterval;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.tab_stats, container, false);

        layoutWidgetBestChoice = (CoordinatorLayout) rootView.findViewById(R.id.widgetBestChoice);
        layoutWidgetBestInterval = (CoordinatorLayout) rootView.findViewById(R.id.widgetBestInterval);

        widgetBestChoiceForHumor = new BestChoiceWithHumor(layoutWidgetBestChoice);
        widgetBestInterval = new BestInterval(layoutWidgetBestInterval);


        widgetBestChoiceForHumor.widgetShow();
        widgetBestInterval.widgetShow();

        return rootView;
    }

}
