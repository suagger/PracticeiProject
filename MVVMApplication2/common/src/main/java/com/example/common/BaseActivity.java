package com.example.common;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.alibaba.android.arouter.launcher.ARouter;

public abstract class BaseActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(Build.VERSION.SDK_INT >= 21){
            View decroView = getWindow().getDecorView();
//ARouter的注入
            ARouter.getInstance().inject(this);

//            隐藏状态栏
            int systemUiFlagLayoutStable = View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN;
            decroView.setSystemUiVisibility(systemUiFlagLayoutStable);
//            设置导航栏和状态栏为透明色
            getWindow().setNavigationBarColor(Color.TRANSPARENT);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
//            隐藏ActionBar
            getSupportActionBar().hide();

        }
    }
}
