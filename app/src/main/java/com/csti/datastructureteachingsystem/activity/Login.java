package com.csti.datastructureteachingsystem.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.csti.datastructureteachingsystem.R;
import com.csti.datastructureteachingsystem.module.User;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

public class Login extends AppCompatActivity {
    private EditText account, password;
    private TextView log, registered;

    public static Intent newIntent(Context packageContext) {
        return new Intent(packageContext, Login.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        account = findViewById(R.id.account);
        password = findViewById(R.id.password);
        log = findViewById(R.id.log);
        registered = findViewById(R.id.registered);

        //登录
        log.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                if (account.getText().toString().equals("admin") && password.getText().toString().equals("123")) {
//                    Intent intent = new Intent(Login.this, Admin.class);
//                    startActivity(intent);
//                } else {
                    mLogin(account.getText().toString(), password.getText().toString());
             //   }
            }
        });
        //注册
        registered.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Login.this, Registered.class);
                startActivity(intent);
            }
        });
    }

    public void mLogin(String s_account, String s_password) {
        if (!(s_account.equals("")) && !(s_password.equals(""))) {
            final User user = new User();
            //此处替换为你的用户名
            user.setUsername(s_account);
            //此处替换为你的密码
            user.setPassword(s_password);
            user.login(new SaveListener<User>() {
                @Override
                public void done(User person, BmobException e) {
                    if (e == null) {
                        User user = BmobUser.getCurrentUser(User.class);
                        Intent intent = new Intent(Login.this, MainActivity.class);
                        intent.putExtra("User", user);
                        startActivity(intent);
                        finish();
                    } else {
                        Toast.makeText(Login.this, "登录失败" + e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }
            });
        } else {
            Toast.makeText(Login.this, "请输入用户名或密码！", Toast.LENGTH_LONG).show();
        }
    }
}
