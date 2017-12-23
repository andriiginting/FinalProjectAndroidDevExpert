package com.example.andriginting.myapplication.adapter;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.andriginting.myapplication.fragment.NowPlayingFragment;
import com.example.andriginting.myapplication.fragment.UpComingFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Andri Ginting on 12/22/2017.
 */

public class MoviePagerAdapter extends FragmentPagerAdapter {
    String judul[] = {"Up Coming", "Now Playing"};
    private final List<Fragment> fragmentList = new ArrayList<>();

    public MoviePagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment =null;
        switch (position) {
            case 0:
                fragment = new UpComingFragment();
                return fragment;
            case 1:
                fragment = new NowPlayingFragment();
                return fragment;
        }

        return fragment;
    }

    @Override
    public int getCount() {
        return judul.length;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return judul[position];
    }

    public void addFrag(Fragment fragment) {
        fragmentList.add(fragment);
    }

}
