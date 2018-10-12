package com.marketingknob.clubapp.util;

import android.app.ProgressDialog;
import android.content.Context;

import com.marketingknob.clubapp.R;

/**
 * Created by Akshya on 5/10/2018.
 */

public class ProgressDialogUtil {

    public static ProgressDialog getProgressDialog(Context context) {
        ProgressDialog progressDialog = new ProgressDialog(context, R.style.MyAlertDialogStyle);
        progressDialog.setIndeterminate(true);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Loading...");
        return  progressDialog;
    }

    public static ProgressDialog getProgressDialogMsg(Context context, String msg) {
        ProgressDialog progressDialog = new ProgressDialog(context, R.style.MyAlertDialogStyle);
        progressDialog.setIndeterminate(true);
        progressDialog.setCancelable(false);
        progressDialog.setMessage(msg);
        return  progressDialog;
    }

}
