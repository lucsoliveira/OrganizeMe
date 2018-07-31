package com.lucas.study.organizeme;

import com.orm.SugarRecord;

import java.util.List;

public class ToDoModel extends SugarRecord<ToDoModel> {

    private String description;
    private int status;
    private String createdAt;
    private String updatedAt;

    /*
     + Status
        0 - Todo
        1 - Finished
     */

    public ToDoModel(){
    }

    public ToDoModel(String description, int status, String createdAt){

        this.description = description;
        this.status = status;
        this.createdAt = createdAt;

    }

    /* GETS AND SETS*/

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
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
    /* END GETS AND SETS*/

    /* LIST CREATOR */
    public static List<ToDoModel> creatListTodo(String status) {

        List<ToDoModel> listToDo = ToDoModel.findWithQuery(ToDoModel.class,"Select * from To_Do_Model where status = ? order by id DESC",status);
        return listToDo;
    }
}