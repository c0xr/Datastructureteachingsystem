package com.csti.datastructureteachingsystem.handler;

import android.widget.ImageView;

import com.csti.datastructureteachingsystem.module.User;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.QueryListener;

import static com.csti.datastructureteachingsystem.helper.SystemHelper.print;

public class AvatarLoader extends ImageLoader{
    private User mUser;

    public AvatarLoader(ImageView imageView, User user) {
        super(imageView);
        mUser = user;
    }

    public void load(){
        BmobQuery<User> q=new BmobQuery<>();
        q.getObject(mUser.getObjectId(), new QueryListener<User>() {
            @Override
            public void done(User user, BmobException e) {
                if (e==null) {
                    if(user.getAvatar()!=null) {
                        setUrl(user.getAvatar().getUrl());
                        AvatarLoader.super.load();
                    }
                } else {
                    print("get avatar fail:"+e);
                }
            }
        });
    }

    //Don not adjust
    @Override
    protected void prepareSettingBitmap() {
        mImageView.setImageBitmap(mBitmap);
    }
}
