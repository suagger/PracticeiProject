package com.example.modulea.activity;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
<<<<<<< HEAD

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
=======
import android.os.Bundle;
import android.view.View;
>>>>>>> 未添加网络请求

import com.alibaba.android.arouter.facade.annotation.Route;
import com.example.common.BaseActivity;
import com.example.modulea.R;
import com.example.modulea.base.IBaseBindingAdapterItem;
import com.example.modulea.data.DataBean;
import com.example.modulea.databinding.ActivityRecyclerViewBinding;
import com.example.modulea.base.MutiItemAdapter;

import java.util.ArrayList;
import java.util.List;

<<<<<<< HEAD
@Route(path = "/app/RecyclerViewActivity")
=======
@Route(path = "/pointer/RecyclerViewActivity")
>>>>>>> 未添加网络请求
public class RecyclerViewActivity extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener, View.OnClickListener {

    private MutiItemAdapter adapter;
    private SwipeRefreshLayout swipeRefreshLayout;
    private List<IBaseBindingAdapterItem> mList = new ArrayList<>();

<<<<<<< HEAD
    public RecyclerViewActivity() {
    }

=======
>>>>>>> 未添加网络请求
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityRecyclerViewBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_recycler_view);
        initData();
        adapter = new MutiItemAdapter(this,mList);
        LinearLayoutManager manager = new LinearLayoutManager(this, RecyclerView.VERTICAL,false);
        binding.recyclerView.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.VERTICAL));
        binding.recyclerView.setLayoutManager(manager);
        binding.recyclerView.setAdapter(adapter);
        swipeRefreshLayout = binding.refreshLayout;
        swipeRefreshLayout.setOnRefreshListener(this);
        binding.back.setOnClickListener(this);
<<<<<<< HEAD

=======
>>>>>>> 未添加网络请求
    }

    public void initData(){
        mList.add(new DataBean("茶饭","2019,3,4"));
        mList.add(new DataBean("跑步","2019,4,5"));
        mList.add(new DataBean("吃药","2019.3.5"));
    }
    @Override
    public void onRefresh() {
        mList.add(new DataBean("睡觉","2019.4.5"));
        adapter.notifyDataSetChanged();
        swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void onClick(View v) {
        finish();
    }
}
