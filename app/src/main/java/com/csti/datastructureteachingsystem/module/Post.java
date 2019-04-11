package com.csti.datastructureteachingsystem.module;

import java.util.List;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobFile;

public class Post extends BmobObject {
    private String mTitle;
    private String mContent;
    private List<BmobFile> mImages;
    private Person mAuthor;

    public Post(String title, String content, List<BmobFile> images, Person author) {
        mTitle = title;
        mContent = content;
        mImages = images;
        mAuthor = author;
    }

    public Post(String title, String content, Person author) {
        mTitle = title;
        mContent = content;
        mAuthor = author;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public String getContent() {
        return mContent;
    }

    public void setContent(String content) {
        mContent = content;
    }

    public List<BmobFile> getImages() {
        return mImages;
    }

    public void setImages(List<BmobFile> images) {
        mImages = images;
    }

    public Person getAuthor() {
        return mAuthor;
    }

    public void setAuthor(Person author) {
        mAuthor = author;
    }
}
