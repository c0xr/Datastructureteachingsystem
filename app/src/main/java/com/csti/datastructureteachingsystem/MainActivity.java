package com.csti.datastructureteachingsystem;

import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

public class MainActivity extends AppCompatActivity {

    private FragmentManager fm;
    private Fragment f3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bmob.initialize(this, "1e80a4c2a3073b26958660004ae63da5");
        setContentView(R.layout.activity_main);

        fm=getSupportFragmentManager();
        f3=InfoManagementFragment.newInstance();
        fm.beginTransaction()
                .add(R.id.container,f3)
                .hide(f3)
                .commit();

        final Button b=findViewById(R.id.button);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ConstraintLayout c=(ConstraintLayout) b.getParent();
                c.removeView(b);
                fm.beginTransaction()
                        .show(f3)
                        .commit();
            }
        });
    }
}
