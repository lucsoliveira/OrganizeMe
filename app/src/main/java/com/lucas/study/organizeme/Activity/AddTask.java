package com.lucas.study.organizeme.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.lucas.study.organizeme.Adapter.Task;
import com.lucas.study.organizeme.MainActivity;
import com.lucas.study.organizeme.R;
import com.lucas.study.organizeme.Model.TaskModel;

import java.util.List;

public class AddTask extends AppCompatActivity {

    EditText editTextTitle;
    private Button addTask;

    private CoordinatorLayout coordinatorLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);

        coordinatorLayout = (CoordinatorLayout) findViewById(R.id
                .coordinatorLayout);
        editTextTitle   = (EditText)findViewById(R.id.editTextTitle);

        // Get the Intent that started
        // this activity and extract the string
        Intent intent = getIntent();



        addTask = (Button) findViewById(R.id.addTask);

        addTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                closeKeyboard();
                if(editTextTitle.getText().toString().equals("")){
                    Toast.makeText(getApplicationContext(), R.string.task_error_create, Toast.LENGTH_SHORT).show();
                }else{

                    List<TaskModel> tasksFindSameTitle = TaskModel.find(TaskModel.class, "title_Task = ? and status = 1", editTextTitle.getText().toString());
                    if(tasksFindSameTitle.size() ==0){

                        TaskModel t = new TaskModel(editTextTitle.getText().toString(), 1);
                        t.save();
                        final Long idCreated = t.getId();

                        Toast.makeText(getApplicationContext(),"Atividade criada com sucesso.",Toast.LENGTH_LONG).show();

                        Intent i = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(i);
                        finish();
                    }else{
                        Toast.makeText(getApplicationContext(),"Já existe uma atividade com esse nome.", Toast.LENGTH_SHORT).show();
                    }
                    /*

                    Snackbar snackbar = Snackbar
                            .make(coordinatorLayout, "Atividade criada", Snackbar.LENGTH_LONG)
                            .setAction("DESFAZER", new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    TaskModel toDeleteTask = TaskModel.findById(TaskModel.class, idCreated);
                                    toDeleteTask.delete();

                                    Snackbar snackbar1 = Snackbar.make(coordinatorLayout, "Atividade desfeita!", Snackbar.LENGTH_SHORT);
                                    snackbar1.show();
                                }
                            });

                    snackbar.show();

                    */
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

}
