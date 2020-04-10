package com.example.assignment.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.assignment.R;
import com.example.assignment.adapters.RecyclerAdapter;
import com.example.assignment.modelclasses.UserDataModel;


import java.util.List;


public class ItemFragment extends Fragment {

    RecyclerView mRecyclerview;
    RecyclerAdapter mAdapter;
    RecyclerView.LayoutManager mlayoutManager;


    List<UserDataModel> userdata;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view= inflater.inflate(R.layout.fragment_item, container, false);
        try {
            userdata = (List<UserDataModel>) getArguments().getSerializable("UserList");
        }catch(Exception e){
            Toast.makeText(getContext(),""+e,Toast.LENGTH_SHORT).show();
        }
        mRecyclerview=view.findViewById(R.id.item_recyclerview);
        mlayoutManager= new LinearLayoutManager(getActivity().getApplicationContext());
        mRecyclerview.setLayoutManager(mlayoutManager);
        if(userdata!=null){
            mAdapter=new RecyclerAdapter(getActivity(),userdata);
            mRecyclerview.setAdapter(mAdapter);
        }
        return view;
    }

}
