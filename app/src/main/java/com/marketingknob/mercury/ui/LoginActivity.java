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
import android.view.View;
import android.widget.Toast;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.marketingknob.mercury.R;
import com.marketingknob.mercury.util.CommonUtil;
import com.marketingknob.mercury.util.DialogUtil;
import com.rilixtech.CountryCodePicker;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Akshya on 5/10/2018.
 */

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    ProgressDialog pd;
    Context context;

    @BindView(R.id.btn_login_in)        AppCompatButton btnSignIn;
    @BindView(R.id.input_phone)         AppCompatEditText etPhone;
    @BindView(R.id.input_layout_phone)  TextInputLayout inputLayoutPhone;
    @BindView(R.id.tv_create_new)       AppCompatTextView tvCreateNew;
    @BindView(R.id.ll_main)             LinearLayoutCompat llMain;
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
                    DialogUtil.showDialogMsg(LoginActivity.this, "Internet Error", getResources().getString(R.string.login_offline));

                } else {
                    submitForm();
                }
                break;

            case R.id.tv_create_new:
                CommonUtil.hideKeyboard(LoginActivity.this);
                startActivity(new Intent(this, SignUpActivity.class));
                Animatoo.animateInAndOut(LoginActivity.this);
                break;

            case R.id.ll_main:
                CommonUtil.hideKeyboard(LoginActivity.this);
                break;

        }
    }

    private void submitForm() {

        CommonUtil.hideKeyboard(LoginActivity.this);
        if (!validatePhone()) {
            return;
        }
        Toast.makeText(context, "Sucess", Toast.LENGTH_SHORT).show();
    }


    private boolean validatePhone() {
        String phone = etPhone.getText().toString().trim();

        if (phone.isEmpty() || !CommonUtil.isValidnumber(phone)) {
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
        Animatoo.animateSlideLeft(LoginActivity.this);
    }

}
