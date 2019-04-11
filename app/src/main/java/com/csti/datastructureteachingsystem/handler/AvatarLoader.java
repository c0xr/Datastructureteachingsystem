package com.csti.datastructureteachingsystem.handler;

import android.widget.ImageView;

import com.csti.datastructureteachingsystem.module.Person;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.QueryListener;

public class AvatarLoader extends ImageLoader{
    private Person mUser;

    public AvatarLoader(ImageView imageView, Person user) {
        super(imageView);
        mUser = user;
    }

    public void load(){
        BmobQuery<Person> q=new BmobQuery<>();
        q.getObject(mUser.getObjectId(), new QueryListener<Person>() {
            @Override
            public void done(Person person, BmobException e) {
                if(person.getAvatar()!=null) {
                    setUrl(person.getAvatar().getUrl());
                    AvatarLoader.super.load();
                }
            }
        });
    }
}
