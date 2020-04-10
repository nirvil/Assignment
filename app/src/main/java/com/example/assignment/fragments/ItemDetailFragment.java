package com.example.assignment.fragments;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.assignment.R;
import com.example.assignment.activities.MainActivity;
import com.example.assignment.adapters.DialogRecyclerAdapter;
import com.example.assignment.interfaces.ApiInterface;
import com.example.assignment.modelclasses.PostModel;
import com.example.assignment.modelclasses.UserDataModel;
import com.example.assignment.networks.InternetConnectionDetector;
import com.example.assignment.networks.RetrofitClient;
import com.google.android.material.snackbar.Snackbar;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ItemDetailFragment extends Fragment implements View.OnClickListener {
    private static final String TAG="";


    TextView tv_name, tv_username, tv_address;
    Button bt_showPost;
    String mName=null, mUsername=null, mCity=null, mZipcode=null;

   public static Integer userid=null;




    private InternetConnectionDetector internetConnectionDetector;
    private boolean isConnectionExist=false;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_item_detail, container, false);

        tv_name = view.findViewById(R.id.tv_name);
        tv_username = view.findViewById(R.id.tv_username);
        tv_address = view.findViewById(R.id.tv_address);
        bt_showPost=view.findViewById(R.id.bt_showpost);
        bt_showPost.setOnClickListener(this);
        userid = (Integer) getArguments().get("UserId");
        if (userid != null) {

            mName = getArguments().getString("Name");
            mUsername = getArguments().getString("Username");
            mCity = getArguments().getString("City");
            mZipcode = getArguments().getString("Zip");

            if (mName != null) {
                tv_name.setText("Name :\n" + mName);
            } else {

                tv_name.setOnClickListener(this);
                tv_name.setError("Name not found");
            }
            if (mUsername != null) {
                tv_username.setText("Username :\n" + mUsername);
            } else {
                tv_username.setOnClickListener(this);
                tv_username.setError("Username not found");
            }
            if (mCity != null | mZipcode != null) {
                if(mCity==null) {
                    tv_address.setText("Address :\n" + "Unknown city" + "," + mZipcode);
                }
                if(mZipcode==null) {
                    tv_address.setText("Address :\n" + mCity + ", ZipCode not available");
                }
                if(mCity!=null & mZipcode!=null ) {
                    tv_address.setText("Address :\n" + mCity + "," + mZipcode);
                }
            } else {
                tv_address.setOnClickListener(this);
                tv_address.setError("Address not Found");
            }
            return view;
        }

      return view;
    }


    @Override
    public void onClick(View v) {
        if(v==bt_showPost){
           internetCheck();
        }

        if(v==tv_name) {
            Toast.makeText(getContext(), "Name not found", Toast.LENGTH_SHORT).show();
         }
        if(v==tv_username){
            Toast.makeText(getContext(), "Username not found", Toast.LENGTH_SHORT).show();
        }
        if(v==tv_address){
            Toast.makeText(getContext(), "Address not found", Toast.LENGTH_SHORT).show();
        }

    }


    public void internetCheck(){
        internetConnectionDetector = new InternetConnectionDetector(getActivity().getApplicationContext());
        isConnectionExist = internetConnectionDetector.checkMobileInternetConn();
        if (isConnectionExist) {
            PostFragment pd=new PostFragment();
            FragmentManager fm=getFragmentManager();
            pd.show(fm,"dialog");

        } else {
            Toast.makeText(getContext(),"No internet connection! check your connection and try again",Toast.LENGTH_SHORT).show();
        }
    }


}