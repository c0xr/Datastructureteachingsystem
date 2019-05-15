package com.ml.datastructureteachingsystem.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.ml.datastructureteachingsystem.R;
import com.ml.datastructureteachingsystem.module.Question;

public class QuestionItem extends AppCompatActivity {
    Toolbar toolbar;
    Question question;
    TextView textView;
    EditText answer;
    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question_item);
        toolbar = findViewById(R.id.toolbar3);
        textView=findViewById(R.id.question);
        answer=findViewById(R.id.answer);
        button=findViewById(R.id.ok);
        Intent intent = getIntent();
        question = (Question) intent.getSerializableExtra("question");
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        getSupportActionBar().setTitle(question.getTitle());
        textView.setText(question.getQuestion());
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(answer.getText().toString().equals(question.getAnswer())){
                    ShowDialog("恭喜你答对了！");
                }else{
                    ShowDialog("抱歉，答错了！");
                }
            }
        });

    }
    private void ShowDialog(String title){
        AlertDialog.Builder builder=new AlertDialog.Builder(QuestionItem.this);
        builder.setTitle(title);
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.show();
    }
}
