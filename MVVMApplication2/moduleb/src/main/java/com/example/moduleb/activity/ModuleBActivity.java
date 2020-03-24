package com.example.moduleb.activity;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.example.common.BaseActivity;
import com.example.moduleb.R;
import com.example.moduleb.databinding.ActivityModuleBBinding;
import com.example.moduleb.fragment.RemindingFragment;

public class ModuleBActivity extends BaseActivity {
    ActivityModuleBBinding binding;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_module_b);
        getSupportFragmentManager().beginTransaction().replace(R.id.fl_count,new RemindingFragment()).commit();
    }
}
