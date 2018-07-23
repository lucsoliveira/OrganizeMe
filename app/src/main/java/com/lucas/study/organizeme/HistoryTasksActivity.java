package com.lucas.study.organizeme;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class HistoryTasksActivity extends AppCompatActivity {

    EditText editTextTitle;
    private Button editTask;

    private CoordinatorLayout coordinatorLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_edit_task);

        coordinatorLayout = (CoordinatorLayout) findViewById(R.id.coordinatorLayout);
        editTextTitle   = (EditText)findViewById(R.id.editTextTitle);


        editTask = (Button) findViewById(R.id.editTask);



    }

}
