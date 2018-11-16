package com.lucas.study.organizeme.Widget;

import android.support.design.widget.CoordinatorLayout;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.helper.StaticLabelsFormatter;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.DataPointInterface;
import com.jjoe64.graphview.series.LineGraphSeries;
import com.jjoe64.graphview.series.OnDataPointTapListener;
import com.jjoe64.graphview.series.Series;
import com.lucas.study.organizeme.Model.TimingModel;
import com.lucas.study.organizeme.R;
import com.lucas.study.organizeme.View.Message;

import net.steamcrafted.materialiconlib.MaterialDrawableBuilder;

import org.joda.time.DateTime;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Vector;



public class ChartProductivity {

    private int msg;
    private CoordinatorLayout layoutAttack;
    public Message msgBestInterval;
    public GraphView chart;
    public DataPoint[] valuesChart;
    public LineGraphSeries<DataPoint> series;
    Vector<Float> vectorHoursOfDay = new Vector<Float>();//vector for save the points of best time
    public DataPoint[] values;
    public List<TimingModel> timesInterval;
    public String actualDate;
    public ChartProductivity(){

    }

    public ChartProductivity(CoordinatorLayout layoutAttack){

        msgBestInterval = new Message(R.string.message_need_more_for_interval,
                MaterialDrawableBuilder.IconValue.TIMELAPSE,
                (CoordinatorLayout) layoutAttack.findViewById(R.id.messageBestInterval),
                R.color.colorPrimaryDark,
                96);

        this.layoutAttack = layoutAttack;

        chart = (GraphView) layoutAttack.findViewById(R.id.chartProductivityByHour);
        Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String formattedDate = df.format(c.getTime());
        actualDate = formattedDate;

        /* Here i start to generate date */


    }



    public CoordinatorLayout getLayoutAttack() {
        return layoutAttack;
    }

    public void setLayoutAttack(CoordinatorLayout layoutAttack) {
        this.layoutAttack = layoutAttack;
    }

    public void widgetShow(String[] args){

        msgBestInterval.setIconValue(MaterialDrawableBuilder.IconValue.TIMER);


        valuesChart = generatePoints(args);




        series = new LineGraphSeries<DataPoint>(valuesChart);
        if(actualDate!=args[0]){
            Log.i("actualDate","Entrou aqui");
            chart.removeAllSeries();
        }

        actualDate = args[0];
        series.setDataPointsRadius(10);
        series.setThickness(8);

       // series.resetData(values);
        chart.addSeries(series);



        chart.getViewport().setXAxisBoundsManual(true);
        chart.getViewport().setMinX(0);
        chart.getViewport().setMaxX(24);

        StaticLabelsFormatter staticLabelsFormatter = new StaticLabelsFormatter(chart);
        staticLabelsFormatter.setVerticalLabels(new String[] {"Ruim", "Média", "Boa"});
        staticLabelsFormatter.setHorizontalLabels(new String[] {"Madrugada", "Manhã", "Tarde", "Noite"});
        chart.getGridLabelRenderer().setHumanRounding(false);

        chart.getGridLabelRenderer().setHorizontalLabelsVisible(true);//hide de X-axis labels
        chart.getGridLabelRenderer().setLabelFormatter(staticLabelsFormatter);
        chart.getGridLabelRenderer().setVerticalAxisTitle("Produtividade");
        chart.getGridLabelRenderer().setHorizontalAxisTitle("Período");


        chart.getViewport().setScrollable(false);

        series.setOnDataPointTapListener(new OnDataPointTapListener() {
            @Override
            public void onTap(Series series, DataPointInterface dataPoint) {
                Toast.makeText(getLayoutAttack().getContext(), "Horário: "+ dataPoint.getX() + "h", Toast.LENGTH_SHORT).show();
            }

        });
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
        series.resetData(values);
        getLayoutAttack().setVisibility(View.VISIBLE);
    }


    public void widgetHide(){
        getLayoutAttack().setVisibility(View.GONE);
    }

    public DataPoint[] generatePoints(String[] args){


        if(args.length == 1){

            timesInterval = TimingModel.createTaskTime(args[0]);
        }else{

            timesInterval = TimingModel.createTaskTime(args[0],args[1]);
        }
        Log.i("Tamanho do chart",String.valueOf(timesInterval.size()));

        clearVector(vectorHoursOfDay);
        Log.i("Tamanho do vectorHour",String.valueOf(vectorHoursOfDay.size()));



        float biggerNumber = 0;

        for(int i = 0; i < timesInterval.size(); i++) {

            DateTime dt = new DateTime(timesInterval.get(i).getFinishedAt());

            float previousValue = vectorHoursOfDay.get(dt.getHourOfDay());
            float sumValue = (float) timesInterval.get(i).getProductivity() + previousValue;
            vectorHoursOfDay.add(dt.getHourOfDay(), sumValue);

            if(sumValue > biggerNumber){
                biggerNumber = sumValue;
            }
        }

        values = new DataPoint[vectorHoursOfDay.size()];
        Log.i("Tamanho do ValuesCHart", String.valueOf(values.length));


        for (int i=0; i<vectorHoursOfDay.size(); i++) {
            Integer xi = i;
            Float yi = vectorHoursOfDay.get(i);
            DataPoint v = new DataPoint(xi, yi);
            values[i] = v;
        }

        chart.getViewport().setYAxisBoundsManual(true);
        chart.getViewport().setMinY(0);
        chart.getViewport().setMaxY(biggerNumber);

        return values;
    }


    void clearVector(Vector<Float> vector){

        if (vector.size() == 0) {

            for(int i = 0; i < 24; i++){
                vector.add(i, (float) 0);
            }

        }else{
                vector.clear();

        }

    }




}
