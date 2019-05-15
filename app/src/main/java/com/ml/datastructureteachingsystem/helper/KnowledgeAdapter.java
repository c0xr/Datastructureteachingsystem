package com.ml.datastructureteachingsystem.helper;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.ml.datastructureteachingsystem.R;
import com.ml.datastructureteachingsystem.module.Knowledge;

import java.util.List;

public class KnowledgeAdapter extends ArrayAdapter<Knowledge> {
    private int resourceId;
    private String changeStr="";
    private List<Knowledge> mlist;

    public KnowledgeAdapter(Context context, int resource, List<Knowledge> objects) {
        super(context, resource, objects);
        resourceId = resource;
        mlist=objects;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Knowledge knowledge= getItem(position);
        View view;
        if (convertView == null) {
            view = LayoutInflater.from(getContext()).inflate(resourceId, parent, false);
        } else {
            view = convertView;
        }
        TextView textView=view.findViewById(R.id.item_tilte);
        TextView textView1=view.findViewById(R.id.knowledge);
        textView.setText(knowledge.getTitle());
        textView1.setText(knowledge.getKnowledge());
/*
        if(null!=mlist.get(position)&&mlist.get(position).getKnowledge().contains(changeStr)){
            int index=mlist.get(position).getKnowledge().indexOf(changeStr);
            int len=changeStr.length();
            Spanned temp= Html.fromHtml(mlist.get(position).getKnowledge().substring(0,index)+"<font color=#ff0000>"
                    +mlist.get(position).getKnowledge().substring(index,index+len)+"</font>"
                    +mlist.get(position).getKnowledge().substring(index+len,mlist.get(position).getKnowledge().length()));
            textView1.setText(temp);
        }else{
            textView1.setText(mlist.get(position).getKnowledge());
        }
*/
        return view;
    }
    public void changeText(String textstr){
        this.changeStr=textstr;
        notifyDataSetChanged();
    }

}
