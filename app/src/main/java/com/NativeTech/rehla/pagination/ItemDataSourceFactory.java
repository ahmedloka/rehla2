package com.NativeTech.rehla.pagination;

import android.arch.lifecycle.MutableLiveData;
import android.arch.paging.DataSource;
import android.arch.paging.PageKeyedDataSource;

import com.NativeTech.rehla.model.chat.Model;

public class ItemDataSourceFactory extends DataSource.Factory {
    //creating the mutable live data
    private MutableLiveData<PageKeyedDataSource<Integer, Model>> itemLiveDataSource = new MutableLiveData<>();

    ItemDataSource itemDataSource;

    @Override
    public DataSource<Integer, Model> create() {
        //getting our data source object
        itemDataSource = new ItemDataSource();
        // itemDataSource.invalidate();

        //posting the datasource to get the values
        itemLiveDataSource.postValue(itemDataSource);

        //returning the datasource
        return itemDataSource;
    }


    //getter for itemlivedatasourcex
    public MutableLiveData<PageKeyedDataSource<Integer, Model>> getItemLiveDataSource() {
        return itemLiveDataSource;
    }


    public void invalidateDataSource() {
        itemDataSource.invalidate();
    }
}
