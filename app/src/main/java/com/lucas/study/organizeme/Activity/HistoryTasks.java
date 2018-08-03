package com.lucas.study.organizeme.Activity;

import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;

import com.lucas.study.organizeme.Adapter.Task;
import com.lucas.study.organizeme.R;
import com.lucas.study.organizeme.Adapter.Timing;
import com.lucas.study.organizeme.Model.TimingModel;
import com.lucas.study.organizeme.View.Message;
import com.omega_r.libs.omegarecyclerview.OmegaRecyclerView;

import net.steamcrafted.materialiconlib.MaterialDrawableBuilder;

public class HistoryTasks extends AppCompatActivity {

    EditText editTextTitle;
    private Button editTask;

    public CoordinatorLayout layoutMessageNoTiming, coordinatorLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timing_history);


        layoutMessageNoTiming = (CoordinatorLayout) findViewById(R.id.messageNoTiming);
        OmegaRecyclerView omegaRecyclerView = findViewById(R.id.recyclerViewTimes);
        Message messageNoTiming = new Message(R.string.message_no_timing,
                MaterialDrawableBuilder.IconValue.TIMER_SAND_EMPTY,
                layoutMessageNoTiming, R.color.colorPrimaryDark,
                96);


        if (TimingModel.createTaskTimeList().size() == 0) {

            messageNoTiming.showMessageView();

        } else {
            Timing adapter = new Timing(TimingModel.createTaskTimeList(),this);
            omegaRecyclerView.setAdapter(adapter);
        }

    }

}
