package com.csti.datastructureteachingsystem;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

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
