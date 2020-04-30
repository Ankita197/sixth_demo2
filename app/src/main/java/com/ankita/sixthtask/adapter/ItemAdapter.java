package com.ankita.sixthtask.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.paging.PagedListAdapter;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.ankita.sixthtask.R;
import com.ankita.sixthtask.model.CreateResponse;
import com.ankita.sixthtask.model.User;

public class ItemAdapter extends PagedListAdapter<CreateResponse.Datum,ItemAdapter.ViewHolder> {
    private Context context;
    private OnItemSelected onItemSelected;
    public ItemAdapter(Context context,OnItemSelected onItemSelected) {
        super(DIFF_CALLBACK);
        this.context=context;
        this.onItemSelected=onItemSelected;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_user,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
       final CreateResponse.Datum user=getItem(position);
        holder.tvPostTitleValue.setText(user.getTitle());
        holder.tvCreatedAtValue.setText(user.getCreated_at());
        holder.tvItemLink.setText(user.getUrl());
        holder.tvCreatedAt.setText("Created at");
        holder.tvTitle.setText("Post Title");
        //holder.switchSelected.setChecked(user.isSelected());
        holder.switchSelected.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                onItemSelected.onSelect(buttonView,isChecked,position);
                if(buttonView.isChecked()){
                   //user.setSelected(true);
                }

            }
        });

    }
    private static DiffUtil.ItemCallback<CreateResponse.Datum> DIFF_CALLBACK =
            new DiffUtil.ItemCallback<CreateResponse.Datum>() {
                @Override
                public boolean areItemsTheSame(CreateResponse.Datum oldItem, CreateResponse.Datum newItem) {
                    return oldItem == newItem;
                }

                @SuppressLint("DiffUtilEquals")
                @Override
                public boolean areContentsTheSame(CreateResponse.Datum oldItem, CreateResponse.Datum newItem) {
                    return oldItem.equals(newItem);
                }
            };

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvCreatedAtValue,tvItemLink,tvPostTitleValue,tvCreatedAt,tvTitle;
        Switch switchSelected;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvCreatedAtValue=itemView.findViewById(R.id.tvCreatedAtValue);
            tvItemLink=itemView.findViewById(R.id.tvItemLink);
            tvPostTitleValue=itemView.findViewById(R.id.tvPostTitleValue);
            tvCreatedAt=itemView.findViewById(R.id.tvCreatedAt);
            tvTitle=itemView.findViewById(R.id.tvPostTitle);
            switchSelected=itemView.findViewById(R.id.switchSelectUser);

        }
    }
}
