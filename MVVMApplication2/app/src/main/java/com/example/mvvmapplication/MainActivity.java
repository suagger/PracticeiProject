package com.example.mvvmapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.widget.RadioGroup;

import com.example.modulea.fragment.CountFragment;
<<<<<<< HEAD
=======
import com.example.moduleb.fragment.RemindingFragment;
>>>>>>> 未添加网络请求
import com.example.mvvmapplication.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity implements RadioGroup.OnCheckedChangeListener {
    private ActivityMainBinding binding;
    RadioGroup group;
    Fragment f1;
    Fragment f2;
    Fragment f3;
    Fragment f4;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_main);
        group  = binding.include.rg;
        group.check(R.id.rb_1);
        f1 = new CountFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.fl_content,f1).commit();
        group.setOnCheckedChangeListener(this);

    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();

        if(checkedId == R.id.rb_1){
<<<<<<< HEAD

=======
>>>>>>> 未添加网络请求
            hideFragment(transaction);
            Fragment f1 = new CountFragment();
            transaction.replace(R.id.fl_content,f1);
            transaction.commit();
        }else if(checkedId == R.id.rb_2){
            hideFragment(transaction);
            Fragment f2 = new CountFragment();
            transaction.replace(R.id.fl_content,f2);
            transaction.commit();
        }else if(checkedId == R.id.rb_3){
            hideFragment(transaction);
<<<<<<< HEAD
            Fragment f3 = new CountFragment();
=======
            Fragment f3 = new RemindingFragment();
>>>>>>> 未添加网络请求
            transaction.replace(R.id.fl_content,f3);
            transaction.commit();
        }else if(checkedId == R.id.rb_4){
            hideFragment(transaction);
            Fragment f4 = new CountFragment();
            transaction.replace(R.id.fl_content,f4);
            transaction.commit();
        }
    }

    public void hideFragment(FragmentTransaction transaction){
        if(f1 != null){
            transaction.remove(f1);
        }
        if(f2 != null){
            transaction.remove(f2);
        }
        if(f3 != null){
            transaction.remove(f3);
        }
        if(f4 != null){
            transaction.remove(f4);
        }
    }
}
