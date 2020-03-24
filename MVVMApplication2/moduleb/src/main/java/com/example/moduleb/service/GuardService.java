package com.example.moduleb.service;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Build;
import android.os.IBinder;

import androidx.core.app.NotificationCompat;

import com.example.moduleb.ProcessConnection;

public class GuardService extends Service {
    private int GuardId = 1;
    public GuardService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        return new ProcessConnection.Stub() {
        };
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            builder.setChannelId("notification_id");
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationManager notificationManager = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);
            NotificationChannel channel = new NotificationChannel("notification_id", "notification_name", NotificationManager.IMPORTANCE_LOW);
            notificationManager.createNotificationChannel(channel);
        }
//提高进程的优先级
        startForeground(GuardId,builder.build());
//        绑定建立连接
        bindService(new Intent(this, AlarmService.class),mServiceConnection, Context.BIND_IMPORTANT);
        return START_STICKY;
    }

    ServiceConnection mServiceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
//            Toast.makeText(GuardService.this, "与Message建立连接", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            startService(new Intent(GuardService.this,AlarmService.class));
            bindService(new Intent(GuardService.this,AlarmService.class),mServiceConnection, Context.BIND_IMPORTANT);
        }
    };

}
