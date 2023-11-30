package com.example.duan1_13.Fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.duan1_13.Adapter.Top_Adapter;
import com.example.duan1_13.Fragment.Tab.Mypager;
import com.example.duan1_13.Model.Top;
import com.example.duan1_13.R;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.ArrayList;

public class Top_Fragment extends Fragment {


    private ListView lv;
    ArrayList<Top> list;
    Top_Adapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_top_,
                container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        String[] titles = {"Nước Hoa", "Nhân viên"};
        ViewPager2 viewPager = (ViewPager2) view.findViewById(R.id.my_view_pager);
        viewPager.setAdapter(new Mypager(getActivity()));
        TabLayout tabLayout = (TabLayout) view.findViewById(R.id.my_tabs);
        new TabLayoutMediator(tabLayout, viewPager,
                (tab, position) -> tab.setText(titles[position]))
                .attach();
    }
}