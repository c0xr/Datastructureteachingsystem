package com.csti.datastructureteachingsystem;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

public class Registered extends AppCompatActivity {
    private EditText register, register_password;
    private TextView push;
    public static Intent newIntent(Context packageContext){
        return new Intent(packageContext,Registered.class);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bmob.initialize(this, "1e80a4c2a3073b26958660004ae63da5");
        setContentView(R.layout.activity_registered);
        register = findViewById(R.id.register);
        register_password = findViewById(R.id.register_password);
        push = findViewById(R.id.push);
        push.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!(register.getText().toString().equals("")) && !(register_password.getText().toString().equals(""))) {
                    final Person user = new Person();
                    user.setUsername(register.getText().toString());
                    user.setPassword(register_password.getText().toString());
                    user.signUp(new SaveListener<Person>() {
                        @Override
                        public void done(Person user, BmobException e) {
                            if (e == null) {
                                Toast.makeText(Registered.this, "注册成功！", Toast.LENGTH_SHORT).show();
                                finish();
                            } else {
                                Toast.makeText(Registered.this, "注册失败！" + e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                } else {
                    Toast.makeText(Registered.this, "账号或密码不能为空！", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}
