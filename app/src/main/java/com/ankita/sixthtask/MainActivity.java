package com.ankita.sixthtask;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.paging.PagedList;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ankita.sixthtask.adapter.ItemAdapter;
import com.ankita.sixthtask.adapter.ItemUserAdapter;
import com.ankita.sixthtask.adapter.OnItemSelected;
import com.ankita.sixthtask.databinding.ActivityMainBinding;
import com.ankita.sixthtask.model.CreateResponse;
import com.ankita.sixthtask.model.User;
import com.ankita.sixthtask.repository.APIClient;
import com.ankita.sixthtask.repository.APIInterface;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private RecyclerView rvUserItem;
    private TextView tvSelectedItemValue;
    private APIInterface apiInterface;
    private ArrayList<User> userList;
    private User user;
   private ItemAdapter adapter;
    private  ItemUserAdapter itemUserAdapter;
    int count;
    private Button btnShow;
    private ItemViewModel itemViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        itemViewModel=  ViewModelProviders.of(this).get(ItemViewModel.class);
        //init();
        adapter = new ItemAdapter(this, new OnItemSelected() {
            @Override
            public void onSelect(CompoundButton btn, boolean isChk, int pos) {

            }
        });
        binding.setMyAdapter(adapter);
            itemViewModel.itemPagedList.observe(this, new Observer<PagedList<CreateResponse.Datum>>() {
                @Override
                public void onChanged(PagedList<CreateResponse.Datum> users) {
                    adapter.submitList(users);
                }
            });
//            rvUserItem.setAdapter(adapter);
        //callApi();
       // showUserList();
//        btnShow.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                tvSelectedItemValue.setText(String.valueOf(count));
//
//            }
//        });
    binding.getRoot();
    }

    private void showUserList() {
        for(User usr:userList){
            Log.d("$$$",usr.getCreated_at()+usr.getUrl()+usr.getTitle());
        }
    }

    private void setAdapter() {

         itemUserAdapter=new ItemUserAdapter(this, userList, new OnItemSelected() {
             @Override
             public void onSelect(CompoundButton btn, boolean isChk,int pos) {
                 if(btn.isChecked()){
                     count+=1;
                 }
                 else {
                    count-=1;

                 }
             }
         });
        rvUserItem.setAdapter(itemUserAdapter);
    }

    private void callApi() {
        Call<CreateResponse> response = apiInterface.doGetListResour("story", 1);
        response.enqueue(new Callback<CreateResponse>() {
            @Override
            public void onResponse(Call<CreateResponse> call, Response<CreateResponse> response) {
                CreateResponse createResponse=response.body();
                if(createResponse!=null){
                    List<CreateResponse.Datum> datumList=createResponse.data;
                    for(CreateResponse.Datum data:datumList){
                        user=new User();
                        if(data.title!=null&&data.created_at!=null&&data.url!=null){
                            user.setCreated_at(data.created_at);
                            user.setTitle(data.title);
                            user.setUrl(data.url);
                            Log.d("Created at",data.created_at);                        }
                       else {
                            user.setCreated_at("1234");
                            user.setTitle("big bajar");
                            user.setUrl("www.google.com");
                        }
                        userList.add(user);
                    }
                }
                else {
                    Log.d("$$$","data is not available");

                }
                setAdapter();
            }

            @Override
            public void onFailure(Call<CreateResponse> call, Throwable t) {

            }
        });
    }

    private void init() {
        rvUserItem = findViewById(R.id.rvItemUser);
        tvSelectedItemValue = findViewById(R.id.tvSelectedUserValue);
        apiInterface = APIClient.getClient().create(APIInterface.class);
        userList=new ArrayList<>();
        btnShow=findViewById(R.id.btnShow);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(this);
        rvUserItem.setLayoutManager(linearLayoutManager);
        itemViewModel=  ViewModelProviders.of(this).get(ItemViewModel.class);
        adapter = new ItemAdapter(this, new OnItemSelected() {
            @Override
            public void onSelect(CompoundButton btn, boolean isChk, int pos) {

            }
        });
    }
}
