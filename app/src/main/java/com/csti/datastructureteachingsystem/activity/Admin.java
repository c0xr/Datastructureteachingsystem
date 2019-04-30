package com.csti.datastructureteachingsystem.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.csti.datastructureteachingsystem.R;
import com.csti.datastructureteachingsystem.module.Knowledge;
import com.csti.datastructureteachingsystem.module.Question;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

public class Admin extends AppCompatActivity {
    public EditText question, knowledge, answer, title, title1;
    public Button pushanswer, pushknowledge;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bmob.initialize(this, "1e80a4c2a3073b26958660004ae63da5");
        setContentView(R.layout.activity_admin);
        question = findViewById(R.id.question);
        knowledge = findViewById(R.id.knowledge);
        answer = findViewById(R.id.answer);
        pushanswer = findViewById(R.id.pushanswer);
        pushknowledge = findViewById(R.id.pushknowledge);
        title = findViewById(R.id.item_tilte);
        title1 = findViewById(R.id.title1);
        pushknowledge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String knowledge1 = knowledge.getText().toString();
                String title1 = title.getText().toString();
                Knowledge knowledge = new Knowledge();
                knowledge.setKnowledge(knowledge1);
                knowledge.setTitle(title1);
                knowledge.save(new SaveListener<String>() {
                    @Override
                    public void done(String s, BmobException e) {
                        if (e == null) {
                            Toast.makeText(Admin.this, "知识点添加成功", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
        pushanswer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String question1 = question.getText().toString();
                String answer1 = answer.getText().toString();
                Question question = new Question();
                question.setAnswer(answer1);
                question.setQuestion(question1);
                question.setTitle(title1.getText().toString());
                question.save(new SaveListener<String>() {
                    @Override
                    public void done(String s, BmobException e) {
                        if (e == null) {
                            Toast.makeText(Admin.this, "问题添加成功", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
    }
}
