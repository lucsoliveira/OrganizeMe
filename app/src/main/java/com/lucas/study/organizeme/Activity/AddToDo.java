package com.lucas.study.organizeme.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

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



                ToDoModel t = new ToDoModel(editTextTodoTitle.getText().toString(),
                        editTextTodoDescription.getText().toString(),
                        0,
                        formattedDate);
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
