package com.example.duan1_13.Fragment.Tab;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.fragment.app.Fragment;

import com.example.duan1_13.Adapter.Top_Adapter;
import com.example.duan1_13.DAO.ThongKe_DAO;
import com.example.duan1_13.Model.Top;
import com.example.duan1_13.R;

import java.util.ArrayList;

public class Top10 extends Fragment {
    private ListView lv;
    ArrayList<Top> list;

    Top_Adapter adapter;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.tab_top10,
                container, false);
        lv = view.findViewById(R.id.tk_top_lv);
        ThongKe_DAO tkD = new ThongKe_DAO(getActivity());
        list = (ArrayList<Top>) tkD.getTop();
        adapter = new Top_Adapter(getActivity(), null, list);
        lv.setAdapter(adapter);


        return view;
    }
}
