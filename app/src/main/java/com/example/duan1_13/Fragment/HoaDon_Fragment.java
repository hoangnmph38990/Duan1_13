package com.example.duan1_13.Fragment;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.duan1_13.Adapter.Hang_Adapter;
import com.example.duan1_13.Adapter.HoaDonCT_Adapter;
import com.example.duan1_13.Adapter.HoaDon_Adapter;
import com.example.duan1_13.Adapter.SpAdapter.Nuochoa_Spiner_Adapter;
import com.example.duan1_13.DAO.Hang_DAO;
import com.example.duan1_13.DAO.HoaDonCt_DAO;
import com.example.duan1_13.DAO.HoaDon_DAO;
import com.example.duan1_13.DAO.Kho_DAO;
import com.example.duan1_13.DAO.NhanVien_DAO;
import com.example.duan1_13.DAO.NuocHoa_DAO;
import com.example.duan1_13.MainActivity;
import com.example.duan1_13.Model.Hang;
import com.example.duan1_13.Model.HoaDon;
import com.example.duan1_13.Model.HoaDonCT;
import com.example.duan1_13.Model.NhanVien;
import com.example.duan1_13.Model.NuocHoa;
import com.example.duan1_13.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class HoaDon_Fragment extends Fragment implements View.OnClickListener {

    private ListView lv, lv2;
    private FloatingActionButton fab;
    private EditText edNV, edKH;
    private TextView tvNgay;
    private Button btSave, btReset;
    // dialog hóa đơn chi tiết
    private Spinner sp;
    private EditText edSl;
    private Button btThem, btLuu;
    private String user1 = "";
    private MainActivity mainActivity;
    ArrayList<NuocHoa> listNH;

    ArrayList<HoaDonCT> listCt;
    HoaDonCT hdct;
    HoaDonCt_DAO hdctD;
    HoaDonCT_Adapter hdctA;

    HoaDon_DAO hdD;
    HoaDon hd;
    HoaDon_Adapter hdA;
    private ArrayList<HoaDon> list;
    Dialog dialog;
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    String maNV, TenNH;
    int MaNH, number, MaHD;
    Double giaNH;

    NhanVien_DAO nvD;
    Context context2;

    int mYear, mMonth, mDay;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_hoa_don_,
                container, false);

        lv = v.findViewById(R.id.hd_lv);
        fab = v.findViewById(R.id.hd_fab);
        mainActivity = (MainActivity) getActivity();
        maNV = mainActivity.getUser();

        hdD = new HoaDon_DAO(getActivity());
        capNhatLv();
        context2 = getActivity();

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // new save
                openDialog(getActivity(), 0);
            }
        });
        lv.setOnItemLongClickListener((adapterView, view, position, id) -> {
            hd = list.get(position);
            MaHD = list.get(position).getMaHD();
            setMaHD(MaHD);
            Log.d("", "onCreateView: " + MaHD);
//            openDialog(getActivity(), 1);
            //
            final PopupMenu menu = new PopupMenu(getActivity(), view);
            menu.getMenuInflater().inflate(R.menu.menu_popup, menu.getMenu());
            menu.getMenu().findItem(R.id.menu_xoaHd).setVisible(false);
            if (maNV.equals(list.get(position).getMaNV()) || maNV.equals("admin")) {
                menu.getMenu().findItem(R.id.menu_xoaHd).setVisible(true);
            }
            menu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem menuItem) {
                    int it = menuItem.getItemId();
                    if (it == R.id.menu_hd) {
                        openDialog(getActivity(), 1);
                    } else if (it == R.id.menu_hdct) {
                        openDialog_hdct(getActivity(), getMaHD());
                    } else if (it == R.id.menu_xoaHd) {
                        xoa_hd(String.valueOf(getMaHD()));
                    } else
                        return onMenuItemClick(menuItem);
                    return true;
                }
            });
            menu.show();
            return false;
        });

        return v;
    }

    private void setMaHD(int MaHD) {
        this.MaHD = MaHD;
    }

    private int getMaHD() {
        return MaHD;
    }


    @Override
    public void onClick(View v) {
        v.post(new Runnable() {
            @Override
            public void run() {
                showPopupMenu(v);
            }
        });
    }

    private void showPopupMenu(View view) {

        PopupMenu popup = new PopupMenu(getActivity(), view);

        popup.getMenuInflater().inflate(R.menu.menu_popup, popup.getMenu());

        popup.show();
    }

    void capNhatLv() {
        list = (ArrayList<HoaDon>) hdD.getAll();
        hdA = new HoaDon_Adapter(getActivity(), this, list);
        lv.setAdapter(hdA);
    }

    protected void openDialog(final Context context, final int type) {


        dialog = new Dialog(context);
        dialog.setContentView(R.layout.diablog_hoa_don);

        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        int width = (int) (getResources().getDisplayMetrics().widthPixels * 0.90);
        dialog.getWindow().setLayout(width, ViewGroup.LayoutParams.WRAP_CONTENT);

        edNV = dialog.findViewById(R.id.hd_diag_ed_nv);
        edKH = dialog.findViewById(R.id.hd_diag_ed_kh);
        tvNgay = dialog.findViewById(R.id.hd_diag_tv_day);
        btSave = dialog.findViewById(R.id.hd_diag_bt_save);
        btReset = dialog.findViewById(R.id.hd_diag_bt_reset);

        hdctD = new HoaDonCt_DAO(context);

        nvD = new NhanVien_DAO(context);

        NhanVien nv = nvD.getID(maNV);
        edNV.setText(nv.getHoTen());
        edNV.setEnabled(false);

        tvNgay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog d = new DatePickerDialog(
                        getActivity(), 0, mDate, mYear, mMonth, mDay);
                d.show();
            }

            DatePickerDialog.OnDateSetListener mDate = new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfYear) {
                    mYear = year;
                    mMonth = monthOfYear;
                    mDay = dayOfYear;
                    GregorianCalendar c = new GregorianCalendar(mYear, mMonth, mDay);
                    tvNgay.setText(sdf.format(c.getTime()));
                }
            };
        });


        if (type != 0) {
            edKH.setText(String.valueOf(hd.getTenKH()));
            tvNgay.setText(sdf.format(hd.getNgay()));

        }


        btReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                edKH.setText("");
                tvNgay.setText("");
            }
        });

        btSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (validate() > 0) {
                    hd = new HoaDon();
                    hd.setMaNV(nv.getMaNV());
                    hd.setTienTong(0.00);
                    hd.setTenKH(edKH.getText().toString());
                    try {
                        hd.setNgay(sdf.parse(tvNgay.getText().toString()));
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }

                    if (type == 0) {
                        MaHD = (int) hdD.insert(hd);
                        Log.d("", "onClick: " + MaHD);
                        if (MaHD > 0) {

                            Toast.makeText(context, "Thêm thành công", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(context, "Thêm thất bại", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        hd.setMaHD(MaHD);
                        int a = hdctD.getTongTien(MaHD);
                        hd.setTienTong(Double.parseDouble(String.valueOf(a)));
                        if (hdD.update(hd) > 0) {
                            Toast.makeText(context, "Sửa thành công", Toast.LENGTH_SHORT).show();
                            Log.d("Ma hd:", "onClick: " + MaHD);
                            openDialog_hdct(context, MaHD);
                        } else {
                            Toast.makeText(context, "Sửa thất bại", Toast.LENGTH_SHORT).show();
                        }
                    }
                    capNhatLv();
                    dialog.dismiss();
                    openDialog_hdct(context, MaHD);
                }
            }
        });
        dialog.show();
    }

    //
    public void xoa_hd(final String Id) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Delete");
        builder.setMessage("Bạn có chắc chắn muốn xóa không");
        builder.setCancelable(true);
        builder.setPositiveButton("Yes", (DialogInterface dialog, int id) -> {

            // Xóa cả hóa đơn chi tiết
            Kho_DAO kD = new Kho_DAO(getActivity());
            hdctD = new HoaDonCt_DAO(getActivity());
            for (int i = 0; i < hdctD.getAllHD(Id).size(); i++) {
                hdct = hdctD.getAllHD(Id).get(i);
                int sl = kD.getSLConLai(hdct.getMaNH()) + hdct.getSoLuong();
                kD.updateSL(sl, hdct.getMaNH());
            }

            hdctD.deleteHD(Id);
            // Xóa hóa đơn
            hdD.delete(Id);
            capNhatLv();
            dialog.cancel();
        });
        builder.setNegativeButton("No", (dialog, id) -> {
            dialog.cancel();
        });
        AlertDialog alert = builder.create();
        builder.show();
    }

    //
    void capNhatLv_hdct(int _maHD) {
        listCt = (ArrayList<HoaDonCT>) hdctD.getAllHD(String.valueOf(_maHD));
        hdctA = new HoaDonCT_Adapter(getActivity(), this, listCt);
        lv2.setAdapter(hdctA);
    }


    protected void openDialog_hdct(final Context _context, int _type) {


        dialog = new Dialog(_context);
        dialog.setContentView(R.layout.diablog_hoa_don_chi_tiet);

        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        int width = (int) (getResources().getDisplayMetrics().widthPixels * 0.90);
        dialog.getWindow().setLayout(width, ViewGroup.LayoutParams.WRAP_CONTENT);

        edSl = dialog.findViewById(R.id.hdct_diag_ed_sl);
        sp = dialog.findViewById(R.id.hdct_diag_sp);
        btLuu = dialog.findViewById(R.id.hdct_diag_bt_luu);
        btThem = dialog.findViewById(R.id.hdct_diag_bt_them);
        lv2 = dialog.findViewById(R.id.hdct_diag_lv);

        hdctD = new HoaDonCt_DAO(_context);
        capNhatLv_hdct(_type);
        NuocHoa_DAO nhD = new NuocHoa_DAO(_context);

        listNH = new ArrayList<NuocHoa>();
        listNH = (ArrayList<NuocHoa>) nhD.getAll();
        Nuochoa_Spiner_Adapter nhSpa = new Nuochoa_Spiner_Adapter(_context, listNH);
        sp.setAdapter(nhSpa);


        sp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                MaNH = listNH.get(i).getMaNH();
                TenNH = listNH.get(i).getTenNH();
                giaNH = listNH.get(i).getGia();
                Toast.makeText(_context, "Chọn: " + MaNH + " " + TenNH + " " + giaNH, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        btLuu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                capNhatLv_hdct(_type);
                int a = hdctD.getTongTien(MaHD);
                hd.setTienTong(Double.parseDouble(String.valueOf(a)));
                capNhatLv();
                dialog.dismiss();
            }
        });

        btThem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (validate_hdct() > 0) {
                    hdct = new HoaDonCT();
                    hdct.setMaNH(MaNH);
                    Log.d("HDCT: THEM", "onClick: " + MaNH);
                    int sl = Integer.parseInt(edSl.getText().toString());
                    hdct.setSoLuong(sl);

                    //Cập nhật lại số lượng cho kho

                    Kho_DAO khoDao = new Kho_DAO(_context);
                    sl = khoDao.getSLConLai(MaNH) - sl;
                    khoDao.updateSL(sl, MaNH);

                    hdct.setMaHD(_type);

                    Log.d("TAG", "onClick: " + hdctD.getByIdNH(String.valueOf(MaNH), String.valueOf(MaHD)));
                    setMaHDCT((int) hdctD.getByIdNH(String.valueOf(MaNH), String.valueOf(MaHD)));
                    if (getMaHDCT() < 0) {
                        Double tien = giaNH * Double.parseDouble(edSl.getText().toString());
                        hdct.setThanhTien(tien);
                        if (hdctD.insert(hdct) > 0) {
                            Toast.makeText(_context, "Thêm thành công", Toast.LENGTH_SHORT).show();
                            edSl.setText("");

                            // Dùng để cập nhập lại tổng tiền hóa đơn

                            int a = hdctD.getTongTien(MaHD);
                            hd.setTienTong(Double.parseDouble(String.valueOf(a)));
                            hdD.updateH(Double.parseDouble(String.valueOf(a)), MaHD);
                        } else {
                            Toast.makeText(_context, "Thêm thất bại", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        int slu = hdctD.getIdAll(String.valueOf(getMaHDCT())).getSoLuong() + Integer.parseInt(edSl.getText().toString());
                        Double tienU = hdctD.getIdTien(String.valueOf(getMaHDCT())) + giaNH * Double.parseDouble(edSl.getText().toString());
                        int b = hdctD.updateSL(getMaHDCT(), slu, tienU, getMaHD());
                        Log.d("update" + getMaHDCT(), "onClick: " + slu);
                        if (b > 0) {
                            Toast.makeText(_context, "Thêm thành công", Toast.LENGTH_SHORT).show();
                            edSl.setText("");
                            // Dùng để cập nhập lại tổng tiền hóa đơn
                            int a = hdctD.getTongTien(MaHD);
                            hd.setTienTong(Double.parseDouble(String.valueOf(a)));
                            hdD.updateH(Double.parseDouble(String.valueOf(a)), MaHD);
                        } else {
                            Log.d("update", "onClick: " + b);
                            Toast.makeText(_context, "Thêm thất bại", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
                capNhatLv_hdct(_type);
                capNhatLv();
            }
        });
        dialog.show();
    }

    int mct;

    private void setMaHDCT(int mct) {
        this.mct = mct;
    }

    private int getMaHDCT() {
        return mct;
    }

    public void xoa_hdct(final String Id, int _MaHD, int Sl, int _maNH) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Delete");
        builder.setMessage("Bạn có chắc chắn muốn xóa không");
        builder.setCancelable(true);
        builder.setPositiveButton("Yes", (DialogInterface dialog, int id) -> {
            hdctD.delete(Id);
            capNhatLv();
            capNhatLv_hdct(_MaHD);
            Kho_DAO kD = new Kho_DAO(getActivity());
            kD.updateSL(Sl, _maNH);
            int a = hdctD.getTongTien(_MaHD);
            hdD.updateH(Double.parseDouble(String.valueOf(a)), MaHD);
            dialog.cancel();
        });
        builder.setNegativeButton("No", (dialog, id) -> {
            dialog.cancel();
        });
        AlertDialog alert = builder.create();
        builder.show();
    }

    public int validate_hdct() {
        Kho_DAO kD = new Kho_DAO(getActivity());
        int check = 1;
        if (edSl.getText().length() == 0) {
            Toast.makeText(getContext(), "Không được để trống số lượng", Toast.LENGTH_SHORT).show();
            check = -1;
        } else if (sp.getSelectedItem() == null) {
            Toast.makeText(getContext(), "Chưa có dữ liệu nước hoa", Toast.LENGTH_SHORT).show();
            check = -1;
        } else if (Integer.parseInt(edSl.getText().toString()) > kD.getSLConLai(MaNH)) {
            Toast.makeText(getContext(), "Số lượng trong kho không đủ", Toast.LENGTH_SHORT).show();
            check = -1;
        }
        return check;
    }

    public int validate() {
        int check = 1;
        String ten = edKH.getText().toString();
        if (edKH.getText().length() == 0) {
            Toast.makeText(getContext(), "Không được để trống tên khách hàng", Toast.LENGTH_SHORT).show();
            check = -1;
        } else if (tvNgay.getText().length() == 0) {
            Toast.makeText(getContext(), "Không được để trống ngày tạo hóa đơn", Toast.LENGTH_SHORT).show();
            check = -1;
        } else if (ten.startsWith(" ") || ten.endsWith(" ")) {
            Toast.makeText(getContext(), "Không được để khoảng trắng ở đầu và cuối tên", Toast.LENGTH_SHORT).show();
            check = -1;
            edKH.setText("");
        }
        return check;
    }

}