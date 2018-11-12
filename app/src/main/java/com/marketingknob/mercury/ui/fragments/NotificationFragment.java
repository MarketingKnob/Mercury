package com.marketingknob.mercury.ui.fragments;


import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.marketingknob.mercury.R;
import com.marketingknob.mercury.adapter.RvDrinkDetails;
import com.marketingknob.mercury.adapter.RvNotification;
import com.marketingknob.mercury.model.DrinkCategoryModel;
import com.marketingknob.mercury.model.NotificationModel;
import com.marketingknob.mercury.util.DialogUtil;
import com.marketingknob.mercury.util.FloatingButton;
import com.marketingknob.mercury.util.MediaUtils;
import com.marketingknob.mercury.util.ProgressDialogUtil;
import com.marketingknob.mercury.util.SnackBarUtil;
import com.marketingknob.mercury.util.TinyDB;
import com.marketingknob.mercury.webservices.ApiHelper;
import com.marketingknob.mercury.webservices.WebConstants;
import com.marketingknob.mercury.webservices.interfaces.ApiResponseHelper;
import com.marketingknob.mercury.webservices.webresponse.NotificationResponse;
import com.marketingknob.mercury.webservices.webresponse.ProfileUpdateResponse;
import com.marketingknob.mercury.webservices.webresponse.UpdateNotification;

import java.util.ArrayList;

import retrofit2.Response;

/**
 * Created by Akshya on 26/10/2018.
 */

public class NotificationFragment extends Fragment implements  ApiResponseHelper {

    RecyclerView rvNotification;
    Activity activity;
    RvNotification rvNotificationAdapter;
    ProgressDialog pd;
    LinearLayoutCompat llMain;
    ArrayList<NotificationModel> notificationModelArrayList;
    String strUserId="";
    TinyDB tinyDB;
    private static final String TAG = "NotificationFragment";

    public NotificationFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_notification, container, false);

        init(view);
        return view;

    }

    void init(View view){

        activity                        = this.getActivity();
        rvNotification                  = view.findViewById(R.id.rv_notification);
        llMain                          = view.findViewById(R.id.ll_main);

        tinyDB                          = new TinyDB(activity);
        strUserId                       = tinyDB.getString("LoginId");

        /*Notification RecyclerView Configure*/
        final LinearLayoutManager layoutManager1 = new LinearLayoutManager(activity);
        layoutManager1.setOrientation(LinearLayoutManager.VERTICAL);
        rvNotification.setLayoutManager(layoutManager1);
        rvNotification.setItemAnimator(new DefaultItemAnimator());

        new ApiHelper().getNotification(NotificationFragment.this,strUserId);

    }

    @Override
    public void onSuccess(Response<JsonElement> response, String typeApi) {
        dismissDialog();
        notificationModelArrayList      = new ArrayList<>();
        if (typeApi.equalsIgnoreCase("Notification")) {
            NotificationResponse notificationResponse = new Gson().fromJson(response.body(), NotificationResponse.class);
            if (notificationResponse != null) {
                if (!notificationResponse.getError()) {

                    for (int i = 0; i < notificationResponse.getNotification().getResult().size(); i++) {
                        NotificationModel notificationModel = new NotificationModel();
                        notificationModel.setId(notificationResponse.getNotification().getResult().get(i).getId());
                        notificationModel.setDescription(notificationResponse.getNotification().getResult().get(i).getDescription());
                        notificationModel.setStatus(notificationResponse.getNotification().getResult().get(i).getStatus());

                        notificationModelArrayList.add(notificationModel);

                    }
                    rvNotificationAdapter = new RvNotification(NotificationFragment.this,notificationModelArrayList);
                    rvNotification.setAdapter(rvNotificationAdapter);

                } else {
                    SnackBarUtil.showSnackBar(activity, getResources().getString(R.string.error_try_again), llMain);
                }
            } else {
                SnackBarUtil.showSnackBar(activity, getResources().getString(R.string.error_try_again), llMain);
            }
        }
       else if (typeApi.equalsIgnoreCase("UpdateNotification")) {
            UpdateNotification updateNotification = new Gson().fromJson(response.body(), UpdateNotification.class);
            if (updateNotification != null) {
                if (!updateNotification.getError()) {
                    new ApiHelper().getNotification(NotificationFragment.this,strUserId);

                } else {
                    SnackBarUtil.showSnackBar(activity, getResources().getString(R.string.error_try_again), llMain);
                }
            } else {
                SnackBarUtil.showSnackBar(activity, getResources().getString(R.string.error_try_again), llMain);
            }
        }
    }

    @Override
    public void onFailure(String error) {
        dismissDialog();
        DialogUtil.showDialogMsg(activity, "Server Error", getResources().getString(R.string.server_error_try_again));
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

   public void readNotification(String strNotificationId){
       Log.d(TAG, "readNotification: "+strNotificationId);
       pd = ProgressDialogUtil.getProgressDialogMsg(activity, getResources().getString(R.string.text_loading));
       pd.show();
       new ApiHelper().updateNotification(NotificationFragment.this,strNotificationId,strUserId);
    }
}
