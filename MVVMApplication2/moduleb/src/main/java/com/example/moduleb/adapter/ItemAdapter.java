package com.example.moduleb.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.moduleb.data.Dates;
import com.example.moduleb.data.IBaseBindingAdapterItem;
import com.example.moduleb.R;
import com.example.moduleb.databinding.ItemRecyclerBinding;

import java.util.List;

public class ItemAdapter extends BaseBindRecyclerViewAdapter<IBaseBindingAdapterItem> {
    public ItemAdapter(Context context, List mList) {
        super(context, mList);
    }
    onItemClick mOnItemClick;

    public void setmOnItemClick(onItemClick mOnItemClick) {
        this.mOnItemClick = mOnItemClick;
    }

    public void remove(int position){
        mList.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position,mList.size() - position);
    }
    @Override
    public int getItemViewType(int position) {
        return mList.get(position).getIteViewType();
    }

    @Override
    public RecyclerView.ViewHolder onCreateMyViewHolder(ViewGroup parent, int viewType) {
        ItemRecyclerBinding binding;
        binding = DataBindingUtil.inflate(inflater, R.layout.item_recycler,parent,false);
        return new ProjectViewHolder(binding);
    }

    @Override
    public void onBindMyViewHolder(RecyclerView.ViewHolder holder, int position) {
        Dates dataBean = (Dates) mList.get(position);
        ((ProjectViewHolder) holder).getBinding().itemRecycler.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                int pos = holder.getLayoutPosition();
                mOnItemClick.onItemLongClick(((ProjectViewHolder) holder).getBinding().itemRecycler,pos);
                return false;
            }
        });
        ((ProjectViewHolder) holder).getBinding().setTimeInformation(dataBean);
        ((ProjectViewHolder) holder).getBinding().executePendingBindings();//解决DataBinding 闪烁问题
    }

    class ProjectViewHolder extends RecyclerView.ViewHolder{
        private ItemRecyclerBinding binding;

        public ItemRecyclerBinding getBinding() {
            return binding;
        }

        public ProjectViewHolder(ItemRecyclerBinding binding){
            super(binding.getRoot());
            this.binding = binding;
        }
    }

    public interface onItemClick{
        void onItemLongClick(View view,int position);
    }
}
