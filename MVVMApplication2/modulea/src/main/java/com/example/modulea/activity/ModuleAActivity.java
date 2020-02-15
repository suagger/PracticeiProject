package com.example.modulea.activity;

import android.os.Bundle;

import com.example.common.BaseActivity;
import com.example.modulea.R;
import com.example.modulea.fragment.CountFragment;

public class ModuleAActivity extends BaseActivity{


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_module_a);
        getSupportFragmentManager().beginTransaction().replace(R.id.fl_count,new CountFragment()).commit();
    }

}
