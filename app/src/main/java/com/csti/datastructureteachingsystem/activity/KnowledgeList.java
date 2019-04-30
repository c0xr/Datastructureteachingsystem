package com.csti.datastructureteachingsystem.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;

import com.csti.datastructureteachingsystem.R;
import com.csti.datastructureteachingsystem.helper.KnowledgeAdapter;
import com.csti.datastructureteachingsystem.module.Knowledge;
import com.csti.datastructureteachingsystem.module.Post;
import com.csti.datastructureteachingsystem.module.User;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

public class KnowledgeList extends AppCompatActivity {
    static List<Knowledge> mlist = new ArrayList<>();
    ListView listView;
    EditText find;
    KnowledgeAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_knowledge_list);
        listView = findViewById(R.id.knowledgeList);
        find=findViewById(R.id.find);
        getList();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Knowledge knowledge=mlist.get(position);
                Intent intent=new Intent(KnowledgeList.this,KnowledgeItem.class);
                Bundle bundle=new Bundle();
                bundle.putSerializable("know",knowledge);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });


        find.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                adapter.changeText(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }

    private void getList() {
        BmobQuery<Knowledge> query = new BmobQuery<>();
        query.findObjects(new FindListener<Knowledge>() {
            @Override
            public void done(List<Knowledge> list, BmobException e) {
                mlist=list;
                adapter = new KnowledgeAdapter(KnowledgeList.this, R.layout.knowledge_item, list);
                listView.setAdapter(adapter);
            }
        });
    }
}
