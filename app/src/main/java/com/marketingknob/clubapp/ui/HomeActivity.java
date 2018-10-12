package com.marketingknob.clubapp.ui;

import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.LinearLayoutCompat;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;

import com.marketingknob.clubapp.R;
import com.marketingknob.clubapp.ui.fragments.FirstFragment;
import com.marketingknob.clubapp.ui.fragments.HomeFragment;
import com.marketingknob.clubapp.ui.fragments.SecondFragment;
import com.marketingknob.clubapp.ui.fragments.ThirdFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Akshya on 10/10/2018.
 */

public class HomeActivity extends AppCompatActivity implements View.OnClickListener{

    @BindView(R.id.lay_toolbar) View layToolbar;
    @BindView(R.id.iv_drawer) AppCompatImageView ivDrawer;
    @BindView(R.id.drawer_layout) DrawerLayout mDrawer;
    @BindView(R.id.nvView) NavigationView nvDrawer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        init();

    }

    void init(){

        ButterKnife.bind(this);
        setupDrawerContent(nvDrawer);
        ivDrawer.setOnClickListener(this);

        View headerLayout = nvDrawer.getHeaderView(0);
//        ImageView ivHeaderPhoto = headerLayout.findViewById(R.id.imageView);


        FragmentTransaction tx = getSupportFragmentManager().beginTransaction();
        tx.replace(R.id.flContent, new HomeFragment());
        tx.commit();

    }

        private void setupDrawerContent (NavigationView navigationView){
            navigationView.setNavigationItemSelectedListener(
                    new NavigationView.OnNavigationItemSelectedListener() {
                        @Override
                        public boolean onNavigationItemSelected(MenuItem menuItem) {
                            selectDrawerItem(menuItem);
                            return true;
                        }
                    });
        }

        public void selectDrawerItem (MenuItem menuItem){
            Fragment fragment = null;
            Class fragmentClass;
            switch (menuItem.getItemId()) {
                case R.id.nav_default_fragment:
                    fragmentClass = HomeFragment.class;
                    break;
                    case R.id.nav_first_fragment:
                    fragmentClass = FirstFragment.class;
                    break;
                case R.id.nav_second_fragment:
                    fragmentClass = SecondFragment.class;
                    break;
                case R.id.nav_third_fragment:
                    fragmentClass = ThirdFragment.class;
                    break;
                default:
                    fragmentClass = HomeFragment.class;
            }

            try {
                fragment = (Fragment) fragmentClass.newInstance();
            } catch (Exception e) {
                e.printStackTrace();
            }

            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.flContent, fragment).commit();
            menuItem.setChecked(true);
            setTitle(menuItem.getTitle());
            mDrawer.closeDrawers();
        }

    @Override
    public void onClick(View view) {
        if (view==ivDrawer){
            if (mDrawer.isDrawerOpen(Gravity.LEFT)) {
                mDrawer.closeDrawer(Gravity.LEFT);
            } else {
                mDrawer.openDrawer(Gravity.LEFT);
            }
        }
    }
}