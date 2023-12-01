package com.example.duan1_13.Adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.duan1_13.DAO.Hang_DAO;
import com.example.duan1_13.Fragment.NuocHoa_Fragment;
import com.example.duan1_13.Model.Hang;
import com.example.duan1_13.Model.NuocHoa;
import com.example.duan1_13.R;

import java.util.ArrayList;

public class NuocHoa_Adapter extends ArrayAdapter<NuocHoa> {

    private Context context;
    private ArrayList<NuocHoa> list;
    NuocHoa_Fragment fragment;

    TextView tvTen, tvHang, tvGia, tvSl;
    ImageView iv;
    Button bt;

    public NuocHoa_Adapter(Context _context, NuocHoa_Fragment _fragment, ArrayList<NuocHoa> _list) {
        super(_context, 0, _list);
        this.context = _context;
        this.fragment = _fragment;
        this.list = _list;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View v = convertView;
        if (v == null) {
            LayoutInflater inf = (LayoutInflater)
                    context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inf.inflate(R.layout.items_nuoc_hoa, null);
        }

        final NuocHoa nh = list.get(position);
        if (nh != null) {
            tvTen = v.findViewById(R.id.items_nh_tv_ten);
            tvHang = v.findViewById(R.id.items_nh_tv_hang);
            tvGia = v.findViewById(R.id.items_nh_tv_gia);
            tvSl = v.findViewById(R.id.items_nh_tv_sl);
            iv = v.findViewById(R.id.items_nh_iv);

            tvTen.setText(nh.getTenNH());
            Hang_DAO hD = new Hang_DAO(context);
            Hang hnh = hD.getID(String.valueOf(nh.getMaH_NH()));

            tvGia.setText(String.format("%,.0f", nh.getGia()) + "");
            tvSl.setText("SL: " + nh.getSoLuong());

            if (hnh != null) {
                tvTen.setText(hnh.getTenH());
            } else {
                tvTen.setText("Nước Hoa");
            }

            if (nh.getImg() != null) { //nếu mà file có ảnh ms lấy
                byte[] _img = nh.getImg();
                Bitmap bitmap = BitmapFactory.decodeByteArray(_img, 0, _img.length);
                iv.setImageBitmap(bitmap);
            }
        }
        return v;
    }
}
