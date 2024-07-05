package com.example.project1test;

import static android.app.PendingIntent.getActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.project1test.ui.MainActivity2;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;

public class PickQuiz extends AppCompatActivity {
    TextView topnickname;
    Button quizname;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        Toolbar toolbar=findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        this.topnickname=findViewById(R.id.textView8);
        //this.quizname=findViewById(R.id.button4);

        this.topnickname.setText(User.nickname);

    }

    //onClick method to start the Trivia quiz
    public void startTQ(View V){
        //Get the name of the chosen quiz;
        QuizTry.quizName=((TextView)V).getText().toString();
        Question.qall=null;
        Question.qall=new Question[]{
                new Question("What element does 'O' represent on the periodic table?","Oxygen", new String[]{"OBlock","Oxygen","OxyClean","Oh Woww"}),
                new Question("What is your body's largest organ?","Skin",new String[]{"Heart","Brain","Stomach","Skin"}),
                new Question("Who is attributed with discovering gravity?","Newton",new String[]{"Newton","Galileo","Einstein","Darwin"}),
                new Question("What is the largest land animal?","African elephant",new String[]{"Lion","Rhino","African elephant","Hippo"}),
                new Question("How many stripes are there on the US flag?","13",new String[]{"51","13","14","19"})
        };
        Intent i=new Intent(getApplicationContext(), Quizzes.class);
        startActivity(i);
        finish();
    }

    //onClick method to start the Geography quiz
    public void startGQ(View V){
        //Get the name of the chosen quiz;
        QuizTry.quizName=((TextView)V).getText().toString();
        Question.qall=null;
        Question.qall=new Question[]{
                new Question("Which country has the largest population in the world??","China", new String[]{"USA","Antarctica","China","India"}),
                new Question("What is the largest US state?","Alaska",new String[]{"Alaska","California","Texas","Idaho"}),
                new Question("How many countries are there in the United Kingdom?","4",new String[]{"3","5","1","4"}),
                new Question("What is the name of the smallest country in the world?","Vatican City",new String[]{"Vatican City","Luxembourg","Madagascar","Denmark"}),
                new Question("What country has the most natural lakes?","Canada",new String[]{"Mexico","Brazil","Russia","Canada"})
        };
        Intent i=new Intent(getApplicationContext(),Quizzes.class);
        startActivity(i);
        finish();
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
}