package com.lucas.study.organizeme;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.omega_r.libs.omegarecyclerview.OmegaRecyclerView;

public class TabTasks extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_tab1, container, false);

        OmegaRecyclerView omegaRecyclerView = rootView.findViewById(R.id.custom_recycler_view);

        TasksAdapter adapter = new TasksAdapter(TaskModel.createTaskList(),getActivity());
        omegaRecyclerView.setAdapter(adapter);


        return rootView;
    }


}