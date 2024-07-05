package com.example.project1test.ui;

public class Question {
    private String qs;
    private String qa;
    private String[] options;
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
        //Log.d("WTFFFFF", "getOptions: "+this.options[0]);
        return this.options;
    }
}
