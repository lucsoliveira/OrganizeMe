package com.lucas.study.organizeme;

import com.orm.SugarRecord;

import java.util.Date;
import java.util.List;

public class TaskTimingModel extends SugarRecord<TaskTimingModel> {

    private Long idTask;
    private String createdAt;
    private String updatedAt;
    private int timeSecondsTask;
    private int productivity;
    private int humor;
    private String moreInformation;
    /*

     + Productivity
        2 - Good
        1 - Medium
        0 - Bad

     + Humor
        2 - Happy
        1 - Neutral
        0 - Sad

     */

    public TaskTimingModel(){
    }

    public TaskTimingModel(Long idTask, int timeSecondsTask, int productivity, int humor, String moreInformation, String createdAt){

        this.idTask = idTask;
        this.timeSecondsTask = timeSecondsTask;
        this.productivity = productivity;
        this.humor = humor;
        this.moreInformation = moreInformation;
        this.createdAt = createdAt;

    }

    /* GETS AND SETS*/
    public Long getIdTask() {
        return idTask;
    }

    public void setIdTask(Long idTask) {
        this.idTask = idTask;
    }

    public int getTimeSecondsTask() {
        return timeSecondsTask;
    }

    public void setTimeSecondsTask(int timeSecondsTask) {
        this.timeSecondsTask = timeSecondsTask;
    }

    public int getProductivity() {
        return productivity;
    }

    public void setProductivity(int productivity) {
        this.productivity = productivity;
    }

    public int getHumor() {
        return humor;
    }

    public void setHumor(int humor) {
        this.humor = humor;
    }

    public String getMoreInformation() {
        return moreInformation;
    }

    public void setMoreInformation(String moreInformation) {
        this.moreInformation = moreInformation;
    }
    /* END GETS AND SETS*/

    /* LIST CREATOR */
    public static List<TaskTimingModel> createTaskTimeList() {

        //List<TaskTimingModel> listTasksTiming= TaskTimingModel.find(TaskTimingModel.class,"");
        List<TaskTimingModel> listTasksTiming = TaskTimingModel.findWithQuery(TaskTimingModel.class,"Select * from Task_Timing_Model order by id DESC");
        return listTasksTiming;
    }

    /* LIST CREATOR */
    public static List<TaskTimingModel> createTaskTimeListWithLimit(String limit) {

        List<TaskTimingModel> listTasksTiming = TaskTimingModel.findWithQuery(TaskTimingModel.class,"Select * from Task_Timing_Model order by id DESC limit ?",limit);
        return listTasksTiming;
    }
    public static List<TaskTimingModel> createTaskTimeListWithLimit(String limit, String id) {

        List<TaskTimingModel> listTasksTiming = TaskTimingModel.findWithQuery(TaskTimingModel.class,"Select * from Task_Timing_Model where id_Task = ? order by id DESC limit ?", id, limit);

        return listTasksTiming;
    }
    public static List<TaskTimingModel> createTaskTimeProductivity(String productivity, String humor, String limit) {

        List<TaskTimingModel> listTimeProductivity = TaskTimingModel.findWithQuery(TaskTimingModel.class,"Select * from Task_Timing_Model where productivity = ? and humor = ? order by id DESC limit ?", productivity, humor, limit);

        return listTimeProductivity;
    }

    /* END LIST CREATOR */
    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }
}