package com.example.duan1_13.Fragment;

import android.app.DatePickerDialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.duan1_13.DAO.NuocHoa_DAO;
import com.example.duan1_13.DAO.ThongKe_DAO;
import com.example.duan1_13.Model.NuocHoa;
import com.example.duan1_13.Model.PieChart;
import com.example.duan1_13.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

public class DoanhThu_Fragment extends Fragment {

    Button btTo, btFrom, btDt;
    EditText edTo, edFrom;
    TextView tvDoanhThu;
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    int mYear, mMonth, mDay;
    ThongKe_DAO tkD;
    List<PieChart> list;
    NuocHoa_DAO nhD;
    NuocHoa nh;

    String from, to;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_doanh_thu_,
                container, false);

        edTo = v.findViewById(R.id.nh_ed_to);
        edFrom = v.findViewById(R.id.nh_ed_from);
        tvDoanhThu = v.findViewById(R.id.nh_tv_dt);
        btTo = v.findViewById(R.id.nh_bt_to);
        btFrom = v.findViewById(R.id.nh_bt_from);
        btDt = v.findViewById(R.id.nh_bt_dt);

        edTo.setEnabled(false);
        edFrom.setEnabled(false);

        tkD = new ThongKe_DAO(getActivity());
        nhD = new NuocHoa_DAO(getActivity());
        list = tkD.getChart();

        btFrom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog d = new DatePickerDialog(
                        getActivity(), 0, mDateDenNgay, mYear, mMonth, mDay);
                d.show();
            }
        });
        btTo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog d = new DatePickerDialog(
                        getActivity(), 0, mDateTuNgay, mYear, mMonth, mDay);
                d.show();
            }
        });
        btDt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                to = edTo.getText().toString();
                from = edFrom.getText().toString();
                if (edFrom.getText().length() == 0){
                    Toast.makeText(getActivity(), "Không được để trống ngày bắt đầu ", Toast.LENGTH_SHORT).show();
                }else if (edTo.getText().length() == 0){
                    Toast.makeText(getActivity(), "Không được để trống  ngày kêt thúc", Toast.LENGTH_SHORT).show();
                }else {
                    try {
                        Date c = sdf.parse(from);
                        Date b = sdf.parse(to);
                        if (c.after(b)){
                            Toast.makeText(getActivity(), "Ngày bắt đầu không được lớn hơn ngày kêt thúc", Toast.LENGTH_SHORT).show();
                        }else {
                            ThongKe_DAO tkD = new ThongKe_DAO(getActivity());
                            int a = tkD.getDoanhThu(from, to);
                            tvDoanhThu.setText(""+ a + " VNĐ");
                        }
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        return v;
    }

    DatePickerDialog.OnDateSetListener mDateTuNgay = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfYear) {
            mYear = year;
            mMonth = monthOfYear;
            mDay = dayOfYear;
            GregorianCalendar c = new GregorianCalendar(mYear, mMonth, mDay);
            edTo.setText(sdf.format(c.getTime()));
        }
    };

    DatePickerDialog.OnDateSetListener mDateDenNgay = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfYear) {
            mYear = year;
            mMonth = monthOfYear;
            mDay = dayOfYear;
            GregorianCalendar c = new GregorianCalendar(mYear, mMonth, mDay);
            edFrom.setText(sdf.format(c.getTime()));
        }
    };


}