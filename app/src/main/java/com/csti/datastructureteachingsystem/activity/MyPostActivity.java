package com.csti.datastructureteachingsystem.activity;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.csti.datastructureteachingsystem.R;
import com.csti.datastructureteachingsystem.module.Person;
import com.csti.datastructureteachingsystem.module.Post;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.QueryListener;

import static com.csti.datastructureteachingsystem.helper.SystemHelper.print;

public class MyPostActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private ItemAdapter mAdapter;
    private List<Post> mPosts;
    private Person mTempUser;

    private class ItemHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView mTitle;

        public ItemHolder(LayoutInflater inflater, ViewGroup parent){
            super(inflater.inflate(R.layout.item_my_post_list,parent,false));
            mTitle=itemView.findViewById(R.id.title);
        }

        public void bind(int position){
            mTitle.setText(mPosts.get(position).getTitle());

            itemView.setOnClickListener(this);
            itemView.setTag(position);
        }

        @Override
        public void onClick(View v) {
            int positon=(int)itemView.getTag();
            startActivity(PostActivity.newIntent(MyPostActivity.this,mPosts.get(positon)));
        }
    }

    private class ItemAdapter extends RecyclerView.Adapter<ItemHolder>{

        @NonNull
        @Override
        public ItemHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            LayoutInflater inflater=LayoutInflater.from(MyPostActivity.this);
            return new ItemHolder(inflater,viewGroup);
        }

        @Override
        public void onBindViewHolder(@NonNull ItemHolder itemHolder, int i) {
            itemHolder.bind(i);
        }

        @Override
        public int getItemCount() {
            return mPosts.size();
        }
    }

    public static Intent newIntent(Context packageContext){
        return new Intent(packageContext,MyPostActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_post);

        mRecyclerView=findViewById(R.id.recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        getList();
    }

    private void updateUI(){
        if(mAdapter==null){
            mAdapter=new ItemAdapter();
            mRecyclerView.setAdapter(mAdapter);
        }else{
            mAdapter.notifyDataSetChanged();
        }
    }

    private void getList(){
        final BmobQuery<Post> query=new BmobQuery<>();
        Person user= BmobUser.getCurrentUser(Person.class);
        query.addWhereEqualTo("mAuthor",user);
        query.findObjects(new FindListener<Post>() {
            @Override
            public void done(final List<Post> list, BmobException e) {
                if(e==null){
                    mPosts=list;
                    updateUI();
                    print("get post list 2 success");
                    for(int i=0;i<list.size();i++){
                        BmobQuery<Person> q2=new BmobQuery<>();
                        mTempUser=mPosts.get(i).getAuthor();
                        q2.getObject(mTempUser.getObjectId(), new QueryListener<Person>() {
                            @Override
                            public void done(Person person, BmobException e) {
                                mTempUser=person;
                                updateUI();
                            }
                        });
                    }
                }else{
                    print("get post lits 2 fail:"+e+" / "+e.getErrorCode());
                }
            }
        });
    }
}
