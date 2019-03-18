package com.csti.datastructureteachingsystem.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.csti.datastructureteachingsystem.R;

public class MyReplyActivity extends AppCompatActivity {

    public static Intent newIntent(Context packageContext){
        return new Intent(packageContext,MyReplyActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_reply);
    }
}
