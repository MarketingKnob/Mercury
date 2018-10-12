package com.marketingknob.clubapp.util;

import android.support.design.widget.Snackbar;
import android.view.View;

/**
 * Created by Akshya on 5/10/2018.
 */

public class SnackBarUtil {

    private static Snackbar snackbar = null;

    public static void showToastLong(View view, String message){
        if(snackbar != null)
            snackbar.dismiss();

        snackbar = Snackbar.make(view, message, Snackbar.LENGTH_LONG);
        snackbar.show();
    }

    public static void showToastShort(View view, String message){
        if(snackbar != null)
            snackbar.dismiss();

        snackbar = Snackbar.make(view, message, Snackbar.LENGTH_SHORT);
        snackbar.show();
    }

}
