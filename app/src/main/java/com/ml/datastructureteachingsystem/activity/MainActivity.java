package com.ml.datastructureteachingsystem.activity;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.ml.datastructureteachingsystem.fragment.HomePageFragment;
import com.ml.datastructureteachingsystem.fragment.InfoManagementFragment;
import com.ml.datastructureteachingsystem.fragment.PostListFragment;
import com.ml.datastructureteachingsystem.R;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobUser;

public class MainActivity extends AppCompatActivity {
    private final static String SAVED_PRESENT_F = "present";
    private final static String TAG_F1 = "f1";
    private final static String TAG_F2 = "f2";
    private final static String TAG_F3 = "f3";

    private FragmentManager mFm;
    private Fragment mF1;
    private Fragment mF2;
    private Fragment mF3;
    private int mPresentF;
    private ImageView mHomeIcon;
    private ImageView mPostIcon;
    private ImageView mMyInfoIcon;
    private TextView mTitle;
    private TextView mTextHome;
    private TextView mTextPost;
    private TextView mTextMyInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bmob.initialize(this, "1e80a4c2a3073b26958660004ae63da5");
        setContentView(R.layout.activity_main);
        mHomeIcon = findViewById(R.id.home_icon);
        mPostIcon = findViewById(R.id.post_icon);
        mMyInfoIcon = findViewById(R.id.my_info_icon);
        mTitle = findViewById(R.id.item_tilte);
        mTextHome=findViewById(R.id.text_home);
        mTextPost=findViewById(R.id.text_post);
        mTextMyInfo=findViewById(R.id.text_my_info);

        if(!BmobUser.isLogin()){
            startActivity(Login.newIntent(this));
            finish();
        }

        if (savedInstanceState != null) {
            mPresentF = savedInstanceState.getInt(SAVED_PRESENT_F);
        }

        mFm = getSupportFragmentManager();
        mF1=mFm.findFragmentByTag(TAG_F1);
        mF2 = mFm.findFragmentByTag(TAG_F2);
        mF3 = mFm.findFragmentByTag(TAG_F3);
        //碎片初始化
        if (mF2 == null) {
            mF1 = HomePageFragment.newInstance();
            mF2 = PostListFragment.newInstance();
            mF3 = InfoManagementFragment.newInstance();
            mFm.beginTransaction()
                    .add(R.id.container,mF1,TAG_F1)//添加碎片1
                    .add(R.id.container, mF2, TAG_F2)//2
                    .add(R.id.container, mF3, TAG_F3)//3
                    .hide(mF2)//隐藏2
                    .hide(mF3)//3
                    .commit();
            mPresentF = 1;
        }

        setTint();//设置下方图标颜色
        setTitle();//设置上方标题
        setTextColor();

        findViewById(R.id.home).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mFm.beginTransaction()
                        .hide(getFragment())
                        .show(mF1)
                        .commit();
                recoveryTint();
                recoveryTextColor();
                mPresentF=1;//当前碎片编号
                setTint();
                setTitle();
                setTextColor();
            }
        });

        findViewById(R.id.post).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mFm.beginTransaction()
                        .hide(getFragment())
                        .show(mF2)
                        .commit();
                recoveryTint();
                recoveryTextColor();
                mPresentF = 2;
                setTint();
                setTitle();
                setTextColor();
            }
        });

        findViewById(R.id.my_info).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mFm.beginTransaction()
                        .hide(getFragment())
                        .show(mF3)
                        .commit();
                recoveryTint();
                recoveryTextColor();
                mPresentF = 3;
                setTint();
                setTitle();
                setTextColor();
            }
        });
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putSerializable(SAVED_PRESENT_F, mPresentF);
        super.onSaveInstanceState(outState);
    }

    private Fragment getFragment() {
        switch (mPresentF) {
            case 1:
                return mF1;
            case 2:
                return mF2;
            case 3:
                return mF3;
            default:
                return mF1;
        }
    }

    private void recoveryTint() {
        switch (mPresentF) {
            case 1:
                mHomeIcon.setBackgroundTintList(null);
                break;
            case 2:
                mPostIcon.setBackgroundTintList(null);
                break;
            case 3:
                mMyInfoIcon.setBackgroundTintList(null);
                break;
            default:
                mHomeIcon.setBackgroundTintList(null);
        }
    }

    private void setTint() {
        final ColorStateList tint = ColorStateList.valueOf(getResources().getColor(R.color.bottom_icon_tint));

        switch (mPresentF) {
            case 1:
                mHomeIcon.setBackgroundTintList(tint);
                break;
            case 2:
                mPostIcon.setBackgroundTintList(tint);
                break;
            case 3:
                mMyInfoIcon.setBackgroundTintList(tint);
                break;
            default:
                mHomeIcon.setBackgroundTintList(tint);
        }
    }

    private void setTextColor() {
        int color=getResources().getColor(R.color.theme_color);

        switch (mPresentF) {
            case 1:
                mTextHome.setTextColor(color);
                break;
            case 2:
                mTextPost.setTextColor(color);
                break;
            case 3:
                mTextMyInfo.setTextColor(color);
                break;
            default:
                mTextHome.setTextColor(color);
        }
    }

    private void recoveryTextColor() {
        int color=getResources().getColor(R.color.text_color);

        switch (mPresentF) {
            case 1:
                mTextHome.setTextColor(color);
                break;
            case 2:
                mTextPost.setTextColor(color);
                break;
            case 3:
                mTextMyInfo.setTextColor(color);
                break;
            default:
                mTextHome.setTextColor(color);
        }
    }

    private void setTitle() {
        if (mTitle == null) {
            return;
        }
        switch (mPresentF) {
            case 1:
                mTitle.setText("主页");
                break;
            case 2:
                mTitle.setText("博客");
                break;
            case 3:
                mTitle.setText("我的");
                break;
            default:
                mTitle.setText("主页");
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        int statusType = intent.getIntExtra("closeType", 0);
        if (statusType == 1) {
            //打开登录页
            Intent intent1 = new Intent(this, Login.class);
            startActivity(intent1);
            finish();
        }
    }
}
