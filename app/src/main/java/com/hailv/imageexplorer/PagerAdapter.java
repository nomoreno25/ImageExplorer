package com.hailv.imageexplorer;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.hailv.imageexplorer.Fragment.AllFragment;
import com.hailv.imageexplorer.Fragment.FolderFragment;
import com.hailv.imageexplorer.Fragment.TimeFragment;

public class PagerAdapter extends FragmentStatePagerAdapter {

    public PagerAdapter(FragmentManager fm){
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;
        switch (position){
            case 0: fragment = new AllFragment();
                break;
            case 1: fragment = new FolderFragment();
                break;
            case 2: fragment = new TimeFragment();
                break;
        }
        return fragment;
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        String title = "";
        switch (position){
            case 0: title = "TẤT CẢ";
                break;
            case 1: title = "THƯ MỤC";
                break;
            case 2: title = "THỜI GIAN";
                break;
        }
        return title;
    }
}
