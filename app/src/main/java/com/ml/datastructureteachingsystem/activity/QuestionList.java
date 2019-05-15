package com.ml.datastructureteachingsystem.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.ml.datastructureteachingsystem.R;
import com.ml.datastructureteachingsystem.helper.QuestionAdapter;
import com.ml.datastructureteachingsystem.module.Question;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

public class QuestionList extends AppCompatActivity {
    static List<Question> mlist = new ArrayList<>();
    Toolbar toolbar;
    ListView listView;
    QuestionAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question_list);
        toolbar=findViewById(R.id.toolbar2);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        getSupportActionBar().setTitle("问题列表");
        listView=findViewById(R.id.questionlist);
        getList();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Question question=mlist.get(position);
                Intent intent=new Intent(QuestionList.this,QuestionItem.class);
                Bundle bundle=new Bundle();
                bundle.putSerializable("question",question);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

    }
    private void getList() {
        BmobQuery<Question> query = new BmobQuery<>();
        query.findObjects(new FindListener<Question>() {
            @Override
            public void done(List<Question> list, BmobException e) {
                mlist=list;
                adapter = new QuestionAdapter(QuestionList.this, R.layout.knowledge_item, list);
                listView.setAdapter(adapter);
            }
        });
    }
}
