package com.example.project1test.ui;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.project1test.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity3 extends AppCompatActivity {
    TextView topnickname;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main3);

        Toolbar toolbar=findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        int score=getIntent().getIntExtra("Score",0);
        //Log.d("ACT#", "onCreate score: "+score);

        TextView t= findViewById(R.id.ScoreView);
        String res= (score+"/5");
        t.setText(res);
        this.topnickname=(TextView) findViewById(R.id.textView11);
        this.topnickname.setText(User.nickname);


        /*ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });*/

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
            //finish();
        }
        else if (item.getItemId()==R.id.navigation_dashboard) {
            Intent i=new Intent(getApplicationContext(), MainActivity3.class);
            startActivity(i);
            //finish();
        } else if (item.getItemId()==R.id.navigation_logout) {
            //todo ajoute le logout de firebase!!!!!!!!!!!!!!!!
            FirebaseAuth.getInstance().signOut();
            Intent i=new Intent(getApplicationContext(), LoginForm.class);
            startActivity(i);
            //finish();
        }
        return super.onOptionsItemSelected(item);
    }
}