package com.lucas.study.organizeme.Activity;

import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;

import com.lucas.study.organizeme.R;
import com.lucas.study.organizeme.Adapter.Timing;
import com.lucas.study.organizeme.Model.TimingModel;
import com.omega_r.libs.omegarecyclerview.OmegaRecyclerView;

public class HistoryTasks extends AppCompatActivity {

    EditText editTextTitle;
    private Button editTask;

    private CoordinatorLayout coordinatorLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_timing_history);


        OmegaRecyclerView omegaRecyclerView = findViewById(R.id.recyclerViewTimes);


        Timing adapter = new Timing(TimingModel.createTaskTimeList(),this);
        omegaRecyclerView.setAdapter(adapter);

    }

}
