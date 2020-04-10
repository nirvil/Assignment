package com.example.assignment.interfaces;

import com.example.assignment.modelclasses.PostModel;
import com.example.assignment.modelclasses.UserDataModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiInterface {


    @GET("users")
    Call<List<UserDataModel>> getUserData();

    @GET("posts")
    Call<List<PostModel>> getPosts(@Query("userId")int userid);
}
