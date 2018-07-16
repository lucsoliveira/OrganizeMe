package com.lucas.study.organizeme;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.omega_r.libs.omegarecyclerview.OmegaRecyclerView;
import com.omega_r.libs.omegarecyclerview.swipe_menu.SwipeViewHolder;

import java.util.ArrayList;
import java.util.List;


public class TasksAdapter extends OmegaRecyclerView.Adapter<TasksAdapter.ViewHolder> {

    private Context mcon;

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

        private ImageButton btnPlay;
        private ImageButton btnPause;
        private ImageButton btnStop;

        private TextView txtNameTask;

        private Button btnEditTask;
        private Button btnDeleteTask;

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
                    t.delete();

                    showToast("Atividade deletada!");


                    mTasksList.remove(getIdTask());
                    updateView(t);

                    break;

                case R.id.btnPlay:
                    showToast("Voce clicou em Play: ");

                    break;

                case R.id.btnPause:
                    showToast("Voce clicou em Pause: ");

                    break;

                case R.id.btnStop:

                    DialogEndActivity cdd = new DialogEndActivity(itemView.getContext(), getIdTask());
                    cdd.show();

                    /*
                    Intent i2 = new Intent(mcon, DialogEndActivity.class);
                    //i2.putExtra("idTask", getIdTask());
                    mcon.startActivity(i2);
                    */


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