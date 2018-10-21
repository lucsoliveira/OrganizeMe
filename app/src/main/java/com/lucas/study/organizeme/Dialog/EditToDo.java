package com.lucas.study.organizeme.Dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.lucas.study.organizeme.R;
import com.lucas.study.organizeme.Model.ToDoModel;

import java.util.Calendar;

public class EditToDo extends Dialog implements View.OnClickListener {

    public Context c;
    public Button editToDo, deleteToDo;
    public Long idToDo;
    public EditText description, title;
    private CoordinatorLayout coordinatorLayout;
    public Calendar calendar;
    public boolean setNotChange = false;
    public EditToDo(Context a, Long idToDo) {
            super(a);
            // TODO Auto-generated constructor stub
            this.c = a;
            this.idToDo = idToDo;
        }


        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            requestWindowFeature(Window.FEATURE_NO_TITLE);
            setContentView(R.layout.dialog_edit_todo);

            editToDo = (Button) findViewById(R.id.editToDo);
            editToDo.setOnClickListener(this);


            deleteToDo = (Button) findViewById(R.id.deleteToDo);
            deleteToDo.setOnClickListener(this);

            title = (EditText)findViewById(R.id.editTodoName);
            description = (EditText)findViewById(R.id.editTodoDescription);


            ToDoModel todo = ToDoModel.findById(ToDoModel.class, idToDo);
            title.setText(todo.getTitle());
            description.setText(todo.getDescription());


            coordinatorLayout = (CoordinatorLayout) findViewById(R.id.coordinatorLayout);

        }


        @Override
        public void onClick(View v) {
            switch (v.getId()) {


                case R.id.editToDo:

                    ToDoModel t = ToDoModel.findById(ToDoModel.class, idToDo);

                    if(title.getText().toString().equals("")){
                        Toast.makeText(getContext(),R.string.todo_no_name,Toast.LENGTH_LONG).show();
                    }else{
                        t.setTitle(title.getText().toString());
                        t.setDescription(description.getText().toString());
                        t.save();
                        Toast.makeText(getContext(),R.string.todo_edit_success,Toast.LENGTH_LONG).show();
                    }

                    dismiss();


                    break;

                case R.id.deleteToDo:


                    final ToDoModel t2 = ToDoModel.findById(ToDoModel.class, idToDo);

                    Snackbar snackbar = Snackbar
                            .make(coordinatorLayout, "Lembrete deletado", Snackbar.LENGTH_LONG)
                            .setAction("DESFAZER", new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {

                                    setNotChange = true;
                                    t2.setStatus(0);
                                    t2.save();
                                    Snackbar snackbar1 = Snackbar.make(coordinatorLayout, "Lembrete retornou!", Snackbar.LENGTH_SHORT);
                                    snackbar1.show();
                                }
                            });

                    snackbar.show();

                    if(!setNotChange){
                        t2.setStatus(1);
                        t2.save();
                    }

                    break;

                default:
                    break;
            }

            //dismiss();
        }
}
