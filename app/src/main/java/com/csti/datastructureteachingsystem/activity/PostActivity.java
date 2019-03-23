package com.csti.datastructureteachingsystem.activity;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.csti.datastructureteachingsystem.R;
import com.csti.datastructureteachingsystem.handler.ImageLoader;
import com.csti.datastructureteachingsystem.module.Post;
import com.csti.datastructureteachingsystem.module.Reply;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;

import static com.csti.datastructureteachingsystem.helper.SystemHelper.print;

public class PostActivity extends AppCompatActivity {
    private final static String EXTRA_POST="post";

    private Post mPost;
    private TextView mTitle;
    private TextView mNick;
    private TextView mContent;
    private LinearLayout mPostContianer;
    private List<ImageView> mImages;
    private EditText mReplyContent;
    private TextView mReplyButton;
    private LinearLayout mReplyContainer;
    private int mReplyCount;

    public static Intent newIntent(Context packageContext,Post post){
        Intent intent=new Intent(packageContext,PostActivity.class);
        intent.putExtra(EXTRA_POST,post);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);

        mPost=(Post)getIntent().getSerializableExtra(EXTRA_POST);

        mTitle=findViewById(R.id.title);
        mNick=findViewById(R.id.nick);
        mContent=findViewById(R.id.content);
        mPostContianer=findViewById(R.id.post_container);
        mReplyContent=findViewById(R.id.reply_content);
        mReplyButton=findViewById(R.id.reply);
        mReplyContainer=findViewById(R.id.reply_contianer);

        mImages=new ArrayList<>();
        final LayoutInflater inflater=getLayoutInflater();

        int i=3;
        for(BmobFile bmobFile:mPost.getImages()){
            LinearLayout root=(LinearLayout) inflater.inflate(R.layout.image_layout,mPostContianer);
            ImageView imageView=(ImageView)root.getChildAt(i++);
            mImages.add(imageView);
            new ImageLoader(imageView,bmobFile.getUrl()).sendEmptyMessage(0);
        }

        BmobQuery<Reply> replyQuery=new BmobQuery<>();
        BmobQuery<Post> postQuery=new BmobQuery<>();
        postQuery.addWhereEqualTo("objectId",mPost.getObjectId());
        replyQuery.addWhereMatchesQuery("mPost","Post",postQuery);
        replyQuery.findObjects(new FindListener<Reply>() {
            @Override
            public void done(List<Reply> list, BmobException e) {
                if(list!=null){
                    for(int i=0;i<list.size();i++){
                        addReplyView(inflater,list.get(i).getContent());
                    }
                }
            }
        });

        mTitle.setText(mPost.getTitle());
        mContent.setText(mPost.getContent());

        mReplyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addReplyView(inflater,mReplyContent.getText().toString());
                Reply reply=new Reply(mPost,mReplyContent.getText().toString());
                reply.save(new SaveListener<String>() {
                    @Override
                    public void done(String s, BmobException e) {
                        if (e == null) {
                            print("reply success");
                        }else {
                            print("reply fail:"+e);
                        }
                    }
                });
            }
        });

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

    }

    public void addReplyView(LayoutInflater inflater,String content){
        LinearLayout root=(LinearLayout)inflater.inflate(R.layout.reply_layout,mReplyContainer);
        TextView textView=(TextView)root.getChildAt(mReplyCount++);
        textView.setText(content);
    }
}