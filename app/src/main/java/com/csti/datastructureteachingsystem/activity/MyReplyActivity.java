package com.csti.datastructureteachingsystem.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.csti.datastructureteachingsystem.R;
import com.csti.datastructureteachingsystem.module.Person;
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

        TextView title=findViewById(R.id.title);
        title.setText("我的回复");
    }

    @Override
    protected void getList(){
        final BmobQuery<Reply> query=new BmobQuery<>();
        Person user= BmobUser.getCurrentUser(Person.class);
        query.addWhereEqualTo("mAuthor",user);
        query.findObjects(new FindListener<Reply>() {
            @Override
            public void done(final List<Reply> list, BmobException e) {
                if(e==null){
                    mItems=list;
                    print("get reply list 2 success");
                    for(int i=0;i<list.size();i++){
                        final Reply reply=mItems.get(i);
                        BmobQuery<Post> q2=new BmobQuery<>();
                        q2.getObject(reply.getPost().getObjectId(), new QueryListener<Post>() {
                            @Override
                            public void done(final Post post, BmobException e) {
                                BmobQuery<Person> q2=new BmobQuery<>();
                                q2.getObject(post.getAuthor().getObjectId(), new QueryListener<Person>() {
                                    @Override
                                    public void done(Person person, BmobException e) {
                                        reply.setPost(post);
                                        post.setAuthor(person);
                                        updateUI();
                                    }
                                });
                            }
                        });
                    }
                }else{
                    print("get reply list 2 fail:"+e+" / "+e.getErrorCode());
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
