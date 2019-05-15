package com.ml.datastructureteachingsystem.helper;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;


import com.ml.datastructureteachingsystem.R;
import com.ml.datastructureteachingsystem.module.Question;

import java.util.List;

public class QuestionAdapter extends ArrayAdapter<Question> {
    private int resourceId;

    public QuestionAdapter(Context context, int resource, List<Question> objects) {
        super(context, resource, objects);
        resourceId=resource;
    }

    @Override
    public View getView(int position, View convertView,ViewGroup parent) {
       Question question=getItem(position);
        View view;
        if (convertView == null) {
            view = LayoutInflater.from(getContext()).inflate(resourceId, parent, false);
        } else {
            view = convertView;
        }
        TextView textView=view.findViewById(R.id.item_tilte);
        TextView textView1=view.findViewById(R.id.knowledge);
        textView.setText(question.getTitle());
        textView1.setText(question.getQuestion());
        return view;
    }
}
