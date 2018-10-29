package com.marketingknob.mercury.ui.fragments;


import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.LinearLayoutCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.marketingknob.mercury.R;
import com.marketingknob.mercury.util.CommonUtil;
import com.marketingknob.mercury.util.MediaUtils;
import com.marketingknob.mercury.util.TinyDB;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Akshya on 26/10/2018.
 */

public class AccountFragment extends Fragment  implements MediaUtils.GetImg, View.OnClickListener{

    @BindView(R.id.et_mobile)       AppCompatEditText etMobile;
    @BindView(R.id.et_username)     AppCompatEditText etUsername;
    @BindView(R.id.et_email)        AppCompatEditText etEmail;
    @BindView(R.id.ll_main)         LinearLayoutCompat llMain;
    @BindView(R.id.profile_image)   CircleImageView CivProfileImage;
    @BindView(R.id.iv_add_photos)   AppCompatImageView ivAddPhotos;

    String strMobile="",strUserName="",strEmail="";
    TinyDB tinyDB;
    Activity activity;
    private static final String TAG = "AccountFragment";
    MediaUtils mMediaUtils;

    public AccountFragment() {
        // Required eprofile_imagempty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_account, container, false);

        init(view);
        return  view;
    }

    void init(View view){

        ButterKnife.bind(this,view);

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

        // initialize MediaUtils
        mMediaUtils = new MediaUtils(this);

        ivAddPhotos.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {

        if (v==llMain){
            CommonUtil.hideKeyboard(activity);
        }else if (v== ivAddPhotos){
            mMediaUtils.openImageDialog();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        mMediaUtils.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mMediaUtils.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void imgdata(String imgPath) {
        Log.d(TAG, "imgdata: " + imgPath);
        File imgFile = new  File(imgPath);
        if(imgFile.exists()){
            Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
            CivProfileImage.setImageBitmap(myBitmap);
        }
    }

}
