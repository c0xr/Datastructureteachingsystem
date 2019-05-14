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
import com.csti.datastructureteachingsystem.module.Similarity;
import com.csti.datastructureteachingsystem.module.User;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

public class KnowledgeList extends AppCompatActivity {
    static List<Knowledge> mlist = new ArrayList<>();
    List<Knowledge> mlist1 = new ArrayList<>();
    ListView listView;
    EditText find;
    KnowledgeAdapter adapter;
    static Similarity[] similarity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_knowledge_list);
        listView = findViewById(R.id.knowledgeList);
        find = findViewById(R.id.find);
        getList();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Knowledge knowledge;
                if (mlist1.size() == 0) {
                    knowledge = mlist.get(position);
                }else {
                    knowledge=mlist1.get(position);
                }
                Intent intent = new Intent(KnowledgeList.this, KnowledgeItem.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("know", knowledge);
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
                //adapter.changeText(s.toString());
                if (s.toString().equals("")) {
                    mlist1.clear();
                    adapter = new KnowledgeAdapter(KnowledgeList.this, R.layout.knowledge_item, mlist);
                    listView.setAdapter(adapter);
                } else {
                    similarity = new Similarity[mlist.size()];
                    mlist1.clear();
                    for (int i = 0; i < mlist.size(); i++) {
                        similarity[i] = new Similarity();
                        similarity[i].setSim(levenshtein(s.toString(), mlist.get(i).getTitle()));
                        similarity[i].knowledge = mlist.get(i);
                    }
                    for (int i = 0; i < similarity.length; i++) {
                        for (int j = 0; j < similarity.length - i - 1; j++) {
                            if (similarity[j].sim <= similarity[j + 1].sim) {
                                Similarity temp = similarity[j];
                                similarity[j] = similarity[j + 1];
                                similarity[j + 1] = temp;
                            }
                        }
                    }
                    mlist1.add(similarity[0].knowledge);
                    mlist1.add(similarity[1].knowledge);
                    mlist1.add(similarity[2].knowledge);
                    adapter = new KnowledgeAdapter(KnowledgeList.this, R.layout.knowledge_item, mlist1);
                    listView.setAdapter(adapter);
                }
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
                mlist = list;
                adapter = new KnowledgeAdapter(KnowledgeList.this, R.layout.knowledge_item, list);
                listView.setAdapter(adapter);
            }
        });
    }


    public static float levenshtein(String str1, String str2) {
        //计算两个字符串的长度。
        int len1 = str1.length();
        int len2 = str2.length();
        //建立上面说的数组，比字符长度大一个空间
        int[][] dif = new int[len1 + 1][len2 + 1];
        //赋初值，步骤B。
        for (int a = 0; a <= len1; a++) {
            dif[a][0] = a;
        }
        for (int a = 0; a <= len2; a++) {
            dif[0][a] = a;
        }
        //计算两个字符是否一样，计算左上的值
        int temp;
        for (int i = 1; i <= len1; i++) {
            for (int j = 1; j <= len2; j++) {
                if (str1.charAt(i - 1) == str2.charAt(j - 1)) {
                    temp = 0;
                } else {
                    temp = 1;
                }
                //取三个值中最小的
                dif[i][j] = min(dif[i - 1][j - 1] + temp, dif[i][j - 1] + 1,
                        dif[i - 1][j] + 1);
            }
        }
        System.out.println("字符串\"" + str1 + "\"与\"" + str2 + "\"的比较");
        //取数组右下角的值，同样不同位置代表不同字符串的比较
        System.out.println("差异步骤：" + dif[len1][len2]);
        //计算相似度
        float similarity = 1 - (float) dif[len1][len2] / Math.max(str1.length(), str2.length());
        System.out.println("相似度：" + similarity);
        return similarity;

    }

    //得到最小值
    private static int min(int... is) {
        int min = Integer.MAX_VALUE;
        for (int i : is) {
            if (min > i) {
                min = i;
            }
        }
        return min;
    }
}
