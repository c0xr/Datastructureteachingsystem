package com.csti.datastructureteachingsystem.fragment;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.csti.datastructureteachingsystem.R;
import com.csti.datastructureteachingsystem.activity.Admin;
import com.csti.datastructureteachingsystem.activity.Compile;
import com.csti.datastructureteachingsystem.activity.KnowledgeList;
import com.csti.datastructureteachingsystem.activity.QuestionItem;
import com.csti.datastructureteachingsystem.activity.QuestionList;
import com.csti.datastructureteachingsystem.module.Question;
import com.csti.datastructureteachingsystem.module.User;

import cn.bmob.v3.BmobUser;

public class HomePageFragment extends Fragment {
    public Button button, button1, button2;

    public static HomePageFragment newInstance() {
        HomePageFragment fragment = new HomePageFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home_page, container, false);
        button1=view.findViewById(R.id.button2);
        button2=view.findViewById(R.id.button3);
        button=view.findViewById(R.id.pushAll);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity(), KnowledgeList.class);
                startActivity(intent);
            }
        });
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity(), QuestionList.class);
                startActivity(intent);
            }
        });
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                User user= BmobUser.getCurrentUser(User.class);
                if(user.getUsername().equals("admin")){
                    Intent intent = new Intent(getActivity(), Admin.class);
                    startActivity(intent);
                }else {
                    AlertDialog.Builder builder=new AlertDialog.Builder(getContext());
                    builder.setTitle("无管理员权限！");
                    builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });
                    builder.show();
                }
            }
        });
        return view;
    }

}
