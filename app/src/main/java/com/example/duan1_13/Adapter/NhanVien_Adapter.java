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

import com.example.duan1_13.Fragment.NhanVien_Fragment;
import com.example.duan1_13.Model.NhanVien;
import com.example.duan1_13.R;

import java.util.ArrayList;


public class NhanVien_Adapter extends ArrayAdapter<NhanVien> {
    NhanVien_Fragment fragment;
    private ArrayList<NhanVien> list;
    private Context context;
    // khai bao dialog
    TextView tvMa, tvTen, tvEmail;
    ImageView iv;
    Button bt;

    public NhanVien_Adapter(Context _context, NhanVien_Fragment _fragment, ArrayList<NhanVien> _list) {
        super(_context, 0, _list);
        this.context = _context;
        this.fragment = _fragment;
        this.list = _list;
    }

    //
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;

        if (v == null) {
            LayoutInflater inf = (LayoutInflater)
                    context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inf.inflate(R.layout.items_nhanvien, null);
        }
        final NhanVien nv = list.get(position);
        if (nv != null) {
            tvMa = v.findViewById(R.id.items_tv_maUser);
            tvTen = v.findViewById(R.id.items_tv_hoTen);
            tvEmail = v.findViewById(R.id.items_tv_email);
            iv = v.findViewById(R.id.items_iv_img);

            tvMa.setText(" " + nv.getMaNV());
            tvTen.setText(" " + nv.getHoTen());
            tvEmail.setText(" " + nv.getEmail());

            //nếu mà file có ảnh ms lấy
            if (nv.getHinhAnh() != null) {
                byte[] _img = nv.getHinhAnh();
                Bitmap bitmap = BitmapFactory.decodeByteArray(_img, 0, _img.length);
                iv.setImageBitmap(bitmap);
            }
        }
        return v;
    }
    //
}
