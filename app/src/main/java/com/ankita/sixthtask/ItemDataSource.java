package com.ankita.sixthtask;

import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.paging.PageKeyedDataSource;

import com.ankita.sixthtask.model.CreateResponse;
import com.ankita.sixthtask.model.User;
import com.ankita.sixthtask.repository.APIClient;
import com.ankita.sixthtask.repository.APIInterface;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class ItemDataSource extends PageKeyedDataSource<Integer, CreateResponse.Datum> {
    final int page_start=1;
    APIInterface apiInterface;

    @Override
    public void loadInitial(@NonNull LoadInitialParams<Integer> params, @NonNull final LoadInitialCallback<Integer, CreateResponse.Datum> callback) {
        apiInterface= APIClient.getClient().create(APIInterface.class);
        apiInterface.doGetListResour("story",1).enqueue(new Callback<CreateResponse>() {
            @Override
            public void onResponse(Call<CreateResponse> call, Response<CreateResponse> response) {
                if(response.body()!=null){
                    callback.onResult(response.body().data, null, page_start + 1);
                }
            }

            @Override
            public void onFailure(Call<CreateResponse> call, Throwable t) {

            }
        });
    }


    @Override
    public void loadBefore(@NonNull final LoadParams<Integer> params, @NonNull final LoadCallback<Integer, CreateResponse.Datum> callback) {
        apiInterface= APIClient.getClient().create(APIInterface.class);
        apiInterface.doGetListResour("story",params.key-1).enqueue(new Callback<CreateResponse>() {
            @Override
            public void onResponse(Call<CreateResponse> call, Response<CreateResponse> response) {
                Integer adjacentKey = (params.key > 1) ? params.key - 1 : null;
                if(response.body()!=null){
                    callback.onResult(response.body().data,  adjacentKey);
                }
            }

            @Override
            public void onFailure(Call<CreateResponse> call, Throwable t) {

            }
        });
    }

    @Override
    public void loadAfter(@NonNull final LoadParams<Integer> params, @NonNull final LoadCallback<Integer, CreateResponse.Datum> callback) {
        apiInterface= APIClient.getClient().create(APIInterface.class);
        Log.d("___@@___",params.key+" ");
        apiInterface.doGetListResour("story",params.key).enqueue(new Callback<CreateResponse>() {
            @Override
            public void onResponse(Call<CreateResponse> call, Response<CreateResponse> response) {
                if(response.body()!=null){
                    Integer key = response.body().page<5? params.key + 1 : null;

                    //passing the loaded data and next page value
                    callback.onResult(response.body().data, key);
                }
            }

            @Override
            public void onFailure(Call<CreateResponse> call, Throwable t) {

            }
        });

    }
}
