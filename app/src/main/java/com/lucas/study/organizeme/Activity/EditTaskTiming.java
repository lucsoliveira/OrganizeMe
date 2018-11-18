package com.lucas.study.organizeme.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.NumberPicker;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.lucas.study.organizeme.Model.TaskModel;
import com.lucas.study.organizeme.Model.TimingModel;
import com.lucas.study.organizeme.R;

import org.joda.time.DateTime;

public class EditTaskTiming extends AppCompatActivity {

    public RadioButton optionGood, optionMedium, optionBad, optionHappy, optionNeutral, optionSad;
    public TextView txtTitleTime;
    public NumberPicker numberPicker;
    public int newProductivity, newHumor;
    private Button editTask;
    private CoordinatorLayout coordinatorLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_task_timing);

        coordinatorLayout = (CoordinatorLayout) findViewById(R.id.coordinatorLayout);
        txtTitleTime = (TextView) findViewById(R.id.txtNameTime);

        //Productivity
        optionGood = (RadioButton) findViewById(R.id.optionGood);
        optionMedium = (RadioButton) findViewById(R.id.optionMedium);
        optionBad = (RadioButton) findViewById(R.id.optionBad);

        //Humor
        optionHappy = (RadioButton) findViewById(R.id.optionHappy);
        optionNeutral = (RadioButton) findViewById(R.id.optionNeutral);
        optionSad = (RadioButton) findViewById(R.id.optionSad);

        Intent intent = getIntent();
        // Capture the DB title and set the string as its text
        final TimingModel time = TimingModel.findById(TimingModel.class, intent.getLongExtra("idTime", 0));
        final TaskModel task = TaskModel.findById(TaskModel.class, time.getIdTask());

        txtTitleTime.setText("#" + time.getId() + " " + task.getTitleTask());

        numberPicker = (NumberPicker) findViewById(R.id.numberMinutes);

        numberPicker.setMinValue(0);
        numberPicker.setMaxValue(120);
        final int minutes = time.getTimeSecondsTask() / 60;

        numberPicker.setValue(minutes);

        switch (time.getProductivity()) {
            case 2:
                optionGood.setChecked(true);
                break;
            case 1:
                optionMedium.setChecked(true);
                break;
            case 0:
                optionBad.setChecked(true);
                break;

        }

        switch (time.getHumorAfter()) {
            case 2:
                optionHappy.setChecked(true);
                break;
            case 1:
                optionNeutral.setChecked(true);
                break;
            case 0:
                optionSad.setChecked(true);
                break;
        }

        editTask = (Button) findViewById(R.id.saveTimeTask);

        editTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                int oldProductivity = time.getProductivity();
                int oldHumor = time.getHumorAfter();
                int oldMinute = time.getTimeSecondsTask() / 60;


                /* RADIO BUTTONS */
                if (optionGood.isChecked()) {
                    newProductivity = 2;
                } else if (optionMedium.isChecked()) {
                    newProductivity = 1;
                } else if (optionBad.isChecked()) {
                    newProductivity = 0;
                }

                if (optionHappy.isChecked()) {
                    newHumor = 2;
                } else if (optionNeutral.isChecked()) {
                    newHumor = 1;
                } else if (optionSad.isChecked()) {
                    newHumor = 0;
                }
                /* END RADIO BUTTONS */

                int newMinute = numberPicker.getValue();


                if (newProductivity == oldProductivity) {

                } else {
                    time.setProductivity(newProductivity);
                }

                if (newHumor == oldHumor) {

                } else {
                    time.setHumorAfter(newHumor);
                }

                if (oldMinute == newMinute) {

                } else {

                    DateTime dt = DateTime.parse(time.getFinishedAt());


                    time.setTimeSecondsTask(newMinute * 60);//set the seconds duration;
                    // clear the minutes before
                    time.setFinishedAt(dt.minusMinutes(minutes).plusMinutes(newMinute).toString());//set the finshedAt time
                }

                time.save();

                Toast.makeText(getApplicationContext(), "Alterações salvas", Toast.LENGTH_LONG).show();

                Intent i = new Intent(getApplicationContext(), HistoryTasks.class);
                startActivity(i);
                finish();

            }


        });
    }


}

