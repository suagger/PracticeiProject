package com.example.moduleb.service;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.Vibrator;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;

import com.example.moduleb.ProcessConnection;
import com.example.moduleb.R;
import com.example.moduleb.data.Dates;
import com.example.moduleb.fragment.RemindingFragment;

import java.io.IOException;

public class AlarmService extends Service {
    private int alarmId = 1;
    private MediaPlayer mediaPlayer;
    private Handler mHandler = new Handler(Looper.getMainLooper());
    public AlarmService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        return new AlarmBind();
    }
    private class AlarmBind extends ProcessConnection.Stub {
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        String information;
        Dates item = (Dates) RemindingFragment.dateList.get(RemindingFragment.j - 1);
        information = item.getInformation();
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
        builder.setContentTitle("任务提醒")
                .setContentText(information)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setDefaults(Notification.DEFAULT_ALL)
                .setWhen(System.currentTimeMillis());
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            builder.setChannelId("notification_id");
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationManager notificationManager = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);
            NotificationChannel channel = new NotificationChannel("notification_id", "notification_name", NotificationManager.IMPORTANCE_LOW);
            channel.enableLights(true);
            channel.setLockscreenVisibility(Notification.VISIBILITY_SECRET);
            channel.enableVibration(true);
            channel.shouldShowLights();
            notificationManager.createNotificationChannel(channel);
        }
//        提高进程的优先级
        startForeground(alarmId,builder.build());
//        绑定建立连接
        bindService(new Intent(this, GuardService.class),mServiceConnection, Context.BIND_IMPORTANT);
        mHandler.post(new Runnable() {
            @Override
            public void run() {
//                sendVibrate(getApplicationContext());
                playSound(getApplicationContext(),getAlarmUri());
                try {
                    Thread.sleep(8000);
                    mediaPlayer.stop();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        return START_STICKY;
    }

    public void sendVibrate(Context context){
        Vibrator vibrator = (Vibrator) context.getSystemService(context.VIBRATOR_SERVICE);
        vibrator.vibrate(8000);
    }

    public void playSound(Context context, Uri alery){
        mediaPlayer = new MediaPlayer();
        try{
            mediaPlayer.setDataSource(context,alery);
            final AudioManager audioManager = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
            if(audioManager.getStreamVolume(AudioManager.STREAM_ALARM) != 0){
                //指定流媒体类型
                mediaPlayer.setAudioStreamType(AudioManager.STREAM_ALARM);
                mediaPlayer.prepare();
                mediaPlayer.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Uri getAlarmUri(){
        Uri alert = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
        if(alert == null){
            alert = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        }
        return alert;
    }

    ServiceConnection mServiceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
//            Toast.makeText(AlarmService.this, "与Guard建立连接", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            startService(new Intent(AlarmService.this, GuardService.class));
            //绑定建立连接
            bindService(new Intent(AlarmService.this, GuardService.class), mServiceConnection,
                    Context.BIND_IMPORTANT);
        }
    };
}
