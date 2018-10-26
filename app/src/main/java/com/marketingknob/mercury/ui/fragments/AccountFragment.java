package com.marketingknob.mercury.ui.fragments;


import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.LinearLayoutCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.marketingknob.mercury.R;
import com.marketingknob.mercury.ui.LoginActivity;
import com.marketingknob.mercury.util.CommonUtil;
import com.marketingknob.mercury.util.TinyDB;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Akshya on 26/10/2018.
 */

public class AccountFragment extends Fragment implements View.OnClickListener{

    @BindView(R.id.et_mobile)   AppCompatEditText etMobile;
    @BindView(R.id.et_username) AppCompatEditText etUsername;
    @BindView(R.id.et_email)    AppCompatEditText etEmail;
    @BindView(R.id.ll_main)     LinearLayoutCompat llMain;

    String strMobile="",strUserName="",strEmail="";
    TinyDB tinyDB;
    Activity activity;
    private static final String TAG = "AccountFragment";

    public AccountFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_account, container, false);

        ButterKnife.bind(this,view);
        init(view);

        return  view;
    }

    void init(View view){

        activity = this.getActivity();
        tinyDB   = new TinyDB(activity);
        llMain.setOnClickListener(this);

        strMobile   = tinyDB.getString("LoginMobile");
        strUserName = tinyDB.getString("LoginUserName");
        strEmail    = tinyDB.getString("LoginEmail");

        Log.d(TAG, "init: "+strMobile+" "+strUserName+" "+strEmail);

        etMobile.setText(""+strMobile);
        etUsername.setText(strUserName);
        if (!strEmail.equals(""))
        etEmail.setText(strEmail);

    }

    @Override
    public void onClick(View v) {

        if (v==llMain){
            CommonUtil.hideKeyboard(activity);
        }
    }
}
