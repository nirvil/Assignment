package com.example.assignment.fragments;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.assignment.R;
import com.example.assignment.adapters.DialogRecyclerAdapter;
import com.example.assignment.interfaces.ApiInterface;
import com.example.assignment.modelclasses.PostModel;
import com.example.assignment.networks.RetrofitClient;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PostFragment extends DialogFragment {
 private static final String TAG=null;
    ImageView closeDialog;
    RecyclerView recyclerView;
    DialogRecyclerAdapter adapter;
    RecyclerView.LayoutManager layoutManager;

    Integer userid;

    ApiInterface apiInterface;
    ProgressDialog progressDialog;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

       View view= inflater.inflate(R.layout.dialog_recycler, container, false);
       closeDialog=view.findViewById(R.id.iv_close_dialog);
       userid=ItemDetailFragment.userid;
        progressDialog=new ProgressDialog(getContext());
        recyclerView=view.findViewById(R.id.dialog_recyclerview);
        layoutManager=new LinearLayoutManager(getActivity().getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        hitPostApi(userid);

        closeDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        return view;
    }

    public void hitPostApi(int id){
        apiInterface= RetrofitClient.getRetrofitClient().create(ApiInterface.class);
        progressDialog.setMessage("Loading....");
        if(progressDialog.isShowing()){
            progressDialog.dismiss();
        }else {
            progressDialog.setCancelable(false);
            progressDialog.show();
        }
        Call<List<PostModel>> call=apiInterface.getPosts(id);
        call.enqueue(new Callback<List<PostModel>>() {
            @Override
            public void onResponse(Call<List<PostModel>> call, Response<List<PostModel>> response) {
                if(progressDialog.isShowing()) {
                    progressDialog.dismiss();
                }
                if(!response.isSuccessful()){
                    Toast.makeText(getContext(),"Server not Responding...Try again",Toast.LENGTH_SHORT).show();
                    Log.i(TAG,"Server not Responding..."+response.code()+"");
                    return;
                }

                Log.i(TAG,"onResponse:"+response.body());
                List<PostModel>userPost=response.body();

                if(userPost==null){
                    Toast.makeText(getContext(),"No Post to show",Toast.LENGTH_SHORT).show();
                    return;
                }

                if(userPost!=null) {
                    adapter = new DialogRecyclerAdapter(getActivity(),userPost);
                    recyclerView.setAdapter(adapter);
                }
            }

            @Override
            public void onFailure(Call<List<PostModel>> call, Throwable t) {
                if(progressDialog.isShowing()) {
                    progressDialog.dismiss();
                }
                Toast.makeText(getContext(),"Somthing goes wrong...Try again",Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public void dismiss() {
        super.dismiss();
    }
}
