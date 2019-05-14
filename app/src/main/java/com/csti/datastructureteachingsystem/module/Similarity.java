package com.csti.datastructureteachingsystem.module;

public class Similarity {
    public Knowledge knowledge;
    public float sim;
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
