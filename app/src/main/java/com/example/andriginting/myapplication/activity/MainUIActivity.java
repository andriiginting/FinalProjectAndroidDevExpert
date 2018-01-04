package com.example.andriginting.myapplication.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.example.andriginting.myapplication.R;
import com.example.andriginting.myapplication.fragment.FavoriteFragment;
import com.example.andriginting.myapplication.fragment.HomeFragment;

import butterknife.ButterKnife;

public class MainUIActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    public static int CURRENT_TAG_NUMBER = 0;
    private static final String TAG_HOME = "home";
    public static String CURRENT_TAG = TAG_HOME;
    private static final String TAG_FAVORITE = "favorite";
    boolean shouldLoadHomeFragOnBackPress = true;

    Handler handler;
    DrawerLayout drawer;
    NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_ui);
        ButterKnife.bind(this);

        Toolbar toolbar = findViewById(R.id.toolbar);
        navigationView = findViewById(R.id.nav_view);

        drawer = findViewById(R.id.drawer_layout);
        handler = new Handler();

        setSupportActionBar(toolbar);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);

        if (savedInstanceState == null) {
            CURRENT_TAG_NUMBER = 0;
            CURRENT_TAG = TAG_HOME;
            loadFragment();
        }

    }

    private void selectNavMenu() {
        navigationView.getMenu().getItem(CURRENT_TAG_NUMBER).setChecked(true);
    }

    private void loadFragment() {
        selectNavMenu();
        if (getSupportFragmentManager().findFragmentByTag(CURRENT_TAG) != null) {
            drawer.closeDrawers();
            return;
        }

        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                Fragment fragment = goToFragment();
                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                fragmentTransaction.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out);
                fragmentTransaction.replace(R.id.frame_container, fragment, CURRENT_TAG);
                fragmentTransaction.commitAllowingStateLoss();
            }
        };
        if (runnable != null) {
            handler.post(runnable);
        }
        drawer.closeDrawers();
        invalidateOptionsMenu();
    }

    private Fragment goToFragment() {
        switch (CURRENT_TAG_NUMBER) {
            case 0:
                HomeFragment homeFragment = new HomeFragment();
                return homeFragment;
            case 1:
                FavoriteFragment favoriteFragment = new FavoriteFragment();
                return favoriteFragment;
            default:
                return new HomeFragment();
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawers();
            return;
        }
        if (shouldLoadHomeFragOnBackPress) {
            if (CURRENT_TAG_NUMBER != 0) {
                CURRENT_TAG_NUMBER = 0;
                CURRENT_TAG = TAG_HOME;
                loadFragment();
                return;
            }
        }
        super.onBackPressed();

    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        Fragment fragment = null;
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        switch (id) {
            case R.id.nav_upcoming:
                CURRENT_TAG_NUMBER= 0;
                CURRENT_TAG = TAG_HOME;
                break;
            case R.id.nav_favorit:
                CURRENT_TAG_NUMBER = 1;
                CURRENT_TAG = TAG_FAVORITE;
                break;
            case R.id.nav_first_project:
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                break;
            case R.id.nav_setting:
                Intent intentSetting = new Intent(this,SettingActivity.class);
                //intentSetting.setClassName("com.android.settings", "com.android.settings.LanguageSettings"),Intent.ACTION_MAIN;
                startActivity(intentSetting);
                break;
            default:
                CURRENT_TAG_NUMBER = 0;
        }

        if (item.isChecked()){
            item.setChecked(true);
        }else{
            item.setChecked(true);
        }
        item.setChecked(true);
        loadFragment();
        if (fragment != null) {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.frame_container, fragment);
            ft.commit();
        } else {
            loadFragment();
        }
        item.setChecked(true);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

}
