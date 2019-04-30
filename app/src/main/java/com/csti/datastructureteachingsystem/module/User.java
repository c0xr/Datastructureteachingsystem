package com.csti.datastructureteachingsystem.module;

import java.io.Serializable;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobFile;

public class User extends BmobUser implements Serializable {
    private BmobFile mAvatar;//图片bmob文件1
    private String sex;

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public void setAvatar(BmobFile avatar) {
        this.mAvatar = avatar;
    }

    public BmobFile getAvatar() {
        return mAvatar;
    }
}