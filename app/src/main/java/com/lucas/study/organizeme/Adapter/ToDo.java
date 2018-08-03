package com.lucas.study.organizeme.Adapter;

import android.content.Context;
import android.graphics.Paint;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.TextView;

import com.lucas.study.organizeme.Dialog.EditToDo;
import com.lucas.study.organizeme.R;
import com.lucas.study.organizeme.Model.ToDoModel;
import com.omega_r.libs.omegarecyclerview.OmegaRecyclerView;
import com.omega_r.libs.omegarecyclerview.swipe_menu.SwipeViewHolder;

import java.util.ArrayList;
import java.util.List;


public class ToDo extends OmegaRecyclerView.Adapter<ToDo.ViewHolder> {

    private Context mcon;

    private List<ToDoModel> mTodoList = new ArrayList<>();

    public ToDo(List<ToDoModel> todoList, Context con) {

        mcon = con;
        mTodoList = todoList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(parent);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holderTime, int position) {
        holderTime.updateView(mTodoList.get(position));
    }

    @Override
    public int getItemCount() {
        return mTodoList.size();
    }

    public class ViewHolder extends SwipeViewHolder implements View.OnClickListener{

        private TextView txtNameToDo, txtDescription;
        public ImageButton editToDo;
        private long idToDo;
        private String dateCreate, description, title;
        private CheckBox checkToDo;
        private int statusToDo;

        public ViewHolder(ViewGroup parent) {
            super(parent, R.layout.item_todo);

            //BUTTONS
            editToDo = (ImageButton) findViewById(R.id.editToDo);
            editToDo.setOnClickListener(this);

            //TEXT VIEW
            txtNameToDo = (TextView) findViewById(R.id.txtNameToDo);
            txtNameToDo.setOnClickListener(this);
            //CHECKBOX
            checkToDo= (CheckBox) findViewById(R.id.checkBoxToDo);
            //checkbox click event handling
            checkToDo.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView,
                                             boolean isChecked) {
                    ToDoModel t = ToDoModel.findById(ToDoModel.class, getIdToDo());
                    if (isChecked) {
                        t.setStatus(1);
                        t.save();
                        txtNameToDo.setPaintFlags(txtNameToDo.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);

                    } else {
                        t.setStatus(0);
                        t.save();
                        txtNameToDo.setPaintFlags(txtNameToDo.getPaintFlags() & (~ Paint.STRIKE_THRU_TEXT_FLAG));
                    }
                }
            });

        }

        void updateView(ToDoModel todo) {

            idToDo = todo.getId();
            dateCreate = todo.getCreatedAt();
            title = todo.getTitle();
            description = todo.getDescription();
            txtNameToDo.setText(title);

            statusToDo = todo.getStatus();

            if(statusToDo == 0){
                checkToDo.setChecked(false);
            }

            if(statusToDo == 1){
                checkToDo.setChecked(true);
            }
        }

        Long getIdToDo(){ return idToDo;}
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.txtNameToDo:

                    EditToDo cdd = new EditToDo(itemView.getContext(), getIdToDo());
                    cdd.show();
                    break;
                case R.id.editToDo:

                    EditToDo cdd2 = new EditToDo(itemView.getContext(), getIdToDo());
                    cdd2.show();
                    break;
            }
        }
    }



}