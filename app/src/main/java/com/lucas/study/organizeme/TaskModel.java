package com.lucas.study.organizeme;

import com.orm.SugarRecord;
import com.orm.query.Select;

import java.util.ArrayList;
import java.util.List;

public class TaskModel extends SugarRecord<TaskModel> {

    String titleTask;

    public TaskModel(){
    }

    public TaskModel(String titleTask){
        this.titleTask = titleTask;
    }

    public String getTitleTask(){ return titleTask; }

    private static int lastTaskId = 0;

    public static List<TaskModel> createTaskList() {

        List<TaskModel> listTasks = TaskModel.find(TaskModel.class,"");
        return listTasks;
    }
}