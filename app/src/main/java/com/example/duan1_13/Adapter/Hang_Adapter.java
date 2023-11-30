package com.example.duan1_13.Adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.duan1_13.Fragment.Hang_Fragment;
import com.example.duan1_13.Model.Hang;
import com.example.duan1_13.R;

import java.util.ArrayList;

public class Hang_Adapter extends ArrayAdapter<Hang> {

    private Context context;
    private ArrayList<Hang> list;
    Hang_Fragment fragment;

    TextView tvTen;
    ImageView iv;
    Button bt;

    public Hang_Adapter(Context _context, Hang_Fragment _fragment, ArrayList<Hang> _list) {
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
            v = inf.inflate(R.layout.items_hang, null);
        }

        final Hang h = list.get(position);
        if (h != null) {
            tvTen = v.findViewById(R.id.items_hang_tv_ten);
            iv = v.findViewById(R.id.items_hang_iv);

            tvTen.setText(h.getTenH() + "");
            Log.d("Tên:    ", "" + h.getTenH());

            if (h.getImg() != null) { //nếu mà file có ảnh ms lấy
                byte[] _img = h.getImg();
                Bitmap bitmap = BitmapFactory.decodeByteArray(_img, 0, _img.length);
                iv.setImageBitmap(bitmap);
            }
        }
        return v;
    }
}
