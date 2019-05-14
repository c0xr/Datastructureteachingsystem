package com.csti.datastructureteachingsystem.handler;

import android.os.Handler;
import android.os.Message;
import android.widget.TextView;

import com.csti.datastructureteachingsystem.module.User;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.QueryListener;

import static com.csti.datastructureteachingsystem.helper.SystemHelper.print;

public class AuthorLoader extends Handler {
    private TextView mNick;
    private User mUser;

    public AuthorLoader(TextView nick, User user) {
        mNick = nick;
        mUser = user;
    }

    @Override
    public void handleMessage(Message msg) {
        super.handleMessage(msg);

        mNick.setText(mUser.getUsername());
    }

    public void load(){
        new Thread(){
            @Override
            public void run() {
                BmobQuery<User> q=new BmobQuery<>();
                q.getObject(mUser.getObjectId(), new QueryListener<User>() {
                    @Override
                    public void done(User user, BmobException e) {
                        if(e==null){
                            mUser= user;
                            AuthorLoader.this.sendEmptyMessage(0);
                        }else{
                            print("set nick fail,userID:"+mUser.getObjectId()+"\tE:"+e);
                        }

                    }
                });
            }
        }.start();
    }
}
