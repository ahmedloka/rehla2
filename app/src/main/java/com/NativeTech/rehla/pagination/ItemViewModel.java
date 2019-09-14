package com.NativeTech.rehla.pagination;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;
import android.arch.paging.LivePagedListBuilder;
import android.arch.paging.PageKeyedDataSource;
import android.arch.paging.PagedList;
import android.content.Context;
import android.content.SharedPreferences;

import com.NativeTech.rehla.model.chat.Model;

public class ItemViewModel extends ViewModel {

    private SharedPreferences sharedPreferences;

    //creating livedata for PagedList  and PagedKeyedDataSource
    public LiveData<PagedList<Model>> itemPagedList;
    LiveData<PageKeyedDataSource<Integer, Model>> liveDataSource;

    private ItemDataSourceFactory itemDataSourceFactory;

    //constructor
    public ItemViewModel() {

        //getting our data source factory
        itemDataSourceFactory = new ItemDataSourceFactory();

        //getting the live data source from data source factory
        liveDataSource = itemDataSourceFactory.getItemLiveDataSource();

        //Getting PagedList config
        PagedList.Config pagedListConfig =
                (new PagedList.Config.Builder())
                        .setEnablePlaceholders(false)
                        .setPageSize(Integer.MAX_VALUE).build();

        //Building the paged list
        itemPagedList = (new LivePagedListBuilder(itemDataSourceFactory, pagedListConfig))
                .build();
    }

    public void invalidateDataSource() {
        itemDataSourceFactory.invalidateDataSource();
    }
}
