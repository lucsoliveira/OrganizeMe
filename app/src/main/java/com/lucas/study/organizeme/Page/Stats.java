package com.lucas.study.organizeme.Page;

import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.lucas.study.organizeme.View.Message;
import com.lucas.study.organizeme.R;
import com.lucas.study.organizeme.Model.TaskModel;
import com.lucas.study.organizeme.Model.TimingModel;

import net.steamcrafted.materialiconlib.MaterialDrawableBuilder;

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
    private boolean hasAxes = false;
    private boolean hasAxesNames = false;
    private boolean hasLines = true;
    private boolean hasPoints = false;
    private ValueShape shape = ValueShape.CIRCLE;
    private boolean isFilled = false;
    private boolean hasLabels = false;
    private boolean isCubic = false;
    private boolean hasLabelForSelected = false;
    private boolean pointsHaveDifferentColor;
    private boolean hasGradientToTransparent = false;
    public CoordinatorLayout messageChart, messageBestChoice, chartView;
    public Message msgBestChoice, msgNoTasks;
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



        //messageView(R.string.message_need_more_counts_bestchoice,MaterialDrawableBuilder.IconValue.WEATHER_RAINY, messageBestChoice);


        msgBestChoice = new Message(R.string.message_need_more_counts_bestchoice,
                MaterialDrawableBuilder.IconValue.INFORMATION,
                messageBestChoice,
                R.color.colorPrimaryDark,
                96);

        msgNoTasks = new Message(R.string.message_no_tasks,
                MaterialDrawableBuilder.IconValue.CHART_LINE,
                messageChart, R.color.colorPrimaryDark,
                96);

        Spinner spinnerHumor = (Spinner) rootView.findViewById(R.id.spinnerHumor);

        //IF DON`T HAVE ANY TASK ADDED
        if(listTasks.size() != 0){

            if(times.size() >= 5){

                //messageView(R.string.message_need_more_counts_bestchoice,MaterialDrawableBuilder.IconValue.WEATHER_RAINY, messageBestChoice);
                chartView.setVisibility(View.VISIBLE);
                //msgBestChoice.hideMessageView();
                //msgBestChoice.showMessageViewWithString("Aqui ficara um teste");


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

                msgBestChoice.showMessageViewWithString("Com base em suas cronometragens, nos dias em que você sentir-se \"" + activesHumor.get(pos) + "\" e desejaste obter uma boa produtividade, recomendamos " +
                        "uma duração média de aproximadamente " + minutes + " minutos para suas atividades.");

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
            //List<TaskTimingModel> timesProductivityMedium = TaskTimingModel.createTaskTimeProductivity("1",humor,"100");
            int sizeList = timesProductivity.size();
            //int sizeListMedium = timesProductivityMedium.size();
            int secondsNeutral = 0;

            if(sizeList == 0){
                return 0;
            }

            int secondsMedium = 0;

            for(int i = 0; i< sizeList; i++){
                secondsMedium += timesProductivity.get(i).getTimeSecondsTask();
            }

            /*
            if(sizeListMedium != 0){
                for(int i = 0; i< sizeListMedium; i++){
                    secondsNeutral += timesProductivityMedium.get(i).getTimeSecondsTask();
                }


                return (((sizeListMedium*secondsNeutral)+(secondsMedium*sizeList))/sizeList*sizeListMedium)/60;
            }
            //int minutesBest = (secondsMedium /sizeList)/60;
            */
            return ((secondsMedium/sizeList)/60);
        }


        return 0;

    }



}
