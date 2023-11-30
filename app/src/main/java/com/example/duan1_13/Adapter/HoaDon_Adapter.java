package com.example.duan1_13.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.example.duan1_13.DAO.NhanVien_DAO;
import com.example.duan1_13.Fragment.HoaDon_Fragment;
import com.example.duan1_13.Model.HoaDon;
import com.example.duan1_13.Model.NhanVien;
import com.example.duan1_13.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class HoaDon_Adapter extends ArrayAdapter<HoaDon> {
    private Context context;
    private ArrayList<HoaDon> list;
    HoaDon_Fragment fragment;
    SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
    private TextView tvNV, tvKH, tvMa, tvGia, tvNgay;
    Button bt;

    public HoaDon_Adapter(Context context, HoaDon_Fragment fragment, ArrayList<HoaDon> list) {
        super(context, 0, list);
        this.context = context;
        this.fragment = fragment;
        this.list = list;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;

        if (v == null) {
            LayoutInflater inf = (LayoutInflater)
                    context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inf.inflate(R.layout.items_hoa_don, null);
        }

        tvNV = v.findViewById(R.id.items_hd_tv_nv);
        tvKH = v.findViewById(R.id.items_hd_tv_kh);
        tvGia = v.findViewById(R.id.items_hd_tv_gia);
        tvNgay = v.findViewById(R.id.items_hd_tv_ngay);


        final HoaDon hd = list.get(position);
        if (hd != null) {
            NhanVien_DAO nvD = new NhanVien_DAO(context);
            NhanVien nv = nvD.getID(hd.getMaNV());
            tvNV.setText("" + nv.getHoTen());
            tvKH.setText("KH: " + hd.getTenKH());
            tvNgay.setText("" + sdf.format(hd.getNgay()));
            tvGia.setText(String.format("%,.0f", hd.getTienTong()) + "$");
        }

        return v;
    }

}
