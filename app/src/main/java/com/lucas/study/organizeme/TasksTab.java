package com.lucas.study.organizeme;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.omega_r.libs.omegarecyclerview.OmegaRecyclerView;

import net.steamcrafted.materialiconlib.MaterialDrawableBuilder;

import java.util.List;

public class TasksTab extends Fragment {

    public List<TaskModel> listTasks;
    public CoordinatorLayout messageNoActivities;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.tab_activities, container, false);

        OmegaRecyclerView omegaRecyclerView = rootView.findViewById(R.id.custom_recycler_view);

        listTasks = TaskModel.findWithQuery(TaskModel.class, "Select * from Task_Model where status = ? order by id DESC", "1");
        messageNoActivities = (CoordinatorLayout) rootView.findViewById(R.id.messageActivities);

        if (listTasks.size() == 0) {

            MessageView msgNoTasks = new MessageView(R.string.message_no_tasks,
                    MaterialDrawableBuilder.IconValue.WEATHER_RAINY,
                    messageNoActivities, R.color.colorPrimaryDark,
                    96);

            msgNoTasks.showMessageView();

        } else {
            TasksAdapter adapter = new TasksAdapter(listTasks, getActivity());
            omegaRecyclerView.setAdapter(adapter);
        }

        return rootView;
    }
}
