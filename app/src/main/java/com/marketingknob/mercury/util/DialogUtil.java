package com.marketingknob.mercury.util;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.Window;
import android.widget.DatePicker;


import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.marketingknob.mercury.R;
import com.marketingknob.mercury.ui.HomeActivity;
import com.marketingknob.mercury.ui.LoginActivity;

import static android.app.DatePickerDialog.OnDateSetListener;

/**
 * Created by Akshya on 5/10/2018.
 */

public class DialogUtil {

    public static void showDialogMsg(Context context, String type, String msg){
        AlertDialog.Builder builder = new AlertDialog.Builder(context, R.style.MyAlertDialogStyle);
//        builder.setTitle(type);
        builder.setMessage(msg);
//        builder.setIcon(R.mipmap.ic_launcher);
        //Yes Button
        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        AlertDialog alertDialog = builder.create();
        alertDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        alertDialog.show();
    }


    /**
     * @param context
     */
    public void showDialogThree(Context context){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);

//                         Dialog with custom theme
//        AlertDialog.Builder builder = new AlertDialog.Builder(context, R.style.MyDialogTheme);
        builder.setTitle("AlertDialog with No Buttons");
        builder.setMessage("Hello, you can hide this message by just tapping outside the dialog box!");
//        builder.setIcon(R.mipmap.ic_launcher);
        //Yes Button
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        //No Button
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        //Cancel Button
        builder.setNeutralButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    /**
     * Logout Alert
     */
    public static void LogoutDialog(final Activity context, final TinyDB tinyDB){
        AlertDialog.Builder builder = new AlertDialog.Builder(context, R.style.MyAlertDialogStyle);

//                         Dialog with custom theme
//        AlertDialog.Builder builder = new AlertDialog.Builder(context, R.style.MyDialogTheme);
        builder.setTitle(context.getResources().getText(R.string.logout));
        builder.setMessage(context.getResources().getText(R.string.logout_really));
        //Yes Button
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                tinyDB.clear();
                Animatoo.animateFade(context);
                Intent intent= new Intent(context,LoginActivity.class);
                context.startActivity(intent);
                context.startActivity(new Intent(context, LoginActivity.class));
                context.finish();
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

    /**
     * @param context
     */
    public void showDialogOne(Context context){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);

//                 Dialog with custom theme
//        AlertDialog.Builder builder = new AlertDialog.Builder(context, R.style.MyDialogTheme);
        builder.setTitle("AlertDialog with No Buttons");
        builder.setMessage("Hello, you can hide this message by just tapping outside the dialog box!");
        //Yes Button
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }


    /**
     * @param context
     */
    public void showDialogSingleChoice(Context context) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);

//         Dialog with custom theme
//        AlertDialog.Builder builder = new AlertDialog.Builder(context, R.style.MyDialogTheme);
        builder.setTitle("Title");

        //list of items
//        String[] items = context.getResources().getStringArray(R.array.ringtone_list);
        String[] items = {"One", "Two", "Three"};
        builder.setSingleChoiceItems(items, 0,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // item selected logic
                    }
                });

        String positiveText = context.getString(android.R.string.ok);
        builder.setPositiveButton(positiveText,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // positive button logic
                    }
                });

        String negativeText = context.getString(android.R.string.cancel);
        builder.setNegativeButton(negativeText,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // negative button logic
                    }
                });

        AlertDialog dialog = builder.create();
        // display dialog
        dialog.show();
    }


    /**
     * @param context
     */
    public void showDialogMultipleChoice(Context context) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);

//                 Dialog with custom theme
//        AlertDialog.Builder builder = new AlertDialog.Builder(context, R.style.MyDialogTheme);
        builder.setTitle("Title");

        //list of items
//        String[] items = context.getResources().getStringArray(R.array.ringtone_list);
        String[] items = {"One", "Two", "Three"};
        builder.setSingleChoiceItems(items, 0,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // item selected logic
                    }
                });

        String positiveText = context.getString(android.R.string.ok);
        builder.setPositiveButton(positiveText,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // positive button logic
                    }
                });

        String negativeText = context.getString(android.R.string.cancel);
        builder.setNegativeButton(negativeText,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // negative button logic
                    }
                });

        AlertDialog dialog = builder.create();
        // display dialog
        dialog.show();
    }

    public void showDialogDatePicker(Context context){

        OnDateSetListener listener = new OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {

            }
        };
        DatePickerDialog datePickerDialog = new DatePickerDialog(context, listener, 2017, 8, 01);
//        Datepicker dialg with custom theme
//        DatePickerDialog datePickerDialog = new DatePickerDialog(context, R.style.MyDialogTheme, listener, 2017, 8, 01);
        datePickerDialog.show();
    }


}
