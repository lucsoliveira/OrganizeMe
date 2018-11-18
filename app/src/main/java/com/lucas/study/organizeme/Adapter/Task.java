package com.lucas.study.organizeme.Adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.lucas.study.organizeme.Activity.EditTask;
import com.lucas.study.organizeme.Activity.FocusMode;
import com.lucas.study.organizeme.Model.TaskModel;
import com.lucas.study.organizeme.R;
import com.omega_r.libs.omegarecyclerview.OmegaRecyclerView;
import com.omega_r.libs.omegarecyclerview.swipe_menu.SwipeViewHolder;

import java.util.ArrayList;
import java.util.List;


public class Task extends OmegaRecyclerView.Adapter<Task.ViewHolder> {

    public int sizeList;
    public CoordinatorLayout coordinatorLayout;
    public boolean setNotChange = false;
    private Context mcon;
    private boolean stateChronometer;
    private List<TaskModel> mTasksList = new ArrayList<>();

    public Task(List<TaskModel> tasksList, Context con) {

        mcon = con;
        mTasksList = tasksList;
        sizeList = mTasksList.size();
    }

    public boolean getStateChronometer(View v) {
        return stateChronometer;
    }

    public void setStateChronometer(View v, boolean state) {
        this.stateChronometer = state;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(parent);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.updateView(mTasksList.get(position));
    }

    @Override
    public int getItemCount() {
        return mTasksList.size();
    }

    public class ViewHolder extends SwipeViewHolder implements View.OnClickListener {

        private ImageButton btnPlay, btnPause, btnStop;
        private TextView txtNameTask;
        private Button btnEditTask, btnDeleteTask, btnBack;
        private long idTask;
        private String nameTask;

        public ViewHolder(ViewGroup parent) {
            super(parent, R.layout.item_task, R.layout.item_left_swipe_menu);


            btnPlay = findViewById(R.id.btnPlay);
            btnPlay.setOnClickListener(this);

            btnPause = findViewById(R.id.btnPause);
            btnPause.setOnClickListener(this);

            btnStop = findViewById(R.id.btnStop);
            btnStop.setOnClickListener(this);

            btnBack = findViewById(R.id.btnBack);
            btnBack.setOnClickListener(this);

            btnEditTask = findViewById(R.id.btnEditTask);
            btnEditTask.setOnClickListener(this);

            btnDeleteTask = findViewById(R.id.btnDeleteTask);
            btnDeleteTask.setOnClickListener(this);


            txtNameTask = findViewById(R.id.txtNameTask);

            setSwipeEnable(true);

            contentView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    smoothOpenBeginMenu();
                    return true;
                }
            });
        }

        public Chronometer chronometer() {
            return ((Chronometer) findViewById(R.id.chronometer_task));
        }

        void updateView(TaskModel task) {
            txtNameTask.setText(task.getTitleTask());
            idTask = task.getId();
            nameTask = task.getTitleTask();
        }


        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.btnEditTask:

                    Intent i = new Intent(mcon, EditTask.class);
                    i.putExtra("idTask", getIdTask());
                    mcon.startActivity(i);

                    break;

                case R.id.btnDeleteTask:

                    final TaskModel t = TaskModel.findById(TaskModel.class, getIdTask());
                    t.status = 0;
                    t.save();

                    txtNameTask.setPaintFlags(txtNameTask.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                    btnPlay.setVisibility(v.GONE);

                    btnDeleteTask.setVisibility(v.GONE);
                    btnEditTask.setVisibility(v.GONE);
                    btnBack.setVisibility(v.VISIBLE);

                    smoothCloseMenu();

                    showToast("Atividade deletada. Segure-a para reativá-la.");

                    updateView(t);


                    break;

                case R.id.btnBack:

                    final TaskModel tBack = TaskModel.findById(TaskModel.class, getIdTask());
                    tBack.status = 1;
                    tBack.save();

                    txtNameTask.setPaintFlags(txtNameTask.getPaintFlags() & (~Paint.STRIKE_THRU_TEXT_FLAG));
                    btnPlay.setVisibility(v.VISIBLE);
                    btnDeleteTask.setVisibility(v.VISIBLE);
                    btnEditTask.setVisibility(v.VISIBLE);
                    btnBack.setVisibility(v.GONE);

                    showToast("Atividade retornou!");
                    smoothCloseMenu();
                    updateView(tBack);

                    break;
                case R.id.btnPlay:

                    Intent intentPlay = new Intent(mcon, FocusMode.class);
                    intentPlay.putExtra("idTask", getIdTask());

                    NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(mcon, "sd")
                            .setSmallIcon(R.drawable.ic_launcher)
                            .setContentTitle(getTitleTask())
                            .setContentText("00:00")
                            .setPriority(NotificationCompat.PRIORITY_DEFAULT);
                    NotificationManagerCompat notificationManager = NotificationManagerCompat.from(mcon);
                    notificationManager.notify(1, mBuilder.build());


                    mcon.startActivity(intentPlay);

                    //startChronometer(v);


                    break;
            }
        }

        private void showToast(String message) {
            Toast.makeText(itemView.getContext(), message, Toast.LENGTH_SHORT).show();
        }

        Long getIdTask() {
            return idTask;
        }

        String getTitleTask() {
            return nameTask;
        }
    }

}