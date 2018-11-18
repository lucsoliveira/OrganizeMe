package com.lucas.study.organizeme.Activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.lucas.study.organizeme.Adapter.ToDo;
import com.lucas.study.organizeme.MainActivity;
import com.lucas.study.organizeme.Model.TaskModel;
import com.lucas.study.organizeme.Page.ToDos;
import com.lucas.study.organizeme.R;
import com.lucas.study.organizeme.Model.ToDoModel;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class AddToDo extends AppCompatActivity {

    EditText editTextTodoTitle, editTextTodoDescription;
    private Button addTodo;

    public Calendar calendar;
    private CoordinatorLayout coordinatorLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_todo);

        coordinatorLayout = (CoordinatorLayout) findViewById(R.id
                .coordinatorLayout);

        editTextTodoTitle   = (EditText)findViewById(R.id.editTextTodoName);
        editTextTodoDescription   = (EditText)findViewById(R.id.editTextTodoDescription);

        // Get the Intent that started this activity and extract the string
        Intent intent = getIntent();

        addTodo = (Button) findViewById(R.id.addToDo);

        addTodo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //GET TIME NOW
                Calendar c = Calendar.getInstance();
                //System.out.println("Current time =&gt; "+c.getTime());
                SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
                String formattedDate = df.format(c.getTime());
                //Toast.makeText(getContext(), formattedDate, Toast.LENGTH_SHORT).show();

                closeKeyboard();

                if (editTextTodoTitle.getText().toString().equals("")) {
                    Toast.makeText(getApplicationContext(), "Preencha todos os campos.", Toast.LENGTH_SHORT).show();
                } else {
                    ToDoModel t = new ToDoModel(editTextTodoTitle.getText().toString(),
                            editTextTodoDescription.getText().toString(),
                            0,
                            formattedDate);
                    t.save();

                    Toast.makeText(getApplicationContext(), "Lembrete criado com sucesso.", Toast.LENGTH_SHORT).show();


                    //finish and open mainactivity with tab to do
                    Intent i = new Intent(getApplicationContext(), MainActivity.class);
                    i.putExtra("pageTab",2);
                    startActivity(i);
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
