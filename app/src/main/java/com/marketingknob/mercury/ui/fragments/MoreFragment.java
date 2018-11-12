package com.marketingknob.mercury.ui.fragments;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.marketingknob.mercury.R;
import com.marketingknob.mercury.ui.AboutDeveloperActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Akshya on 29/10/2018.
 */

public class MoreFragment extends Fragment implements View.OnClickListener{

    @BindView(R.id.rl_about_developer) RelativeLayout rlAboutDeveloper;

    Activity activity;

    public MoreFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_more, container, false);

        init(view);

        return view;
    }

    void init(View view){

        ButterKnife.bind(this,view);
        activity = this.getActivity();
        rlAboutDeveloper.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v== rlAboutDeveloper){
            startActivity(new Intent(activity, AboutDeveloperActivity.class));
            Animatoo.animateInAndOut(activity);
        }
    }
}
