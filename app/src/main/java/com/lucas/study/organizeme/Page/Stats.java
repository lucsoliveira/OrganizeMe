package com.lucas.study.organizeme.Page;

import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.lucas.study.organizeme.View.Message;
import com.lucas.study.organizeme.R;
import com.lucas.study.organizeme.Model.TaskModel;
import com.lucas.study.organizeme.Model.TimingModel;

import net.steamcrafted.materialiconlib.MaterialDrawableBuilder;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import lecho.lib.hellocharts.formatter.SimpleAxisValueFormatter;
import lecho.lib.hellocharts.model.Axis;
import lecho.lib.hellocharts.model.Line;
import lecho.lib.hellocharts.model.LineChartData;
import lecho.lib.hellocharts.model.PointValue;
import lecho.lib.hellocharts.model.ValueShape;
import lecho.lib.hellocharts.model.Viewport;
import lecho.lib.hellocharts.util.ChartUtils;
import lecho.lib.hellocharts.view.LineChartView;

public class Stats extends Fragment implements AdapterView.OnItemSelectedListener {

    /* CHART */
    private LineChartView chart;
    private LineChartData data;
    private int numberOfLines;

    public List<PointValue> values;
    List<String> activesHumor;
    List<Integer> intHumor;
    private boolean hasAxes = false, hasAxesNames = false, hasLines = true, hasPoints = false;
    private ValueShape shape = ValueShape.CIRCLE;
    private boolean isFilled = false, hasLabels = false, isCubic = false, hasLabelForSelected = false, pointsHaveDifferentColor;
    private boolean hasGradientToTransparent = false;
    public CoordinatorLayout messageChart, messageBestChoice, chartView, messageBestInterval;
    public Message msgBestChoice, msgNoTasks, msgBestInterval;
    public ImageView iconCardBest1, iconCardBest2, iconCardBest3;
    public TextView textCardBest1, textCardBest2, textCardBest3;
    public List<TaskModel> listTasks = TaskModel.findWithQuery(TaskModel.class,"Select * from Task_Model where status = ? order by id DESC", "1");
    /* END CHART */

    final List<TimingModel> times = TimingModel.createTaskTimeListWithLimit("6");

    List<Long> idActiveTasks = new ArrayList<Long>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.tab_stats, container, false);
        chart = (LineChartView) rootView.findViewById(R.id.chart);

        chartView = (CoordinatorLayout) rootView.findViewById(R.id.chartView);
        messageChart = (CoordinatorLayout) rootView.findViewById(R.id.messageChart);
        messageBestChoice = (CoordinatorLayout) rootView.findViewById(R.id.messageBestChoice);
        messageBestInterval = (CoordinatorLayout) rootView.findViewById(R.id.messageBestInterval);

        /* CARD BEST CHOICE */
        textCardBest1 = (TextView) rootView.findViewById(R.id.text1CardBestChoide);
        textCardBest2 = (TextView) rootView.findViewById(R.id.text2CardBestChoide);
        textCardBest3 = (TextView) rootView.findViewById(R.id.text3CardBestChoide);

        iconCardBest1 = (ImageView) rootView.findViewById(R.id.icon1CadBestChoice);
        iconCardBest2 = (ImageView) rootView.findViewById(R.id.icon2CardBestChoice);
        iconCardBest3 = (ImageView) rootView.findViewById(R.id.icon3CardBestChoice);

        iconCardBest1.setImageResource(R.drawable.ic_happy);
        iconCardBest2.setImageResource(R.drawable.ic_neutral);
        iconCardBest3.setImageResource(R.drawable.ic_sad);



        msgBestChoice = new Message(R.string.message_need_more_counts_bestchoice,
                MaterialDrawableBuilder.IconValue.INFORMATION,
                messageBestChoice,
                R.color.colorPrimaryDark,
                96);

        msgNoTasks = new Message(R.string.message_no_tasks,
                MaterialDrawableBuilder.IconValue.CHART_LINE,
                messageChart, R.color.colorPrimaryDark,
                96);

        msgBestInterval = new Message(R.string.message_need_more_for_interval,
                MaterialDrawableBuilder.IconValue.TIMELAPSE,
                messageBestInterval, R.color.colorPrimaryDark,
                96);


        Spinner spinnerHumor = (Spinner) rootView.findViewById(R.id.spinnerHumor);

        //IF DON`T HAVE ANY TASK ADDED
        if(listTasks.size() != 0){
            //Toast.makeText(getContext(), "Best Interval: " + bestInterval(), Toast.LENGTH_SHORT).show();

            msgBestInterval.setIconValue(MaterialDrawableBuilder.IconValue.TIMER);

            if(bestInterval() == "0"){
                msgBestInterval.showMessageViewWithString("Nao ha cronometragens suficientes. Continue usando o aplicativo.");
                msgBestInterval.showMessageView();
            }else{
                msgBestInterval.showMessageViewWithString("Com base em suas estatísticas, o intervalo do dia em que você é mais produtivo ocorre entre " + bestInterval());

            }

            if(times.size() >= 5){

                chartView.setVisibility(View.VISIBLE);


                activesHumor = new ArrayList<String>();
                intHumor = new ArrayList<Integer>();
                spinnerHumor.setVisibility(View.VISIBLE);
                spinnerHumor.setOnItemSelectedListener(this);
                activesHumor.add("Feliz"); intHumor.add(2);
                activesHumor.add("Neutro"); intHumor.add(1);
                activesHumor.add("Cansado"); intHumor.add(0);

                ArrayAdapter<String> dataAdapterHumor = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, activesHumor);
                dataAdapterHumor.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinnerHumor.setAdapter(dataAdapterHumor);

                /* SPINNER HUMOR **/

                /* BETA */

                int minutesHappy = bestProductivity(String.valueOf(2),null);
                int minutesNeutral = bestProductivity(String.valueOf(1),null);
                int minutesSad = bestProductivity(String.valueOf(0),null);

                if(minutesHappy > 0){ textCardBest1.setText(minutesHappy + " m."); }
                if(minutesHappy != 0){ textCardBest2.setText(minutesNeutral + " m."); }
                if(minutesHappy != 0){ textCardBest3.setText(minutesSad + " m."); }

                /* END BETA */


            }


            /* SPINNER CHART */
            Spinner spinnerTaskChart = (Spinner) rootView.findViewById(R.id.spinnerTaskChart);
            spinnerTaskChart.setVisibility(View.VISIBLE);
            spinnerTaskChart.setOnItemSelectedListener(this);
            List<String> activesTasks = new ArrayList<String>();

            for(int i = 0; i < listTasks.size(); i++){
                activesTasks.add(listTasks.get(i).getTitleTask());
                idActiveTasks.add(listTasks.get(i).getId());
            }
            ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, activesTasks);
            dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinnerTaskChart.setAdapter(dataAdapter);

            msgBestChoice.showMessageView();



        }

        if(listTasks.size() == 0){


            msgNoTasks.showMessageView();
            msgBestChoice.showMessageView();

            msgBestInterval.setIconValue(MaterialDrawableBuilder.IconValue.TIMER);
            msgBestInterval.showMessageViewWithString("Nao ha cronometragens suficientes. Continue usando o aplicativo.");
            msgBestInterval.showMessageView();

        }

        return rootView;
    }


    private void reset() {
        numberOfLines = 1;

        hasAxes = false;
        hasAxesNames = false;
        hasLines = false;
        hasPoints = true;
        shape = ValueShape.CIRCLE;
        isFilled = false;
        hasLabels = false;
        isCubic = false;
        hasLabelForSelected = false;
        pointsHaveDifferentColor = true;

        resetViewport();
    }

    private void resetViewport() {
        final Viewport v = new Viewport(chart.getMaximumViewport());
        v.bottom = Float.parseFloat("-0.5");;
        v.top = Float.parseFloat("2.5");

        v.left = 0;
        v.right = 5;

        chart.setMaximumViewport(v);
        chart.setCurrentViewport(v);
        chart.setZoomEnabled(false);
        chartView.setVisibility(View.VISIBLE);
    }

    private void generateData() {

        List<Line> lines = new ArrayList<Line>();

        Line line = new Line(values);
        line.setColor(R.color.colorPrimaryDark);
        line.setShape(shape);
        line.setCubic(isCubic);
        line.setFilled(isFilled);
        line.setHasLabels(hasLabels);
        line.setHasLabelsOnlyForSelected(hasLabelForSelected);
        line.setHasLines(hasLines);
        line.setHasPoints(hasPoints);


        if (pointsHaveDifferentColor){
                line.setPointColor(ChartUtils.COLORS[2]);
        }

        lines.add(line);

        data = new LineChartData(lines);

        if (hasAxes) {
            Axis axisX = new Axis();

            SimpleAxisValueFormatter formatterX = new SimpleAxisValueFormatter();
            formatterX.setDecimalDigitsNumber(0);
            axisX.setFormatter(formatterX);


            Axis axisY = new Axis().setHasLines(true);
            SimpleAxisValueFormatter formatterY = new SimpleAxisValueFormatter();
            formatterY.setDecimalDigitsNumber(0);
            formatterY.setDecimalSeparator('.');//by default taken from locale
            axisY.setFormatter(formatterY);

            /*

            if (hasAxesNames) {
                axisX.setName("Atividade");
                axisY.setName("Produtividade");
            }

            */

            data.setAxisXBottom(axisX);
            data.setAxisYLeft(axisY);

        } else {
            data.setAxisXBottom(null);
            data.setAxisYLeft(null);
        }

        data.setBaseValue(Float.NEGATIVE_INFINITY);
        chart.setLineChartData(data);

    }

    /**
     * Adds lines to data, after that data should be set again with
     * {@link LineChartView#setLineChartData(LineChartData)}. Last 4th line has non-monotonically x values.
     */

    public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {

        Spinner spinner = (Spinner) parent;
        if(spinner.getId() == R.id.spinnerHumor) {

            String itemHumor = parent.getItemAtPosition(pos).toString();

            int minutes = bestProductivity(String.valueOf(intHumor.get(pos)),null);

            if(minutes > 0){

                msgBestChoice.hideMessageView();
                switch (activesHumor.get(pos)){
                    case "Feliz":
                        msgBestChoice.setIconValue(MaterialDrawableBuilder.IconValue.EMOTICON_HAPPY);
                        msgBestChoice.setColorIcon(R.color.green);
                        break;
                    case "Neutro":
                        msgBestChoice.setIconValue(MaterialDrawableBuilder.IconValue.EMOTICON_NEUTRAL);
                        msgBestChoice.setColorIcon(R.color.blue);
                        break;
                    case "Cansado":
                        msgBestChoice.setIconValue(MaterialDrawableBuilder.IconValue.EMOTICON_SAD);
                        msgBestChoice.setColorIcon(R.color.red);
                        break;

                }

                msgBestChoice.showMessageViewWithString("Com base em suas cronometragens, nos dias em que você está \"" + activesHumor.get(pos) + "\" e desejaste obter uma boa produtividade, recomendamos " +
                        "uma duração maxima de aproximadamente " + minutes + " minutos para realizar suas atividades.");

            }else{

                msgBestChoice.hideMessageView();
                msgBestChoice.setIconValue(MaterialDrawableBuilder.IconValue.TIMER_SAND_EMPTY);
                msgBestChoice.showMessageViewWithString("Não há cronometragens suficientes para este estado de espírito. Continue usando o aplicativo ou selecione outro humor.");

            }

        }else
        if(spinner.getId() == R.id.spinnerTaskChart) {

            String item = parent.getItemAtPosition(pos).toString();

            /* CHART */
            //generateValues();
            List<TimingModel> list = TimingModel.createTaskTimeListWithLimit("6", String.valueOf(idActiveTasks.get(pos)));

            Message msgNeedMoreCountsChart = new Message(R.string.message_need_more_counts_chart,
                    MaterialDrawableBuilder.IconValue.CHART_LINE,
                    messageChart,
                    R.color.colorPrimaryDark,
                    96);

            msgNeedMoreCountsChart.showMessageView();

            if(list.size() >= 5){

                generateTimingValues(list);
                generateData();
                chart.setViewportCalculationEnabled(false);
                resetViewport();
                msgNeedMoreCountsChart.hideMessageView();

            }else{
                chartView.setVisibility(View.GONE);


            }


        }


    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
        /*
        else if(spinner.getId() == R.id.spinner2)
        {
            //do this
        }
        */

    private void generateTimingValues(List<TimingModel> list) {

        int sizeList = list.size() - 1;
        values = new ArrayList<PointValue>();

        for(int i = 0; i <= sizeList; i++){
            values.add(new PointValue(i, list.get(sizeList - i).getProductivity()));
        }



    }

    private int bestProductivity(String humor, Long task){

        if(task == null){
            List<TimingModel> timesProductivity = TimingModel.createTaskTimeProductivity("2",humor,"100");
            int sizeList = timesProductivity.size();
            int secondsNeutral = 0;

            if(sizeList == 0){
                return 0;
            }

            int secondsMedium = 0;

            for(int i = 0; i< sizeList; i++){
                secondsMedium += timesProductivity.get(i).getTimeSecondsTask();
            }

            return ((secondsMedium/sizeList)/60);
        }


        return 0;

    }

    private String bestInterval(){
        List<TimingModel> timesInterval = TimingModel.createTaskTimeProductivity("2","50");
        List<String> createdAt = new ArrayList<String>();
        List<String> finishedAt = new ArrayList<String>();
        List<Integer> arrayIntCreatedAt = new ArrayList<Integer>();

        for(int i = 0; i < timesInterval.size(); i++){
            createdAt.add(timesInterval.get(i).getStartAt());
            finishedAt.add(timesInterval.get(i).getCreatedAt());
        }

        for(int i = 0; i < createdAt.size(); i++){

        }
        //verification what interval
        if(timesInterval.size() != 0){
            return "19:00 às 20:00";
        }else{
            return "0";
        }

    }
}
