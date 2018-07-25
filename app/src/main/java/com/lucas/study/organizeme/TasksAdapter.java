package com.lucas.study.organizeme;
import android.content.Context;
import android.content.Intent;
import android.os.SystemClock;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.omega_r.libs.omegarecyclerview.OmegaRecyclerView;
import com.omega_r.libs.omegarecyclerview.swipe_menu.SwipeViewHolder;

import java.util.ArrayList;
import java.util.List;


public class TasksAdapter extends OmegaRecyclerView.Adapter<TasksAdapter.ViewHolder> {

    private Context mcon;
    private boolean stateChronometer;

    public boolean getStateChronometer(View v){
        return stateChronometer;
    }

    public void setStateChronometer(View v, boolean state){
        this.stateChronometer = state;
    }


    private List<TaskModel> mTasksList = new ArrayList<>();

    public TasksAdapter(List<TaskModel> tasksList, Context con) {

        mcon = con;
        mTasksList = tasksList;
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
        private Button btnEditTask, btnDeleteTask;
        private long idTask;
        private String nameTask;

        public Chronometer chronometer(){
            return ((Chronometer) findViewById(R.id.chronometer_task));
        }

        public ViewHolder(ViewGroup parent) {
            super(parent, R.layout.item_task, R.layout.item_left_swipe_menu);


            btnPlay = findViewById(R.id.btnPlay);
            btnPlay.setOnClickListener(this);

            btnPause = findViewById(R.id.btnPause);
            btnPause.setOnClickListener(this);

            btnStop = findViewById(R.id.btnStop);
            btnStop.setOnClickListener(this);

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

        void updateView(TaskModel task) {
            txtNameTask.setText(task.getTitleTask());
            idTask = task.getId();
            nameTask = task.getTitleTask();
        }

        /* Chronometer */

        public void startChronometer(View view) {

            if(getStateChronometer(view)){

                Toast.makeText(mcon, "Termine sua atividade antes de iniciar outra.", Toast.LENGTH_LONG).show();

            }else{
                setStateChronometer(view, true);
                chronometer().setBase(SystemClock.elapsedRealtime());
                showChronometer(view);
                chronometer().start();
                btnPause.setVisibility(View.VISIBLE);
                btnStop.setVisibility(View.VISIBLE);
                btnPlay.setVisibility(View.GONE);


                setSwipeEnable(false);
            }


        }

        public void pauseChronometer(View view) {
            chronometer().stop();
        }

        public void stopChronometer(View view) {
            pauseChronometer(view);
            setStateChronometer(view, false);
            clearChronometer(view);
        }

        public String getChronometer(View view) {
            return chronometer().getFormat();
        }

        public void showChronometer(View view) {
            chronometer().setVisibility(View.VISIBLE);
        }

        public void clearChronometer(View view) {

            chronometer().setVisibility(View.GONE);
            btnPause.setVisibility(View.GONE);
            btnStop.setVisibility(View.GONE);
            btnPlay.setVisibility(View.VISIBLE);
        }

        private int getElapsedTime() {
            //Duration in seconds
            int elapsedTime = (int) (SystemClock.elapsedRealtime() - chronometer().getBase())/1000;
            return elapsedTime;
        }

        /* end Chronometer */

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.btnEditTask:

                    Intent i = new Intent(mcon, EditTaskActivity.class);
                    i.putExtra("idTask", getIdTask());
                    mcon.startActivity(i);

                    break;

                case R.id.btnDeleteTask:

                    TaskModel t = TaskModel.findById(TaskModel.class, getIdTask());
                    t.status = 0;
                    t.save();
                    findViewById(R.id.itemTask).setVisibility(v.GONE);
                    findViewById(R.id.swipe_left).setVisibility(v.GONE);
                    showToast("Atividade deletada!");


                    mTasksList.remove(getIdTask());


                    updateView(t);

                    break;

                case R.id.btnPlay:
                    startChronometer(v);
                    break;

                case R.id.btnPause:
                    pauseChronometer(v);
                    break;

                case R.id.btnStop:


                    DialogEndActivity cdd = new DialogEndActivity(itemView.getContext(), getIdTask(), getElapsedTime());
                    cdd.show();
                    stopChronometer(v);
                    setSwipeEnable(true);

                    break;
            }
        }

        private void showToast(String message) {
            Toast.makeText(itemView.getContext(), message, Toast.LENGTH_SHORT).show();
        }


        Long getIdTask(){ return idTask; }
        String getTitleTask(){ return nameTask; }
    }




}