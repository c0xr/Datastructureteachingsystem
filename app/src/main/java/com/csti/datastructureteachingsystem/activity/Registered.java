package com.csti.datastructureteachingsystem.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.csti.datastructureteachingsystem.R;
import com.csti.datastructureteachingsystem.module.User;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

public class Registered extends AppCompatActivity {
    private EditText register, register_password,register_email;
    private TextView push;
    private RadioButton man,woman;
    private RadioGroup radioGroup;
    private String sex;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bmob.initialize(this, "1e80a4c2a3073b26958660004ae63da5");
        setContentView(R.layout.activity_registered);

        radioGroup=findViewById(R.id.radioGroup);
        man=findViewById(R.id.man);
        woman=findViewById(R.id.woman);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if(man.getId()==checkedId) {
                    sex = man.getText().toString();
                }else {
                    sex=woman.getText().toString();
                }
            }
        });

        register = findViewById(R.id.register);
        register_password = findViewById(R.id.register_password);
        push = findViewById(R.id.push);
        push.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!(register.getText().toString().equals("")) && !(register_password.getText().toString().equals(""))&&!(register_email.getText().toString().equals(""))) {
                    final User user = new User();
                    user.setUsername(register.getText().toString());
                    user.setPassword(register_password.getText().toString());
                    user.setEmail(register_email.getText().toString());
                    user.setSex(sex);
                    user.signUp(new SaveListener<User>() {
                        @Override
                        public void done(User user, BmobException e) {
                            if (e == null) {
                                Toast.makeText(Registered.this, "注册成功！", Toast.LENGTH_SHORT).show();
                                finish();
                            } else {
                                Toast.makeText(Registered.this, "注册失败！" + e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                } else {
                    Toast.makeText(Registered.this, "信息不能为空！", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
