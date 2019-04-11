package com.csti.datastructureteachingsystem.module;

import cn.bmob.v3.BmobObject;

public class Reply extends BmobObject {
    private Post mPost;
    private String mContent;
    private Person mAuthor;

    public Reply(Post post, String content, Person author) {
        mPost = post;
        mContent = content;
        mAuthor = author;
    }

    public Post getPost() {
        return mPost;
    }

    public void setPost(Post post) {
        mPost = post;
    }

    public String getContent() {
        return mContent;
    }

    public void setContent(String content) {
        mContent = content;
    }

    public Person getAuthor() {
        return mAuthor;
    }

    public void setAuthor(Person author) {
        mAuthor = author;
    }
}
