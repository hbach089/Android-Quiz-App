package com.example.project1test;

import android.util.Log;

public class Question {
    private String qs;
    private String qa;
    private String[] options;
    //public static Question q;
    public static Question[] qall;
    public Question(String qs,String qa,String[] options){
        this.qs=qs;
        this.qa=qa;
        this.options=options;
    }
    public String getQa() {
        return qa;
    }
    public void setQa(String qa){
        this.qa=qa;
    }
    public String getQs() {
        return qs;
    }
    public void setQs(String qs){
        this.qs=qs;
    }

    public String[] getOptions(){
        return this.options;
    }

    public static Question[] getQall(){
        return Question.qall;
    }
}
