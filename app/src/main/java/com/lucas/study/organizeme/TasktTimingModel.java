package com.lucas.study.organizeme;

import com.orm.SugarRecord;

import java.util.List;

public class TasktTimingModel extends SugarRecord<TasktTimingModel> {

    Long idTask;
    int timeSecondsTask;
    int productivity;
    int humor;
    String moreInformation;

    /*

     + Productivity
        1 - Good
        2 - Medium
        3 - Bad

     + Humor
        1 - Happy
        2 - Neutral
        3 - Sad

     */

    public TasktTimingModel(){
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
    public static List<TasktTimingModel> createTaskTimingList() {

        List<TasktTimingModel> listTasksTiming= TasktTimingModel.find(TasktTimingModel.class,"");
        return listTasksTiming;
    }
}