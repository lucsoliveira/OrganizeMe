package com.lucas.study.organizeme.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.lucas.study.organizeme.Activity.EditTaskTiming;
import com.lucas.study.organizeme.Model.TaskModel;
import com.lucas.study.organizeme.Model.TimingModel;
import com.lucas.study.organizeme.R;
import com.omega_r.libs.omegarecyclerview.OmegaRecyclerView;
import com.omega_r.libs.omegarecyclerview.swipe_menu.SwipeViewHolder;

import java.util.ArrayList;
import java.util.List;


public class Timing extends OmegaRecyclerView.Adapter<Timing.ViewHolder> {

    private Context mcon;

    private List<TimingModel> mTasksTimeList = new ArrayList<>();

    public Timing(List<TimingModel> tasksList, Context con) {

        mcon = con;
        mTasksTimeList = tasksList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(parent);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holderTime, int position) {
        holderTime.updateView(mTasksTimeList.get(position));
    }

    @Override
    public int getItemCount() {
        return mTasksTimeList.size();
    }

    public class ViewHolder extends SwipeViewHolder implements View.OnClickListener {

        private TextView txtNameTask, txtDuration, txtProductivity,
                txtHumorAfter, txtHumorBefore, txtMoreInfo, txtDate, txtCreated;
        private ImageButton editTime, deleteTime;
        private long idTime, idTask;
        private int durationTime;
        private String nameTask, dateCreate;

        public ViewHolder(ViewGroup parent) {
            super(parent, R.layout.item_task_timing);

            //IMAGE BUTTONS
            editTime = findViewById(R.id.editTime);
            editTime.setOnClickListener(this);
            deleteTime = findViewById(R.id.deleteTime);
            deleteTime.setOnClickListener(this);

            //TEXT VIEW
            txtNameTask = findViewById(R.id.txtNameTask);
            txtDuration = findViewById(R.id.txtDuration);
            txtProductivity = findViewById(R.id.txtProductivity);
            txtHumorAfter = findViewById(R.id.txtHumorAfter);
            txtHumorBefore= findViewById(R.id.txtHumorBefore);
            txtMoreInfo = findViewById(R.id.txtMoreInfo);
            txtDate = findViewById(R.id.txtDateEnd);
            txtCreated = findViewById(R.id.txtDate);



        }

        void updateView(TimingModel time) {

            idTime = time.getId();
            idTask = time.getIdTask();
            dateCreate = time.getCreatedAt();
            durationTime = time.getTimeSecondsTask();

            TaskModel task = TaskModel.findById(TaskModel.class, idTask);
            txtNameTask.setText("#" + idTime + " " + task.getTitleTask());

            if(time.getMoreInformation().equals("")){
                findViewById(R.id.linear_more_information).setVisibility(View.GONE);
            }else{
                txtMoreInfo.setText(time.getMoreInformation());
            }

            txtDuration.setText(getDurationString(durationTime));
            txtDate.setText(dateCreate);
            txtCreated.setText(time.getStartAt());



            switch (time.getProductivity() ){
                case 2:
                    txtProductivity.setText(R.string.txt_good);
                    break;
                case 1:
                    txtProductivity.setText(R.string.txt_medium);
                    break;
                case 0:
                    txtProductivity.setText(R.string.txt_bad);
                    break;

            }

            switch (time.getHumorAfter() ){
                case 2:
                    txtHumorAfter.setText(R.string.txt_happy);
                    break;
                case 1:
                    txtHumorAfter.setText(R.string.txt_neutral);
                    break;
                case 0:
                    txtHumorAfter.setText(R.string.txt_sad);
                    break;
            }

            switch (time.getHumorBefore() ){
                case 2:
                    txtHumorBefore.setText(R.string.txt_happy);
                    break;
                case 1:
                    txtHumorBefore.setText(R.string.txt_neutral);
                    break;
                case 0:
                    txtHumorBefore.setText(R.string.txt_sad);
                    break;
            }

        }

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.editTime:

                    Intent i = new Intent(mcon, EditTaskTiming.class);
                    i.putExtra("idTime", getIdTime());
                    mcon.startActivity(i);

                    break;

                case R.id.deleteTime:

                    TimingModel timing = TimingModel.findById(TimingModel.class, getIdTime());
                    timing.delete();

                    findViewById(R.id.itemTiming).setVisibility(v.GONE);

                    showToast("Cronometragem deletada!");

                    break;
            }
        }

        private void showToast(String message) {
            Toast.makeText(itemView.getContext(), message, Toast.LENGTH_SHORT).show();
        }


        Long getIdTime(){ return idTime; }



    }

    private String getDurationString(int seconds) {

        int hours = seconds / 3600;
        int minutes = (seconds % 3600) / 60;
        seconds = seconds % 60;

        return twoDigitString(hours) + " : " + twoDigitString(minutes) + " : " + twoDigitString(seconds);
    }

    private String twoDigitString(int number) {
        if (number == 0) { return "00"; }
        if (number / 10 == 0) { return "0" + number; }
        return String.valueOf(number);
    }

}