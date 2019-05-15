package com.ml.datastructureteachingsystem.module;

public class Similarity {
    public Knowledge knowledge;//知识点
    public float sim;//相似度
    public Similarity(){
        knowledge=null;
        sim=-1;
    }

    public float getSim() {
        return sim;
    }

    public void setKnowledge(Knowledge knowledge) {
        this.knowledge = knowledge;
    }

    public Knowledge getKnowledge() {
        return knowledge;
    }

    public void setSim(float sim) {
        this.sim = sim;
    }
}
