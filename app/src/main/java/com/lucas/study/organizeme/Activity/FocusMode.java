package com.lucas.study.organizeme.Activity;

import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.lucas.study.organizeme.Dialog.EndActivity;
import com.lucas.study.organizeme.R;
import com.lucas.study.organizeme.Model.TaskModel;

import net.steamcrafted.materialiconlib.MaterialDrawableBuilder;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import static android.app.Notification.EXTRA_NOTIFICATION_ID;

public class FocusMode extends AppCompatActivity implements View.OnClickListener {

    EditText editTextTodoTitle, editTextTodoDescription;
    private Button addTodo;

    public Calendar calendar;
    private CoordinatorLayout coordinatorLayout;

    private ImageButton btnPlay, btnPause, btnStop;
    private TextView titleActivity;
    private long idTask;
    private String nameTask;
    long timeWhenStopped = 0;
    public void setStateChronometer(View v, boolean state){
        this.stateChronometer = state;
    }
    private boolean stateChronometer;

    public String startedAt;

    public boolean getStateChronometer(View v){
        return stateChronometer;
    }

    public Chronometer chronometer(){
        return ((Chronometer) findViewById(R.id.chronometer_task));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_focous);

        coordinatorLayout = (CoordinatorLayout) findViewById(R.id
                .coordinatorLayout);


        // Get the Intent that started this activity and extract the string

        Intent intent = getIntent();
        idTask = intent.getLongExtra("idTask", 0);
        // Capture the DB title and set the string as its text
        final TaskModel t = TaskModel.findById(TaskModel.class, idTask);

        btnPlay = findViewById(R.id.btnPlay);
        btnPlay.setOnClickListener(this);

        btnPause = findViewById(R.id.btnPause);
        btnPause.setOnClickListener(this);

        btnStop = findViewById(R.id.btnStop);
        btnStop.setOnClickListener(this);

        titleActivity = (TextView) findViewById(R.id.titleActivity);
        titleActivity.setText(t.getTitleTask());

        startChronometer(coordinatorLayout);

        if(chronometer().getBase() != 0){
            chronometer().setBase(chronometer().getBase());
        }

        //GET TIME CREATED
        Calendar c = Calendar.getInstance();

        SimpleDateFormat df = new SimpleDateFormat("HH:mm:ss dd/MM/yy");
        startedAt = df.format(c.getTime());


    }

    /* Chronometer */

    Long getIdTask(){ return idTask; }
    String getTitleTask(){ return nameTask; }

    public void startChronometer(View view) {
        setStateChronometer(view, true);
        chronometer().setBase(SystemClock.elapsedRealtime());
        showChronometer(view);
        chronometer().start();

        btnPause.setVisibility(View.VISIBLE);
        btnStop.setVisibility(View.VISIBLE);
        btnPlay.setVisibility(View.GONE);


    }

    public void playChronometer(View view) {
        setStateChronometer(view, true);
        chronometer().setBase(SystemClock.elapsedRealtime()+timeWhenStopped);
        chronometer().start();

        btnPause.setVisibility(View.VISIBLE);
        btnStop.setVisibility(View.VISIBLE);
        btnPlay.setVisibility(View.GONE);


    }

    public void pauseChronometer(View view) {
        chronometer().stop();
        btnPlay.setVisibility(View.VISIBLE);
        btnPause.setVisibility(View.GONE);


        timeWhenStopped = chronometer().getBase() - SystemClock.elapsedRealtime();
    }

    public String getChronometer(View view) {
        return chronometer().getFormat();
    }

    public void showChronometer(View view) {
        chronometer().setVisibility(View.VISIBLE);
    }

    private int getElapsedTime() {
        //Duration in seconds
        int elapsedTime = (int) (SystemClock.elapsedRealtime() - chronometer().getBase())/1000;
        return elapsedTime;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnPlay:

                playChronometer(view);

                break;

            case R.id.btnPause:
                pauseChronometer(view);
                break;

            case R.id.btnStop:



                EndActivity cdd = new EndActivity(view.getContext(), getIdTask(), getElapsedTime(), startedAt);
                cdd.show();
                pauseChronometer(view);
                break;
        }
    }

    /* end Chronometer */

}
