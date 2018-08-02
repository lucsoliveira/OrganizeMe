package com.lucas.study.organizeme;

import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.omega_r.libs.omegarecyclerview.OmegaRecyclerView;

import net.steamcrafted.materialiconlib.MaterialDrawableBuilder;

import java.util.List;

public class ToDoTab extends Fragment {

    public List<ToDoModel> listToDoDoing;
    public CoordinatorLayout messageNoActivities;
    public FloatingActionButton mFloatingActionButton;
    public CoordinatorLayout coordinatorLayout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.tab_todo, container, false);

        OmegaRecyclerView omegaRecyclerView = rootView.findViewById(R.id.recycler_view_todo);
        coordinatorLayout = (CoordinatorLayout) rootView.findViewById(R.id.coordinatorLayoutToDo);
        mFloatingActionButton = (FloatingActionButton) rootView.findViewById(R.id.floating_action_button_todo);
        messageNoActivities = (CoordinatorLayout) rootView.findViewById(R.id.messagesToDo);

        omegaRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (dy > 0 && mFloatingActionButton.getVisibility() == View.VISIBLE) {
                    mFloatingActionButton.hide();
                } else if (dy < 0 && mFloatingActionButton.getVisibility() != View.VISIBLE) {
                    mFloatingActionButton.show();
                }
            }
        });

        listToDoDoing = ToDoModel.creatListTodo("0");




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
