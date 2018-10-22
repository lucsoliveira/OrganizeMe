package com.lucas.study.organizeme.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.lucas.study.organizeme.R;
import com.lucas.study.organizeme.Model.TaskModel;

public class EditTask extends AppCompatActivity {

    EditText editTextTitle;
    private Button editTask;

    private CoordinatorLayout coordinatorLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_task);

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

                closeKeyboard();
                if(oldTitle.equals(newTitle)){

                    showToast("Não houve nenhuma alteração.");

                }else{

                    t.titleTask = newTitle; // modify the values
                    t.save(); // updates the previous entry with new values.

                    Snackbar snackbar = Snackbar
                            .make(coordinatorLayout, "Atividade atualizada", Snackbar.LENGTH_LONG)
                            .setAction("DESFAZER", new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {

                                    t.titleTask = oldTitle; // revert the title
                                    t.save();
                                    Snackbar snackbar1 = Snackbar.make(coordinatorLayout, "Atividade revertida!", Snackbar.LENGTH_SHORT);
                                    snackbar1.show();
                                }
                            });

                    snackbar.show();

                }

            }
        });


    }

    private void closeKeyboard() {
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(getApplicationContext().INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

}
