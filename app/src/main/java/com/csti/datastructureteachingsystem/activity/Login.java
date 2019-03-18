package com.csti.datastructureteachingsystem.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.csti.datastructureteachingsystem.module.Person;
import com.csti.datastructureteachingsystem.R;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

public class Login extends AppCompatActivity {
    private EditText account, password;
    private TextView log,registered;
    private SharedPreferences.Editor editor;
    private boolean isremember;
    private String s_account;
    private String s_password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        account = findViewById(R.id.account);
        password = findViewById(R.id.password);
        log = findViewById(R.id.log);
        registered=findViewById(R.id.registered);
        s_account = account.getText().toString();
        s_password = password.getText().toString();
        log.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
                            } else {
                                Toast.makeText(Login.this, "登录失败" + e.getMessage(), Toast.LENGTH_LONG).show();
                            }
                        }
                    });
                } else {
                    Toast.makeText(Login.this, "请输入用户名或密码！", Toast.LENGTH_LONG).show();
                }
            }
        });
        registered.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Login.this, Registered.class);
                startActivity(intent);
            }
        });
    }
}
