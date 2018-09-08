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
import com.lucas.study.organizeme.Widget.BestChoiceWithHumor;
import com.lucas.study.organizeme.Widget.BestInterval;

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
    private boolean hasAxes = false, hasAxesNames = false, hasLines = true, hasPoints = false;
    private ValueShape shape = ValueShape.CIRCLE;
    private boolean isFilled = false, hasLabels = false, isCubic = false, hasLabelForSelected = false, pointsHaveDifferentColor;
    private boolean hasGradientToTransparent = false;
    public CoordinatorLayout messageChart, messageBestChoice, chartView, layoutWidgetBestChoice, layoutWidgetBestInterval;
    public Message msgNoTasks;
    public BestChoiceWithHumor widgetBestChoiceForHumor;
    public BestInterval widgetBestInterval;
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
        layoutWidgetBestChoice = (CoordinatorLayout) rootView.findViewById(R.id.widgetBestChoice);
        layoutWidgetBestInterval = (CoordinatorLayout) rootView.findViewById(R.id.widgetBestInterval);

        msgNoTasks = new Message(R.string.message_no_tasks,
                MaterialDrawableBuilder.IconValue.CHART_LINE,
                messageChart, R.color.colorPrimaryDark,
                96);

        widgetBestChoiceForHumor = new BestChoiceWithHumor(layoutWidgetBestChoice);
        widgetBestInterval = new BestInterval(layoutWidgetBestInterval);


        widgetBestChoiceForHumor.widgetShow();
        widgetBestInterval.widgetShow();

        //IF DON`T HAVE ANY TASK ADDED
        if(listTasks.size() != 0){

            if(times.size() >= 5){

                chartView.setVisibility(View.VISIBLE);

                //Show card widget's


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


        }


        if(listTasks.size() == 0){


            msgNoTasks.showMessageView();

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

    private void generateTimingValues(List<TimingModel> list) {

        int sizeList = list.size() - 1;
        values = new ArrayList<PointValue>();

        for(int i = 0; i <= sizeList; i++){
            values.add(new PointValue(i, list.get(sizeList - i).getProductivity()));
        }



    }

}
