package com.csti.datastructureteachingsystem.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.csti.datastructureteachingsystem.R;
import com.csti.datastructureteachingsystem.module.Knowledge;

public class KnowledgeItem extends AppCompatActivity {
    Toolbar toolbar;
    Knowledge knowledge;
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_knowledge_item);
        Intent intent = getIntent();
        knowledge = (Knowledge) intent.getSerializableExtra("know");
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        getSupportActionBar().setTitle(knowledge.getTitle());
        textView = findViewById(R.id.textView14);
        textView.setText(knowledge.getKnowledge());
    }
}
