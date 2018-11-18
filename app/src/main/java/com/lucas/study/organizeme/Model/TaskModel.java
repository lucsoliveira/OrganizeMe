package com.lucas.study.organizeme.Model;

import com.orm.SugarRecord;

import java.util.List;

public class TaskModel extends SugarRecord<TaskModel> {

    private static int lastTaskId = 0;
    public String titleTask;
    public int status;//1 - Active ,  0 - Not

    public TaskModel() {
    }

    public TaskModel(String titleTask, int statusTask) {
        this.titleTask = titleTask;
        this.status = statusTask;
    }

    public static List<TaskModel> createTaskList() {

        //List<TaskModel> listTasks = TaskModel.find(TaskModel.class,"");

        List<TaskModel> listTasks = TaskModel.findWithQuery(TaskModel.class, "Select * from Task_Model where status = ? order by id DESC", "1");

        return listTasks;
    }

    public int getStatusTask() {
        return status;
    }

    public void setStatusTask(int statusTask) {
        this.status = statusTask;
    }

    public String getTitleTask() {
        return titleTask;
    }

}