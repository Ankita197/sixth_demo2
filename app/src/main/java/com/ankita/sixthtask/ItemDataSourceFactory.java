package com.ankita.sixthtask;

import androidx.lifecycle.MutableLiveData;
import androidx.paging.DataSource;
import androidx.paging.PageKeyedDataSource;

import com.ankita.sixthtask.model.CreateResponse;
import com.ankita.sixthtask.model.User;

public class ItemDataSourceFactory extends DataSource.Factory {

    private MutableLiveData<PageKeyedDataSource<Integer, CreateResponse.Datum>> itemLiveDataSource = new MutableLiveData<>();

    @Override
    public DataSource create() {
        //getting our data source object
        ItemDataSource itemDataSource = new ItemDataSource();

        //posting the datasource to get the values
        itemLiveDataSource.postValue(itemDataSource);

        //returning the datasource
        return itemDataSource;
    }

    public MutableLiveData<PageKeyedDataSource<Integer, CreateResponse.Datum>> getItemLiveDataSource() {
        return itemLiveDataSource;
    }
}
