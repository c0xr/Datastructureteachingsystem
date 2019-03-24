package com.csti.datastructureteachingsystem.module;

import java.io.Serializable;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobFile;

public class Person extends BmobUser implements Serializable {
    private BmobFile mheadphoto;//图片bmob文件1

    public void setMheadphoto(BmobFile mheadphoto) {
        this.mheadphoto = mheadphoto;
    }

    public BmobFile getMheadphoto() {
        return mheadphoto;
    }
}