package com.example.modulea.base;

import android.content.Context;
import android.view.ViewGroup;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.modulea.R;
import com.example.modulea.base.BaseBindRecyclerView;
import com.example.modulea.base.IBaseBindingAdapterItem;
import com.example.modulea.data.DataBean;
import com.example.modulea.databinding.RecyclerviewItemBinding;

import java.util.List;

public class MutiItemAdapter extends BaseBindRecyclerView<IBaseBindingAdapterItem> {
    public MutiItemAdapter(Context context, List mList) {
        super(context, mList);
    }

    @Override
    public int getItemViewType(int position) {
        return mList.get(position).getIteViewType();
    }

    @Override
    public RecyclerView.ViewHolder onCreateMyViewHolder(ViewGroup parent, int viewType) {
        RecyclerviewItemBinding binding;
        binding = DataBindingUtil.inflate(inflater, R.layout.recyclerview_item,parent,false);
        return new ProjectViewHolder(binding);
    }

    @Override
    public void onBindMyViewHolder(RecyclerView.ViewHolder holder, int position) {
        DataBean dataBean = (DataBean) mList.get(position);
        ((ProjectViewHolder) holder).getBinding().setData(dataBean);
        ((ProjectViewHolder) holder).getBinding().executePendingBindings();//解决DataBinding 闪烁问题
    }

    class ProjectViewHolder extends RecyclerView.ViewHolder{
        private RecyclerviewItemBinding binding ;

        public RecyclerviewItemBinding getBinding() {
            return binding;
        }

        public ProjectViewHolder(RecyclerviewItemBinding binding){
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
