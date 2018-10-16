package com.marketingknob.mercury.ui;

import android.content.Intent;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.LinearLayoutCompat;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.goodiebag.pinview.Pinview;
import com.marketingknob.mercury.R;
import com.marketingknob.mercury.util.CommonUtil;
import com.marketingknob.mercury.util.DialogUtil;
import com.rilixtech.CountryCodePicker;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Akshya on 8/10/2018.
 */

public class SignUpActivity extends AppCompatActivity implements View.OnClickListener{

    @BindView(R.id.btn_login_in)        AppCompatButton btnSignUp;
    @BindView(R.id.input_phone)         AppCompatEditText etPhone;
    @BindView(R.id.input_username)      AppCompatEditText etusername;
    @BindView(R.id.input_email)         AppCompatEditText etEmail;
    @BindView(R.id.tv_login)            AppCompatTextView tvlogin;
    @BindView(R.id.tv_number)           AppCompatTextView tvNumber;

    @BindView(R.id.input_layout_phone)  TextInputLayout inputLayoutPhone;
    @BindView(R.id.input_layout_name)   TextInputLayout inputLayoutName;
    @BindView(R.id.input_layout_email)  TextInputLayout inputLayoutEmail;

    @BindView(R.id.ll_main)             LinearLayoutCompat llMain;
    @BindView(R.id.ll_otp)              LinearLayoutCompat llOtp;
    @BindView(R.id.btn_otp)             AppCompatButton btnOtp;
    @BindView(R.id.ccp)                 CountryCodePicker countryCodePicker;
    @BindView(R.id.pinview)             Pinview pinview;

    public String strEmail ="", strUsername ="", strPhone ="";

    private static final String TAG = "SignUpActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        init();

        pinview.setPinViewEventListener(new Pinview.PinViewEventListener() {
            @Override
            public void onDataEntered(Pinview pinview, boolean b) {
                Toast.makeText(SignUpActivity.this, pinview.getValue(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onClick(View v) {
        if (v== btnSignUp){
            submitForm();
        }else if (v==tvlogin){
            finish();
            Animatoo.animateInAndOut(SignUpActivity.this);
        }else if (v==llMain){
            CommonUtil.hideKeyboard(SignUpActivity.this);
        }else if (v==llOtp){
            CommonUtil.hideKeyboard(SignUpActivity.this);
        }else if(v==btnOtp){
            CommonUtil.hideKeyboard(SignUpActivity.this);

            Animatoo.animateInAndOut(SignUpActivity.this);
            startActivity(new Intent(SignUpActivity.this,ClubLocationActivity.class));
            finish();

        }
    }

    /**
     * This method is used for init.
     */
    void init(){

        ButterKnife.bind(this);
        countryCodePicker.enableSetCountryByTimeZone(true);
        llMain.setVisibility(View.VISIBLE);
        llOtp.setVisibility(View.GONE);

        btnSignUp.setOnClickListener(this);
        tvlogin.setOnClickListener(this);
        llMain.setOnClickListener(this);
        llOtp.setOnClickListener(this);
        btnOtp.setOnClickListener(this);

        pinview.setValue("5846");
    }

    /**
     * Validating form
     */

    private void submitForm() {

        CommonUtil.hideKeyboard(SignUpActivity.this);

        if (!validatePhone()) {
            return;
        }
        if (!validateUsername()) {
            return;
        }
        if (!validateEmail()) {
            return;
        }

        llMain.setVisibility(View.GONE);
        tvNumber.setText(strPhone);
        llOtp.setVisibility(View.VISIBLE);
    }

    /**
     * Phone Number Validation
     */
    private boolean validatePhone() {
        strPhone = etPhone.getText().toString().trim();

        if (strPhone.isEmpty() || !CommonUtil.isValidnumber(strPhone)) {
            DialogUtil.showDialogMsg(SignUpActivity.this, "Number Error", getResources().getString(R.string.error_wrong_number));
            return false;

        } else {
            inputLayoutPhone.setErrorEnabled(false);
        }

        return true;
    }

    /**
     * Username Validation
     */
    private boolean validateUsername() {
        strUsername = etusername.getText().toString().trim();

        if (strUsername.isEmpty() ) {
            DialogUtil.showDialogMsg(SignUpActivity.this, "Username Error", getResources().getString(R.string.err_msg_username));
            return false;
        } else {
            inputLayoutName.setErrorEnabled(false);
        }

        return true;
    }

    /**
     * Email Validation
     */
    private boolean validateEmail() {
        strEmail = etEmail.getText().toString().trim();
        if (strEmail.equalsIgnoreCase("")) {
            return true;
        } else {
            if (!CommonUtil.isValidEmail(strEmail)) {
                DialogUtil.showDialogMsg(SignUpActivity.this, "Email Error",
                        getResources().getString(R.string.err_msg_email));
                return false;
            } else {
                inputLayoutEmail.setErrorEnabled(false);
            }

        }
        return true;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Animatoo.animateSlideLeft(SignUpActivity.this);
    }
}
