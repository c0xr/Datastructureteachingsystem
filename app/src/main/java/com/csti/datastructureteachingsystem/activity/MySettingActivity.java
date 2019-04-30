package com.csti.datastructureteachingsystem.activity;

import android.Manifest;
import android.content.ContentUris;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.UpdateListener;
import cn.bmob.v3.listener.UploadFileListener;

import com.csti.datastructureteachingsystem.R;
import com.csti.datastructureteachingsystem.fragment.ChangePasswordFragment;
import com.csti.datastructureteachingsystem.module.User;

import java.io.File;

import static com.csti.datastructureteachingsystem.helper.SystemHelper.print;

public class MySettingActivity extends AppCompatActivity {
    private EditText username,email;
    private TextView exit,sex;
    private ConstraintLayout changepassword;
    private ImageView headphoto, yes;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    public static final int CHOOSE_PHOTO = 2;
    User user = BmobUser.getCurrentUser(User.class);
    public static Intent newIntent(Context packageContext) {
        return new Intent(packageContext, MySettingActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bmob.initialize(this, "1e80a4c2a3073b26958660004ae63da5");
        setContentView(R.layout.activity_my_setting);
        username = findViewById(R.id.username);
        exit = findViewById(R.id.exit);
        changepassword = findViewById(R.id.changgepassword);
        headphoto = findViewById(R.id.headphoto);
        yes = findViewById(R.id.yes);
        email=findViewById(R.id.editText);
        email.setText(user.getEmail());
        sex=findViewById(R.id.sex);
        sex.setText(user.getSex());
        username.setText(user.getUsername());
        sharedPreferences = getSharedPreferences("data", MODE_PRIVATE);
        String headPhoto = sharedPreferences.getString("headphoto", "");
        if (!(headPhoto.equals(""))) {
            displayImage(headPhoto);
        }
        yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final User user = BmobUser.getCurrentUser(User.class);
                user.setUsername(username.getText().toString());
                user.update(new UpdateListener() {
                    @Override
                    public void done(BmobException e) {
                        if (e == null) {
                            Toast.makeText(MySettingActivity.this, "更新用户名成功", Toast.LENGTH_SHORT).show();
                        } else {

                        }
                    }
                });
            }
        });

        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(MySettingActivity.this);
                builder.setTitle("是否退出");
                builder.setPositiveButton("是", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        BmobUser.logOut();
                        sharedPreferences = getSharedPreferences("data", MODE_PRIVATE);
                        editor = sharedPreferences.edit();
                        editor.putString("account", "");
                        editor.putString("password", "");
                        editor.apply();
                        Intent intent = new Intent(MySettingActivity.this, MainActivity.class);
                        intent.putExtra("closeType", 1);
                        startActivity(intent);
                    }
                });
                builder.show();
            }
        });

        changepassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                repleaseFragment(new ChangePasswordFragment());
            }
        });

        headphoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ContextCompat.checkSelfPermission(MySettingActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(MySettingActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                } else {
                    openAlbum();
                }
            }
        });

    }

    private void openAlbum() {
        Intent intent = new Intent("android.intent.action.GET_CONTENT");
        intent.setType("image/*");
        startActivityForResult(intent, CHOOSE_PHOTO);//打开相册
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case 1:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    openAlbum();
                } else {
                    Toast.makeText(MySettingActivity.this, "无权限", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        switch (requestCode) {
            case CHOOSE_PHOTO:
                if (resultCode == RESULT_OK) {
                    if (Build.VERSION.SDK_INT >= 19) {
                        handleImageOnKitKat(data);
                    } else {
                        handleImageBeforeKitKat(data);
                    }
                }
        }
    }

    private void handleImageOnKitKat(Intent data) {
        String imagePath = null;
        Uri uri = data.getData();
        if (DocumentsContract.isDocumentUri(this, uri)) {
            String docId = DocumentsContract.getDocumentId(uri);
            if ("com.android.providers.media.documents".equals(uri.getAuthority())) {
                String id = docId.split(":")[1];
                String selection = MediaStore.Images.Media._ID + "=" + id;
                imagePath = getImagePath(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, selection);
                sharedPreferences = getSharedPreferences("data", MODE_PRIVATE);
                editor = sharedPreferences.edit();
                editor.putString("headphoto", imagePath);
                editor.apply();
            } else if ("com.android.providers.downloads.documents".equals(uri.getAuthority())) {
                Uri contentUri = ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"), Long.valueOf(docId));
                imagePath = getImagePath(contentUri, null);
                sharedPreferences = getSharedPreferences("data", MODE_PRIVATE);
                editor = sharedPreferences.edit();
                editor.putString("headphoto", imagePath);
                editor.apply();
            }
        } else if ("content".equalsIgnoreCase(uri.getScheme())) {
            imagePath = getImagePath(uri, null);
            sharedPreferences = getSharedPreferences("data", MODE_PRIVATE);
            editor = sharedPreferences.edit();
            editor.putString("headphoto", imagePath);
            editor.apply();
        } else if ("file".equalsIgnoreCase(uri.getScheme())) {
            imagePath = uri.getPath();
            sharedPreferences = getSharedPreferences("data", MODE_PRIVATE);
            editor = sharedPreferences.edit();
            editor.putString("headphoto", imagePath);
            editor.apply();
        }
        displayImage(imagePath);
    }

    private void handleImageBeforeKitKat(Intent data) {
        Uri uri = data.getData();
        String imagePath = getImagePath(uri, null);
        displayImage(imagePath);
    }

    private String getImagePath(Uri uri, String selection) {
        String path = null;
        Cursor cursor = getContentResolver().query(uri, null, selection, null, null);
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
            }
            cursor.close();
        }
        return path;
    }

    private void displayImage(String imagePath) {
        if (imagePath != null) {
            Bitmap bitmap = BitmapFactory.decodeFile(imagePath);
            headphoto.setImageBitmap(bitmap);
            final BmobFile bmobFile = new BmobFile(new File(imagePath));
            bmobFile.uploadblock(new UploadFileListener() {
                @Override
                public void done(BmobException e) {
                    if(e==null){
                        //bmobFile.getFileUrl()--返回的上传文件的完整地址
                        user.setAvatar(bmobFile);
                        user.update(new UpdateListener() {
                            @Override
                            public void done(BmobException e) {

                            }
                        });
                        print("上传文件成功:" + bmobFile.getFileUrl());
                    }else{
                        print("上传文件失败：" + e.getMessage());
                    }

                }
            });
        } else {
            Toast.makeText(this, "得到图片失败", Toast.LENGTH_SHORT).show();
        }
    }

    private void repleaseFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.changepassword_layout, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}
