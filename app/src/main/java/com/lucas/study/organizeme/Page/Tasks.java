package com.lucas.study.organizeme.Page;

import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lucas.study.organizeme.View.Message;
import com.lucas.study.organizeme.R;
import com.lucas.study.organizeme.Model.TaskModel;
import com.lucas.study.organizeme.Adapter.Task;
import com.omega_r.libs.omegarecyclerview.OmegaRecyclerView;

import net.steamcrafted.materialiconlib.MaterialDrawableBuilder;

import java.util.List;

public class Tasks extends Fragment {

    public List<TaskModel> listTasks;
    public CoordinatorLayout messageNoActivities;

    public FloatingActionButton mFloatingActionButtonTasks;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.tab_activities, container, false);

        OmegaRecyclerView omegaRecyclerView = rootView.findViewById(R.id.custom_recycler_view);

        listTasks = TaskModel.findWithQuery(TaskModel.class, "Select * from Task_Model where status = ? order by id DESC", "1");
        messageNoActivities = (CoordinatorLayout) rootView.findViewById(R.id.messageActivities);
        mFloatingActionButtonTasks = (FloatingActionButton) rootView.findViewById(R.id.floating_action_button_tasks);

        omegaRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (dy > 0 && mFloatingActionButtonTasks.getVisibility() == View.VISIBLE) {
                    mFloatingActionButtonTasks.hide();
                } else if (dy < 0 && mFloatingActionButtonTasks.getVisibility() != View.VISIBLE) {
                    mFloatingActionButtonTasks.show();
                }
            }
        });

        Message msgNoTasks = new Message(R.string.message_no_tasks_tab,
                MaterialDrawableBuilder.IconValue.TIMETABLE,
                messageNoActivities, R.color.colorPrimaryDark,
                96);


        if (listTasks.size() == 0) {


            msgNoTasks.showMessageView();

        } else {
            Task adapter = new Task(listTasks, getActivity());
            omegaRecyclerView.setAdapter(adapter);

        }

        return rootView;
    }

}
