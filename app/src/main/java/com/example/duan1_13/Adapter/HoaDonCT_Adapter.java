package com.example.duan1_13.Adapter;

import android.graphics.BitmapFactory;
import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.duan1_13.DAO.Kho_DAO;
import com.example.duan1_13.DAO.NuocHoa_DAO;
import com.example.duan1_13.Fragment.HoaDon_Fragment;
import com.example.duan1_13.Model.HoaDonCT;
import com.example.duan1_13.R;

import java.util.ArrayList;

public class HoaDonCT_Adapter extends ArrayAdapter<HoaDonCT> {
    private Context context;
    private ArrayList<HoaDonCT> list;
    HoaDon_Fragment fragment;
    ImageView iv;
    TextView tvTen, tvSl, tvTien;
    Button bt;


    public HoaDonCT_Adapter(Context context, HoaDon_Fragment fragment, ArrayList<HoaDonCT> list) {
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
            v = inf.inflate(R.layout.items_hoa_don_chi_tiet, null);
        }

        tvTen = v.findViewById(R.id.items_hdct_tv_tennh);
        tvSl = v.findViewById(R.id.items_hdct_tv_sl);
        tvTien = v.findViewById(R.id.items_hdct_tv_giaTien);
        bt = v.findViewById(R.id.items_hdct_bt);
        iv = v.findViewById(R.id.items_hdct_iv);

        final HoaDonCT hdct = list.get(position);
        if (hdct != null) {

            NuocHoa_DAO nhD = new NuocHoa_DAO(context);

            String tennh = nhD.getID(String.valueOf(hdct.getMaNH())).getTenNH();
            Log.d("HDCTADAPTER", "getView: " + tennh + hdct.getMaNH());
            String tien = String.format("%,.0f", hdct.getThanhTien());
            tvTen.setText("" + tennh);
            tvSl.setText("SL: " + hdct.getSoLuong());
            tvTien.setText("" + tien);
            byte[] _img = nhD.getID(String.valueOf(hdct.getMaNH())).getImg();
            if (_img != null) { //nếu mà file có ảnh ms lấy
                Bitmap bitmap = BitmapFactory.decodeByteArray(_img, 0, _img.length);
                iv.setImageBitmap(bitmap);
            }
        }

        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Kho_DAO kD = new Kho_DAO(context);
                int sl = kD.getSLConLai(hdct.getMaNH()) + hdct.getSoLuong();
                fragment.xoa_hdct(String.valueOf(hdct.getMaCT()), hdct.getMaHD(), sl, hdct.getMaNH());
            }
        });
        return v;
    }

}
