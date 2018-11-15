package com.lucas.study.organizeme.Widget;

import android.graphics.Color;
import android.support.design.widget.CoordinatorLayout;
import android.util.Log;
import android.view.View;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.LegendRenderer;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;
import com.lucas.study.organizeme.MainActivity;
import com.lucas.study.organizeme.Model.TimingModel;
import com.lucas.study.organizeme.R;
import com.lucas.study.organizeme.View.Message;

import net.steamcrafted.materialiconlib.MaterialDrawableBuilder;

import org.joda.time.DateTime;
import java.util.List;
import java.util.Vector;


public class BestInterval {

    private int msg;
    private CoordinatorLayout layoutAttack;
    public Message msgBestInterval;
    public GraphView chart;


    Vector<Float> vectorHoursOfDay = new Vector<Float>();//vector for save the points of best time

    public BestInterval(){

    }

    public BestInterval(CoordinatorLayout layoutAttack){

        msgBestInterval = new Message(R.string.message_need_more_for_interval,
                MaterialDrawableBuilder.IconValue.TIMELAPSE,
                (CoordinatorLayout) layoutAttack.findViewById(R.id.messageBestInterval),
                R.color.colorPrimaryDark,
                96);

        this.layoutAttack = layoutAttack;

         chart = (GraphView) layoutAttack.findViewById(R.id.chartProductivityByHour);

        List<TimingModel> timesInterval = TimingModel.createTaskTimeProductivity("2","50");

        clearVector(vectorHoursOfDay);

        for(int i = 0; i < timesInterval.size(); i++) {

            DateTime dt = new DateTime(timesInterval.get(i).getFinishedAt());

            float previousValue = vectorHoursOfDay.get(dt.getHourOfDay());
            vectorHoursOfDay.add(dt.getHourOfDay(), (float) 1 + previousValue);
        }

        DataPoint[] values = new DataPoint[vectorHoursOfDay.size()];
        for (int i=0; i<vectorHoursOfDay.size(); i++) {
            Integer xi = i;
            Float yi = vectorHoursOfDay.get(i);
            DataPoint v = new DataPoint(xi, yi);
            values[i] = v;
        }

        LineGraphSeries<DataPoint> series = new LineGraphSeries<DataPoint>(values);
        series.setDataPointsRadius(10);
        series.setThickness(8);

        chart.addSeries(series);
        // activate horizontal zooming and scrolling
        chart.getViewport().setScalable(true);
        // activate horizontal scrolling
        chart.getViewport().setScrollable(true);
        // activate horizontal and vertical zooming and scrolling
        chart.getViewport().setScalableY(true);
        // activate vertical scrolling
        chart.getViewport().setScrollableY(true);

    }



    public CoordinatorLayout getLayoutAttack() {
        return layoutAttack;
    }

    public void setLayoutAttack(CoordinatorLayout layoutAttack) {
        this.layoutAttack = layoutAttack;
    }

    public void widgetShow(){

        msgBestInterval.setIconValue(MaterialDrawableBuilder.IconValue.TIMER);


        /*
        if(bestInterval() == "0"){
            msgBestInterval.showMessageViewWithString("Nao ha cronometragens suficientes. Continue usando o aplicativo.");
            msgBestInterval.showMessageView();
        }
        /*
        else {
            msgBestInterval.showMessageViewWithString("Com base em suas estatísticas, o intervalo do dia em que você é mais produtivo ocorre entre " + bestInterval());
        }

*/
        getLayoutAttack().setVisibility(View.VISIBLE);
    }


    public void widgetHide(){
        getLayoutAttack().setVisibility(View.GONE);
    }


    void clearVector(Vector<Float> vector){

        for(int i = 0; i < 24; i++){
            vector.add(i, (float) 0);
        }

    }


}
