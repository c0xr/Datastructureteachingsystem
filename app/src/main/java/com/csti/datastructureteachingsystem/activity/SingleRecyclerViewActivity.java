package com.csti.datastructureteachingsystem.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.csti.datastructureteachingsystem.R;

import java.util.List;

public abstract class SingleRecyclerViewActivity<T> extends AppCompatActivity {

    protected RecyclerView mRecyclerView;
    protected SingleRecyclerViewActivity.ItemAdapter mAdapter;
    protected List<T> mItems;

    protected class ItemHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        protected TextView mTitle;

        public ItemHolder(LayoutInflater inflater, ViewGroup parent){
            super(inflater.inflate(R.layout.item_my_post_list,parent,false));
            mTitle=itemView.findViewById(R.id.item_tilte);
        }

        public void bind(int position){
            SingleRecyclerViewActivity.this.bind(mTitle,position);
            itemView.setOnClickListener(this);
            itemView.setTag(position);
        }

        @Override
        public void onClick(View v) {
            int position=(int)itemView.getTag();
            SingleRecyclerViewActivity.this.onClick(position);
        }
    }

    protected class ItemAdapter extends RecyclerView.Adapter<SingleRecyclerViewActivity.ItemHolder>{

        @NonNull
        @Override
        public SingleRecyclerViewActivity.ItemHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            LayoutInflater inflater=LayoutInflater.from(SingleRecyclerViewActivity.this);
            return new SingleRecyclerViewActivity.ItemHolder(inflater,viewGroup);
        }

        @Override
        public void onBindViewHolder(@NonNull SingleRecyclerViewActivity.ItemHolder itemHolder, int i) {
            itemHolder.bind(i);
        }

        @Override
        public int getItemCount() {
            return mItems.size();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_recycler_view);

        mRecyclerView=findViewById(R.id.recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        getList();
    }

    protected void updateUI(){
        if(mAdapter==null){
            mAdapter=new SingleRecyclerViewActivity.ItemAdapter();
            mRecyclerView.setAdapter(mAdapter);
        }else{
            mAdapter.notifyDataSetChanged();
        }
    }

    protected abstract void getList();

    protected abstract void bind(TextView title,int position);

    protected abstract void onClick(int position);
}
