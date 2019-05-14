package com.csti.datastructureteachingsystem.activity;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.csti.datastructureteachingsystem.R;
import com.csti.datastructureteachingsystem.handler.AuthorLoader;
import com.csti.datastructureteachingsystem.handler.AvatarLoader;
import com.csti.datastructureteachingsystem.handler.ImageLoader;
import com.csti.datastructureteachingsystem.module.User;
import com.csti.datastructureteachingsystem.module.Post;
import com.csti.datastructureteachingsystem.module.Reply;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;

import static com.csti.datastructureteachingsystem.helper.SystemHelper.print;
import static com.csti.datastructureteachingsystem.helper.SystemHelper.toast;

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
    private int mReplyCount=1;
    private ImageView mAvatar;
    private ScrollView mScrollView;
    private TextView mDelete;

    public static Intent newIntent(Context packageContext,Post post){
        Intent intent=new Intent(packageContext,PostActivity.class);
        intent.putExtra(EXTRA_POST,post);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);

        mPost=(Post)getIntent().getSerializableExtra(EXTRA_POST);//传帖子对象

        mTitle=findViewById(R.id.item_tilte);
        mNick=findViewById(R.id.nick);
        mContent=findViewById(R.id.content);
        mPostContianer=findViewById(R.id.post_container);
        mReplyContent=findViewById(R.id.reply_content);
        mReplyButton=findViewById(R.id.reply);
        mReplyContainer=findViewById(R.id.reply_contianer);
        mAvatar=findViewById(R.id.avatar);
        mScrollView=findViewById(R.id.scroll_view);
        mDelete=findViewById(R.id.delete);

        mImages=new ArrayList<>();//初始化图片
        final LayoutInflater inflater=getLayoutInflater();//填充图片的填充器

        if(mPost.getImages()!=null) {
            int i = 3;
            for (BmobFile bmobFile : mPost.getImages()) {
                LinearLayout root = (LinearLayout) inflater.inflate(R.layout.image_layout, mPostContianer);
                ImageView imageView = (ImageView) root.getChildAt(i++);
                mImages.add(imageView);
                new ImageLoader(imageView, bmobFile.getUrl()).load();
            }
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
                        addReplyView(inflater,list.get(i),false);
                    }
                }
            }
        });

        mTitle.setText(mPost.getTitle());
        mContent.setText(mPost.getContent());
        new AuthorLoader(mNick,mPost.getAuthor()).load();
        new AvatarLoader(mAvatar,mPost.getAuthor()).load();

        mReplyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!mReplyContent.getText().toString().equals("")) {
                    User user=BmobUser.getCurrentUser(User.class);
                    Reply reply = new Reply(mPost, mReplyContent.getText().toString(),user);
                    addReplyView(inflater,reply,true);
                    reply.save(new SaveListener<String>() {
                        @Override
                        public void done(String s, BmobException e) {
                            if (e == null) {
                                print("reply success");
                            } else {
                                print("reply fail:" + e);
                            }
                        }
                    });
                    mReplyContent.setText("");
                }
            }
        });


        if(mPost.getAuthor().getObjectId().equals(BmobUser.getCurrentUser(User.class).getObjectId())) {
            mDelete.setVisibility(View.VISIBLE);
            mDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Post post = new Post();
                    post.setObjectId(mPost.getObjectId());
                    post.delete(new UpdateListener() {
                        @Override
                        public void done(BmobException e) {
                            if (e == null) {
                                toast("删除成功", PostActivity.this);
                                finish();
                            } else {
                                print(e);
                                toast("删除失败", PostActivity.this);
                            }

                        }
                    });
                }
            });
        }
    }
    //在容器添加回复
    public void addReplyView(LayoutInflater inflater,Reply reply,boolean isScroll){
        LinearLayout root=(LinearLayout)inflater.inflate(R.layout.reply_layout,mReplyContainer);

        if(isScroll) {
            waitForScroll(root);
        }

        LinearLayout parent=((LinearLayout)root.getChildAt(mReplyCount++));
        TextView replyNick=parent.findViewById(R.id.nick);
        User author=reply.getAuthor();
        if(author.getUsername()==null){
            new AuthorLoader(replyNick,author).load();
        }else{
            replyNick.setText(author.getUsername());
        }
        TextView replyContent=parent.findViewById(R.id.content);
        replyContent.setText(reply.getContent());
        ImageView replyAvatar=parent.findViewById(R.id.avatar);
        new AvatarLoader(replyAvatar,reply.getAuthor()).load();
    }
//滚动函数
    private void waitForScroll(final LinearLayout linearLayout){
        final ViewTreeObserver vto=linearLayout.getViewTreeObserver();
        vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                vto.removeOnGlobalLayoutListener(this);
                mScrollView.fullScroll(View.FOCUS_DOWN);
            }
        });
    }
}