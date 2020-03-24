package com.example.moduleb.service;

import android.app.ActivityManager;
import android.app.job.JobInfo;
import android.app.job.JobParameters;
import android.app.job.JobScheduler;
import android.app.job.JobService;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;

import androidx.annotation.RequiresApi;

import java.util.List;

@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
public class JobWakeUpService extends JobService {
    public JobWakeUpService() {
    }
    private int JobWakeUpId = 1;

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i("test","onStartCommand");
        JobInfo.Builder mJobBuilder = new JobInfo.Builder(JobWakeUpId,new ComponentName(this,JobWakeUpService.class))
                .setPeriodic(5 * 1000L);

        JobScheduler mJobScheduler = (JobScheduler) getSystemService(Context.JOB_SCHEDULER_SERVICE);
//        执行任务
        mJobScheduler.schedule(mJobBuilder.build());
        return START_STICKY;
    }
    @Override
    public boolean onStartJob(JobParameters params) {
//        判断服务有没有在运行
        boolean messageServiceAlive = serviceAlive(AlarmService.class.getName());
        if (!messageServiceAlive){
            startService(new Intent(this,AlarmService.class));
        }
        return false;
    }

    @Override
    public boolean onStopJob(JobParameters params) {
        return false;
    }
    private boolean serviceAlive(String serviceName){
        boolean isWork = false;
        ActivityManager myAm = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningServiceInfo> myList = myAm.getRunningServices(100);
        if(myList.size() <= 0){
            return false;
        }
        for(int i = 0; i < myList.size(); i ++){
            String mName = myList.get(i).service.getClassName().toString();
            if(mName.equals(serviceName)){
                isWork = true;
                break;
            }
        }
        return isWork;
    }
}
