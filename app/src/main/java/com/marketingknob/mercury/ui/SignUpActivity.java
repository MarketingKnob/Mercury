package com.marketingknob.mercury.ui;

import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.LinearLayoutCompat;
import android.view.View;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.marketingknob.mercury.R;
import com.marketingknob.mercury.util.CommonUtil;
import com.marketingknob.mercury.util.DialogUtil;

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

    @BindView(R.id.input_layout_phone)  TextInputLayout inputLayoutPhone;
    @BindView(R.id.input_layout_name)   TextInputLayout inputLayoutName;
    @BindView(R.id.input_layout_email)  TextInputLayout inputLayoutEmail;

    @BindView(R.id.ll_main) LinearLayoutCompat llMain;
    @BindView(R.id.ll_otp) LinearLayoutCompat llOtp;

    public String strEmail ="", strUsername ="", strPhone ="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        init();

    }

    @Override
    public void onClick(View v) {
        if (v== btnSignUp){
            submitForm();
        }else if (v==tvlogin){
            finish();
            Animatoo.animateSlideLeft(SignUpActivity.this);
        }else if (v==llMain){
            CommonUtil.hideKeyboard(SignUpActivity.this);
        }else if (v==llOtp){
            CommonUtil.hideKeyboard(SignUpActivity.this);
        }
    }

    /**
     * This method is used for init.
     */
    void init(){

        ButterKnife.bind(this);
        llMain.setVisibility(View.VISIBLE);
        llOtp.setVisibility(View.GONE);

        btnSignUp.setOnClickListener(this);
        tvlogin.setOnClickListener(this);
        llMain.setOnClickListener(this);
        llOtp.setOnClickListener(this);
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
        llOtp.setVisibility(View.VISIBLE);

//        CommonUtil.hideKeyboard(SignUpActivity.this);
//        startActivity(new Intent(this, ClubLocationActivity.class));
//        finish();
//        Animatoo.animateInAndOut(SignUpActivity.this);
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
                DialogUtil.showDialogMsg(SignUpActivity.this, "Email Error", getResources().getString(R.string.err_msg_email));
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
