package com.lucas.study.organizeme.Activity;

import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.lucas.study.organizeme.Dialog.EndActivity;
import com.lucas.study.organizeme.Model.TaskModel;
import com.lucas.study.organizeme.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class FocusMode extends AppCompatActivity implements View.OnClickListener {

    public Calendar calendar;
    public Intent notifyIntent;
    public Handler handler;
    public String startedAt;
    public Runnable r;
    public int secondsTask;
    public String stringElapsedTime;
    EditText editTextTodoTitle, editTextTodoDescription;
    long timeWhenStopped = 0;
    Future longRunningTaskFuture;
    ExecutorService executorService;
    private Button addTodo;
    private CoordinatorLayout coordinatorLayout;
    private ImageButton btnPlay, btnPause, btnStop;
    private TextView titleActivity;
    private long idTask;
    private String nameTask;
    private boolean stateChronometer;

    public void setStateChronometer(View v, boolean state) {
        this.stateChronometer = state;
    }

    public boolean getStateChronometer(View v) {
        return stateChronometer;
    }

    public Chronometer chronometer() {
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

        if (intent.hasExtra("secondTask")) {

            chronometer().setBase(intent.getIntExtra("secondTask", 1));
            intent.getIntExtra("secondTask", 1);

            Log.i("o que passou", String.valueOf(intent.getIntExtra("secondTask", 1)));
        } else {
            // Do something else
        }

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

        if (chronometer().getBase() != 0) {
            chronometer().setBase(chronometer().getBase());
        }

        //GET TIME CREATED
        Calendar c = Calendar.getInstance();

        SimpleDateFormat df = new SimpleDateFormat("HH:mm:ss dd/MM/yy");
        startedAt = df.format(c.getTime());
        handler = new Handler();


        notifyIntent = new Intent(this, FocusMode.class);
        notifyIntent.setAction(Intent.ACTION_MAIN);
        notifyIntent.addCategory(Intent.CATEGORY_LAUNCHER);
        notifyIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        notifyIntent.putExtra("idTask", getIdTask());
        final PendingIntent notifyPendingIntent = PendingIntent.getActivity(
                this, 0, notifyIntent, 0);
        r = new Runnable() {
            public void run() {

                notifyIntent.putExtra("secondTask", getElapsedTime());

                stringElapsedTime = getDurationString(getElapsedTime());
                NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(getApplicationContext(), "sd")
                        .setSmallIcon(R.drawable.ic_launcher)
                        .setContentTitle(t.getTitleTask())
                        .setContentIntent(notifyPendingIntent)
                        .setContentText(stringElapsedTime)
                        .setPriority(NotificationCompat.PRIORITY_DEFAULT);
                NotificationManagerCompat notificationManager = NotificationManagerCompat.from(getApplicationContext());

                notificationManager.notify(1, mBuilder.build());
                handler.postDelayed(this, 1000);
            }
        };

        handler.postDelayed(r, 1000);
        executorService = Executors.newSingleThreadExecutor();
        longRunningTaskFuture = executorService.submit(r);

    }

    /* Chronometer */

    Long getIdTask() {
        return idTask;
    }

    String getTitleTask() {
        return nameTask;
    }

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
        chronometer().setBase(SystemClock.elapsedRealtime() + timeWhenStopped);
        chronometer().start();

        btnPause.setVisibility(View.VISIBLE);
        btnStop.setVisibility(View.VISIBLE);
        btnPlay.setVisibility(View.GONE);


    }

    public void pauseChronometer(View view) {

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
        notificationManager.cancelAll();
        handler.removeCallbacks(r);
        chronometer().stop();
        btnPlay.setVisibility(View.VISIBLE);
        btnPause.setVisibility(View.GONE);


        timeWhenStopped = chronometer().getBase() - SystemClock.elapsedRealtime();
    }

    public void showChronometer(View view) {
        chronometer().setVisibility(View.VISIBLE);
    }

    private int getElapsedTime() {
        //Duration in seconds
        int elapsedTime = (int) (SystemClock.elapsedRealtime() - chronometer().getBase()) / 1000;
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
                cdd.setCanceledOnTouchOutside(false);
                cdd.show();
                pauseChronometer(view);
                longRunningTaskFuture.cancel(true);
                handler.removeCallbacks(r);

                break;
        }
    }

    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.app_name);
        builder.setIcon(R.mipmap.ic_launcher);
        builder.setMessage("Você deseja para o cronômetro?")
                .setCancelable(false)
                .setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        finish();
                    }
                })
                .setNegativeButton("Não", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(getApplicationContext());
                        notificationManager.cancelAll();
                        handler.removeCallbacks(r);
                        dialog.cancel();

                    }
                });
        AlertDialog alert = builder.create();
        alert.show();

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
        notificationManager.cancelAll();
        handler.removeCallbacks(r);

    }


    public void onDestroy() {


        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.app_name);
        builder.setIcon(R.mipmap.ic_launcher);
        builder.setMessage("Você deseja para o cronômetro?")
                .setCancelable(false)
                .setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        finish();
                    }
                })
                .setNegativeButton("Não", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(getApplicationContext());
                        notificationManager.cancelAll();
                        handler.removeCallbacks(r);
                        dialog.cancel();

                    }
                });
        AlertDialog alert = builder.create();
        alert.show();

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
        notificationManager.cancelAll();
        handler.removeCallbacks(r);
        super.onDestroy();
    }

    private String getDurationString(int seconds) {

        int hours = seconds / 3600;
        int minutes = (seconds % 3600) / 60;
        seconds = seconds % 60;

        return twoDigitString(hours) + " : " + twoDigitString(minutes) + " : " + twoDigitString(seconds);
    }

    private String twoDigitString(int number) {
        if (number == 0) {
            return "00";
        }
        if (number / 10 == 0) {
            return "0" + number;
        }
        return String.valueOf(number);
    }
    /* end Chronometer */

}
