package com.csti.datastructureteachingsystem.activity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.csti.datastructureteachingsystem.R;
import com.csti.datastructureteachingsystem.module.Post;

import java.io.File;
import java.security.Permission;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UploadFileListener;

import static com.csti.datastructureteachingsystem.helper.SystemHelper.print;
import static com.csti.datastructureteachingsystem.helper.SystemHelper.toast;

public class PostActivity extends AppCompatActivity {

    public static Intent newIntent(Context packageContext){
        return new Intent(packageContext,PostActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},0);
        }

/*        final Post post=new Post("test","aevuilobviueabfrvbewoiubweiujBIUJAERWNILOUAERVBNIJAERBBIJLOUA" +
                "DEFSVIJULDEFSVIJULEAFBVIULEFRBVIULBAERVUIOVBELRUIBVIUERBILUBF", BmobUser.getCurrentUser(BmobUser.class));
        String root= Environment.getExternalStorageDirectory().getPath();
        final BmobFile pic=new BmobFile(new File(root+"/img1.jpg"));
        pic.upload(new UploadFileListener() {
            @Override
            public void done(BmobException e) {
                if(e==null){
                    print("file upload success");
                    post.add("mImages",pic);
                    post.add("mImages",pic);
                    post.add("mImages",pic);
                    post.save(new SaveListener<String>() {
                        @Override
                        public void done(String s, BmobException e) {
                            if(e==null){
                                print("post success");
                                toast("post success",PostActivity.this);
                            }else{
                                print("post fail:"+e);
                            }
                        }
                    });
                }else{
                    print("file upload fail:"+e);
                }
            }
        });*/




    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

    }
}
