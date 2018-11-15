package com.lucas.study.organizeme.Widget;

import android.content.res.Resources;
import android.graphics.Color;
import android.support.design.widget.CoordinatorLayout;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.LegendRenderer;
import com.jjoe64.graphview.helper.DateAsXAxisLabelFormatter;
import com.jjoe64.graphview.helper.StaticLabelsFormatter;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.DataPointInterface;
import com.jjoe64.graphview.series.LineGraphSeries;
import com.jjoe64.graphview.series.OnDataPointTapListener;
import com.jjoe64.graphview.series.Series;
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

    public LineGraphSeries<DataPoint> series;


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

        float biggerNumber = 0;

        for(int i = 0; i < timesInterval.size(); i++) {

            DateTime dt = new DateTime(timesInterval.get(i).getFinishedAt());

            float previousValue = vectorHoursOfDay.get(dt.getHourOfDay());
            float sumValue = (float) 1 + previousValue;
            vectorHoursOfDay.add(dt.getHourOfDay(), sumValue);

            if(sumValue > biggerNumber){
                biggerNumber = sumValue;
            }
        }

        DataPoint[] values = new DataPoint[vectorHoursOfDay.size()];
        for (int i=0; i<vectorHoursOfDay.size(); i++) {
            Integer xi = i;
            Float yi = vectorHoursOfDay.get(i);
            DataPoint v = new DataPoint(xi, yi);
            values[i] = v;
        }

        series = new LineGraphSeries<DataPoint>(values);
        series.setDataPointsRadius(10);
        series.setThickness(8);

        chart.addSeries(series);


        chart.getViewport().setYAxisBoundsManual(true);
        chart.getViewport().setMinY(0);
        chart.getViewport().setMaxY(biggerNumber);


        chart.getViewport().setXAxisBoundsManual(true);
        chart.getViewport().setMinX(0);
        chart.getViewport().setMaxX(24);

        StaticLabelsFormatter staticLabelsFormatter = new StaticLabelsFormatter(chart);
        staticLabelsFormatter.setVerticalLabels(new String[] {"Ruim", "Média", "Boa"});
        chart.getGridLabelRenderer().setHumanRounding(false);

        chart.getGridLabelRenderer().setHorizontalLabelsVisible(false);//hide de X-axis labels
        chart.getGridLabelRenderer().setLabelFormatter(staticLabelsFormatter);
        chart.getGridLabelRenderer().setVerticalAxisTitle("Produtividade");


        chart.getViewport().setScrollable(false);

        series.setOnDataPointTapListener(new OnDataPointTapListener() {
            @Override
            public void onTap(Series series, DataPointInterface dataPoint) {
                Toast.makeText(getLayoutAttack().getContext(), "Horário: "+ dataPoint.getX() + "h", Toast.LENGTH_SHORT).show();
            }
        });
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
            vector.add(i, (float) 0.1);
        }

    }




}
