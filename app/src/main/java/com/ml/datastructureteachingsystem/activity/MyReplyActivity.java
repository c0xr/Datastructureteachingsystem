package com.ml.datastructureteachingsystem.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.TextView;

import com.ml.datastructureteachingsystem.R;
import com.ml.datastructureteachingsystem.module.User;
import com.ml.datastructureteachingsystem.module.Post;
import com.ml.datastructureteachingsystem.module.Reply;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.QueryListener;
import cn.bmob.v3.listener.UpdateListener;

import static com.ml.datastructureteachingsystem.helper.SystemHelper.print;
import static com.ml.datastructureteachingsystem.helper.SystemHelper.toast;

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
    protected void bind(TextView title, TextView delete, final int position) {
        title.setText(mItems.get(position).getContent());
        delete.setVisibility(View.VISIBLE);
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder=new AlertDialog.Builder(MyReplyActivity.this);
                builder.setMessage("删除此回复？")
                        .setPositiveButton("删除", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                Reply reply=new Reply();
                                reply.setObjectId(mItems.get(position).getObjectId());
                                reply.delete(new UpdateListener() {
                                    @Override
                                    public void done(BmobException e) {
                                        if(e==null){
                                            print("delete reply success");
                                            toast("删除成功",MyReplyActivity.this);
                                            mItems.remove(position);
                                            updateUI();
                                        }else{
                                            print("delete reply fail:"+e);
                                            toast("删除失败",MyReplyActivity.this);
                                        }
                                    }
                                });
                            }
                        })
                        .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {

                            }
                        });
                AlertDialog dialog=builder.create();
                dialog.show();
                dialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(getResources().getColor(R.color.delete_button_color));
            }
        });
    }

    @Override
    protected void onClick(int position) {
        startActivity(PostActivity.newIntent(MyReplyActivity.this,mItems.get(position).getPost()));
    }
}
