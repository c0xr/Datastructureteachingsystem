package com.csti.datastructureteachingsystem.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.csti.datastructureteachingsystem.fragment.InfoManagementFragment;
import com.csti.datastructureteachingsystem.fragment.PostListFragment;
import com.csti.datastructureteachingsystem.R;
import com.csti.datastructureteachingsystem.module.Person;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

public class MainActivity extends AppCompatActivity {
    private final static String SAVED_PRESENT_F="present";
    private final static String TAG_F1="f1";
    private final static String TAG_F2="f2";
    private final static String TAG_F3="f3";
    private final int time = 2000;
    private boolean lag = true;
    private String s_account;
    private String s_password;

    private FragmentManager mFm;
    private Fragment mF1;
    private Fragment mF2;
    private Fragment mF3;
    private int mPresentF;
    private ImageView mHomeIcon;
    private ImageView mPostIcon;
    private ImageView mMyInfoIcon;
    private ImageView Load;
    private TextView mTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bmob.initialize(this, "1e80a4c2a3073b26958660004ae63da5");
        Person person = (Person) getIntent().getSerializableExtra("User");
        setContentView(R.layout.activity_main);
        mHomeIcon =findViewById(R.id.home_icon);
        mPostIcon =findViewById(R.id.post_icon);
        mMyInfoIcon =findViewById(R.id.my_info_icon);
        mTitle=findViewById(R.id.title);
        Load=findViewById(R.id.imageView6);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {//延时time秒后，将运行如下代码
                if(lag){
//                    Load.setVisibility(View.INVISIBLE);
                    SharedPreferences mSharedPreferences=getSharedPreferences("data",MODE_PRIVATE);
                    s_account=mSharedPreferences.getString("account","");
                    s_password=mSharedPreferences.getString("password","");
                    if(!(s_account.equals(""))&&!(s_password.equals(""))){
                        Login(s_account,s_password);
                    }else {
                        Intent intent = new Intent(MainActivity.this, Login.class);
                        startActivity(intent);
                    }
                }
            }
        } , time);




        if(savedInstanceState!=null){
            mPresentF=savedInstanceState.getInt(SAVED_PRESENT_F);
        }

        mFm =getSupportFragmentManager();
        mF2=mFm.findFragmentByTag(TAG_F2);
        mF3=mFm.findFragmentByTag(TAG_F3);

        if(mF2==null) {
            mF1=new Fragment();
            mF2 = PostListFragment.newInstance();
            mF3 = InfoManagementFragment.newInstance();
            mFm.beginTransaction()
                    .add(R.id.container, mF2,TAG_F2)
                    .add(R.id.container, mF3,TAG_F3)
                    .hide(mF3)
                    .commit();
            mPresentF=2;
        }

        setTint();
        setTitle();

        findViewById(R.id.home).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recoveryTint();
                setTint();
                setTitle();
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
                mPresentF=2;
                setTint();
                setTitle();
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
                mPresentF=3;
                setTint();
                setTitle();
            }
        });
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putSerializable(SAVED_PRESENT_F,mPresentF);
        super.onSaveInstanceState(outState);
    }

    private Fragment getFragment(){
        switch (mPresentF){
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

    private void recoveryTint(){
        switch (mPresentF){
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

    private void setTint(){
        final ColorStateList tint=ColorStateList.valueOf(getResources().getColor(R.color.bottom_icon_tint));

        switch (mPresentF){
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

    private void setTitle(){
        if(mTitle==null){
            return;
        }
        switch (mPresentF){
            case 1:
                mTitle.setText("主页");
                break;
            case 2:
                mTitle.setText("帖子");
                break;
            case 3:
                mTitle.setText("我的");
                break;
            default:
                mTitle.setText("主页");
        }
    }

    public  void Login(String s_account,String s_password) {
        if (!(s_account.equals("")) && !(s_password.equals(""))) {
            final Person user = new Person();
            //此处替换为你的用户名
            user.setUsername(s_account);
            //此处替换为你的密码
            user.setPassword(s_password);
            user.login(new SaveListener<Person>() {
                @Override
                public void done(Person person, BmobException e) {
                    if (e == null) {
                        Person user = BmobUser.getCurrentUser(Person.class);
//                        Intent intent = new Intent(MainActivity.this, MainActivity.class);
//                        intent.putExtra("User", user);
//                        startActivity(intent);
//                        finish();
                    } else {
                        Toast.makeText(MainActivity.this, "登录失败" + e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }
            });
        } else {
            Toast.makeText(MainActivity.this, "请输入用户名或密码！", Toast.LENGTH_LONG).show();
        }
    }
}
