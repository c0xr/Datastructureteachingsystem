package com.ml.datastructureteachingsystem.module;

import java.io.Serializable;

import cn.bmob.v3.BmobObject;

public class Question extends BmobObject implements Serializable {
    String Title;
    String question;
    String answer;

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }
}
