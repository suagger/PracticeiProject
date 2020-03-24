package com.example.moduleb.fragment;

import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.example.moduleb.R;
import com.example.moduleb.adapter.ItemAdapter;
import com.example.moduleb.data.Dates;
import com.example.moduleb.data.GsonData;
import com.example.moduleb.data.IBaseBindingAdapterItem;
import com.example.moduleb.data.LitepalInformation;
import com.example.moduleb.data.TimeData;
import com.example.moduleb.databinding.DialogTimeBinding;
import com.example.moduleb.databinding.FragmentModulebBinding;
import com.example.moduleb.service.AlarmService;
import com.example.moduleb.service.GuardService;
import com.example.moduleb.service.JobWakeUpService;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.CalendarMode;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;
import com.prolificinteractive.materialcalendarview.format.TitleFormatter;

import org.litepal.LitePal;
import org.litepal.crud.DataSupport;
import org.litepal.crud.LitePalSupport;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RemindingFragment extends Fragment implements OnDateSelectedListener, View.OnClickListener, ItemAdapter.onItemClick {
    FragmentModulebBinding binding;
    private static boolean flag;
    private static int mYear;
    private static int mMonth;
    public static int j = 0;
    private static int mDay;
    private static int mHour;
    private static int mMinute;
    private static String infor;
    private static String dateTime;
    private static String appDateTime;
    Button changeButton;
    ItemAdapter adapter;
    public static List<IBaseBindingAdapterItem> dateList = new ArrayList<>();

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_moduleb, container, false);
        if (binding != null) {
            flag = true;
            initView();
            flag = false;
        }
        getInformation();
        binding.calendar.setOnDateChangedListener(this);
        binding.setOnTabClick(this);
        changeButton = binding.changeButton;
        adapter = new ItemAdapter(getContext(),dateList);
        RecyclerView recyclerView = binding.recycler;
        LinearLayoutManager manager = new LinearLayoutManager(getContext(),RecyclerView.VERTICAL,false);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(adapter);
        recyclerView.addItemDecoration(new DividerItemDecoration(getContext(),DividerItemDecoration.VERTICAL));
        adapter.setmOnItemClick(this);
        return binding.getRoot();
    }

    private void initView() {
        Date time = new Date(System.currentTimeMillis());
        Calendar calendar = Calendar.getInstance();
        mYear = calendar.get(Calendar.YEAR);
        mMonth = calendar.get(Calendar.MONTH) + 1;
        mDay = calendar.get(Calendar.DATE);
        appDateTime = mYear + "年" + mMonth + "月" + mDay + "日";
        binding.calendar.setSelectedDate(time);
        if (flag) {
            binding.calendar.state().edit()
                    .setCalendarDisplayMode(CalendarMode.WEEKS)
                    .commit();
        } else {
            binding.calendar.state().edit()
                    .setCalendarDisplayMode(CalendarMode.MONTHS)
                    .commit();
        }
        binding.calendar.setTitleFormatter(new TitleFormatter() {
            @Override
            public CharSequence format(CalendarDay day) {
                StringBuffer buffer = new StringBuffer();
                int year = day.getYear();
                int month = day.getMonth() + 1;
                buffer.append(year).append("年").append(month).append("月");
                return buffer;
            }
        });
        binding.calendar.setArrowColor(Color.WHITE);

    }

    @Override
    public void onDateSelected(@NonNull MaterialCalendarView widget, @NonNull CalendarDay date, boolean selected) {
        if (selected) {
            mYear = date.getYear();
            mMonth = date.getMonth() + 1;
            mDay = date.getDay();
            appDateTime = mYear + "年" + mMonth + "月" + mDay + "日";
             Log.i("test", mYear + "." + mMonth + "." + mDay);
        }
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.change_button) {
            if (!flag) {
                changeButton.setBackgroundResource(R.drawable.push);
                initView();
                flag = true;
            } else {
                changeButton.setBackgroundResource(R.drawable.pull);
                initView();
                flag = false;
            }
        } else {
            tipDialog();
        }
    }
    private void tipDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setMessage("确认添加提醒事项" + "(" + appDateTime + ")")
                .setCancelable(true);
        builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                setTime();
                dialog.dismiss();
            }
        });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }
    private void setTime(){
        View view = getLayoutInflater().inflate(R.layout.dialog_time,null);
        DialogTimeBinding timeBinding = DataBindingUtil.bind(view);
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        TimePicker timePicker = timeBinding.timePicker;
        timePicker.setIs24HourView(true);
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        EditText editText = timeBinding.editText;
        builder.setView(view);
        builder.setTitle("输入信息：");
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                mHour = timePicker.getCurrentHour();
                mMinute = timePicker.getCurrentMinute();
                infor = editText.getText().toString();
                boolean flag = false;
                if(calendar.get(Calendar.YEAR) > mYear || calendar.get(Calendar.MONTH) + 1 > mMonth || calendar.get(Calendar.DAY_OF_MONTH) > mDay ||  calendar.get(Calendar.HOUR_OF_DAY) > mHour){
                    flag = true;
                }
                if(calendar.get(Calendar.YEAR) == mYear && calendar.get(Calendar.MONTH) + 1 == mMonth && calendar.get(Calendar.DAY_OF_MONTH) == mDay &&  calendar.get(Calendar.HOUR_OF_DAY) == mHour && calendar.get(Calendar.MINUTE) > mMinute){
                    flag = true;
                }
                if(infor == null || TextUtils.isEmpty(infor) || flag){
                        Toast.makeText(getContext(),"输入日期不合法或未说明增加事项！",Toast.LENGTH_SHORT).show();
                }else{
                    dateTime = mYear + "年" + mMonth + "月" + mDay + "日   " + (mHour < 10 ? "0" + mHour : mHour) + ":" + (mMinute < 10 ? "0" + mMinute : mMinute);
                    dateList.add(new Dates(mYear,mMonth,mDay,mHour,mMinute,infor,dateTime));
                    LitepalInformation information = new LitepalInformation(mYear,mMonth,mDay,mHour,mMinute,infor,dateTime);
                    information.save();
                }
                setAlarm();
                dialog.dismiss();
            }
        });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }
    @Override
    public void onItemLongClick(View view, int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext())
                .setTitle("确认删除吗?")
                .setNegativeButton("取消",null)
                .setPositiveButton("确认", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        adapter.remove(position);
                        LitePal.delete(LitepalInformation.class,position + 1);
                    }
                });
        AlertDialog dialog = builder.create();
        dialog.show();
    }
    private void setAlarm(){
        getActivity().startService(new Intent(getContext(), JobWakeUpService.class));
        getActivity().startService(new Intent(getContext(), GuardService.class));
        for(; j < dateList.size(); j ++){
            String action = "action" + j;
            AlarmManager am = (AlarmManager) getActivity().getSystemService(Context.ALARM_SERVICE);
            Intent intent = new Intent(getContext(), AlarmService.class);
            intent.setAction(action);
            PendingIntent pendingIntent = PendingIntent.getService(getContext(),j,intent,PendingIntent.FLAG_CANCEL_CURRENT);
            Dates item = (Dates) dateList.get(j);
            intent.putExtra("information",item.getInformation());
            Calendar calendar = Calendar.getInstance();
            calendar.set(Calendar.YEAR,item.getYear());
            calendar.set(Calendar.MONTH,item.getMonth() - 1);
            calendar.set(Calendar.DAY_OF_MONTH,item.getDay());
            calendar.set(Calendar.HOUR_OF_DAY,item.getHour());
            calendar.set(Calendar.MINUTE,item.getMinute());
            calendar.set(Calendar.SECOND,0);
            if(Build.VERSION.SDK_INT < 19){
                am.set(AlarmManager.RTC_WAKEUP,calendar.getTimeInMillis(),pendingIntent);
            }else{
                am.setExact(AlarmManager.RTC_WAKEUP,calendar.getTimeInMillis(),pendingIntent);
            }
        }
    }
    private void getInformation(){
        List<LitepalInformation> list = LitePal.findAll(LitepalInformation.class);
        for(int i = 0; i < list.size(); i ++){
            LitepalInformation info = list.get(i);
            dateList.add(new Dates(info.getYear(),info.getMonth(),info.getDay(),info.getHour(),info.getMinute(),info.getInformation(),info.getTime()));
        }
        /*
        if (list.size() <= 0){
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            TimeData timeData = retrofit.create(TimeData.class);
            Call<GsonData> call = timeData.getGsonData();
            call.enqueue(new Callback<GsonData>() {
                @Override
                public void onResponse(Call<GsonData> call, Response<GsonData> response) {
                    GsonData gsonData = response.body();
                    Log.i("test",gsonData.toString());
                }
                @Override
                public void onFailure(Call<GsonData> call, Throwable t) {
                    t.printStackTrace();
                }
            });
        }*/
    }

}
