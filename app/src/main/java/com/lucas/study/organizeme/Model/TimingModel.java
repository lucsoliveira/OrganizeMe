package com.lucas.study.organizeme.Model;

import com.orm.SugarRecord;

import java.util.List;

public class TimingModel extends SugarRecord<TimingModel> {

    private Long idTask;
    private String updatedAt;
    private String startAt;
    private String createdAt;
    private String finishedAt;
    private int timeSecondsTask;
    private int productivity;
    private int humorAfter;
    private int humorBefore;
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

    public TimingModel(){
    }

    public TimingModel(Long idTask,
                       int timeSecondsTask,
                       int productivity,
                       int humorBefore,
                       int humorAfter,
                       String moreInformation,
                       String createdAt,
                       String startAt,
                       String finishedAt){

        this.idTask = idTask;
        this.timeSecondsTask = timeSecondsTask;
        this.productivity = productivity;
        this.humorBefore = humorBefore;
        this.humorAfter = humorAfter;
        this.moreInformation = moreInformation;
        this.createdAt = createdAt;
        this.startAt = startAt;
        this.finishedAt = finishedAt;

    }

    /* GETS AND SETS*/

    public int getHumorAfter() {
        return humorAfter;
    }

    public void setHumorAfter(int humorAfter) {
        this.humorAfter = humorAfter;
    }

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

    public String getMoreInformation() {
        return moreInformation;
    }

    public void setMoreInformation(String moreInformation) { this.moreInformation = moreInformation; }

    public int getHumorBefore() {
        return humorBefore;
    }

    public void setHumorBefore(int humorBefore) {
        this.humorBefore = humorBefore;
    }


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

    public String getStartAt() {
        return startAt;
    }

    public void setStartAt(String startAt) {
        this.startAt = startAt;
    }

    public String getFinishedAt() { return finishedAt; }

    public void setFinishedAt(String finishedAt) { this.finishedAt = finishedAt; }

    /* END GETS AND SETS*/

    /* LIST CREATOR */
    public static List<TimingModel> createTaskTimeList() {

        //List<TaskTimingModel> listTasksTiming= TaskTimingModel.find(TaskTimingModel.class,"");
        List<TimingModel> listTasksTiming = TimingModel.findWithQuery(TimingModel.class,"Select * from Timing_Model order by finished_At DESC");
        return listTasksTiming;
    }

    /* LIST CREATOR */
    public static List<TimingModel> createTaskTimeListWithLimit(String limit) {

        List<TimingModel> listTasksTiming = TimingModel.findWithQuery(TimingModel.class,"Select * from Timing_Model order by id DESC limit ?",limit);
        return listTasksTiming;
    }
    public static List<TimingModel> createTaskTimeListWithLimit(String limit, String id) {

        List<TimingModel> listTasksTiming = TimingModel.findWithQuery(TimingModel.class,"Select * from Timing_Model where id_Task = ? order by id DESC limit ?", id, limit);

        return listTasksTiming;
    }
    public static List<TimingModel> createTaskTimeProductivity(String productivity, String humor, String limit) {

        List<TimingModel> listTimeProductivity = TimingModel.findWithQuery(TimingModel.class,"Select * from Timing_Model where productivity = ? and humor_Before = ? order by id DESC limit ?", productivity, humor, limit);

        return listTimeProductivity;
    }


    public static List<TimingModel> createTaskTimeProductivity(String productivity, String minDate, String maxDate, String apagar) {

        List<TimingModel> listTimeProductivity = TimingModel.findWithQuery(TimingModel.class,"Select * from Timing_Model where productivity = ? and finished_At between ? and ? order by id DESC", productivity, minDate, maxDate);

        return listTimeProductivity;
    }

    public static List<TimingModel> createTaskTime(String minDate, String maxDate) {

        List<TimingModel> listTimeProductivity = TimingModel.findWithQuery(TimingModel.class,"Select * from Timing_Model where finished_At between ? and ? order by id DESC", minDate, maxDate);

        return listTimeProductivity;
    }

    public static List<TimingModel> createTaskTime(String minDate) {

        List<TimingModel> listTimeProductivity = TimingModel.findWithQuery(TimingModel.class,"Select * from Timing_Model where finished_At < ? order by id DESC LIMIT 100", minDate);

        return listTimeProductivity;
    }


    public static List<TimingModel> createTaskTimeProductivity(String productivity, String dateNow) {

        List<TimingModel> listTimeProductivity = TimingModel.findWithQuery(TimingModel.class,"Select * from Timing_Model where productivity = ? and finished_At < ? order by id DESC LIMIT 100", productivity, dateNow);

        return listTimeProductivity;
    }


    /* END LIST CREATOR */
}