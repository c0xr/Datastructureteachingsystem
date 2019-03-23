package com.csti.datastructureteachingsystem.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.csti.datastructureteachingsystem.R;

public class PostCommitingActivity extends AppCompatActivity {

    public static Intent newIntent(Context packageContext){
        return new Intent(packageContext,PostCommitingActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_commiting);
    }
}
