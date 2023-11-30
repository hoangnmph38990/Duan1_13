package com.example.duan1_13.Fragment.Tab;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class Mypager extends FragmentStateAdapter {
    final int Tab_count = 2;
    public Mypager(FragmentActivity fa){
        super(fa);
    }


    @Override
    public Fragment createFragment(int position) {
        Fragment fragment = null;
        Class fragmentClass = null;
        switch (position){
            case 0:
                fragmentClass = Top10.class;
                break;
            case 1:
                fragmentClass = Top10nh.class;
                break;
            default:
                break;
        }
        try {
            fragment = (Fragment) fragmentClass.newInstance();
        } catch (Exception e){}
        return fragment;
    }

    @Override
    public int getItemCount() {
        return Tab_count;
    }

}
