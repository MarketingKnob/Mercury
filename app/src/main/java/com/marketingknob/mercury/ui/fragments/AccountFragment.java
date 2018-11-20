package com.marketingknob.mercury.ui.fragments;


import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.LinearLayoutCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;
import com.marketingknob.mercury.R;
import com.marketingknob.mercury.util.CommonUtil;
import com.marketingknob.mercury.util.DialogUtil;
import com.marketingknob.mercury.util.MediaUtils;
import com.marketingknob.mercury.util.ProgressDialogUtil;
import com.marketingknob.mercury.util.SnackBarUtil;
import com.marketingknob.mercury.util.TinyDB;
import com.marketingknob.mercury.webservices.ApiHelper;
import com.marketingknob.mercury.webservices.WebConstants;
import com.marketingknob.mercury.webservices.interfaces.ApiResponseHelper;
import com.marketingknob.mercury.webservices.webresponse.ProfileImageResponse;
import com.marketingknob.mercury.webservices.webresponse.ProfileUpdateResponse;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Response;

/**
 * Created by Akshya on 26/10/2018.
 */

public class AccountFragment extends Fragment  implements MediaUtils.GetImg, View.OnClickListener, ApiResponseHelper {

    @BindView(R.id.et_mobile)       AppCompatEditText etMobile;
    @BindView(R.id.et_username)     AppCompatEditText etUsername;
    @BindView(R.id.et_email)        AppCompatEditText etEmail;
    @BindView(R.id.ll_main)         LinearLayoutCompat llMain;
    @BindView(R.id.profile_image)   CircleImageView CivProfileImage;
    @BindView(R.id.iv_add_photos)   AppCompatImageView ivAddPhotos;
    @BindView(R.id.btn_save)        AppCompatButton btnSave;

    String strMobile="",strUserName="",strEmail="", strUserId ="";
    TinyDB tinyDB;
    Activity activity;
    private static final String TAG = "AccountFragment";
    MediaUtils mMediaUtils;
    ProgressDialog pd;

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

        activity    = this.getActivity();
        tinyDB      = new TinyDB(activity);
        strMobile   = tinyDB.getString("LoginMobile");
        strUserName = tinyDB.getString("LoginUserName");
        strEmail    = tinyDB.getString("LoginEmail");

        llMain.setOnClickListener(this);
        btnSave.setOnClickListener(this);

        Log.d(TAG, "init: "+strMobile+" "+strUserName+" "+strEmail);

        etMobile.setText(""+strMobile);
        etUsername.setText(strUserName);
        if (!strEmail.equals(""))
        etEmail.setText(strEmail);

        // initialize MediaUtils
        mMediaUtils = new MediaUtils(this);
        ivAddPhotos.setOnClickListener(this);

        strUserId =  tinyDB.getString("LoginId");
        Log.d(TAG, "init: "+ strUserId);

        String ImagePath=tinyDB.getString("ProfileImage");
        if (!ImagePath.equalsIgnoreCase("")){
            Picasso.get()
                    .load(ImagePath)
                    .placeholder(R.drawable.account)
                    .error(R.drawable.account)
                    .into(CivProfileImage);
        }
    }

    @Override
    public void onClick(View v) {

        if (v==llMain){
            CommonUtil.hideKeyboard(activity);
        }else if (v== ivAddPhotos){
            askPermission();

        }else if (v== btnSave){
            submitForm();
        }
    }
    /**
     * Username Validation
     */
    private boolean validateUsername() {
        strUserName = etUsername.getText().toString().trim();

        if (strUserName.isEmpty() ) {
            DialogUtil.showDialogMsg(activity, "Username Error", getResources().getString(R.string.err_msg_username));
            return false;
        } else {
        }
        return true;
    }

    /**
     * Email Validation
     */
    private boolean validateEmail() {
        strEmail = etEmail.getText().toString().trim();

        if (strEmail.length() > 0) {
            if (!CommonUtil.isValidEmail(strEmail)) {
                DialogUtil.showDialogMsg(activity, "Email Error",
                        getResources().getString(R.string.err_msg_email));
                return false;
            } else {
                return true;
            }
        }
        return true;
    }

    private void submitForm() {

        CommonUtil.hideKeyboard(activity);
        if (!validateUsername()) {
            return;
        }
        if (!validateEmail()) {
            return;
        }
        pd = ProgressDialogUtil.getProgressDialogMsg(activity, getResources().getString(R.string.profile_update));
        pd.show();
        new ApiHelper().update_user(AccountFragment.this, strUserId,strUserName,strEmail);
    }

    /*Runtime Permission*/
    void askPermission(){
        PermissionListener permissionlistener = new PermissionListener() {
            @Override
            public void onPermissionGranted() {
                mMediaUtils.openImageDialog();
            }

            @Override
            public void onPermissionDenied(List<String> deniedPermissions) {

            }
        };

        TedPermission.with(activity)
                .setPermissionListener(permissionlistener)
//                .setRationaleTitle("Runtime permission")
//                .setRationaleMessage("If u use the services of app you need to Confirm Runtime Permission")
                .setDeniedTitle("Permission denied")
                .setDeniedMessage(
                        "If you reject permission,you can not use this service\n\nPlease turn on permissions at [Setting] > [Permission]")
                .setGotoSettingButtonText("Go to setting")
                .setPermissions(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE)
                .check();
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
        pd = ProgressDialogUtil.getProgressDialogMsg(activity, getResources().getString(R.string.profile_update));
        pd.show();

        RequestBody mFile = RequestBody.create(MediaType.parse("image/*"), imgFile);
        MultipartBody.Part fileToUpload = MultipartBody.Part.createFormData("profile_image", imgFile.getName(), mFile);
        RequestBody id = RequestBody.create(MediaType.parse("text/plain"), strUserId);

        new ApiHelper().update_user_image(AccountFragment.this,fileToUpload,id);
    }

    @Override
    public void onSuccess(Response<JsonElement> response, String typeApi) {
        dismissDialog();
        if (typeApi.equalsIgnoreCase("update_profile")) {
            ProfileUpdateResponse profileUpdateResponse = new Gson().fromJson(response.body(), ProfileUpdateResponse.class);
            if (profileUpdateResponse != null) {
                if (!profileUpdateResponse.getError()) {

                    tinyDB.putString("LoginUserName",profileUpdateResponse.getUser().getName());
                    tinyDB.putString("LoginEmail",profileUpdateResponse.getUser().getEmail());

                    etUsername.setText(profileUpdateResponse.getUser().getName());
                    etEmail.setText(profileUpdateResponse.getUser().getEmail());

                } else {
                    strUserName = tinyDB.getString("LoginUserName");
                    strEmail    = tinyDB.getString("LoginEmail");
                    etUsername.setText(strUserName);
                    if (!strEmail.equals(""))
                        etEmail.setText(strEmail);
                    SnackBarUtil.showSnackBar(activity, profileUpdateResponse.getMessage(), llMain);
                }
            } else {
                SnackBarUtil.showSnackBar(activity, getResources().getString(R.string.error_try_again), llMain);
            }
        }
        else if (typeApi.equalsIgnoreCase("UpdateProfiImage")) {

            ProfileImageResponse profileImageResponse = new Gson().fromJson(response.body(), ProfileImageResponse.class);
            if (profileImageResponse != null) {
                if (!profileImageResponse.getError()) {

                    String strImagePath=WebConstants.IMAGES_BASE_URL+profileImageResponse.getUser().getImage();
                    Log.d(TAG, "onSuccess: "+strImagePath);
                    tinyDB.putString("ProfileImage",strImagePath);

                } else {
                    String ImagePath=tinyDB.getString("ProfileImage");
                    if (!ImagePath.equalsIgnoreCase("")){
                        Picasso.get()
                                .load(ImagePath)
                                .placeholder(R.drawable.account)
                                .error(R.drawable.account)
                                .into(CivProfileImage);
                    }
                    SnackBarUtil.showSnackBar(activity, profileImageResponse.getMessage(), llMain);
                }
            } else {
                SnackBarUtil.showSnackBar(activity, getResources().getString(R.string.error_try_again), llMain);
            }
        }
    }

    @Override
    public void onFailure(String error) {
        Log.d(TAG, "onFailure: "+error);
        dismissDialog();
        DialogUtil.showDialogMsg(activity, "Server Error", getResources().getString(R.string.server_error_try_again));
    }

    private void dismissDialog() {
        try {
            if (pd != null) {
                if (pd.isShowing())
                    pd.dismiss();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
