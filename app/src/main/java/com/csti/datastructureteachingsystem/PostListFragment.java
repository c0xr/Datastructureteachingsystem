package com.csti.datastructureteachingsystem;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class PostListFragment extends Fragment {

    private RecyclerView mRecyclerView;
    private PostAdapter mPostAdapter;

    public static PostListFragment newInstance() {
        PostListFragment fragment = new PostListFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    private class PostHolder extends RecyclerView.ViewHolder{

        public PostHolder(LayoutInflater inflater,ViewGroup parent) {
            super(inflater.inflate(R.layout.list_item_post,parent,false));
        }

        public void bind(){

        }
    }

    private class PostAdapter extends RecyclerView.Adapter<PostHolder>{

        @NonNull
        @Override
        public PostHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
            LayoutInflater inflater=LayoutInflater.from(getActivity());
            return new PostHolder(inflater,parent);
        }

        @Override
        public void onBindViewHolder(@NonNull PostHolder postHolder, int i) {
            postHolder.bind();
        }

        @Override
        public int getItemCount() {
            return 50;
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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

        }
    }

}
