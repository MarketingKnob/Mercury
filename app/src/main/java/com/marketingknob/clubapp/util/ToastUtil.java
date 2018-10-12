package com.marketingknob.clubapp.util;

import android.content.Context;
import android.view.Gravity;
import android.widget.Toast;

/**
 * Created by Akshya on 5/10/2018.
 */


public class ToastUtil {

    private static Toast toast = null;
    /**
     \* @param context
     * @param message
     */
    public static void showToastLong(Context context, String message){
        if(toast != null)
            toast.cancel();

        toast = Toast.makeText(context, message, Toast.LENGTH_LONG);
        toast.setGravity(Gravity.BOTTOM, 0 , 0);
        toast.show();
    }

    /**
     * @param context
     * @param message
     */
    public static void showToastShort(Context context, String message){
        if(toast != null)
            toast.cancel();

        toast = Toast.makeText(context, message, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.BOTTOM, 0 , 0);
        toast.show();
    }
}
