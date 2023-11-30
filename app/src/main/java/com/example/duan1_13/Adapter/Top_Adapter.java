package com.example.duan1_13.Adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.duan1_13.DAO.NuocHoa_DAO;
import com.example.duan1_13.Fragment.Top_Fragment;
import com.example.duan1_13.Model.NuocHoa;
import com.example.duan1_13.Model.Top;
import com.example.duan1_13.R;

import java.util.ArrayList;

public class Top_Adapter extends ArrayAdapter<Top> {
    private Context context;
    Top_Fragment fragment;
    private ArrayList<Top> list;
    TextView tvTenD, tvS;
    ImageView iv;

    public Top_Adapter(Context context, Top_Fragment fragment, ArrayList<Top> lists) {
        super(context, 0, lists);
        this.context = context;
        this.fragment = fragment;
        this.list = lists;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
        if (v == null) {
            LayoutInflater inf = (LayoutInflater)
                    context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inf.inflate(R.layout.items_top, null);
        }

        final Top top = list.get(position);
        if (top != null) {
            tvTenD = v.findViewById(R.id.items_top_tv_ten);
            tvS = v.findViewById(R.id.items_top_tv_sl);
            iv = v.findViewById(R.id.items_top_iv);

            NuocHoa_DAO nhD = new NuocHoa_DAO(context);
            int sl = top.getSoLuong();
            tvS.setText("số lượng: " + sl);
            Log.d("TAG", "getView: " + top.getSoLuong());
            NuocHoa nuocHoa = new NuocHoa();
            try {
                nuocHoa = nhD.getID(String.valueOf(top.getMaNH()));
            } catch (Exception e) {

            }
            tvTenD.setText(nuocHoa.getTenNH() + "");


            if (nuocHoa.getImg() != null) { //nếu mà file có ảnh ms lấy
                byte[] _img = nuocHoa.getImg();
                Bitmap bitmap = BitmapFactory.decodeByteArray(_img, 0, _img.length);
                iv.setImageBitmap(bitmap);
            }
        }
        return v;
    }
}
