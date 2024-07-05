package com.example.project1test;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.util.Objects;

public class MainActivity3 extends AppCompatActivity {
    TextView score,topnickname,getavgval,quizname;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main3);

        Toolbar toolbar=findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        int score=getIntent().getIntExtra("Score",0);

        this.score= findViewById(R.id.ScoreView);
        this.quizname=findViewById(R.id.textView6);
        this.getavgval=findViewById(R.id.textView12);
        this.topnickname=findViewById(R.id.textView11);

        String res= (score+"/5");
        Log.d("LOOOOL", "onCreate: "+res);
        this.score.setText(res);

        this.topnickname.setText(User.nickname);

        this.quizname.setText("Your average for "+QuizTry.quizName+":");


        FirebaseUser currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser() ;
        assert currentFirebaseUser != null;
        DatabaseReference myRef=FirebaseDatabase.getInstance().getReference("Users").child(currentFirebaseUser.getUid()).child("Attempts");
        // Read from the database
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                // Add all previous scores corresponding to the clicked quiz and find the average score /5
                int sum=0,i=0;
                for(DataSnapshot ds:dataSnapshot.getChildren()){
                    if(Objects.equals(ds.child("quizName").getValue(String.class), QuizTry.quizName)){
                        sum+=(int)ds.child("score").getValue(Integer.class);
                        i++;
                    }
                }
                String ans= String.valueOf(sum/i);
                getavgval.setText(ans+"/5");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Failed to read value
                Log.w("TAG", "Failed to read value.", error.toException());
            }
        });

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