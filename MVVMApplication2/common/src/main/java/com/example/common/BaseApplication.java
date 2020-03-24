package com.example.common;

import android.app.AlertDialog;
import android.app.Application;
import android.content.Context;

import com.alibaba.android.arouter.launcher.ARouter;

<<<<<<< HEAD
=======
import org.litepal.LitePal;

>>>>>>> 未添加网络请求
public class BaseApplication extends Application {

    private boolean isModuleARouter = true;
    private static BaseApplication instance;
    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        initRouter();
<<<<<<< HEAD
=======
        LitePal.initialize(this);
>>>>>>> 未添加网络请求
    }

    public static BaseApplication getInstance() {
        return instance;
    }

    public void initRouter(){
        if(BuildConfig.DEBUG){
            ARouter.openLog();
            ARouter.openDebug();
        }
        ARouter.init(this);
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        ARouter.getInstance().destroy();
    }

    @Override
    public void onTrimMemory(int level) {
        super.onTrimMemory(level);
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
    }
}
