package com.example.assignment.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.assignment.R;
import com.example.assignment.modelclasses.PostModel;

import java.util.List;

public class DialogRecyclerAdapter extends RecyclerView.Adapter<DialogRecyclerAdapter.MyViewHolder> {
    Context context;
    List<PostModel> userPost;


    public DialogRecyclerAdapter(Context context, List<PostModel> userPost) {
        this.context=context;
        this.userPost=userPost;

    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.row_post,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
            holder.tv_userid.setText("\t"+String.valueOf(userPost.get(position).getUserId()));
            holder.tv_postid.setText("\t"+String.valueOf(userPost.get(position).getId()));
            holder.tv_title.setText("\t"+userPost.get(position).getTitle());
            holder.tv_body.setText("\t"+userPost.get(position).getBody());

    }

    @Override
    public int getItemCount() {
        return userPost.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tv_userid,tv_postid, tv_title,tv_body;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            this.tv_userid=itemView.findViewById(R.id.tv_user_id);
            this.tv_postid=itemView.findViewById(R.id.tv_post_id);
            this.tv_title=itemView.findViewById(R.id.tv_post_title);
            this.tv_body=itemView.findViewById(R.id.tv_post_body);

        }
    }
}
