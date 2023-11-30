package com.example.duan1_13;


import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.duan1_13.DAO.NhanVien_DAO;
import com.example.duan1_13.Fragment.DoanhThu_Fragment;
import com.example.duan1_13.Fragment.DoimatKhau_Fragment;
import com.example.duan1_13.Fragment.Hang_Fragment;
import com.example.duan1_13.Fragment.HoaDon_Fragment;
import com.example.duan1_13.Fragment.NhanVien_Fragment;
import com.example.duan1_13.Fragment.NuocHoa_Fragment;
import com.example.duan1_13.Fragment.Top_Fragment;
import com.example.duan1_13.Model.NhanVien;
import com.google.android.material.navigation.NavigationView;

import java.io.ByteArrayOutputStream;

public class MainActivity extends AppCompatActivity {

    private DrawerLayout drawerLayout;
    private Toolbar toolbar;
    private NavigationView navigationView;
    private ActionBarDrawerToggle drawerToggle;
    private String user;
    ImageView avatar;
    View v;
    TextView tv_nameUser;
    NhanVien_DAO nvD;
    NhanVien nv;
    int a;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_navigation);
        drawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar,
                R.string.drawer_open, R.string.drawer_close);
        drawerToggle.setDrawerIndicatorEnabled(true);
        drawerToggle.syncState();

        drawerLayout.addDrawerListener(drawerToggle);

        navigationView = (NavigationView) findViewById(R.id.drawer_navigation_view);

        //

        Intent i = getIntent();
        user = i.getStringExtra("user");
        Log.d("", "onCreate: " + user);
        Toast.makeText(this, "" + user, Toast.LENGTH_SHORT).show();

        //

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                if (item.getItemId() == R.id.action_quanlyhoadon) {
                    HoaDon_Fragment fragment = new HoaDon_Fragment();
                    replaceFrg(fragment);
                    setTitle("Quản lý hóa đơn");
                } else if (item.getItemId() == R.id.action_qlhang) {
                    Hang_Fragment fragment = new Hang_Fragment();
                    replaceFrg(fragment);
                    setTitle("Quản lý hãng nước hoa");
                } else if (item.getItemId() == R.id.action_qlnh) {
                    NuocHoa_Fragment fragment = new NuocHoa_Fragment();
                    replaceFrg(fragment);
                    setTitle("Quản lý nước hoa");
                } else if (item.getItemId() == R.id.action_qlNhanVien) {
                    NhanVien_Fragment fragment = new NhanVien_Fragment();
                    replaceFrg(fragment);
                    setTitle("Quản lý nhân viên");
                } else if (item.getItemId() == R.id.action_tMuoi) {
                    Top_Fragment fragment = new Top_Fragment();
                    replaceFrg(fragment);
                    setTitle("TOP 10");
                } else if (item.getItemId() == R.id.action_doanhThu) {
                    DoanhThu_Fragment fragment = new DoanhThu_Fragment();
                    replaceFrg(fragment);
                    setTitle("Doanh thu");
                } else if (item.getItemId() == R.id.action_doiPass) {
                    DoimatKhau_Fragment fragment = new DoimatKhau_Fragment();
                    replaceFrg(fragment);
                    setTitle("Đổi mật khẩu");
                } else if (item.getItemId() == R.id.action_logout) {
                    //
                    AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                    builder.setTitle("Thông báo : ");
                    builder.setIcon(android.R.drawable.ic_delete);
                    builder.setMessage("Bạn có muốn đăng xuất không ?");
                    builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            Toast.makeText(MainActivity.this, "Bạn đã đăng xuất...", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(MainActivity.this, Login_Activity.class));
                        }
                    });
                    builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            // không làm gì, tất dialog đi
                            dialogInterface.dismiss();
                        }
                    });
                    builder.setCancelable(false);
                    AlertDialog dialog = builder.create();
                    dialog.show();
                    //
                } else {
                    HoaDon_Fragment fragment = new HoaDon_Fragment();
                    replaceFrg(fragment);
                    setTitle("Quản lý hóa đơn");
                }
                drawerLayout.close();
                //
                return true;
            }
        });

        //
        if (savedInstanceState == null) {
            Class fragmentClass = HoaDon_Fragment.class;
            navigationView.getMenu().getItem(0).setChecked(true);
            v = navigationView.getHeaderView(0);

            nvD = new NhanVien_DAO(this);
            nv = nvD.getID(user);
            String Name = nv.getHoTen();

            tv_nameUser = v.findViewById(R.id.header_nameUser);
            avatar = v.findViewById(R.id.img_avatar);
            // set hình ảnh và tên ở navigation header
            tv_nameUser.setText(Name);

            if (nv.getHinhAnh() != null) { //nếu mà file có ảnh ms lấy
                byte[] _img = nv.getHinhAnh();
                Bitmap bitmap = BitmapFactory.decodeByteArray(_img, 0, _img.length);
                avatar.setImageBitmap(bitmap);
            }

            // Thay avatar của bản thân
            avatar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    getAvatar();
                }
            });

            // admin có quyền add user
            navigationView.getMenu().findItem(R.id.action_qlNhanVien).setVisible(false);
            if (user.equals("admin")) {
                navigationView.getMenu().findItem(R.id.action_qlNhanVien).setVisible(true);
            }
            try {
                Fragment fragment = (Fragment) fragmentClass.newInstance();
                getSupportFragmentManager().beginTransaction().replace(R.id.drawer_Framelayout,
                        fragment).commit();
            } catch (Exception e) {
            }
        }
        //
    }
    //
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 999 && resultCode == RESULT_OK) {
            Uri test = data.getData();
            System.out.println(test);
            if(data.getExtras()!=null)
            {

                Bundle extras = data.getExtras();
                Bitmap imageBitmap = (Bitmap) extras.get("data");
                avatar.setImageBitmap(imageBitmap);
                Log.d("Ma 1 là: ", "_____" +a);
                SaveImg(String.valueOf(a));
            }
            else{

                Uri uri = data.getData();
                avatar.setImageURI(uri);
                SaveImg(String.valueOf(a));
                Log.d("Ma 2 là: ", "_____" +a);

            }

        }

    }
    //

    public void replaceFrg(Fragment frg) {
        FragmentManager fm = getSupportFragmentManager();
        fm.beginTransaction().replace(R.id.drawer_Framelayout, frg).commit();
    }

    // dùng để lưu ảnh
    private void SaveImg(String id){
        BitmapDrawable bitmapDrawable = (BitmapDrawable) avatar.getDrawable();
        Bitmap bitmap = bitmapDrawable.getBitmap();
        ByteArrayOutputStream byOut = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, byOut);
        byte[] image = byOut.toByteArray();

        nv.setHinhAnh(image);
        nvD.update(nv);

    }
    //
    private void getAvatar(){
        // lấy từ library
        Intent pick=new Intent(Intent.ACTION_GET_CONTENT);
        pick.setType("image/*");
        // lấy từ camera
        Intent pho=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Tích hợp
        Intent chosser=Intent.createChooser(pick, "chon");
        chosser.putExtra(Intent.EXTRA_INITIAL_INTENTS,new Intent[]{pho});
        startActivityForResult(chosser, 999);
    }

    @Override
    public boolean onOptionsItemSelected( MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onPostCreate(Bundle savedInstanceState, PersistableBundle persistableBundle) {
        super.onPostCreate(savedInstanceState, persistableBundle);
        drawerToggle.syncState();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }

    public String getUser(){
        return user;
    }

    //
}