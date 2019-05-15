package com.ml.datastructureteachingsystem.module;

import cn.bmob.v3.BmobObject;

public class Reply extends BmobObject {
    private Post mPost;
    private String mContent;
    private User mAuthor;

    public Reply(Post post, String content, User author) {
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

    public User getAuthor() {
        return mAuthor;
    }

    public void setAuthor(User author) {
        mAuthor = author;
    }
}
