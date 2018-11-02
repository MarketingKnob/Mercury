package com.marketingknob.mercury.ui;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.LinearLayoutCompat;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.marketingknob.mercury.R;
import com.marketingknob.mercury.ui.fragments.NotificationFragment;
import com.marketingknob.mercury.ui.fragments.HomeFragment;
import com.marketingknob.mercury.ui.fragments.AccountFragment;
import com.marketingknob.mercury.ui.fragments.MoreFragment;
import com.marketingknob.mercury.util.CommonUtil;
import com.marketingknob.mercury.util.DialogUtil;
import com.marketingknob.mercury.util.ProgressDialogUtil;
import com.marketingknob.mercury.util.SnackBarUtil;
import com.marketingknob.mercury.util.TinyDB;
import com.marketingknob.mercury.webservices.ApiHelper;
import com.marketingknob.mercury.webservices.interfaces.ApiResponseHelper;
import com.marketingknob.mercury.webservices.webresponse.LoginResponse;
import com.marketingknob.mercury.webservices.webresponse.LogoutResponse;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Response;

/**
 * Created by Akshya on 23/10/2018.
 */

public class HomeActivity extends AppCompatActivity implements  ApiResponseHelper {

    HomeFragment homeFragment;
    NotificationFragment notificationFragment;
    AccountFragment accountFragment;
    MoreFragment MoreFragment;
    Fragment currentFragment = null;
    FragmentTransaction ft;
    BottomNavigationView bottomNavigationView;
    private int backPress=0;
    boolean doubleBackToExitPressedOnce = false;
    TinyDB tinyDB;
    ProgressDialog pd;
    String strPhone="";
    private static final String TAG = "HomeActivity";

    @BindView(R.id.ll_main)     LinearLayoutCompat llMain;
    @BindView(R.id.iv_logout)   AppCompatImageView ivLogout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);

        findViewById();
        setupTabLayout();

//        EditText searchEditText = (EditText) searchView.findViewById(android.support.v7.appcompat.R.id.search_src_text);
//        searchEditText.setTextColor(getResources().getColor(R.color.red));
//        searchEditText.setHintTextColor(getResources().getColor(R.color.red));

        ft = getSupportFragmentManager().beginTransaction();
        currentFragment = homeFragment;
        ft.replace(R.id.main_broker_frame, currentFragment);
        ft.addToBackStack(null);
        ft.commit();

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.bn_home:
                        ft = getSupportFragmentManager().beginTransaction();
                        currentFragment = homeFragment;
                        ft.replace(R.id.main_broker_frame, currentFragment);
                        ft.commit();
                        break;

                    case R.id.bn_notification:
                        ft = getSupportFragmentManager().beginTransaction();
                        currentFragment = notificationFragment;
                        ft.replace(R.id.main_broker_frame, currentFragment);
                        ft.commit();
                        break;

                    case R.id.bn_account:
                        ft = getSupportFragmentManager().beginTransaction();
                        currentFragment = accountFragment;
                        ft.replace(R.id.main_broker_frame, currentFragment);
                        ft.commit();
                        break;

                    case R.id.bn_more:
                        ft = getSupportFragmentManager().beginTransaction();
                        currentFragment = MoreFragment;
                        ft.replace(R.id.main_broker_frame, currentFragment);
                        ft.commit();
                        break;
                }

                return true;
            }
        });

        ivLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LogoutDialog();
            }
        });



    }

    @Override
    public void onBackPressed() {

        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottomNavigationView_broker);
        int seletedItemId = bottomNavigationView.getSelectedItemId();
        if (R.id.bn_home != seletedItemId) {
            setHomeItem(this);
        }
        else {
            if (doubleBackToExitPressedOnce) {
                super.onBackPressed();
                finish();
                return;
            }

            this.doubleBackToExitPressedOnce = true;
            SnackBarUtil.showSnackBar(this,"Please click BACK again to exit", llMain);
            new Handler().postDelayed(new Runnable() {

                @Override
                public void run() {
                    doubleBackToExitPressedOnce=false;
                }
            }, 2000);
        }
    }

    public static void setHomeItem(Activity activity) {
        BottomNavigationView bottomNavigationView = (BottomNavigationView)
                activity.findViewById(R.id.bottomNavigationView_broker);
        bottomNavigationView.setSelectedItemId(R.id.bn_home);
    }

    private void setupTabLayout() {

        homeFragment            = new HomeFragment();
        notificationFragment    = new NotificationFragment();
        accountFragment         = new AccountFragment();
        MoreFragment            = new MoreFragment();

    }

    private void findViewById(){

        tinyDB                  = new TinyDB(this);
        bottomNavigationView    = (BottomNavigationView) findViewById(R.id.bottomNavigationView_broker);
//        BottomNavigationViewHelper.removeShiftMode(bottomNavigationView);

        strPhone                = tinyDB.getString("LoginMobile");

    }

    @Override
    public void onSuccess(Response<JsonElement> response, String typeApi) {
        dismissDialog();
        if(typeApi.equalsIgnoreCase("logout")) {
            LogoutResponse logoutResponse = new Gson().fromJson(response.body(), LogoutResponse.class);
            if(logoutResponse != null) {
                if (!logoutResponse.getError()) {
                    if (logoutResponse.getResponseCode()==200){

                        tinyDB.clear();
                        Animatoo.animateFade(this);
                        Intent intent= new Intent(this,LoginActivity.class);
                        startActivity(intent);
                        finish();

                        Toast.makeText(this, ""+logoutResponse.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                    else {
                        DialogUtil.showDialogMsg(HomeActivity.this, "Error", logoutResponse.getMessage());
                    }

                } else {
                    DialogUtil.showDialogMsg(HomeActivity.this, "Error", logoutResponse.getMessage());
                }
            } else {
                DialogUtil.showDialogMsg(HomeActivity.this, "Error", getResources().getString(R.string.error_try_again));
            }
        }
    }

    @Override
    public void onFailure(String error) {
        dismissDialog();
        DialogUtil.showDialogMsg(HomeActivity.this, "Server Error", getResources().getString(R.string.server_error_try_again));
    }

    /**
     * Logout Alert
     */
    public  void LogoutDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.MyAlertDialogStyle);
        builder.setTitle(this.getResources().getText(R.string.logout));
        builder.setMessage(this.getResources().getText(R.string.logout_really));
        //Yes Button
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Log.d(TAG, "onClick: "+strPhone);

                if (!strPhone.equalsIgnoreCase("")){
                    pd = ProgressDialogUtil.getProgressDialogMsg(HomeActivity.this, getResources().getString(R.string.loading_wait));
                    pd.show();
                    new ApiHelper().logout(HomeActivity.this,strPhone);
                }

            }
        });

        //No Button
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }


    /*Dismis Dialog*/

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
