package com.marketingknob.mercury.ui.fragments;


import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.marketingknob.mercury.R;
import com.marketingknob.mercury.adapter.RvDrinkCategory;
import com.marketingknob.mercury.adapter.RvDrinkDetails;
import com.marketingknob.mercury.ui.HomeActivity;
import com.marketingknob.mercury.util.CommonUtil;
import com.marketingknob.mercury.util.DialogUtil;
import com.marketingknob.mercury.util.FloatingButton;
import com.marketingknob.mercury.util.TinyDB;
import com.oguzdev.circularfloatingactionmenu.library.FloatingActionButton;
import com.oguzdev.circularfloatingactionmenu.library.FloatingActionMenu;
import com.oguzdev.circularfloatingactionmenu.library.SubActionButton;

import java.util.ArrayList;
import java.util.List;

import ir.apend.slider.model.Slide;
import ir.apend.slider.ui.Slider;

/**
 * Created by Akshya on 23/10/2018.
 */

public class HomeFragment extends Fragment implements View.OnClickListener{

    Slider slider;
    RecyclerView rViewDrink,rViewDetails;
    SearchView searchView;
    AppCompatImageView ivLogOut;
    Context context;
    TinyDB tinyDB;
    LinearLayoutCompat llMain;

    RvDrinkCategory rvDrinkCategory;
    RvDrinkDetails rvDrinkDetails;

    FloatingActionButton leftCenterButton;
    FloatingActionMenu rightLowerMenu;

    public HomeFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_home, container, false);



        init(view);

        //create list of slides
        List<Slide> slideList = new ArrayList<>();
        slideList.add(new Slide(0,"http://cssslider.com/sliders/demo-20/data1/images/picjumbo.com_img_4635.jpg" , 0));
        slideList.add(new Slide(1,"http://cssslider.com/sliders/demo-12/data1/images/picjumbo.com_hnck1995.jpg" , 0));
        slideList.add(new Slide(2,"http://cssslider.com/sliders/demo-19/data1/images/picjumbo.com_hnck1588.jpg" , 0));
        slideList.add(new Slide(3,"http://wowslider.com/sliders/demo-18/data1/images/shanghai.jpg" , 0));

        //handle slider click listener
        slider.setItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //do what you want
            }
        });

        //add slides to slider
        slider.addSlides(slideList);

        return view;
    }

    void init(View view){

        context         = this.getActivity();
        slider          = view.findViewById(R.id.slider);

        rViewDrink      = view.findViewById(R.id.rv_drink_catg);
        rViewDetails    = view.findViewById(R.id.rv_drink_detail);
        searchView      = view.findViewById(R.id.search_view);
        ivLogOut        = view.findViewById(R.id.iv_logout);
        llMain          = view.findViewById(R.id.ll_main);

        tinyDB          = new TinyDB(context);
        ivLogOut.setOnClickListener(this);
        llMain.setOnClickListener(this);

        EditText searchEditText = (EditText) searchView.findViewById(android.support.v7.appcompat.R.id.search_src_text);
        searchEditText.setTextColor(getResources().getColor(R.color.red));
        searchEditText.setHintTextColor(getResources().getColor(R.color.red));
        searchEditText.setTextSize(getResources().getDimension(R.dimen.font_xxsmall));

        /*Drink Category RecyclerView Configure*/
        final LinearLayoutManager layoutManager = new LinearLayoutManager(context);
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        rViewDrink.setLayoutManager(layoutManager);
        rViewDrink.setItemAnimator(new DefaultItemAnimator());
        rvDrinkCategory = new RvDrinkCategory(context);
        rViewDrink.setAdapter(rvDrinkCategory);

        /*Drink Details RecyclerView Configure*/
        final LinearLayoutManager layoutManager1 = new LinearLayoutManager(context);
        layoutManager1.setOrientation(LinearLayoutManager.VERTICAL);
        rViewDetails.setLayoutManager(layoutManager1);
        rViewDetails.setItemAnimator(new DefaultItemAnimator());
        rvDrinkDetails = new RvDrinkDetails(context);
        rViewDetails.setAdapter(rvDrinkDetails);



        int redActionButtonSize = getResources().getDimensionPixelSize(R.dimen.pad_80dp);
        int redActionButtonMargin = getResources().getDimensionPixelSize(R.dimen.pad_60dp);
        int redActionButtonMarginSmall = getResources().getDimensionPixelSize(R.dimen.pad_15dp);
        ImageView fabIconStar = new ImageView(context);
        fabIconStar.setImageDrawable(getResources().getDrawable(R.drawable.chat));

        FloatingActionButton.LayoutParams starParams = new FloatingActionButton.LayoutParams(redActionButtonSize, redActionButtonSize);
        starParams.setMargins(redActionButtonMargin,
                redActionButtonMargin,
                redActionButtonMargin,
                redActionButtonMargin);
        fabIconStar.setLayoutParams(starParams);

        FloatingActionButton.LayoutParams fabIconStarParams = new FloatingActionButton.LayoutParams(redActionButtonSize, redActionButtonSize);
        fabIconStarParams.setMargins(redActionButtonMarginSmall,
                redActionButtonMarginSmall,
                redActionButtonMarginSmall,
                redActionButtonMargin);

         leftCenterButton = new FloatingActionButton.Builder(getActivity())
                .setContentView(fabIconStar, fabIconStarParams)
                .setPosition(FloatingActionButton.POSITION_BOTTOM_RIGHT)
                .setLayoutParams(starParams)
                .build();

        SubActionButton.Builder rLSubBuilder = new SubActionButton.Builder((Activity) context);
        ImageView rlIcon1 = new ImageView((Activity) context);
        ImageView rlIcon2 = new ImageView((Activity) context);
        ImageView rlIcon3 = new ImageView((Activity) context);

        rlIcon1.setImageDrawable(getResources().getDrawable(R.drawable.user_one));
        rlIcon2.setImageDrawable(getResources().getDrawable(R.drawable.user_two));
        rlIcon3.setImageDrawable(getResources().getDrawable(R.drawable.user_three));

        // Build the menu with default options: light theme, 90 degrees, 72dp radius.
         rightLowerMenu = new FloatingActionMenu.Builder((Activity) context)
                .addSubActionView(rLSubBuilder.setContentView(rlIcon1).build())
                .addSubActionView(rLSubBuilder.setContentView(rlIcon2).build())
                .addSubActionView(rLSubBuilder.setContentView(rlIcon3).build())
                .attachTo(leftCenterButton)
                .build();

        // Listen menu open and close events to animate the button content view
        rightLowerMenu.setStateChangeListener(new FloatingActionMenu.MenuStateChangeListener() {
            @Override
            public void onMenuOpened(FloatingActionMenu menu) {
                // Rotate the icon of rightLowerButton 45 degrees clockwise
                leftCenterButton.setRotation(0);
                PropertyValuesHolder pvhR = PropertyValuesHolder.ofFloat(View.ROTATION, 45);
                ObjectAnimator animation = ObjectAnimator.ofPropertyValuesHolder(leftCenterButton, pvhR);
                animation.start();
            }

            @Override
            public void onMenuClosed(FloatingActionMenu menu) {
                // Rotate the icon of rightLowerButton 45 degrees counter-clockwise
                leftCenterButton.setRotation(45);
                PropertyValuesHolder pvhR = PropertyValuesHolder.ofFloat(View.ROTATION, 0);
                ObjectAnimator animation = ObjectAnimator.ofPropertyValuesHolder(leftCenterButton, pvhR);
                animation.start();
            }
        });

        rlIcon1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, "Chat User One", Toast.LENGTH_SHORT).show();
            }
        });
        rlIcon2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, "Chat User Two", Toast.LENGTH_SHORT).show();
            }
        });
        rlIcon3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, "Chat User Three", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onClick(View view) {
        if (view==ivLogOut){
            DialogUtil.LogoutDialog(getActivity(),tinyDB);
        }else if (view==llMain){
            CommonUtil.hideKeyboard(getActivity());
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        leftCenterButton.detach();
        rightLowerMenu.close(false);
    }

}
