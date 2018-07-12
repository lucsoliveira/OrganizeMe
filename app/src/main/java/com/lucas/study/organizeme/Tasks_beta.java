package com.lucas.study.organizeme;

import java.util.ArrayList;
import java.util.List;

public class Tasks_beta {

    private String mName;
    private boolean mOnline;

    public Tasks_beta(String name, boolean online) {
        mName = name;
        mOnline = online;
    }

    public String getName() {
        return mName;
    }

    public boolean isOnline() {
        return mOnline;
    }

    private static int lastContactId = 0;

    public static List<Tasks_beta> createContactsList(int numContacts) {
        List<Tasks_beta> tasksArrayList = new ArrayList<>();

        for (int i = 0; i < numContacts; i++) {
            tasksArrayList.add(new Tasks_beta("Estudacar Calculo " + ++lastContactId, i <= numContacts / 2));
        }

        return tasksArrayList;
    }

}