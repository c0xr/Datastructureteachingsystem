package com.csti.datastructureteachingsystem.module;

import java.io.Serializable;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobFile;

public class Person extends BmobUser implements Serializable {
    private BmobFile mAvatar;//图片bmob文件1

    public void setAvatar(BmobFile avatar) {
        this.mAvatar = avatar;
    }

    public BmobFile getAvatar() {
        return mAvatar;
    }
}