package com.marketingknob.mercury.ui;

import android.app.Activity;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.marketingknob.mercury.R;
import com.marketingknob.mercury.ui.fragments.FirstFragment;
import com.marketingknob.mercury.ui.fragments.HomeFragment;
import com.marketingknob.mercury.ui.fragments.SecondFragment;
import com.marketingknob.mercury.ui.fragments.ThirdFragment;
import com.marketingknob.mercury.util.BottomNavigationViewHelper;
import com.marketingknob.mercury.util.SnackBarUtil;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Akshya on 23/10/2018.
 */

public class HomeActivity extends AppCompatActivity {

    HomeFragment homeFragment;
    FirstFragment firstFragment;
    SecondFragment secondFragment;
    ThirdFragment thirdFragment;

    Fragment currentFragment = null;
    FragmentTransaction ft;
    BottomNavigationView bottomNavigationView;
    private int backPress=0;
    boolean doubleBackToExitPressedOnce = false;

    @BindView(R.id.rl_main) RelativeLayout rlMain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_new);
        setupTabLayout();

        bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottomNavigationView_broker);
        BottomNavigationViewHelper.removeShiftMode(bottomNavigationView);

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
                        currentFragment = firstFragment;
                        ft.replace(R.id.main_broker_frame, currentFragment);
                        ft.commit();
                        break;

                    case R.id.bn_account:
                        ft = getSupportFragmentManager().beginTransaction();
                        currentFragment = secondFragment;
                        ft.replace(R.id.main_broker_frame, currentFragment);
                        ft.commit();
                        break;

                    case R.id.bn_more:
                        ft = getSupportFragmentManager().beginTransaction();
                        currentFragment = thirdFragment ;
                        ft.replace(R.id.main_broker_frame, currentFragment);
                        ft.commit();
                        break;
                }

                return true;
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
            SnackBarUtil.showSnackBar(this,"Please click BACK again to exit",rlMain);
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

        ButterKnife.bind(this);

        homeFragment          = new HomeFragment();
        firstFragment         = new FirstFragment();
        secondFragment        = new SecondFragment();
        thirdFragment         = new ThirdFragment();

    }
}
