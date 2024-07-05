package com.example.project1test.ui;

public class User {
    public static String email;

    public static String nickname;

    public User(){

    }
    public User(String email,String nickname){
        User.email =email;
        User.nickname =nickname;
    }

    public String getEmail() {
        return email;
    }


    public String getNickname() {
        return User.nickname;
    }
    public void setNickname(String nickname){
        User.nickname=nickname;
    }
/*
    public void setNom(String nom){
            this.nom=nom;
    }
    public String getNom(){
        return nom;
    }
    */
    public static String cleanupEmail(String email){
        return email.replace(".","").replace("#","").replace("$","").replace("[","").replace("]","").replace("/","");
    }
}
