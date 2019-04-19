package com.csti.datastructureteachingsystem.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.csti.datastructureteachingsystem.R;
import com.csti.datastructureteachingsystem.activity.PostActivity;
import com.csti.datastructureteachingsystem.activity.PostCommitingActivity;
import com.csti.datastructureteachingsystem.handler.AuthorLoader;
import com.csti.datastructureteachingsystem.handler.AvatarLoader;
import com.csti.datastructureteachingsystem.module.User;
import com.csti.datastructureteachingsystem.module.Post;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.QueryListener;

import static com.csti.datastructureteachingsystem.helper.SystemHelper.print;

public class PostListFragment extends Fragment {
    private RecyclerView mRecyclerView;
    private PostAdapter mPostAdapter;
    private List<Post> mPosts;
    private FloatingActionButton mFab;

    public static PostListFragment newInstance() {
        PostListFragment fragment = new PostListFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    private class PostHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView mTitle;
        private TextView mContent;
        private TextView mNick;
        private ImageView mAvatar;

        public PostHolder(LayoutInflater inflater,ViewGroup parent) {
            super(inflater.inflate(R.layout.list_item_post,parent,false));
            mTitle=itemView.findViewById(R.id.title);
            mContent=itemView.findViewById(R.id.content);
            mNick=itemView.findViewById(R.id.nick);
            mAvatar=itemView.findViewById(R.id.avatar);
            itemView.setOnClickListener(this);
        }

        public void bind(int position){
            Post post=mPosts.get(position);
            mTitle.setText(post.getTitle());
            mContent.setText(post.getContent());
            User user=mPosts.get(position).getAuthor();
            new AuthorLoader(mNick,user).load();
            new AvatarLoader(mAvatar,user).load();

            itemView.setTag(position);
        }

        @Override
        public void onClick(View v) {
            startActivity(PostActivity.newIntent(getActivity(),mPosts.get((int)v.getTag())));
        }
    }

    private class PostAdapter extends RecyclerView.Adapter<PostHolder>{

        @NonNull
        @Override
        public PostHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater inflater=LayoutInflater.from(getActivity());
            return new PostHolder(inflater,parent);
        }

        @Override
        public void onBindViewHolder(@NonNull PostHolder postHolder, int position) {
            postHolder.bind(position);
        }

        @Override
        public int getItemCount() {
            return mPosts.size();
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mPosts=new ArrayList<>();
        if (getArguments() != null) {
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.fragment_post_list, container, false);
        mRecyclerView=v.findViewById(R.id.recycler_view);
        mFab=v.findViewById(R.id.post);
        mFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(PostCommitingActivity.newIntent(getActivity()),0);
            }
        });

        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        updateUI();
        return v;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==1){
            getList();
        }
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if(!hidden){
            getList();
        }
    }

    private void updateUI(){
        if (mPostAdapter == null) {
            mPostAdapter=new PostAdapter();
            mRecyclerView.setAdapter(mPostAdapter);
        }else{
            mPostAdapter.notifyDataSetChanged();
        }
    }

    private void getList(){
        final BmobQuery<Post> query=new BmobQuery<>();
        query.findObjects(new FindListener<Post>() {
            @Override
            public void done(final List<Post> list, BmobException e) {
                if(e==null){
                    mPosts=list;
                    print("get post list success");
                    for(int i=0;i<list.size();i++){
                        BmobQuery<User> q2=new BmobQuery<>();
                        final User user=list.get(i).getAuthor();
                        q2.getObject(user.getObjectId(), new QueryListener<User>() {
                            @Override
                            public void done(User user, BmobException e) {
                                user.setUsername(user.getUsername());
                                updateUI();
                            }
                        });
                    }
                }else{
                    print("get post list fail:"+e+" / "+e.getErrorCode());
                }
            }
        });
    }

}
