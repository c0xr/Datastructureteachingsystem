package com.ml.datastructureteachingsystem.activity;

import android.content.Context;
import android.content.Intent;
import android.widget.TextView;

import com.ml.datastructureteachingsystem.module.User;
import com.ml.datastructureteachingsystem.module.Post;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.QueryListener;

import static com.ml.datastructureteachingsystem.helper.SystemHelper.print;

public class MyPostActivity extends SingleRecyclerViewActivity<Post> {
    public static Intent newIntent(Context packageContext){
        return new Intent(packageContext,MyPostActivity.class);
    }

    @Override
    protected void getList(){
        final BmobQuery<Post> query=new BmobQuery<>();
        User user= BmobUser.getCurrentUser(User.class);
        query.addWhereEqualTo("mAuthor",user);
        query.findObjects(new FindListener<Post>() {
            @Override
            public void done(final List<Post> list, BmobException e) {
                if(e==null){
                    mItems=list;
                    print("get my post list success");
                    for(int i=0;i<list.size();i++){
                        BmobQuery<User> q2=new BmobQuery<>();
                        final Post post=list.get(i);
                        q2.getObject(post.getAuthor().getObjectId(), new QueryListener<User>() {
                            @Override
                            public void done(User user, BmobException e) {
                                post.setAuthor(user);
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

    @Override
    protected void onResume() {
        super.onResume();
        getList();
    }
}
