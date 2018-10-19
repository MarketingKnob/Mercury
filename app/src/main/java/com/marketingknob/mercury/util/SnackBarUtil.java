package com.marketingknob.mercury.util;

import android.app.Activity;
import android.graphics.Color;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.TextView;

import com.marketingknob.mercury.R;

/**
 * Created by Akshya on 5/10/2018.
 */

public class SnackBarUtil {

    public static void showSnackBar(Activity ac, String message, View lay) {

        Snackbar snackbar = Snackbar.make( lay, message, Snackbar.LENGTH_LONG ).setAction(  ac.getString(R.string.ok), onSnackBarClickListener() );
        snackbar.setActionTextColor( ac.getResources().getColor( R.color.white ) );
        View snackbarView = snackbar.getView();
        snackbarView.setBackgroundColor(ac.getResources().getColor( R.color.main_color ));
        TextView textView   = (TextView) snackbarView.findViewById( android.support.design.R.id.snackbar_text);
        //textView.setTextColor(getResources().getColor(R.color.buttonbackground));
        textView.setTextColor( ContextCompat.getColor(ac, R.color.white));
        snackbar.show();
    }

    private static View.OnClickListener onSnackBarClickListener() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        };
    }

}
