package com.lucas.study.organizeme;

import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.omega_r.libs.omegarecyclerview.OmegaRecyclerView;

import net.steamcrafted.materialiconlib.MaterialDrawableBuilder;

import java.util.List;

public class ToDoTab extends Fragment {

    public List<ToDoModel> listToDoDoing;
    public CoordinatorLayout messageNoActivities;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.tab_todo, container, false);
        /*
        TextView textView = (TextView) rootView.findViewById(R.id.section_label);
        textView.setText(getString(R.string.section_format, getArguments().getInt(ARG_SECTION_NUMBER)));
        */

        OmegaRecyclerView omegaRecyclerView = rootView.findViewById(R.id.recycler_view_todo);

        //listToDoDoing = ToDoModel.findWithQuery(ToDoModel.class, "Select * from To_Do_Model order by id DESC");
        listToDoDoing = ToDoModel.creatListTodo("0");
        messageNoActivities = (CoordinatorLayout) rootView.findViewById(R.id.messagesToDo);



        if (listToDoDoing.size() == 0) {

            MessageView msgNoToDo = new MessageView(R.string.message_no_todo,
                    MaterialDrawableBuilder.IconValue.CHECK,
                    messageNoActivities, R.color.colorPrimaryDark,
                    96);

            msgNoToDo.showMessageView();


        } else {
            ToDoAdapter adapter = new ToDoAdapter(listToDoDoing, getActivity());
            omegaRecyclerView.setAdapter(adapter);
        }

        return rootView;
    }
}
