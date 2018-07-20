package com.lucas.study.organizeme;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class AddTaskActivity extends AppCompatActivity {

    EditText editTextTitle;
    private Button addTask;

    private CoordinatorLayout coordinatorLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_add_task);

        coordinatorLayout = (CoordinatorLayout) findViewById(R.id
                .coordinatorLayout);

        editTextTitle   = (EditText)findViewById(R.id.editTextTitle);

        // Get the Intent that started this activity and extract the string
        Intent intent = getIntent();

        addTask = (Button) findViewById(R.id.addTask);

        addTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                TaskModel t = new TaskModel(editTextTitle.getText().toString());
                t.save();

                Snackbar snackbar = Snackbar
                        .make(coordinatorLayout, "Atividade criada", Snackbar.LENGTH_LONG)
                        .setAction("DESFAZER", new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Snackbar snackbar1 = Snackbar.make(coordinatorLayout, "Atividade deletada!", Snackbar.LENGTH_SHORT);
                                snackbar1.show();
                            }
                        });

                snackbar.show();
            }
        });

    }

}