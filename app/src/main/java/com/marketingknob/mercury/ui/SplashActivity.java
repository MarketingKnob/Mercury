package com.marketingknob.mercury.ui;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.net.Uri;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatImageView;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.Window;

import com.appolica.flubber.Flubber;
import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.marketingknob.mercury.BuildConfig;
import com.marketingknob.mercury.R;
import com.marketingknob.mercury.util.RootUtil;
import com.marketingknob.mercury.util.TinyDB;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by Akshya on 5/10/2018.
 */

public class SplashActivity extends AppCompatActivity {

    AppCompatImageView ivSplashLogo;
    private static int SPLASH_TIME_OUT = 3000;
    private static final int REQ_CODE_PLAY_STORE = 1001;
    TinyDB tinyDB;
    private static final String TAG = "SplashActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        tinyDB = new TinyDB(this);
        findViewById();

        distance(30.6825,76.8445,30.7398,76.7827);
        Log.d(TAG, "onCreate: "+tinyDB.getBoolean("LoginStatus"));

    }

    /**
     * This method is used for find Id of View.
     */
    void findViewById(){
        ivSplashLogo    = findViewById(R.id.iv_logo);
        setFullScreen();
        checkDeviceRoot();

        Flubber.with()
                .animation(Flubber.AnimationPreset.WOBBLE)
                .interpolator(Flubber.Curve.BZR_EASE_IN_CUBIC)
                .duration(SPLASH_TIME_OUT)
                .autoStart(true)
                .createFor(ivSplashLogo);

    }

    /**
     * This method is used for set Full Screen.
     */
    private void setFullScreen() {
        View decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                        | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
    }

    /**
     * This method is used for Print Key Hash.
     */
    public  void printHashKey() {
        try {
            PackageInfo info = getPackageManager().getPackageInfo(getPackageName(), PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                String hashKey = new String(Base64.encode(md.digest(), 0));
                Log.i("TAG", "printHashKey() Hash Key: " + hashKey);
            }
        } catch (NoSuchAlgorithmException e) {
            Log.e("TAG", "printHashKey()", e);
        } catch (Exception e) {
            Log.e("TAG", "printHashKey()", e);
        }
    }

    /**
     *  check for root.
     */
    void checkDeviceRoot(){
        if (RootUtil.isDeviceRooted()) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.MyAlertDialogStyle);
            builder.setMessage(R.string.error_device_rooted);
            builder.setCancelable(false);
            builder.setPositiveButton(R.string.text_proceed, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(final DialogInterface dialog, final int which) {
                    printHashKey();

                    if (tinyDB.getBoolean("LoginStatus")){
                        HomeScreenIntent();
                    }else {
                        LoginScreenIntent();
                    }

                }
            });
            builder.setNegativeButton(R.string.text_exit, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(final DialogInterface dialog, final int which) {
                    finish();
                }
            });
            AlertDialog alertDialog = builder.create();
            alertDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            alertDialog.show();
            builder.show();

        } else {
            printHashKey();

            if (tinyDB.getBoolean("LoginStatus")){
                HomeScreenIntent();
            }else {
                LoginScreenIntent();
            }
        }
    }

    /**
     * Intent To Login Screen.
     */
    void LoginScreenIntent(){
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(SplashActivity.this, LoginActivity.class));
                Animatoo.animateFade(SplashActivity.this);
                finish();
            }
        }, SPLASH_TIME_OUT);

    }

    /**
     * Intent To Home Screen.
     */
    void HomeScreenIntent(){
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(SplashActivity.this, ClubLocationActivity.class));
                Animatoo.animateFade(SplashActivity.this);
                finish();
            }
        }, SPLASH_TIME_OUT);

    }

    /**
     * Open play store.
     */
    private void openPlayStoreForAppUpdate() {
        final Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse("market://details?id=" + BuildConfig.APPLICATION_ID));
        try {
            startActivityForResult(intent, REQ_CODE_PLAY_STORE);
        } catch (final Exception e) {
            intent.setData(Uri.parse("https://play.google.com/store/apps/details?id=" + BuildConfig.APPLICATION_ID));
            startActivityForResult(intent, REQ_CODE_PLAY_STORE);
        }
    }


    /** calculates the distance between two locations in MILES  For better use please go to Google Matrix API */
    public static float distance(Double lat1, Double lng1, Double lat2, Double lng2) {
        double earthRadius = 6371000; //meters
        double dLat = Math.toRadians(lat2 - lat1);
        double dLng = Math.toRadians(lng2 - lng1);
        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2) +
                Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) *
                        Math.sin(dLng / 2) * Math.sin(dLng / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        float dist = (float) (earthRadius * c);

        Log.d(TAG, "distance: "+dist);
        return dist;
    }
}
