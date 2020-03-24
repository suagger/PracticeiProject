package com.example.moduleb.data;

import java.util.Date;

public class GsonData {
//    任务名称
    String taskname;
//    任务时间
    Date tasktime;

    public Date getTasktime() {
        return tasktime;
    }

    public void setTasktime(Date tasktime) {
        this.tasktime = tasktime;
    }

    public String getTaskname() {
        return taskname;
    }

    public void setTaskname(String taskname) {
        this.taskname = taskname;
    }

    @Override
    public String toString() {
        return "GsonData{" +
                "taskname='" + taskname + '\'' +
                ", tasktime=" + tasktime +
                '}';
    }
}
