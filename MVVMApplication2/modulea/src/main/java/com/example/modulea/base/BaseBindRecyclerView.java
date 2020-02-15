package com.example.modulea.base;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.view.ViewParent;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import java.util.List;

public abstract class BaseBindRecyclerView<T> extends RecyclerView.Adapter {

    public List<T> mList;//数据源
    public LayoutInflater inflater;

    public BaseBindRecyclerView(Context context,List<T> mList){
        this.mList = mList;
//        通过getSystemService系统接口获取LayoutInflater 服务实例
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return onCreateMyViewHolder(parent,viewType);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        onBindMyViewHolder(holder,position);
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }
    public abstract RecyclerView.ViewHolder onCreateMyViewHolder(ViewGroup parent,int viewType);
    public abstract void onBindMyViewHolder(RecyclerView.ViewHolder holder,int position);
}
