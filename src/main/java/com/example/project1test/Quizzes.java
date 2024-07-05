package com.example.project1test;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Locale;
import java.util.Objects;

public class Quizzes extends AppCompatActivity {
    int nextq,prevq;
    int qdisp,score=0;
    int nextB,prevB,ans1B,ans2B,ans3B,ans4B;
    int cnt=0;
    TextView questionView;
    QuizTry quizTry;
    String timestamp;
    Button buttonNext,buttonPrev,button1ans,button2ans,button3ans,button4ans,submit;
    TextView topnickname;

    Question[] q =Question.getQall();
    public String[] answers=new String[q.length];
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_geography_quiz);

        Toolbar toolbar=findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        this.topnickname=findViewById(R.id.textView5);
        this.topnickname.setText(User.nickname);

        this.nextq=R.id.nextb;
        this.prevq=R.id.prevb;

        this.qdisp=R.id.textView2;

        this.nextB=R.id.nextb;
        this.prevB=R.id.prevb;

        this.ans1B=R.id.op1;
        this.ans2B=R.id.op2;
        this.ans3B=R.id.op3;
        this.ans4B=R.id.op4;

        this.questionView= findViewById(this.qdisp);
        this.buttonNext=findViewById(this.nextB);
        this.buttonPrev=findViewById(this.prevB);
        this.buttonPrev.setBackgroundColor(Color.MAGENTA);
        this.buttonNext.setBackgroundColor(Color.MAGENTA);

        this.button1ans= findViewById(this.ans1B);
        this.button2ans= findViewById(this.ans2B);
        this.button3ans= findViewById(this.ans3B);
        this.button4ans= findViewById(this.ans4B);

        this.submit= findViewById(R.id.submitbutton);

        this.topnickname= findViewById(R.id.textView5);
        this.topnickname.setText(User.nickname);

        //Set the buttons with the question answer options (start at index 0 of array q)
        this.button1ans.setText((this.q[0].getOptions())[0]);
        this.button2ans.setText((this.q[0].getOptions())[1]);
        this.button3ans.setText((this.q[0].getOptions())[2]);
        this.button4ans.setText((this.q[0].getOptions())[3]);

        //When a button is not clicked, set the it to color magenta
        this.button1ans.setBackgroundColor(Color.MAGENTA);
        this.button2ans.setBackgroundColor(Color.MAGENTA);
        this.button3ans.setBackgroundColor(Color.MAGENTA);
        this.button4ans.setBackgroundColor(Color.MAGENTA);

        //Since we are at index 0, disable the previous button
        this.buttonPrev.setEnabled(false);
        this.questionView.setText(this.q[0].getQs());
    }
    public void GotoNextQ(View V){
        cnt+=1;
        this.buttonPrev.setEnabled(true);

        //clear the button color when going next so it doesn't apply color pattern of the current question
        this.button1ans.setBackgroundColor(Color.MAGENTA);
        this.button2ans.setBackgroundColor(Color.MAGENTA);
        this.button3ans.setBackgroundColor(Color.MAGENTA);
        this.button4ans.setBackgroundColor(Color.MAGENTA);

        //When going to the next question compare answers previous clicked to the button options
        //so to apply the correct pattern as the buttons were clicked
        if(this.answers[cnt]!=null){
            if (this.answers[cnt].equals(this.q[cnt].getOptions()[0])) {
                this.button1ans.setBackgroundColor(Color.CYAN);
                this.button2ans.setBackgroundColor(Color.MAGENTA);
                this.button3ans.setBackgroundColor(Color.MAGENTA);
                this.button4ans.setBackgroundColor(Color.MAGENTA);
            } else if (this.answers[cnt].equals(this.q[cnt].getOptions()[1])) {
                this.button1ans.setBackgroundColor(Color.MAGENTA);
                this.button2ans.setBackgroundColor(Color.CYAN);
                this.button3ans.setBackgroundColor(Color.MAGENTA);
                this.button4ans.setBackgroundColor(Color.MAGENTA);
            } else if (this.answers[cnt].equals(this.q[cnt].getOptions()[2])) {
                this.button1ans.setBackgroundColor(Color.MAGENTA);
                this.button2ans.setBackgroundColor(Color.MAGENTA);
                this.button3ans.setBackgroundColor(Color.CYAN);
                this.button4ans.setBackgroundColor(Color.MAGENTA);
            } else if (this.answers[cnt].equals(this.q[cnt].getOptions()[3])) {
                this.button1ans.setBackgroundColor(Color.MAGENTA);
                this.button2ans.setBackgroundColor(Color.MAGENTA);
                this.button3ans.setBackgroundColor(Color.MAGENTA);
                this.button4ans.setBackgroundColor(Color.CYAN);
            }
        }

        //first keep the next button enabled and then change the button answers (when going next)
        this.button1ans.setText((this.q[cnt].getOptions())[0]);
        this.button2ans.setText((this.q[cnt].getOptions())[1]);
        this.button3ans.setText((this.q[cnt].getOptions())[2]);
        this.button4ans.setText((this.q[cnt].getOptions())[3]);

        //when the cnt becomes greater than number of questions disable the next button
        //otherwise keep it enabled
        if(cnt>=this.q.length-1){
            cnt=this.q.length-1;
            this.questionView.setText(this.q[cnt].getQs());
            V.setEnabled(false);
        }
        else{
            this.questionView.setText(this.q[cnt].getQs());
        }
    }
    public void GotoPrevQ(View V){
        cnt-=1;

        //clear the button color when going back so it doesn't apply color pattern of the current question
        this.button1ans.setBackgroundColor(Color.MAGENTA);
        this.button2ans.setBackgroundColor(Color.MAGENTA);
        this.button3ans.setBackgroundColor(Color.MAGENTA);
        this.button4ans.setBackgroundColor(Color.MAGENTA);

        //When going back to the previous question compare answers previous clicked to the button options
        //so to apply the correct pattern as the buttons were clicked
        if(this.answers[cnt]!=null){
            if (this.answers[cnt].equals(this.q[cnt].getOptions()[0])) {
                this.button1ans.setBackgroundColor(Color.CYAN);
                this.button2ans.setBackgroundColor(Color.MAGENTA);
                this.button3ans.setBackgroundColor(Color.MAGENTA);
                this.button4ans.setBackgroundColor(Color.MAGENTA);
            } else if (this.answers[cnt].equals(this.q[cnt].getOptions()[1])) {
                this.button1ans.setBackgroundColor(Color.MAGENTA);
                this.button2ans.setBackgroundColor(Color.CYAN);
                this.button3ans.setBackgroundColor(Color.MAGENTA);
                this.button4ans.setBackgroundColor(Color.MAGENTA);
            } else if (this.answers[cnt].equals(this.q[cnt].getOptions()[2])) {
                this.button1ans.setBackgroundColor(Color.MAGENTA);
                this.button2ans.setBackgroundColor(Color.MAGENTA);
                this.button3ans.setBackgroundColor(Color.CYAN);
                this.button4ans.setBackgroundColor(Color.MAGENTA);
            } else if (this.answers[cnt].equals(this.q[cnt].getOptions()[3])) {
                this.button1ans.setBackgroundColor(Color.MAGENTA);
                this.button2ans.setBackgroundColor(Color.MAGENTA);
                this.button3ans.setBackgroundColor(Color.MAGENTA);
                this.button4ans.setBackgroundColor(Color.CYAN);
            }
        }

        //first keep the next button enabled and then change the button answers (when going next)
        this.buttonNext.setEnabled(true);
        this.button1ans.setText((this.q[cnt].getOptions())[0]);
        this.button2ans.setText((this.q[cnt].getOptions())[1]);
        this.button3ans.setText((this.q[cnt].getOptions())[2]);
        this.button4ans.setText((this.q[cnt].getOptions())[3]);

        //when the cnt becomes 0 or smaller disable the prev button
        //otherwise keep it enabled
        // TODO: 2024-07-02 verifie c'est quoi t????????????????????????????
        if(cnt<=0){
            cnt=0;
            this.questionView.setText(this.q[cnt].getQs());
            V.setEnabled(false);
        }
        else{
            V.setEnabled(true);
            this.questionView.setText(this.q[cnt].getQs());
        }
    }

    public void AnsweredClicked(View V){
        //For each answer, set the clicked button to cyan color and keep the others to magenta

        if(V.getId()==this.ans1B) {
            this.answers[cnt] = this.button1ans.getText().toString();
            this.button1ans.setBackgroundColor(Color.CYAN);
            this.button2ans.setBackgroundColor(Color.MAGENTA);
            this.button3ans.setBackgroundColor(Color.MAGENTA);
            this.button4ans.setBackgroundColor(Color.MAGENTA);

        }
        else if(this.ans2B==V.getId()) {
            this.answers[cnt] = this.button2ans.getText().toString();
            this.button1ans.setBackgroundColor(Color.MAGENTA);
            this.button2ans.setBackgroundColor(Color.CYAN);
            this.button3ans.setBackgroundColor(Color.MAGENTA);
            this.button4ans.setBackgroundColor(Color.MAGENTA);
        }
        else if (V.getId()==this.ans3B) {
            this.answers[cnt] = this.button3ans.getText().toString();
            this.button1ans.setBackgroundColor(Color.MAGENTA);
            this.button2ans.setBackgroundColor(Color.MAGENTA);
            this.button3ans.setBackgroundColor(Color.CYAN);
            this.button4ans.setBackgroundColor(Color.MAGENTA);
        }
        else if (V.getId()==this.ans4B) {
            this.answers[cnt] = this.button4ans.getText().toString();
            this.button1ans.setBackgroundColor(Color.MAGENTA);
            this.button2ans.setBackgroundColor(Color.MAGENTA);
            this.button3ans.setBackgroundColor(Color.MAGENTA);
            this.button4ans.setBackgroundColor(Color.CYAN);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.bottom_nav_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId()==R.id.navigation_home){
            Intent i=new Intent(getApplicationContext(), PickQuiz.class);
            startActivity(i);
            finish();
        }
        else if (item.getItemId()==R.id.navigation_dashboard) {
            Intent i=new Intent(getApplicationContext(), MainActivity3.class);
            startActivity(i);
            finish();
        } else if (item.getItemId()==R.id.navigation_logout) {
            FirebaseAuth.getInstance().signOut();
            Intent i=new Intent(getApplicationContext(), LoginForm.class);
            startActivity(i);
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @SuppressLint("SimpleDateFormat")
    public void SubmitQ(View V){

        //Before submitting, count the score obtained by comparing user answers in array to correct answers.
        for(int j=0;j<this.answers.length;j++){
            if(Objects.equals(this.answers[j], this.q[j].getQa())){
                this.score+=1;
            }
        }

        //Create object for QuizTry class to insert in realtime database containing score, quiz name and timestamp of completion

        this.timestamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new java.util.Date());
        this.quizTry=new QuizTry(score,QuizTry.quizName,this.timestamp);

        //Insert in database and go to next activity
        FirebaseUser currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser() ;
        assert currentFirebaseUser != null;
        DatabaseReference myRef= FirebaseDatabase.getInstance().getReference("Users").child(currentFirebaseUser.getUid()).child("Attempts");
        myRef.push().setValue(quizTry).addOnCompleteListener(task1 -> {
            Intent i = new Intent(getApplicationContext(), MainActivity3.class);
            i.putExtra("Score", score);
            startActivity(i);
            finish();
        });
    }
}
