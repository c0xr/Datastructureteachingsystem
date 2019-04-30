package com.csti.datastructureteachingsystem.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.csti.datastructureteachingsystem.R;
import com.csti.datastructureteachingsystem.module.User;
import com.csti.datastructureteachingsystem.module.Post;
import com.csti.datastructureteachingsystem.module.Reply;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.QueryListener;

import static com.csti.datastructureteachingsystem.helper.SystemHelper.print;

public class MyReplyActivity extends SingleRecyclerViewActivity<Reply> {
    public static Intent newIntent(Context packageContext){
        return new Intent(packageContext,MyReplyActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        TextView title=findViewById(R.id.item_tilte);
        title.setText("我的回复");
    }

    @Override
    protected void getList(){
        final BmobQuery<Reply> query=new BmobQuery<>();
        User user= BmobUser.getCurrentUser(User.class);
        query.addWhereEqualTo("mAuthor",user);
        query.findObjects(new FindListener<Reply>() {
            @Override
            public void done(final List<Reply> list, BmobException e) {
                if(e==null){
                    mItems=list;
                    print("get my reply list success");
                    for(int i=0;i<list.size();i++){
                        final Reply reply=mItems.get(i);
                        BmobQuery<Post> q2=new BmobQuery<>();
                        q2.getObject(reply.getPost().getObjectId(), new QueryListener<Post>() {
                            @Override
                            public void done(final Post post, BmobException e) {
                                BmobQuery<User> q2=new BmobQuery<>();
                                q2.getObject(post.getAuthor().getObjectId(), new QueryListener<User>() {
                                    @Override
                                    public void done(User user, BmobException e) {
                                        reply.setPost(post);
                                        post.setAuthor(user);
                                        updateUI();
                                    }
                                });
                            }
                        });
                    }
                }else{
                    print("get my reply list fail:"+e+" / "+e.getErrorCode());
                }
            }
        });
    }

    @Override
    protected void bind(TextView title, int position) {
        title.setText(mItems.get(position).getContent());
    }

    @Override
    protected void onClick(int position) {
        startActivity(PostActivity.newIntent(MyReplyActivity.this,mItems.get(position).getPost()));
    }
}
