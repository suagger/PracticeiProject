package com.example.modulea.fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.alibaba.android.arouter.launcher.ARouter;
import com.example.modulea.data.Densti;
import com.example.modulea.data.MyView;
import com.example.modulea.R;
import com.example.modulea.databinding.CountFragmentBinding;

import java.util.ArrayList;
import java.util.List;

public class CountFragment extends Fragment implements MyView.OnItemClickListener, RadioGroup.OnCheckedChangeListener , View.OnClickListener {
    List<MyView.PieEntry> pieEntries = new ArrayList<>();
    CountFragmentBinding binding;
    MyView myView;
    RadioGroup group;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        binding = DataBindingUtil.inflate(inflater, R.layout.count_fragment,container,false);
        View view = binding.getRoot();
        myView = binding.myview;
        group = binding.group;

        binding.setOnClickListener(this);
        myView.setOnItemClickListener(this);
        myView.setRadius(Densti.dp2px(getContext(),80));
        pieEntries.add(new MyView.PieEntry(12, R.color.chart_green,false));
        pieEntries.add(new MyView.PieEntry(45, R.color.chart_blue,false));
        myView.setPieEntry(pieEntries);
        group.setOnCheckedChangeListener(this);
         return view;
    }
<<<<<<< HEAD


=======
    Toast lastToast = null;
>>>>>>> 未添加网络请求
    @SuppressLint("ShowToast")
    @Override
    public void onItemClick(int position) {
        Toast toast = null;
        if(position == 0){
            toast = Toast.makeText(getContext(),"未完成 ："  + (int)pieEntries.get(position).getNumber(),Toast.LENGTH_SHORT);
        }else if (position == 1){
            toast = Toast.makeText(getContext(),"完成 ："  + (int)pieEntries.get(position).getNumber(),Toast.LENGTH_SHORT);
        }
<<<<<<< HEAD
        if(toast != null){
            toast.setGravity(Gravity.TOP,0,getView().getMeasuredHeight() / 3);
            toast.show();
        }
=======
        if(toast != null) {
            toast.setGravity(Gravity.TOP, 0, getView().getMeasuredHeight() / 3);
            toast.show();
        }
        if(lastToast != null){
            lastToast.cancel();
        }
        lastToast = toast;
>>>>>>> 未添加网络请求
    }
    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        if(R.id.success == checkedId){
            if(!pieEntries.get(1).isSelected())
                pieEntries.get(1).setSelected(true);
            else
                pieEntries.get(1).setSelected(false);
        }else if(checkedId == R.id.unsuccess){
            if(!pieEntries.get(0).isSelected())
                pieEntries.get(0).setSelected(true);
            else
                pieEntries.get(0).setSelected(false);
        }
        myView.setPieEntry(pieEntries);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.ok || id == R.id.no) {
<<<<<<< HEAD
//            startActivity(new Intent(getContext(), RecyclerViewActivity.class));
            ARouter.getInstance().build("/app/RecyclerViewActivity").navigation();
=======
            ARouter.getInstance().build("/pointer/RecyclerViewActivity").navigation();
>>>>>>> 未添加网络请求
        }
    }

}
