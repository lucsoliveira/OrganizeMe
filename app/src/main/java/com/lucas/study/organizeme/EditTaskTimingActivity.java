package com.lucas.study.organizeme;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class EditTaskTimingActivity extends AppCompatActivity {

    EditText editTextTitle;
    private Button editTask;

    private CoordinatorLayout coordinatorLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_task_timing);

        coordinatorLayout = (CoordinatorLayout) findViewById(R.id.coordinatorLayout);
        editTextTitle   = (EditText)findViewById(R.id.editTextTitle);



        Intent intent = getIntent();
        // Capture the DB title and set the string as its text
        final TaskTimingModel t = TaskTimingModel.findById(TaskTimingModel.class, intent.getLongExtra("idTime", 0));

        editTask = (Button) findViewById(R.id.editTask);
        /*
        editTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String newTitle = editTextTitle.getText().toString();

                if(oldTitle.equals(newTitle)){

                }else{

                    t.titleTask = newTitle; // modify the values
                    t.save(); // updates the previous entry with new values.

                    Snackbar snackbar = Snackbar
                            .make(coordinatorLayout, "Atividade atualizada", Snackbar.LENGTH_LONG)
                            .setAction("DESFAZER", new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {


                                    t.titleTask = oldTitle; // revert the title
                                    Snackbar snackbar1 = Snackbar.make(coordinatorLayout, "Atividade revertida!", Snackbar.LENGTH_SHORT);
                                    snackbar1.show();
                                }
                            });

                    snackbar.show();

                }



            }
            */
        }


}

