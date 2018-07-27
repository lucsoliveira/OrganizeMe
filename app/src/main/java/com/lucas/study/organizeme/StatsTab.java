package com.lucas.study.organizeme;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;


import java.util.ArrayList;
import java.util.List;

import lecho.lib.hellocharts.animation.ChartAnimationListener;
import lecho.lib.hellocharts.formatter.SimpleAxisValueFormatter;
import lecho.lib.hellocharts.listener.LineChartOnValueSelectListener;
import lecho.lib.hellocharts.model.Axis;
import lecho.lib.hellocharts.model.AxisValue;
import lecho.lib.hellocharts.model.Line;
import lecho.lib.hellocharts.model.LineChartData;
import lecho.lib.hellocharts.model.PointValue;
import lecho.lib.hellocharts.model.ValueShape;
import lecho.lib.hellocharts.model.Viewport;
import lecho.lib.hellocharts.util.ChartUtils;
import lecho.lib.hellocharts.view.LineChartView;

public class StatsTab extends Fragment implements AdapterView.OnItemSelectedListener {

    /* CHART */
    private LineChartView chart;
    private LineChartData data;
    private int numberOfLines = 1;
    //private int numberOfPoints = 5;

    public List<PointValue> values;
    private boolean hasAxes = true;
    private boolean hasAxesNames = true;
    private boolean hasLines = true;
    private boolean hasPoints = true;
    private ValueShape shape = ValueShape.CIRCLE;
    private boolean isFilled = false;
    private boolean hasLabels = false;
    private boolean isCubic = true;
    private boolean hasLabelForSelected = false;
    private boolean pointsHaveDifferentColor;
    private boolean hasGradientToTransparent = false;

    public List<TaskModel> listTasks = TaskModel.findWithQuery(TaskModel.class,"Select * from Task_Model where status = ? order by id DESC", "1");
    /* END CHART */

    final List<TaskTimingModel> times = TaskTimingModel.createTaskTimeListWithLimit("6");

    List<Long> idActiveTasks = new ArrayList<Long>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.tab_stats, container, false);
        chart = (LineChartView) rootView.findViewById(R.id.chart);

        if(times.size() >= 5){

            /* CHART */
            /* SPINNER CHART */
            Spinner spinnerTaskChart = (Spinner) rootView.findViewById(R.id.spinnerTaskChart);
            spinnerTaskChart.setOnItemSelectedListener(this);
            List<String> activesTasks = new ArrayList<String>();
            for(int i = 0; i < listTasks.size(); i++){
                activesTasks.add(listTasks.get(i).getTitleTask());
                idActiveTasks.add(listTasks.get(i).getId());
            }
            ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, activesTasks);
            // Drop down layout style - list view with radio button
            dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

            // attaching data adapter to spinner
            spinnerTaskChart.setAdapter(dataAdapter);

            /* END SPINNER CHART */

            /* END CHART */


        }else{

            chart.setVisibility(View.GONE);

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
        v.bottom = 0;
        v.top = Float.parseFloat("2.5");

        v.left = 0;
        v.right = 5;

        chart.setMaximumViewport(v);
        chart.setCurrentViewport(v);
        chart.setZoomEnabled(false);
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

    private void togglePointColor() {
        pointsHaveDifferentColor = !pointsHaveDifferentColor;

        generateData();
    }

    private void setCircles() {
        shape = ValueShape.CIRCLE;

        generateData();
    }

    private void setSquares() {
        shape = ValueShape.SQUARE;

        generateData();
    }

    private void setDiamonds() {
        shape = ValueShape.DIAMOND;

        generateData();
    }

    private void toggleLabels() {
        hasLabels = !hasLabels;

        if (hasLabels) {
            hasLabelForSelected = false;
            chart.setValueSelectionEnabled(hasLabelForSelected);
        }

        generateData();
    }

    private void toggleLabelForSelected() {
        hasLabelForSelected = !hasLabelForSelected;

        chart.setValueSelectionEnabled(hasLabelForSelected);

        if (hasLabelForSelected) {
            hasLabels = false;
        }

        generateData();
    }

    private void toggleAxes() {
        hasAxes = !hasAxes;

        generateData();
    }

    private void toggleAxesNames() {
        hasAxesNames = !hasAxesNames;

        generateData();
    }


    private void prepareDataAnimation() {
        for (Line line : data.getLines()) {
            for (PointValue value : line.getValues()) {
                // Here I modify target only for Y values but it is OK to modify X targets as well.
                value.setTarget(value.getX(), (float) Math.random() * 100);
            }
        }
    }

    public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {

        Spinner spinner = (Spinner) parent;
        if(spinner.getId() == R.id.spinnerTaskChart) {
            String item = parent.getItemAtPosition(pos).toString();

            /* CHART */
            //generateValues();
            generateTimingValues(TaskTimingModel.createTaskTimeListWithLimit("6", String.valueOf(idActiveTasks.get(pos))));
            generateData();
            chart.setViewportCalculationEnabled(false);
            resetViewport();

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

    private void generateTimingValues(List<TaskTimingModel> list) {

        int sizeList = list.size() - 1;
        values = new ArrayList<PointValue>();
        for(int i = 0; i <= sizeList; i++){
            values.add(new PointValue(i, list.get(sizeList - i).getProductivity()));
        }

    }
}
