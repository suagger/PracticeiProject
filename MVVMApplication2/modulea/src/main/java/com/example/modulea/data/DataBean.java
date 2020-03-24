package com.example.modulea.data;

import androidx.databinding.BaseObservable;

import com.example.modulea.R;
import com.example.modulea.base.IBaseBindingAdapterItem;

import androidx.databinding.Bindable;
import androidx.databinding.library.baseAdapters.BR;

public class DataBean extends BaseObservable implements IBaseBindingAdapterItem {
    private String itemThing;
    private String time;

    public void setTime(String time) {
        this.time = time;
        notifyPropertyChanged(BR.time);
    }

<<<<<<< HEAD
    public void setItem(String item) {
        this.itemThing = itemThing;
        notifyPropertyChanged(BR.itemThing);
    }

    public void setItemThing(String itemThing) {
        this.itemThing = itemThing;
=======
    public void setItemThing(String itemThing) {
        this.itemThing = itemThing;
        notifyPropertyChanged(BR.itemThing);
>>>>>>> 未添加网络请求
    }

    @Bindable
    public String getTime() {
        return time;
    }


    @Bindable
    public String getItemThing() {
        return itemThing;
    }

    public DataBean(String itemThing, String time) {
        this.itemThing = itemThing;
        this.time = time;
    }

    @Override
    public int getIteViewType() {
        return R.layout.recyclerview_item;
    }
}
