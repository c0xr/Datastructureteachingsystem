package com.ml.datastructureteachingsystem.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ml.datastructureteachingsystem.R;

import java.util.List;
//对应我的贴子。。
public abstract class SingleRecyclerViewActivity<T> extends AppCompatActivity {

    protected RecyclerView mRecyclerView;//列表
    protected SingleRecyclerViewActivity.ItemAdapter mAdapter;//列表适配器对象
    protected List<T> mItems;//存每个列表信息的List
//存每个帖子，并显示，可以点击，每个帖子对应一个（内部类）
    protected class ItemHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        protected TextView mTitle;
        protected TextView mDelete;
//构造方法
        public ItemHolder(LayoutInflater inflater, ViewGroup parent){
            super(inflater.inflate(R.layout.item_my_post_list,parent,false));
            mTitle=itemView.findViewById(R.id.item_tilte);
            mDelete=itemView.findViewById(R.id.item_delete);
        }
//绑定数据，自动调用
        public void bind(int position){
            SingleRecyclerViewActivity.this.bind(mTitle,mDelete,position);
            itemView.setOnClickListener(this);
            itemView.setTag(position);
        }
//点击事件，点击显示
        @Override
        public void onClick(View v) {
            int position=(int)itemView.getTag();
            SingleRecyclerViewActivity.this.onClick(position);
        }
    }
//适配器，每个列表对应一个
    protected class ItemAdapter extends RecyclerView.Adapter<SingleRecyclerViewActivity.ItemHolder>{

        @NonNull
        @Override//创建的时候调用
        public SingleRecyclerViewActivity.ItemHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            LayoutInflater inflater=LayoutInflater.from(SingleRecyclerViewActivity.this);
            return new SingleRecyclerViewActivity.ItemHolder(inflater,viewGroup);
        }
//绑定
        @Override
        public void onBindViewHolder(@NonNull SingleRecyclerViewActivity.ItemHolder itemHolder, int i) {
            itemHolder.bind(i);
        }
//获得博客数量
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
//负责显示内容，设置适配器
    protected void updateUI(){
        if(mAdapter==null){
            mAdapter=new SingleRecyclerViewActivity.ItemAdapter();
            mRecyclerView.setAdapter(mAdapter);
        }else{
            mAdapter.notifyDataSetChanged();
        }
    }

    protected abstract void getList();

    protected abstract void bind(TextView title,TextView delete,int position);

    protected abstract void onClick(int position);
}
