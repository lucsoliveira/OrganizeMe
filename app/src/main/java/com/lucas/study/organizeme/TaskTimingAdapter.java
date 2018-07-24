package com.lucas.study.organizeme;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.omega_r.libs.omegarecyclerview.OmegaRecyclerView;
import com.omega_r.libs.omegarecyclerview.swipe_menu.SwipeViewHolder;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class TaskTimingAdapter extends OmegaRecyclerView.Adapter<TaskTimingAdapter.ViewHolder> {

    private Context mcon;

    private List<TaskTimingModel> mTasksTimeList = new ArrayList<>();

    public TaskTimingAdapter(List<TaskTimingModel> tasksList, Context con) {

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

        private TextView txtNameTask, txtDuration, txtProductivity, txtHumor, txtMoreInfo;
        private ImageButton editTime, deleteTime;
        private long idTime, idTask;
        private int durationTime;
        private String nameTask;

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
            txtHumor = findViewById(R.id.txtHumor);
            txtMoreInfo = findViewById(R.id.txtMoreInfo);

        }

        void updateView(TaskTimingModel time) {

            idTime = time.getId();
            idTask = time.getIdTask();
            durationTime = time.getTimeSecondsTask();
            Date d = new Date(durationTime * 1000);


            TaskModel task = TaskModel.findById(TaskModel.class, idTask);
            txtNameTask.setText(task.getTitleTask());

            if(time.getMoreInformation().equals("")){
                findViewById(R.id.linear_more_information).setVisibility(View.GONE);
            }else{
                txtMoreInfo.setText(time.getMoreInformation());
            }

            txtDuration.setText(String.valueOf(durationTime));

            switch (time.getProductivity() ){
                case 1:
                    txtProductivity.setText(R.string.txt_good);
                    break;
                case 2:
                    txtProductivity.setText(R.string.txt_medium);
                    break;
                case 3:
                    txtProductivity.setText(R.string.txt_bad);
                    break;

            }

            switch (time.getHumor() ){
                case 1:
                    txtHumor.setText(R.string.txt_happy);
                    break;
                case 2:
                    txtHumor.setText(R.string.txt_neutral);
                    break;
                case 3:
                    txtHumor.setText(R.string.txt_sad);
                    break;
            }

        }

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.editTime:
                    /*
                    Intent i = new Intent(mcon, EditTaskActivity.class);
                    i.putExtra("idTask", getIdTask());
                    mcon.startActivity(i);
                    */
                    break;

                case R.id.deleteTime:

                    /*
                    TaskModel t = TaskModel.findById(TaskModel.class, getIdTask());
                    t.delete();

                    showToast("Atividade deletada!");


                    mTasksTimeList.remove(getIdTask());
                    updateView(t);
                    */

                    TaskTimingModel timing = TaskTimingModel.findById(TaskTimingModel.class, getIdTime());
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





}