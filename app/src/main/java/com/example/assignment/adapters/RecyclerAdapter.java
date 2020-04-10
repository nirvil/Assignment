package com.example.assignment.adapters;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.example.assignment.R;
import com.example.assignment.activities.MainActivity;
import com.example.assignment.fragments.ItemDetailFragment;
import com.example.assignment.modelclasses.UserDataModel;

import java.util.List;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.MyViewHolder> {
    Context context;
    List<UserDataModel>userdata;



    public RecyclerAdapter(Context applicationContext, List<UserDataModel> userdata) {
        this.context=applicationContext;
        this.userdata=userdata;

    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.row_item,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, final int position) {
        holder.mUserId.setText("\t"+String.valueOf(userdata.get(position).getId()));
        holder.mUserEmail.setText("\t"+userdata.get(position).getEmail());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Integer userid=userdata.get(position).getId();

                Bundle args=new Bundle();
                args.putInt("UserId",userid);
                args.putString("Name",userdata.get(position).getName());
                args.putString("Username",userdata.get(position).getUsername());
                args.putString("City",userdata.get(position).getAddress().getCity());
                args.putString("Zip",userdata.get(position).getAddress().getZipcode());

                ItemDetailFragment itemDetailFragment=new ItemDetailFragment();
                itemDetailFragment.setArguments(args);
                MainActivity mainActivity=(MainActivity)context;
              FragmentManager fragmentManager=mainActivity.getSupportFragmentManager();
              FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
              fragmentTransaction.replace(R.id.framelayout_bottom,itemDetailFragment).commit();
            }
        });

    }

    @Override
    public int getItemCount() {
        return userdata.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView mUserId,mUserEmail;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            this.mUserId=itemView.findViewById(R.id.user_id);
            this.mUserEmail=itemView.findViewById(R.id.user_email);
        }
    }
}
