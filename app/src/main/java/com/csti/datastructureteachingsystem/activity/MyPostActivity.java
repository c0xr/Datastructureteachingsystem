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

public class MyPostActivity extends SingleRecyclerViewActivity<Post> {
    public static Intent newIntent(Context packageContext){
        return new Intent(packageContext,MyPostActivity.class);
    }

    @Override
    protected void getList(){
        final BmobQuery<Post> query=new BmobQuery<>();
        Person user= BmobUser.getCurrentUser(Person.class);
        query.addWhereEqualTo("mAuthor",user);
        query.findObjects(new FindListener<Post>() {
            @Override
            public void done(final List<Post> list, BmobException e) {
                if(e==null){
                    mItems=list;
                    print("get my post list success");
                    for(int i=0;i<list.size();i++){
                        BmobQuery<Person> q2=new BmobQuery<>();
                        final Post post=list.get(i);
                        q2.getObject(post.getAuthor().getObjectId(), new QueryListener<Person>() {
                            @Override
                            public void done(Person person, BmobException e) {
                                post.setAuthor(person);
                                updateUI();
                            }
                        });
                    }
                }else{
                    print("get my post list fail:"+e+" / "+e.getErrorCode());
                }
            }
        });
    }

    @Override
    protected void bind(TextView title, int position) {
        title.setText(mItems.get(position).getTitle());
    }

    @Override
    protected void onClick(int position) {
        startActivity(PostActivity.newIntent(MyPostActivity.this,mItems.get(position)));
    }
}
