package com.example.duan1_13.Adapter.SpAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.duan1_13.Model.NuocHoa;
import com.example.duan1_13.R;

import java.util.ArrayList;

public class Nuochoa_Spiner_Adapter extends ArrayAdapter<NuocHoa> {

    private Context context;
    private ArrayList<NuocHoa> list;
    private TextView tv;

    public Nuochoa_Spiner_Adapter(Context context, ArrayList<NuocHoa> list){
        super(context, 0, list);
        this.context = context;
        this.list = list;
    }

    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View v = convertView;
        if (v == null){
            LayoutInflater inf=(LayoutInflater)
                    context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inf.inflate(R.layout.items_spinner_nuoc_hoa, null);

            final NuocHoa nh = list.get(position);
            if (nh != null){
                tv = v.findViewById(R.id.sp_nh_tv);

                tv.setText("Tên Nước Hoa: "+nh.getTenNH());
            }
        }
        return v;
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
        if (v == null){
            LayoutInflater inf=(LayoutInflater)
                    context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inf.inflate(R.layout.items_spinner_nuoc_hoa, null);

            final NuocHoa nh = list.get(position);
            if (nh != null){
                tv = v.findViewById(R.id.sp_nh_tv);
                tv.setText("Tên Nước Hoa: "+nh.getTenNH());
            }
        }
        return v;
    }
}
