package com.marketingknob.mercury.ui.fragments;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.marketingknob.mercury.R;
import com.marketingknob.mercury.adapter.RvDrinkDetails;
import com.marketingknob.mercury.adapter.RvNotification;
import com.marketingknob.mercury.model.DrinkCategoryModel;
import com.marketingknob.mercury.util.FloatingButton;
import com.marketingknob.mercury.util.ProgressDialogUtil;
import com.marketingknob.mercury.util.TinyDB;
import com.marketingknob.mercury.webservices.ApiHelper;
import com.marketingknob.mercury.webservices.WebConstants;

import java.util.ArrayList;

/**
 * Created by Akshya on 26/10/2018.
 */

public class NotificationFragment extends Fragment {

    RecyclerView rvNotification;
    Context context;
    RvNotification rvNotificationAdapter;

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

        context                         = this.getActivity();
        rvNotification                  = view.findViewById(R.id.rv_notification);

        /*Notification RecyclerView Configure*/
        final LinearLayoutManager layoutManager1 = new LinearLayoutManager(context);
        layoutManager1.setOrientation(LinearLayoutManager.VERTICAL);
        rvNotification.setLayoutManager(layoutManager1);
        rvNotification.setItemAnimator(new DefaultItemAnimator());
        rvNotificationAdapter = new RvNotification(context);
        rvNotification.setAdapter(rvNotificationAdapter);
    }

}
