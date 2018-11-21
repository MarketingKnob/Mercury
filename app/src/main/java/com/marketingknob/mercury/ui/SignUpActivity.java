package com.marketingknob.mercury.ui;

import android.Manifest;
import android.animation.LayoutTransition;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.design.widget.TextInputLayout;
import android.support.transition.TransitionManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.LinearLayoutCompat;
import android.transition.Fade;
import android.util.Log;
import android.view.View;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.cooltechworks.views.shimmer.ShimmerRecyclerView;
import com.goodiebag.pinview.Pinview;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;
import com.marketingknob.mercury.PickerPopWin.DatePickerPopWin;
import com.marketingknob.mercury.R;
import com.marketingknob.mercury.otp.SmsListener;
import com.marketingknob.mercury.otp.SmsReceiver;
import com.marketingknob.mercury.util.CommonUtil;
import com.marketingknob.mercury.util.DialogUtil;
import com.marketingknob.mercury.util.ProgressDialogUtil;
import com.marketingknob.mercury.util.SnackBarUtil;
import com.marketingknob.mercury.util.TinyDB;
import com.marketingknob.mercury.webservices.ApiHelper;
import com.marketingknob.mercury.webservices.interfaces.ApiResponseHelper;
import com.marketingknob.mercury.webservices.webresponse.OtpResponse;
import com.marketingknob.mercury.webservices.webresponse.SignupResponse;
import com.rilixtech.CountryCodePicker;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.ghyeok.stickyswitch.widget.StickySwitch;
import retrofit2.Response;

import android.os.CountDownTimer;
import android.view.animation.TranslateAnimation;
import android.widget.Toast;

import org.jetbrains.annotations.NotNull;

import java.util.Calendar;
import java.util.List;

/**
 * Created by Akshya on 8/10/2018.
 */

public class SignUpActivity extends AppCompatActivity implements View.OnClickListener , ApiResponseHelper {

    @BindView(R.id.btn_login_in)        AppCompatButton btnSignUp;
    @BindView(R.id.input_phone)         AppCompatEditText etPhone;
    @BindView(R.id.input_username)      AppCompatEditText etusername;
    @BindView(R.id.input_email)         AppCompatEditText etEmail;
    @BindView(R.id.tv_login)            AppCompatTextView tvlogin;
    @BindView(R.id.tv_number)           AppCompatTextView tvNumber;
    @BindView(R.id.tv_timer)            AppCompatTextView tvTimer;
    @BindView(R.id.tv_dob)              AppCompatTextView tvDob;

    @BindView(R.id.input_layout_phone)  TextInputLayout inputLayoutPhone;
    @BindView(R.id.input_layout_name)   TextInputLayout inputLayoutName;
    @BindView(R.id.input_layout_email)  TextInputLayout inputLayoutEmail;

    @BindView(R.id.ll_main)             LinearLayoutCompat llMain;
    @BindView(R.id.ll_top)              LinearLayoutCompat llTop;
    @BindView(R.id.ll_otp)              LinearLayoutCompat llOtp;
    @BindView(R.id.btn_otp)             AppCompatButton btnOtp;
    @BindView(R.id.btn_resend_otp)      AppCompatButton btnResendOtp;
    @BindView(R.id.ccp)                 CountryCodePicker countryCodePicker;
    @BindView(R.id.pinview)             Pinview pinview;
    @BindView(R.id.sticky_switch)       StickySwitch stickySwitch;

    public String strEmail ="", strUsername ="", strPhone ="", strDeviceId ="",strOtp="",strUserId="",strGender="Male";
    public int counter;
    private static final String TAG = "SignUpActivity";
    ProgressDialog pd;
    TinyDB tinyDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        init();

        pinview.setPinViewEventListener(new Pinview.PinViewEventListener() {
            @Override
            public void onDataEntered(Pinview pinview, boolean b) {

                strOtp=pinview.getValue();
            }
        });

        SmsReceiver.bindListener(new SmsListener() {
            @Override
            public void messageReceived(String messageText) {
                String otp =messageText.replaceAll("\\D+","");
                strOtp=otp;
                Log.d(TAG, "messageReceived:"+strOtp+" In integer"+Integer.valueOf(strOtp.trim()));
                pinview.setValue(otp);
            }
        });

        stickySwitch.setOnSelectedChangeListener(new StickySwitch.OnSelectedChangeListener() {
            @Override
            public void onSelectedChange(@NotNull StickySwitch.Direction direction, @NotNull String text) {
                strGender=text.trim();
                Log.d(TAG, "Now Selected : " + direction.name() + ", Current Text : " + text);
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onClick(View v) {
        if (v== btnSignUp){
            submitForm();

        }else if (v==tvlogin){
            finish();
            CommonUtil.hideKeyboard(SignUpActivity.this);
            Animatoo.animateSwipeLeft(SignUpActivity.this);
        }else if (v==llMain){
            CommonUtil.hideKeyboard(SignUpActivity.this);
        }else if (v==llOtp){
            CommonUtil.hideKeyboard(SignUpActivity.this);
        }else if(v==btnOtp){
            strOtp=pinview.getValue();
            Log.d(TAG, "onClick:PinGet "+strOtp);
            otpValidation();
        } else if (v==btnResendOtp){
            CommonUtil.hideKeyboard(SignUpActivity.this);
            llMain.setVisibility(View.VISIBLE);
            llOtp.setVisibility(View.GONE);
        }else if (v==tvDob){
            datePicker();
        }
    }

    /*Date Picker for Date of Birth Like as IOs*/
    void datePicker() {

        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.YEAR,1);
        int CurrentYear = calendar.get(Calendar.YEAR);
        DatePickerPopWin pickerPopWin = new DatePickerPopWin.Builder(SignUpActivity.this, new DatePickerPopWin.OnDatePickedListener() {
            @Override
            public void onDatePickCompleted(int year, int month, int day, String dateDesc) {
                Log.d(TAG, "onDatePickCompleted: "+dateDesc+" "+year);
                tvDob.setText(""+day+"/"+month+"/"+year);
            }
        }).textConfirm("CONFIRM") //text of confirm button
                .textCancel("CANCEL") //text of cancel button
                .btnTextSize(16) // button text size
                .viewTextSize(25) // pick view text size
                .colorCancel(Color.parseColor("#999999")) //color of cancel button
                .colorConfirm(Color.parseColor("#009900"))//color of confirm button
                .minYear(1960) //min year in loop
                .maxYear(CurrentYear) // max year in loop
//                .dateChose("2013-11-11") // date chose when init popwindow
                .build();
        pickerPopWin.showPopWin(SignUpActivity.this);
    }

    /**
     * This method is used for init.
     */
    void init(){

        ButterKnife.bind(this);
        tinyDB = new TinyDB(SignUpActivity.this);
        countryCodePicker.enableSetCountryByTimeZone(true);
        countryCodePicker.setClickable(false);
        llMain.setVisibility(View.VISIBLE);
        llOtp.setVisibility(View.GONE);
        btnResendOtp.setVisibility(View.GONE);

        btnSignUp.setOnClickListener(this);
        tvlogin.setOnClickListener(this);
        llMain.setOnClickListener(this);
        llOtp.setOnClickListener(this);
        btnOtp.setOnClickListener(this);
        btnResendOtp.setOnClickListener(this);
        tvDob.setOnClickListener(this);
//        pinview.setValue("5846");

        //For Focus of Cursor into Edit Text
        etPhone.requestFocusFromTouch();

    }

    /**
     * Validating Registration Form
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
        askPermission();
    }

    /**
     * OTP Validation
     */
    private void otpValidation() {
        Log.d(TAG, "otpValidation: "+strOtp);
        CommonUtil.hideKeyboard(SignUpActivity.this);
        if (!strOtp.equalsIgnoreCase("")) {
            pd = ProgressDialogUtil.getProgressDialogMsg(SignUpActivity.this, getResources().getString(R.string.otp_verify));
            pd.show();
            new ApiHelper().verifyOtp(strOtp,strUserId,SignUpActivity.this);

        } else {
            DialogUtil.showDialogMsg(SignUpActivity.this, "Blank OTP", getResources().getString(R.string.blank_otp));

        }
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

        Log.d(TAG, "validateEmail: "+strEmail.length());
        if (strEmail.length()>0){
            if (!CommonUtil.isValidEmail(strEmail)) {
                DialogUtil.showDialogMsg(SignUpActivity.this, "Email Error",
                        getResources().getString(R.string.err_msg_email));
                return false;
            } else {
                return true;
            }
        }
        return true;
    }

    /**
     * Ask For Runtime Permission by user
     */
    void askPermission(){
        PermissionListener permissionlistener = new PermissionListener() {
            @Override
            public void onPermissionGranted() {
                pd = ProgressDialogUtil.getProgressDialogMsg(SignUpActivity.this, getResources().getString(R.string.create_account));
                pd.show();
                new ApiHelper().signUpUser(strUsername,strPhone,strDeviceId,strEmail,strGender,SignUpActivity.this);

            }

            @Override
            public void onPermissionDenied(List<String> deniedPermissions) {
                DialogUtil.showDialogMsg(SignUpActivity.this, " OTP permission",
                        getResources().getString(R.string.otp_permission));

            }
        };

        TedPermission.with(this)
                .setPermissionListener(permissionlistener)
//                .setRationaleTitle("Runtime permission")
//                .setRationaleMessage("If u use the services of app you need to Confirm Runtime Permission")
                .setDeniedTitle("Permission denied")
                .setDeniedMessage("If you reject permission,you can not use this service\n\nPlease turn on permissions at [Setting] > [Permission]")
                .setGotoSettingButtonText("Go to setting")
                .setPermissions(Manifest.permission.READ_SMS, Manifest.permission.RECEIVE_SMS)
                .check();
    }

    /**
     * OTP timer start
     */
    private void otpTimerStart(){
        new CountDownTimer(30000, 1000) {

            public void onTick(long millisUntilFinished) {
                tvTimer.setText("Get OTP within: " + millisUntilFinished / 1000+" Seconds");
            }
            public void onFinish() {
                tvTimer.setText("Resend OTP!");
                btnOtp.setVisibility(View.GONE);
                btnResendOtp.setVisibility(View.VISIBLE);
            }
        }.start();
    }

    @Override
    public void onSuccess(Response<JsonElement> response, String typeApi) {
        dismissDialog();
        if(typeApi.equalsIgnoreCase("signup")) {
            SignupResponse signupResponse = new Gson().fromJson(response.body(), SignupResponse.class);
            if(signupResponse != null) {
                if (!signupResponse.getError()) {
                    SnackBarUtil.showSnackBar(SignUpActivity.this,signupResponse.getMessage(),llTop);
                    strUserId  = String.valueOf(signupResponse.getUser().getUserid());

                    llMain.setVisibility(View.GONE);
                    tvNumber.setText(strPhone);
                    llOtp.setVisibility(View.VISIBLE);
                    btnOtp.setVisibility(View.VISIBLE);
                    btnResendOtp.setVisibility(View.GONE);
                    otpTimerStart();

                } else {
                    DialogUtil.showDialogMsg(SignUpActivity.this, "Error", signupResponse.getMessage());
                }
            } else {
                DialogUtil.showDialogMsg(SignUpActivity.this, "Error", getResources().getString(R.string.error_try_again));
            }
        }

        /*For Verify OTP*/
       else if(typeApi.equalsIgnoreCase("verifyOTP")) {
            OtpResponse otpResponse = new Gson().fromJson(response.body(), OtpResponse.class);
            if(otpResponse != null) {
                if (!otpResponse.getError()) {

                    Toast.makeText(this, ""+otpResponse.getMessage(), Toast.LENGTH_SHORT).show();

                    Log.d(TAG, "onSuccess: Phone"+otpResponse.getUser().getPhone()+" Name"+otpResponse.getUser().getName()
                    +" Email"+otpResponse.getUser().getEmail()+"Gender"+otpResponse.getUser().getGender()+"LoginStatus"+
                            otpResponse.getUser().getLoginStatus()+"LoginId"+ otpResponse.getUser().getUserid());

                    tinyDB.putString("LoginMobile",otpResponse.getUser().getPhone());
                    tinyDB.putString("LoginUserName",otpResponse.getUser().getName());
                    tinyDB.putString("LoginEmail",otpResponse.getUser().getEmail());
                    tinyDB.putString("LoginGender",otpResponse.getUser().getGender());
                    tinyDB.putBoolean("LoginStatus",otpResponse.getUser().getLoginStatus());
                    tinyDB.putString("LoginId",otpResponse.getUser().getUserid().trim());
                    Log.d(TAG, "onSuccess: TinyDB"+tinyDB.getString("LoginMobile"));

                    CommonUtil.hideKeyboard(SignUpActivity.this);
                    Animatoo.animateInAndOut(SignUpActivity.this);
                    startActivity(new Intent(SignUpActivity.this, ClubLocationActivity.class));
                    finish();

                } else {
                    DialogUtil.showDialogMsg(SignUpActivity.this, "Error", otpResponse.getMessage());
                }
            } else {
                DialogUtil.showDialogMsg(SignUpActivity.this, "Error", getResources().getString(R.string.error_try_again));
            }
        }

    }

    @Override
    public void onFailure(String error) {
        dismissDialog();
        DialogUtil.showDialogMsg(SignUpActivity.this, "Server Error", getResources().getString(R.string.server_error_try_again));
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

        if (llOtp.getVisibility()==View.VISIBLE){

        }
        else {
            super.onBackPressed();
            CommonUtil.hideKeyboard(SignUpActivity.this);
            Animatoo.animateSwipeLeft(SignUpActivity.this);
        }
    }

    @Override
    protected void onDestroy() {
        SmsReceiver.unbindListener();
        super.onDestroy();
    }

}
