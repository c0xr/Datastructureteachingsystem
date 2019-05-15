package com.ml.datastructureteachingsystem.module;

import java.io.Serializable;

import cn.bmob.v3.BmobObject;

public class Knowledge extends BmobObject implements Serializable {
    String Title;
    String knowledge;

    public Knowledge() {
    }

    public Knowledge(String tile, String knowledge) {
        this.Title = tile;
        this.knowledge = knowledge;
    }

    public String getKnowledge() {
        return knowledge;
    }

    public void setKnowledge(String knowledge) {
        this.knowledge = knowledge;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }
}
