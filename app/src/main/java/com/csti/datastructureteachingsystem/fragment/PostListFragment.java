package com.csti.datastructureteachingsystem.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.csti.datastructureteachingsystem.R;
import com.csti.datastructureteachingsystem.activity.PostActivity;
import com.csti.datastructureteachingsystem.module.Post;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

import static com.csti.datastructureteachingsystem.helper.SystemHelper.print;

public class PostListFragment extends Fragment {
    private RecyclerView mRecyclerView;
    private PostAdapter mPostAdapter;
    private List<Post> mPosts;

    public static PostListFragment newInstance() {
        PostListFragment fragment = new PostListFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    private class PostHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView mTitle;
        private TextView mContent;

        public PostHolder(LayoutInflater inflater,ViewGroup parent) {
            super(inflater.inflate(R.layout.list_item_post,parent,false));
            mTitle=itemView.findViewById(R.id.title);
            mContent=itemView.findViewById(R.id.content);
            itemView.setOnClickListener(this);
        }

        public void bind(int position){
            Post post=mPosts.get(position);
            mTitle.setText(post.getTitle());
            mContent.setText(post.getContent());
        }

        @Override
        public void onClick(View v) {
            startActivity(PostActivity.newIntent(getActivity()));
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
        getList();
        if (getArguments() != null) {
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.fragment_post_list, container, false);
        mRecyclerView=v.findViewById(R.id.recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        updateUI();
        return v;
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
        BmobQuery<Post> query=new BmobQuery<>();
        query.findObjects(new FindListener<Post>() {
            @Override
            public void done(List<Post> list, BmobException e) {
                if(e==null){
                    mPosts=list;
                    updateUI();
                    print("success");
                }else{
                    print("fail:"+e);
                }
            }
        });
    }

}
