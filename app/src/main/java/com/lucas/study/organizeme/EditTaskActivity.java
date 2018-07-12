package com.lucas.study.organizeme;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class EditTaskActivity extends AppCompatActivity {

    EditText editTextTitle;
    private Button editTask;

    private CoordinatorLayout coordinatorLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_edit_task);

        coordinatorLayout = (CoordinatorLayout) findViewById(R.id.coordinatorLayout);
        editTextTitle   = (EditText)findViewById(R.id.editTextTitle);



        Intent intent = getIntent();
        // Capture the DB title and set the string as its text
        final TaskModel t = TaskModel.findById(TaskModel.class, intent.getLongExtra("idTask", 0));
        final String oldTitle = t.getTitleTask();
        //change de edit text for name text
        editTextTitle.setText(oldTitle);


        editTask = (Button) findViewById(R.id.editTask);

        editTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //verify if the old title dont have change
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
        });


    }

}
