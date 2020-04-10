package com.example.assignment.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.assignment.R;
import com.example.assignment.fragments.ItemDetailFragment;
import com.example.assignment.fragments.ItemFragment;

import com.example.assignment.interfaces.ApiInterface;
import com.example.assignment.modelclasses.UserDataModel;
import com.example.assignment.networks.InternetConnectionDetector;
import com.example.assignment.networks.RetrofitClient;

import java.io.Serializable;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG="";
    TextView mErrorMessage;
    Button mRetry;
    LinearLayout mLayout;
    FrameLayout mTopFrameLayout,mBottomFramelaout;


    private InternetConnectionDetector internetConnectionDetector;
    private boolean isConnectionExist=false;

    ApiInterface apiInterface;
    ProgressDialog progressDialog;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mErrorMessage=findViewById(R.id.tv_message);
        mRetry=findViewById(R.id.bt_retry);
        mLayout=findViewById(R.id.message_layout);
        mTopFrameLayout=findViewById(R.id.framelayout_top);
        mBottomFramelaout=findViewById(R.id.framelayout_bottom);
        progressDialog=new ProgressDialog(MainActivity.this);
        internetCheck();
    }
    public void internetCheck(){
        internetConnectionDetector = new InternetConnectionDetector(getApplicationContext());
        isConnectionExist = internetConnectionDetector.checkMobileInternetConn();
        if (isConnectionExist) {

            hitUserAPI();

        } else {

           mTopFrameLayout.setVisibility(View.GONE);
           mErrorMessage.setText("No Internet Connection!,Check your internet connection");
           mLayout.setVisibility(View.VISIBLE);
           mRetry.setOnClickListener(this);


        }
    }

public void hitUserAPI(){
        apiInterface= RetrofitClient.getRetrofitClient().create(ApiInterface.class);
        progressDialog.setMessage("Loading Data...Please Wait");
        if(progressDialog!=null) {
            if (progressDialog.isShowing()) {
                progressDialog.dismiss();
            } else {
                progressDialog.setCancelable(false);
                progressDialog.show();
            }
        }
    Call<List<UserDataModel>>call=apiInterface.getUserData();


    call.enqueue(new Callback<List<UserDataModel>>() {
        @Override
        public void onResponse(Call<List<UserDataModel>> call, Response<List<UserDataModel>> response) {
            if(progressDialog!=null) {
                if (progressDialog.isShowing()) {
                    progressDialog.setCancelable(true);
                    progressDialog.dismiss();
                }
            }
            if(!response.isSuccessful()){
                mTopFrameLayout.setVisibility(View.GONE);
                mErrorMessage.setText("Server not responding.. please Try again later");
                mLayout.setVisibility(View.VISIBLE);
                Log.i(TAG,"Server not Responding..."+response.code()+"");
                return;
            }

            Log.i(TAG,"onResponse:"+response.body());
            List<UserDataModel>userData=response.body();

            //List<UserDataModel>userData=null;
            if(userData==null){
                mTopFrameLayout.setVisibility(View.GONE);
                mErrorMessage.setText("User not found! please Try again");
                mLayout.setVisibility(View.VISIBLE);
                return;
            }
            if(userData!=null) {
                mLayout.setVisibility(View.GONE);
                ItemFragment itemFragment=new ItemFragment();
                Bundle args = new Bundle();
                args.putSerializable("UserList", (Serializable) userData);

                itemFragment.setArguments(args);
                FragmentManager fragmentManager=getSupportFragmentManager();
                final FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.framelayout_top,itemFragment).commit();
                mTopFrameLayout.setVisibility(View.VISIBLE);

            }

        }

        @Override
        public void onFailure(Call<List<UserDataModel>> call, Throwable t) {
            if(progressDialog.isShowing()) {
                progressDialog.dismiss();
            }
            mTopFrameLayout.setVisibility(View.GONE);
            mErrorMessage.setText("Somthing goes wrong...Please try again");
            mLayout.setVisibility(View.VISIBLE);
            mRetry.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    internetCheck();
                }
            });
            return;

        }
    });

}
    @Override
    public void onClick(View v) {
        internetCheck();


    }

}
