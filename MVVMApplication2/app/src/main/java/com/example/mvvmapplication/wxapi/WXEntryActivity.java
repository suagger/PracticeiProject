package com.example.mvvmapplication.wxapi;

import android.os.Bundle;

import com.umeng.commonsdk.UMConfigure;
import com.umeng.socialize.PlatformConfig;
import com.umeng.socialize.weixin.view.WXCallbackActivity;

public class WXEntryActivity extends WXCallbackActivity {
    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        UMConfigure.init(this, "58edcfeb310c93091c000be2", "Umeng",
                UMConfigure.DEVICE_TYPE_PHONE, "1fe6a20054bcef865eeb0991ee84525b");
        UMConfigure.setLogEnabled(true);

        PlatformConfig.setWeixin("wxdc1e388c3822c80b", "3baf1193c85774b3fd9d18447d76cab0");


    }
}
