package com.ml.datastructureteachingsystem.activity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.ml.datastructureteachingsystem.R;
import com.ml.datastructureteachingsystem.module.User;
import com.ml.datastructureteachingsystem.module.Post;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UploadBatchListener;

import static com.ml.datastructureteachingsystem.helper.SystemHelper.print;
import static com.ml.datastructureteachingsystem.helper.SystemHelper.toast;

public class PostCommitingActivity extends AppCompatActivity {
    private TextView mCommit;
    private EditText mTitle;
    private EditText mContent;
    private List<ImageView> mImageViews=new ArrayList<>();
    private int mTag;
    private List<String> mPaths=new ArrayList<>();
    private int mI;
    private ConstraintLayout mLoadInfo;
    private TextView mPercent;

    public static Intent newIntent(Context packageContext){
        return new Intent(packageContext,PostCommitingActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_commiting);

        mCommit=findViewById(R.id.delete);
        mTitle=findViewById(R.id.item_tilte);
        mContent=findViewById(R.id.content);
        mImageViews.add((ImageView)findViewById(R.id.img_a));
        mImageViews.add((ImageView)findViewById(R.id.img_b));
        mImageViews.add((ImageView)findViewById(R.id.img_c));
        mImageViews.add((ImageView)findViewById(R.id.img_d));
        mImageViews.add((ImageView)findViewById(R.id.img_e));
        mLoadInfo=findViewById(R.id.load_info);
        mPercent=findViewById(R.id.percent);

        mCommit.setOnClickListener(new View.OnClickListener() {
            View.OnClickListener onClickListener=this;

            @Override
            public void onClick(View v) {
                if(mTitle.getText().toString().equals("")||mContent.getText().toString().equals("")){
                    toast("标题或内容不能为空",PostCommitingActivity.this);
                    return;
                }
                mCommit.setOnClickListener(null);
                final Post post=new Post(mTitle.getText().toString(),mContent.getText().toString(),
                        BmobUser.getCurrentUser(User.class));
                if(mPaths.size()==0){
                    post.save(new SaveListener<String>() {
                        @Override
                        public void done(String s, BmobException e) {
                            if (e == null) {
                                print("commit post success");
                                toast("发布成功",PostCommitingActivity.this);
                                setResult(1);
                                finish();
                            }else{
                                print("commit post fail:"+e);
                                toast("发布失败,请重试",PostCommitingActivity.this);
                                mCommit.setOnClickListener(onClickListener);
                            }
                        }
                    });
                    return;
                }
                //提示上传量
                mLoadInfo.setVisibility(View.VISIBLE);
                mLoadInfo.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                });
                final String[] path=new String[mPaths.size()];
                mPaths.toArray(path);
                BmobFile.uploadBatch(path, new UploadBatchListener() {
                    @Override
                    public void onSuccess(List<BmobFile> list, List<String> list1) {
                        print("upload pic success");
                        if(list.size()!=path.length){
                            return;
                        }
                        print("upload all pics success");
                        post.addAll("mImages",list);
                        post.save(new SaveListener<String>() {
                            @Override
                            public void done(String s, BmobException e) {
                                if (e == null) {
                                    print("commit post success");
                                    toast("发布成功",PostCommitingActivity.this);
                                    setResult(1);
                                    finish();
                                }else{
                                    print("commit post fail:"+e);
                                    mLoadInfo.setVisibility(View.INVISIBLE);
                                    mLoadInfo.setOnClickListener(null);
                                    toast("发布失败,请重试",PostCommitingActivity.this);
                                    mCommit.setOnClickListener(onClickListener);
                                }
                            }
                        });
                    }

                    @Override
                    public void onProgress(int i, int i1, int i2, int i3) {
                        float counter=(i-1)*100+i1;
                        mPercent.setText((int)(counter/i2)+"%");
                    }

                    @Override
                    public void onError(int i, String s) {
                        print("upload pics fail:"+s);
                    }
                });
            }
        });

        for(mI =0; mI <mImageViews.size(); mI++){
            final ImageView imageView=mImageViews.get(mI);
            imageView.setOnClickListener(new View.OnClickListener() {
                private int mTag=mI;

                @Override
                public void onClick(View v) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        PostCommitingActivity.this.mTag=this.mTag;
                        requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},1);
                    }
                }
            });
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK && null != data) {
            Uri selectedImage = data.getData();
            String[] filePathColumn = {MediaStore.Images.Media.DATA};

            Cursor cursor = getContentResolver().query(selectedImage,
                    filePathColumn, null, null, null);
            cursor.moveToFirst();

            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            String picturePath = cursor.getString(columnIndex);
            cursor.close();

            if(mPaths.size()==mTag){
                mPaths.add(mTag,picturePath);
            }else {
                mPaths.set(mTag, picturePath);
            }
            Bitmap bitmap= BitmapFactory.decodeFile(picturePath);
            mImageViews.get(mTag).setImageBitmap(bitmap);
            mImageViews.get(mTag).setBackgroundResource(R.drawable.gray_stroke);
            if(mTag!=mImageViews.size()-1) {
                mImageViews.get(mTag + 1).setVisibility(View.VISIBLE);
            }
            print("added pic path:"+picturePath);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        Intent intent = new Intent(Intent.ACTION_PICK,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, 1);
    }
}
