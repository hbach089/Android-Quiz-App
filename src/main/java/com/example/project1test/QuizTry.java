package com.example.project1test;

public class QuizTry {
    private int score;
    private String timestamp;
    public static String quizName;

    public QuizTry(int score,String quizName,String timestamp){
        this.score=score;
        QuizTry.quizName =quizName;
        this.timestamp=timestamp;
    }

    public int getScore() {
        return score;
    }
    public void setScore(int score){
        this.score=score;
    }

    public String getQuizName(){
        return quizName;
    }
    public void setQuizName(String quizName){
        QuizTry.quizName =quizName;
    }
    public String getTimestamp(){
        return this.timestamp;
    }
    public void setTimestamp(String timestamp){
        this.timestamp=timestamp;
    }
}
