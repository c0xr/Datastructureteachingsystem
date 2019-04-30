package com.csti.datastructureteachingsystem.fragment;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.csti.datastructureteachingsystem.R;
import com.csti.datastructureteachingsystem.activity.Compile;
import com.csti.datastructureteachingsystem.activity.KnowledgeList;
import com.csti.datastructureteachingsystem.activity.QuestionList;
import com.csti.datastructureteachingsystem.module.Question;

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
        button=view.findViewById(R.id.button);
        button1=view.findViewById(R.id.button2);
        button2=view.findViewById(R.id.button3);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity(), Compile.class);
                startActivity(intent);
            }
        });
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
        return view;
    }
}
