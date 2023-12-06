package com.example.duan1_13.Fragment;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.duan1_13.Adapter.NuocHoa_Adapter;
import com.example.duan1_13.Adapter.SpAdapter.Hang_Spiner_Adapter;
import com.example.duan1_13.DAO.Hang_DAO;
import com.example.duan1_13.DAO.HoaDonCt_DAO;
import com.example.duan1_13.DAO.NuocHoa_DAO;
import com.example.duan1_13.Model.Hang;
import com.example.duan1_13.Model.NuocHoa;
import com.example.duan1_13.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

public class NuocHoa_Fragment extends Fragment implements View.OnClickListener {

    private ListView lv;
    private FloatingActionButton fab;
    private EditText edTen, edGia, edSl;
    private Spinner sp;
    private Button btSave, btReset;
    Hang_Spiner_Adapter spiner_adapter;
    private ImageView iv;
    NuocHoa_DAO nhD;
    private ArrayList<NuocHoa> list;
    NuocHoa_Adapter nhA;
    NuocHoa nh;
    Dialog dialog;
    Hang_DAO hD;
    Hang hang;
    ArrayList<Hang> listH;
    int MaH, number, Ma_nh, TYPE = 0;
    String TenH;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_nuoc_hoa,
                container, false);

        lv = v.findViewById(R.id.nh_lv);
        fab = v.findViewById(R.id.nh_fab);

        nhD = new NuocHoa_DAO(getActivity());

        capNhatLv();

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openDialog(getActivity(), 0);
            }
        });

        HoaDonCt_DAO hdctD = new HoaDonCt_DAO(getActivity());
        lv.setOnItemLongClickListener((adapterView, view, position, id) -> {
            nh = list.get(position);
            Ma_nh = nh.getMaNH();
            //update
//            openDialog(getActivity(), 1);
            final PopupMenu menu = new PopupMenu(getActivity(), view);
            menu.getMenuInflater().inflate(R.menu.menu_item, menu.getMenu());
            if (hdctD.checkHdct(String.valueOf(Ma_nh)) > 0) {
                menu.getMenu().findItem(R.id.menu_xoa).setVisible(false);
            }
            menu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem menuItem) {
                    int it = menuItem.getItemId();
                    if (it == R.id.menu_sua) {
                        openDialog(getActivity(), 1);
                    } else if (it == R.id.menu_xoa) {
                        xoa(String.valueOf(nh.getMaNH()));
                    } else {
                        return onMenuItemClick(menuItem);
                    }
                    return true;
                }
            });
            menu.show();
            return false;
        });

        return v;
    }

    void capNhatLv() {
        list = (ArrayList<NuocHoa>) nhD.getAll();
        nhA = new NuocHoa_Adapter(getActivity(), this, list);
        lv.setAdapter(nhA);
        Log.d("", "Chạy r nè");
    }

    public void onClick(View viewp) {
        viewp.post(new Runnable() {
            @Override
            public void run() {
                showPopupMenu(viewp);
            }
        });
    }

    private void showPopupMenu(View view) {

        PopupMenu popup = new PopupMenu(getActivity(), view);

        popup.getMenuInflater().inflate(R.menu.menu_item, popup.getMenu());

        popup.show();
    }

    protected void openDialog(final Context context, final int type) {


        dialog = new Dialog(context);
        dialog.setContentView(R.layout.diablog_nuoc_hoa);

        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        int width = (int) (getResources().getDisplayMetrics().widthPixels * 0.90);
        dialog.getWindow().setLayout(width, ViewGroup.LayoutParams.WRAP_CONTENT);

        edTen = dialog.findViewById(R.id.nh_diag_ed_ten);
        edGia = dialog.findViewById(R.id.nh_diag_ed_gia);
        edSl = dialog.findViewById(R.id.nh_diag_ed_sl);
        sp = dialog.findViewById(R.id.nh_diag_spinner);
        btSave = dialog.findViewById(R.id.nh_diag_bt_save);
        btReset = dialog.findViewById(R.id.nh_diag_bt_reset);
        iv = dialog.findViewById(R.id.nh_diag_iv);

        listH = new ArrayList<Hang>();
        hD = new Hang_DAO(getContext());
        listH = (ArrayList<Hang>) hD.getAll();
        spiner_adapter = new Hang_Spiner_Adapter(context, listH);
        sp.setAdapter(spiner_adapter);


        sp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(context, "Chon : " + listH.get(i).getTenH(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        if (type != 0) {
            edTen.setText(String.valueOf(nh.getTenNH()));
            edGia.setText(String.valueOf(nh.getGia()));
            edSl.setText(String.valueOf(nh.getSoLuong()));

            byte[] _img = nh.getImg();
            Bitmap bitmap = BitmapFactory.decodeByteArray(_img, 0, _img.length);
            iv.setImageBitmap(bitmap);

            for (int i = 0; i < listH.size(); i++) {
                if (nh.getMaNH() == (listH.get(i).getMaH())) {
                    number = i;
                }
            }
            Log.i("demo", "posSach" + number);
            sp.setSelection(number);
        }
        iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent pick = new Intent(Intent.ACTION_GET_CONTENT);
                pick.setType("image/*");
                // lấy từ camera
                Intent pho = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                // Tích hợp
                Intent chosser = Intent.createChooser(pick, "chon");

                chosser.putExtra(Intent.EXTRA_INITIAL_INTENTS, new Intent[]{pho});
                someActivityResultLauncher.launch(chosser);
            }
        });
        btReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                edTen.setText("");
                edGia.setText("");
                edSl.setText("");
                sp.setSelection(0);
            }
        });
        btSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TYPE = type;
                if (validate() > 0) {
                    nh = new NuocHoa();
                    nh.setTenNH(edTen.getText().toString());
                    nh.setSoLuong(Integer.parseInt(edSl.getText().toString()));
                    nh.setGia(Double.parseDouble(edGia.getText().toString()));
                    nh.setImg(saveImg());
                    nh.setMaH_NH(MaH);
                    if (type == 0) {
                        if (nhD.insert(nh) > 0) {
                            Toast.makeText(context, "Thêm thành công", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(context, "Thêm thất bại", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        nh.setMaNH(Ma_nh);
                        if (nhD.update(nh) > 0) {
                            Toast.makeText(context, "Sửa thành công", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(context, "Sửa thất bại", Toast.LENGTH_SHORT).show();
                        }
                    }
                    capNhatLv();
                    dialog.dismiss();
                }
            }
        });
        dialog.show();
    }

    // nhận dữ liệu hình ảnh
    ActivityResultLauncher<Intent> someActivityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == Activity.RESULT_OK) {

                        Intent data = result.getData();
                        if (data.getExtras() != null) {
                            Bundle extras = data.getExtras();
                            Bitmap imageBitmap = (Bitmap) extras.get("data");
                            iv.setImageBitmap(imageBitmap);
                        } else {
                            Uri uri = data.getData();
                            iv.setImageURI(uri);
                        }
                    }
                }
            });

    // nhận dữ liệu hình ảnh để lưu
    private byte[] saveImg() {
        BitmapDrawable bitmapDrawable = (BitmapDrawable) iv.getDrawable();
        Bitmap bitmap = bitmapDrawable.getBitmap();
        ByteArrayOutputStream byOut = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, byOut);
        byte[] image = byOut.toByteArray();

        return image;
    }

    public void xoa(String Id) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Delete");
        builder.setMessage("Bạn có chắc chắn muốn xóa không");
        builder.setCancelable(true);
        builder.setPositiveButton("Yes", (dialog, id) -> {
            nhD.delete(Id);
            capNhatLv();
            dialog.cancel();
        });
        builder.setNegativeButton("No", (dialog, id) -> {
            dialog.cancel();
        });
        AlertDialog alert = builder.create();
        builder.show();
    }

    public int validate() {
        int check = 1;
        String ten = edTen.getText().toString();
        if (edTen.getText().length() == 0) {
            Toast.makeText(getContext(), "Không được để trống tên điện thoại", Toast.LENGTH_SHORT).show();
            check = -1;
        } else if (sp.getSelectedItem() == null) {
            Toast.makeText(getContext(), "Chưa có dữ liệu hãng", Toast.LENGTH_SHORT).show();
            check = -1;
        } else if (edGia.getText().length() == 0) {
            Toast.makeText(getContext(), "Không được để trống giá", Toast.LENGTH_SHORT).show();
            check = -1;
        } else if (edSl.getText().length() == 0) {
            Toast.makeText(getContext(), "Không được để trống số lượng hàng", Toast.LENGTH_SHORT).show();
        } else if (iv.getDrawable() == null) {
            Toast.makeText(getContext(), "Không được để trống ảnh", Toast.LENGTH_SHORT).show();
            check = -1;
        } else if (ten.startsWith(" ") || ten.endsWith(" ")) {
            Toast.makeText(getContext(), "Không được để khoảng trắng ở đầu và cuối tên", Toast.LENGTH_SHORT).show();
            check = -1;
            edTen.setText("");
        } else if (TYPE != 1) {
            if (nhD.checkTen(edTen.getText().toString()).equals(edTen.getText().toString())) {
                Toast.makeText(getContext(), "Tên của điện thoại đã tồn tại", Toast.LENGTH_SHORT).show();
                check = -1;
                edTen.setText("");
            }
        }

        return check;
    }

}