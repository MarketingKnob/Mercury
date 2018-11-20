package com.marketingknob.mercury.ui;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.LinearLayoutCompat;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.marketingknob.mercury.R;
import com.marketingknob.mercury.util.CommonUtil;
import com.marketingknob.mercury.util.DialogUtil;
import com.marketingknob.mercury.util.ProgressDialogUtil;
import com.marketingknob.mercury.util.SnackBarUtil;
import com.marketingknob.mercury.util.TinyDB;
import com.marketingknob.mercury.webservices.ApiHelper;
import com.marketingknob.mercury.webservices.interfaces.ApiResponseHelper;
import com.marketingknob.mercury.webservices.webresponse.LoginResponse;
import com.rilixtech.CountryCodePicker;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Response;

/**
 * Created by Akshya on 5/10/2018.
 */

public class LoginActivity extends AppCompatActivity implements View.OnClickListener, ApiResponseHelper {

    ProgressDialog pd;
    Context context;
    private static final String TAG = "LoginActivity";
    String strPhone ="";
    TinyDB tinyDB;

    @BindView(R.id.btn_login_in)        AppCompatButton btnSignIn;
    @BindView(R.id.input_phone)         AppCompatEditText etPhone;
    @BindView(R.id.input_layout_phone)  TextInputLayout inputLayoutPhone;
    @BindView(R.id.tv_create_new)       AppCompatTextView tvCreateNew;
    @BindView(R.id.ll_main)             LinearLayoutCompat llMain;
    @BindView(R.id.ll_top)              LinearLayoutCompat llTop;
    @BindView(R.id.ccp)                 CountryCodePicker ccp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        init();

    }

    /**
     * This method is used for init.
     */

    void init() {
        tinyDB  = new TinyDB(this);
        context = LoginActivity.this;
        ButterKnife.bind(this);
        btnSignIn.setOnClickListener(this);
        llMain.setOnClickListener(this);
        tvCreateNew.setOnClickListener(this);
        ccp.setClickable(false);
//        etPhone.addTextChangedListener(new MyTextWatcher(etPhone));
        CommonUtil.hideKeyboard(LoginActivity.this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_login_in:
                if (!CommonUtil.isNetworkAvailable(this)) {
                    DialogUtil.showDialogMsg(LoginActivity.this,
                            "Internet Error", getResources().getString(R.string.login_offline));

                } else {
                    submitForm();
                }
                break;

            case R.id.tv_create_new:
                CommonUtil.hideKeyboard(LoginActivity.this);
                startActivity(new Intent(this, SignUpActivity.class));
                Animatoo.animateSwipeRight(LoginActivity.this);
                break;

            case R.id.ll_main:
                CommonUtil.hideKeyboard(LoginActivity.this);
                break;

        }
    }

    /*Submit Login Form*/
    private void submitForm() {

        CommonUtil.hideKeyboard(LoginActivity.this);
        if (!validatePhone()) {
            return;
        }

        pd = ProgressDialogUtil.getProgressDialogMsg(LoginActivity.this, getResources().getString(R.string.login_online));
        pd.show();
        new ApiHelper().login(strPhone,LoginActivity.this);
    }

    /*Validate Phone*/
    private boolean validatePhone() {
        strPhone = etPhone.getText().toString().trim();

        if (strPhone.isEmpty() || !CommonUtil.isValidnumber(strPhone)) {
            DialogUtil.showDialogMsg(LoginActivity.this, "Number Error", getResources().getString(R.string.error_wrong_number));
            return false;

        } else {
            inputLayoutPhone.setErrorEnabled(false);
        }

        return true;
    }

    private class MyTextWatcher implements TextWatcher {

        private View view;

        private MyTextWatcher(View view) {
            this.view = view;
        }

        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        public void afterTextChanged(Editable editable) {
            switch (view.getId()) {
                case R.id.input_phone:
                    validatePhone();
                    break;
            }
        }
    }


    @Override
    public void onSuccess(Response<JsonElement> response, String typeApi) {
        dismissDialog();
        if(typeApi.equalsIgnoreCase("login_user")) {
            LoginResponse loginResponse = new Gson().fromJson(response.body(), LoginResponse.class);
            if(loginResponse != null) {
                if (!loginResponse.getError()) {

                    Log.d(TAG, "onSuccess: "+loginResponse.getUser().getPhone()+" Email"+loginResponse.getUser().getEmail()
                    +" Phone"+loginResponse.getUser().getPhone()+"Gender"+loginResponse.getUser().getGender()+"LoginStatus"+
                            loginResponse.getUser().getLoginStatus()+"LoginId"+ loginResponse.getUser().getUserid());
                    Toast.makeText(context, ""+loginResponse.getMessage(), Toast.LENGTH_SHORT).show();

                    tinyDB.putString("LoginMobile",loginResponse.getUser().getPhone());
                    tinyDB.putString("LoginUserName",loginResponse.getUser().getName());
                    tinyDB.putString("LoginEmail",loginResponse.getUser().getEmail());
                    tinyDB.putString("LoginGender",loginResponse.getUser().getGender());
                    tinyDB.putBoolean("LoginStatus",loginResponse.getUser().getLoginStatus());
                    tinyDB.putString("LoginId",loginResponse.getUser().getUserid().trim());

                    Toast.makeText(this, ""+loginResponse.getMessage(), Toast.LENGTH_SHORT).show();
                    CommonUtil.hideKeyboard(LoginActivity.this);
                    Animatoo.animateSwipeRight(LoginActivity.this);
                    startActivity(new Intent(LoginActivity.this, ClubLocationActivity.class));
                    finish();

                } else {
                    DialogUtil.showDialogMsg(LoginActivity.this, "Error", loginResponse.getMessage());
                }
            } else {
                DialogUtil.showDialogMsg(LoginActivity.this, "Error", getResources().getString(R.string.error_try_again));
            }
        }
    }

    @Override
    public void onFailure(String error) {
        dismissDialog();
        DialogUtil.showDialogMsg(LoginActivity.this, "Server Error", getResources().getString(R.string.server_error_try_again));
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

    @Override
    public void onBackPressed() {
        super.onBackPressed();

    }
}
