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

    public static void floatingWorking(final Context context, Activity activity) {
        final ImageView fabIconNew = new ImageView(context);
        fabIconNew.setImageDrawable(context.getResources().getDrawable(R.drawable.chat));
        final FloatingActionButton rightLowerButton = new FloatingActionButton.Builder(activity)
                .setContentView(fabIconNew)
                .build();

        SubActionButton.Builder rLSubBuilder = new SubActionButton.Builder(activity);
        ImageView rlIcon1 = new ImageView(context);
        ImageView rlIcon2 = new ImageView(context);
        ImageView rlIcon3 = new ImageView(context);

        rlIcon1.setImageDrawable(context.getResources().getDrawable(R.drawable.user_one));
        rlIcon2.setImageDrawable(context.getResources().getDrawable(R.drawable.user_two));
        rlIcon3.setImageDrawable(context.getResources().getDrawable(R.drawable.user_three));

        // Build the menu with default options: light theme, 90 degrees, 72dp radius.
        final FloatingActionMenu rightLowerMenu = new FloatingActionMenu.Builder(activity)
                .addSubActionView(rLSubBuilder.setContentView(rlIcon1).build())
                .addSubActionView(rLSubBuilder.setContentView(rlIcon2).build())
                .addSubActionView(rLSubBuilder.setContentView(rlIcon3).build())
                .attachTo(rightLowerButton)
                .build();

        // Listen menu open and close events to animate the button content view
        rightLowerMenu.setStateChangeListener(new FloatingActionMenu.MenuStateChangeListener() {
            @Override
            public void onMenuOpened(FloatingActionMenu menu) {
                // Rotate the icon of rightLowerButton 45 degrees clockwise
                fabIconNew.setRotation(0);
                PropertyValuesHolder pvhR = PropertyValuesHolder.ofFloat(View.ROTATION, 45);
                ObjectAnimator animation = ObjectAnimator.ofPropertyValuesHolder(fabIconNew, pvhR);
                animation.start();
            }

            @Override
            public void onMenuClosed(FloatingActionMenu menu) {
                // Rotate the icon of rightLowerButton 45 degrees counter-clockwise
                fabIconNew.setRotation(45);
                PropertyValuesHolder pvhR = PropertyValuesHolder.ofFloat(View.ROTATION, 0);
                ObjectAnimator animation = ObjectAnimator.ofPropertyValuesHolder(fabIconNew, pvhR);
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
