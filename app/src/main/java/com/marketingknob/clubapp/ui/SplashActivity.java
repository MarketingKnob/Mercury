package com.marketingknob.clubapp.ui;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatImageView;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.appolica.flubber.Flubber;
import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.marketingknob.clubapp.R;
import com.marketingknob.clubapp.util.RootUtil;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by Akshya on 5/10/2018.
 */

public class SplashActivity extends AppCompatActivity {

    AppCompatImageView ivSplashLogo;
    private static int SPLASH_TIME_OUT = 3000;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        findViewById();

//        Animation myanim = AnimationUtils.loadAnimation(this,R.anim.fade_in);
//        ivSplashLogo.startAnimation(myanim);

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

//    check for root
    void checkDeviceRoot(){

        if (RootUtil.isDeviceRooted()) {

            AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.MyAlertDialogStyle);
            builder.setMessage(R.string.error_device_rooted);
            builder.setCancelable(false);
            builder.setPositiveButton(R.string.text_proceed, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(final DialogInterface dialog, final int which) {
                    printHashKey();
                    nextScreenIntent();
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
            nextScreenIntent();
        }
    }

//   Intent To Login Screen
    void nextScreenIntent(){
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(SplashActivity.this, LoginActivity.class));
//                overridePendingTransition(R.anim.right_in, R.anim.left_out);
                Animatoo.animateInAndOut(SplashActivity.this);
                finish();
            }
        }, SPLASH_TIME_OUT);
    }

}
