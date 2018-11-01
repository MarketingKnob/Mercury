package com.marketingknob.mercury.util;

import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.marketingknob.mercury.R;
import com.oguzdev.circularfloatingactionmenu.library.FloatingActionButton;
import com.oguzdev.circularfloatingactionmenu.library.FloatingActionMenu;
import com.oguzdev.circularfloatingactionmenu.library.SubActionButton;

public class FloatingButton {

    public static FloatingActionButton leftCenterButton;
    public static FloatingActionMenu rightLowerMenu;

    public static void floatingWorking(final Context context, Activity activity) {
        int floatButtonLayoutSize = context.getResources().getDimensionPixelSize(R.dimen.float_btn_layout_dimension);
        int floatButtonLayoutMargin = context.getResources().getDimensionPixelOffset(R.dimen.pad_48dp);
        int threeDp = context.getResources().getDimensionPixelOffset(R.dimen.pad_3dp);
        int redActionButtonContentMargin = context.getResources().getDimensionPixelSize(R.dimen.float_btn_icon_dimensions);
        int floatIconSize = context.getResources().getDimensionPixelSize(R.dimen.float_btn_icon_size);

        ImageView fabIconStar = new ImageView(context);
        fabIconStar.setImageDrawable(context.getResources().getDrawable(R.drawable.chat));

        FloatingActionButton.LayoutParams starParams = new FloatingActionButton.LayoutParams(floatButtonLayoutSize, floatButtonLayoutSize);
        starParams.setMargins(floatButtonLayoutMargin,
                floatButtonLayoutMargin,
                threeDp,
                floatButtonLayoutMargin);
        fabIconStar.setLayoutParams(starParams);

        FloatingActionButton.LayoutParams fabIconStarParams = new FloatingActionButton.LayoutParams(floatIconSize, floatIconSize);
        fabIconStarParams.setMargins(redActionButtonContentMargin,
                redActionButtonContentMargin,
                redActionButtonContentMargin,
                redActionButtonContentMargin);


        leftCenterButton = new FloatingActionButton.Builder(activity)
                .setContentView(fabIconStar, fabIconStarParams)
                .setPosition(FloatingActionButton.POSITION_BOTTOM_RIGHT)
                .setLayoutParams(starParams)
                .setBackgroundDrawable(R.drawable.transparent)
                .build();

        SubActionButton.Builder rLSubBuilder = new SubActionButton.Builder((Activity) context);
        ImageView rlIcon1 = new ImageView((Activity) context);
        ImageView rlIcon2 = new ImageView((Activity) context);
        ImageView rlIcon3 = new ImageView((Activity) context);

        rlIcon1.setImageDrawable(context.getResources().getDrawable(R.drawable.user_one));
        rlIcon2.setImageDrawable(context.getResources().getDrawable(R.drawable.user_two));
        rlIcon3.setImageDrawable(context.getResources().getDrawable(R.drawable.user_three));

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
}
