package com.csti.datastructureteachingsystem.module;

import cn.bmob.v3.BmobObject;

public class Reply extends BmobObject {
    private Post mPost;
    private String mContent;

    public Reply(Post post, String content) {
        mPost = post;
        mContent = content;
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
}
